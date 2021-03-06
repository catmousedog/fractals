package me.catmousedog.fractals.workers;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.catmousedog.fractals.data.Field;
import me.catmousedog.fractals.main.Settings;
import me.catmousedog.fractals.paneloperators.filters.Filter;
import me.catmousedog.fractals.paneloperators.fractals.Fractal;
import me.catmousedog.fractals.paneloperators.functions.Function;

/**
 * A class used for managing the global {@link Generator} and {@link Painter}.
 * This class is the only way to access the <code>Generator</code> and
 * <code>Painter</code> class other than inheritance.
 * <p>
 * <b>NOTE:</b> Always make sure the parameters given to a method match the
 * documentation. The objects can either be clones or references.<br>
 * A reference to a <code>Fractal</code> is not the same as its clone!
 */
public class RenderWorker {

	/**
	 * The singleton instance of this class.
	 */
	@NotNull
	private final static RenderWorker RENDER = new RenderWorker();

	/**
	 * @return the {@link RenderWorker#RENDER}
	 */
	@NotNull
	public static RenderWorker getInstance() {
		return RENDER;
	}

	private RenderWorker() {
		scheduled_workers = Settings.getInstance().isScheduled_workers();
	}

	private final Logger logger = Logger.getLogger("fractals");

	/**
	 * The boolean from {@link Settings#isScheduled_workers()}.
	 */
	private boolean scheduled_workers;

	/**
	 * A <code>boolean</code> to determine if a renderer is working. <br>
	 * If a renderer is working no other <code>generators</code> or
	 * <code>painters</code> can be scheduled until it has finished.
	 */
	private boolean rendering = false;

	/**
	 * The <code>Generator</code> that is currently in use. This could either be
	 * generating or already finished.
	 * <p>
	 * Only null at startup.
	 */
	@Nullable
	private Generator currentGenerator;

	/**
	 * True if a new <code>generator</code> can be started. <br>
	 * There are still other factors that might prohibit this from happening.
	 * 
	 */
	private boolean generatorReady = true;

	/**
	 * The <code>Generator</code> that is scheduled after the
	 * {@link RenderWorker#currentGenerator}. <br>
	 * This can either be waiting for execution or <code>null</code>.
	 */
	@Nullable
	private Generator scheduledGenerator;

	/**
	 * The <code>Painter</code> that is currently in use. This could either be
	 * painting or already finished.
	 * <p>
	 * Only null at startup.
	 */
	@Nullable
	private Painter currentPainter;

	/**
	 * True if a new <code>painter</code> can be started. <br>
	 * There are still other factors that might prohibit this from happening.
	 */
	private boolean painterReady = true;

	/**
	 * The <code>Painter</code> that is scheduled after the
	 * {@link RenderWorker#currentPainter}. <br>
	 * This can either be waiting for execution or <code>null</code>.
	 */
	@Nullable
	private Painter scheduledPainter;

	/**
	 * Queues a new <code>renderer</code>.<br>
	 * If a <code>generator</code> is already working this will schedule to work
	 * next.
	 * <p>
	 * The scheduled <code>renderer</code> will block any new
	 * <code>generators</code> or <code>painters</code> that try to schedule until
	 * it has finished.
	 * 
	 * @param field    a reference to the <code>Field</code> that will be used to
	 *                 generate and paint.
	 * @param fractal  a clone of the <code>Fractal</code>.
	 * @param runnable a <code>Runnable</code> ran when the painter has finished.
	 *                 <br>
	 *                 This must call {@link RenderWorker#runScheduledGenerator()}
	 *                 and {@link RenderWorker#runScheduledPainter()} when a new
	 *                 <code>generator</code> and <code>painter</code> can be
	 *                 started.
	 */
	public synchronized void newRender(@NotNull Field field, @NotNull Fractal fractal, @NotNull Runnable runnable) {
		logger.log(Level.FINEST, "RenderWorker.newRender");

		rendering = true; // block new generators and painters

		// This does not check if there are any painters active when starting the
		// generator. This shouldn't cause too much trouble as the render request will
		// repaint anyway. Worst case scenario is that the user will see some artifacts
		// whilst this render request is working.
		Function function = fractal.getFunction();
		Filter filter = function.getFilter();
		generator(field, fractal, function, () -> painter(field, filter, () -> {
			runnable.run();
			rendering = false;
		}, true), true);
	}

	/**
	 * Queues a new <code>generator</code>.<br>
	 * If a <code>generator</code> is already working this will schedule to work
	 * next.
	 * <p>
	 * The scheduled <code>generator</code> can be overridden if another
	 * <code>generator</code> schedules before this one gets to execute.
	 * 
	 * @param field    the reference to the <code>Field</code>.
	 * @param fractal  a clone of the <code>Fractal</code>.
	 * @param runnable a <code>Runnable</code> ran when the generator has finished.
	 *                 <br>
	 *                 This must call {@link RenderWorker#runScheduledGenerator()}
	 *                 when a new <code>generator</code> can be started.
	 */

	public synchronized void newGenerator(@NotNull Field field, @NotNull Fractal fractal, @NotNull Runnable runnable) {
		logger.log(Level.FINEST, "RenderWorker.newGenerator");
		generator(field, fractal, fractal.getFunction(), runnable, false);
	}

	/**
	 * Starts a <code>generator</code> or schedules it if there's already one
	 * working.
	 * 
	 * @param field        a reference to the <code>Field</code> that will be used
	 *                     to generate and paint.
	 * @param fractal      a clone of the <code>Fractal</code>.
	 * @param function     a clone of the <code>Function</code>.
	 * @param runnable     a <code>Runnable</code> ran when the generator has
	 *                     finished. <br>
	 *                     This must call
	 *                     {@link RenderWorker#runScheduledGenerator()} when a new
	 *                     <code>Generator</code> can be started.
	 * @param ignoreRender if true it will not be blocked by the active renderer.
	 */
	private synchronized void generator(@NotNull Field field, @NotNull Fractal fractal, @NotNull Function function,
			@NotNull Runnable runnable, boolean ignoreRender) {
		if (generatorReady) {
			generatorReady = false;
			currentGenerator = new Generator(field, fractal, function, runnable);
			currentGenerator.execute();
		} else if (ignoreRender || !rendering) {
			scheduledGenerator = new Generator(field, fractal, function, runnable);
		}
	}

	/**
	 * Queues a new <code>painter</code>.<br>
	 * If a <code>painter</code> is already working this will schedule to work next.
	 * <p>
	 * The scheduled <code>painter</code> can be overridden if another
	 * <code>painter</code> schedules before this one gets to execute.
	 * 
	 * @param field    the reference to the <code>Field</code>.
	 * @param filter   a clone of the <code>Filter</code>.
	 * @param runnable a <code>Runnable</code> ran when the painter has finished.
	 *                 <br>
	 *                 This must call {@link RenderWorker#runScheduledPainter()}
	 *                 when a new <code>painter</code> can be started.
	 */
	public synchronized void newPainter(@NotNull Field field, @NotNull Filter filter, @NotNull Runnable runnable) {
		logger.log(Level.FINEST, "RenderWorker.newPainter");
		painter(field, filter, runnable, false);
	}

	/**
	 * Starts a <code>painter</code> or schedules it if one there's already one
	 * working.
	 * 
	 * @param field        the reference to the <code>Field</code>.
	 * @param fractal      a clone of the <code>Filter</code>.
	 * @param runnable     a <code>Runnable</code> ran when the painter has
	 *                     finished. <br>
	 *                     This must call {@link RenderWorker#runScheduledPainter()}
	 *                     when a new <code>painter</code> can be started.
	 * @param ignoreRender if true it will not be blocked by the active renderer.
	 */
	private synchronized void painter(@NotNull Field field, @NotNull Filter filter, @NotNull Runnable runnable,
			boolean ignoreRender) {
		if (painterReady) {
			painterReady = false;
			currentPainter = new Painter(field, filter, runnable);
			currentPainter.execute();
		} else if (ignoreRender || !rendering) {
			scheduledPainter = new Painter(field, filter, runnable);
		}
	}

	/**
	 * Attempt to run the {@link RenderWorker#scheduledGenerator}. <br>
	 * This can only work if the {@link RenderWorker#currentGenerator} is ready and
	 * a <code>scheduledGenerator</code> exists. If there is no
	 * <code>scheduledGenerator</code> the {@link RenderWorker#generatorReady} will
	 * be set to true so a new <code>generator</code> can be started.
	 * <p>
	 * This should only be run when the <code>currentGenerator</code> has finished.
	 */
	public synchronized void runScheduledGenerator() {
		logger.log(Level.FINEST, "RenderWorker.runScheduledGenerator");
		if (scheduled_workers && scheduledGenerator != null) {
			generatorReady = false;
			currentGenerator = scheduledGenerator;
			scheduledGenerator = null;
			currentGenerator.execute();
		} else {
			generatorReady = true;
		}
	}

	/**
	 * Attempt to run the {@link RenderWorker#scheduledPainter}. <br>
	 * This can only work if the {@link RenderWorker#currentPainter} is ready and a
	 * <code>scheduledPainter</code> exists. If there is no
	 * <code>scheduledPainter</code> the {@link RenderWorker#painterReady} will be
	 * set to true so a new <code>painter</code> can be started.
	 * <p>
	 * This should only be run when the <code>currentPainter</code> has finished.
	 */
	public synchronized void runScheduledPainter() {
		logger.log(Level.FINEST, "RenderWorker.runScheduledPainter");
		if (scheduled_workers && scheduledPainter != null) {
			painterReady = false; // should already be false
			currentPainter = scheduledPainter; // recursively start another painter
			scheduledPainter = null;
			currentPainter.execute();
		} else {
			painterReady = true;
		}
	}

	/**
	 * Attempts to cancel the {@link RenderWorker#currentGenerator} and
	 * {@link RenderWorker#currentPainter}.
	 * 
	 * @return {@link Generator#cancel(boolean)} for the
	 *         <code>currentGenerator</code>.
	 */
	public boolean cancel() {
		logger.log(Level.FINEST, "RenderWorker.cancel");
		boolean out = false;
		if (currentGenerator.cancel(true)) {
			scheduledGenerator = null;
			generatorReady = true;
			out = true;
		}
		if (currentPainter.cancel(true)) {
			scheduledPainter = null;
			painterReady = true;
		}
		return out;
	}

	/**
	 * @return true if the <code>generator</code> is finished and there are no
	 *         scheduled <code>generators</code> left.
	 */
	public boolean isGeneratorReady() {
		return generatorReady;
	}

	/**
	 * @return true if there is a <code>scheduledGenerator</code><br>
	 *         false if not scheduled or {@link Settings#isScheduled_workers()} is
	 *         false.
	 */
	public boolean isGeneratorScheduled() {
		return scheduled_workers && scheduledGenerator != null;
	}

	/**
	 * @return true if the <code>painter</code> is finished and there are no
	 *         scheduled <code>painters</code> left.
	 */
	public boolean isPainterReady() {
		return painterReady;
	}

	/**
	 * @return true if there is a <code>scheduledPainter</code><br>
	 *         false if not scheduled or {@link Settings#isScheduled_workers()} is
	 *         false.
	 */
	public boolean isPainterScheduled() {
		return scheduled_workers && scheduledPainter != null;
	}

	/**
	 * Enable or disable the scheduled workers feature.
	 * 
	 * @param scheduled_workers
	 */
	public void setScheduled_workers(boolean scheduled_workers) {
		this.scheduled_workers = scheduled_workers;
	}

}
