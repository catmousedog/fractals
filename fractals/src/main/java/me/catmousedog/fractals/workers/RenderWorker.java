package me.catmousedog.fractals.workers;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.catmousedog.fractals.canvas.Field;
import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.fractals.filters.Filter;

/**
 * A class used for managing the global {@link Generator} and {@link Painter}.
 * This class is the only way to access the <code>Generator</code> and
 * <code>Painter</code> class other than inheritance.
 */
public class RenderWorker {

	/**
	 * The singleton instance of this class.
	 */
	@NotNull
	private final static RenderWorker WORKER = new RenderWorker();

	/**
	 * @return the {@link RenderWorker#WORKER}
	 */
	@NotNull
	public static RenderWorker getInstance() {
		return WORKER;
	}

	private RenderWorker() {
	}

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
	 * Puts a new render request in queue. <br>
	 * If there is no <code>Generator</code> working, this can immediately execute.
	 * Otherwise it will become the new {@link RenderWorker#scheduledGenerator}.
	 * <p>
	 * This method will attempt to start a <code>Generator</code> and sequentially a
	 * <code>Painter</code> and will place any <code>Generator</code> as scheduled
	 * until it is finished.
	 * 
	 * @param field    the reference to the <code>Field</code>.
	 * @param fractal  a clone of the <code>Fractal</code>.
	 * @param filter   a clone of the <code>Filter</code>.
	 * @param runnable a <code>Runnable</code> run after the <code>Painter</code>
	 *                 has finished.
	 */
	public void newRender(@NotNull Field field, @NotNull Fractal fractal, @NotNull Filter filter,
			@Nullable Runnable runnable) {
		generator(field, fractal, () -> {
			newPainter(field, filter, () -> {
				if (runnable != null)
					runnable.run();
				generatorReady = true;
				runScheduledGenerator();
			});
		});
	}

	/**
	 * Puts a new <code>Generator</code> in queue.<br>
	 * If there is no <code>Generator</code> working, this can immediately execute.
	 * Otherwise it will become the new {@link RenderWorker#scheduledGenerator}.
	 * 
	 * @param field    the reference to the <code>Field</code>.
	 * @param fractal  a clone of the <code>Fractal</code>.
	 * @param runnable a <code>Runnable</code> run after the <code>Generator</code>
	 *                 has finished.
	 */
	public void newGenerator(@NotNull Field field, @NotNull Fractal fractal, @Nullable Runnable runnable) {
		generator(field, fractal, () -> {
			if (runnable != null)
				runnable.run();
			generatorReady = true;
			runScheduledGenerator();
		});
	}

	private synchronized void generator(@NotNull Field field, @NotNull Fractal fractal, @NotNull Runnable r) {
		if (generatorReady) {
			generatorReady = false;
			currentGenerator = new Generator(field, fractal, r);
			currentGenerator.execute();
		} else {
			scheduledGenerator = new Generator(field, fractal, r);
		}
	}

	/**
	 * Puts a new <code>Painter</code> in queue.<br>
	 * If there is no <code>Painter</code> working, this can immediately execute.
	 * Otherwise it will become the new {@link RenderWorker#scheduledPainter}.
	 * 
	 * @param field    the reference to the <code>Field</code>.
	 * @param filter   a clone of the <code>Filter</code>.
	 * @param runnable a <code>Runnable</code> run after the <code>Painter</code>
	 *                 has finished.
	 */
	public void newPainter(@NotNull Field field, @NotNull Filter filter, @Nullable Runnable runnable) {
		painter(field, filter, () -> {
			if (runnable != null)
				runnable.run();
			painterReady = true;
			runScheduledPainter();
		});
	}

	private synchronized void painter(@NotNull Field field, @NotNull Filter filter, @NotNull Runnable r) {
		if (painterReady) {
			painterReady = false;
			currentPainter = new Painter(field, filter, r);
			currentPainter.execute();
		} else {
			scheduledPainter = new Painter(field, filter, r);
		}
	}

	/**
	 * Attempt to run the {@link RenderWorker#scheduledGenerator}. <br>
	 * This can only work if the {@link RenderWorker#currentGenerator} is ready and
	 * a <code>scheduledGenerator</code> exists.
	 * 
	 * @return true if a <code>scheduledGenerator</code> was executed.
	 */
	synchronized boolean runScheduledGenerator() {
		if (generatorReady && scheduledGenerator != null) {
			generatorReady = false;
			currentGenerator = scheduledGenerator;
			scheduledGenerator = null;
			currentGenerator.execute();
			return true;
		}
		return false;
	}

	/**
	 * Attempt to run the {@link RenderWorker#scheduledPainter}. <br>
	 * This can only work if the {@link RenderWorker#currentPainter} is ready and a
	 * <code>scheduledPainter</code> exists.
	 * 
	 * @return true if a <code>scheduledPainter</code> was executed.
	 */
	synchronized boolean runScheduledPainter() {
		if (painterReady && scheduledPainter != null) {
			painterReady = false;
			currentPainter = scheduledPainter;
			scheduledPainter = null;
			currentPainter.execute();
			return true;
		}
		return false;
	}

	/**
	 * Attempts to cancel the {@link RenderWorker#currentGenerator} and
	 * {@link RenderWorker#currentPainter}.
	 * 
	 * @return {@link Generator#cancel(boolean)} for the <code>currentGenerator</code>.
	 */
	public boolean cancel() {
		boolean out = false;
		if (currentGenerator.cancel(true)) {
			generatorReady = true;
			out = true;
		}
		if (currentPainter.cancel(true))
			painterReady = true;

		return out;
	}
}
