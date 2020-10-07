package me.catmousedog.fractals.workers;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.catmousedog.fractals.canvas.Field;
import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.fractals.filters.Filter;

public class RenderWorker {

	private static RenderWorker WORKER;

	public static RenderWorker getInstance() {
		return WORKER;
	}

	static {
		WORKER = new RenderWorker();
	}

	private RenderWorker() {
	}

	private Generator currentGenerator;

	/**
	 * True if a new <code>generator</code> can be started. <br>
	 * There are still other factors that might prohibit this from happening.
	 * 
	 */
	private boolean generatorReady = true;

	private Generator scheduledGenerator;

	private Painter currentPainter;

	/**
	 * True if a new <code>painter</code> can be started. <br>
	 * There are still other factors that might prohibit this from happening.
	 */
	private boolean painterReady = true;

	private Painter scheduledPainter;

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

	public void newGenerator(Field field, Fractal fractal, Runnable runnable) {
		generator(field, fractal, () -> {
			if (runnable != null)
				runnable.run();
			generatorReady = true;
			runScheduledGenerator();
		});
	}

	private synchronized void generator(Field field, Fractal fractal, Runnable r) {
		if (generatorReady) {
			generatorReady = false;
			currentGenerator = new Generator(field, fractal, r);
			currentGenerator.execute();
		} else {
			scheduledGenerator = new Generator(field, fractal, r);
		}
	}

	public void newPainter(@NotNull Field field, @NotNull Filter filter, @Nullable Runnable runnable) {
		painter(field, filter, () -> {
			if (runnable != null)
				runnable.run();
			painterReady = true;
			runScheduledPainter();
		});
	}

	private synchronized void painter(Field field, Filter filter, Runnable r) {
		if (painterReady) {
			painterReady = false;
			currentPainter = new Painter(field, filter, r);
			currentPainter.execute();
		} else {
			scheduledPainter = new Painter(field, filter, r);
		}
	}

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

	public boolean cancel() {// cancel both????
		if (!currentGenerator.isDone() && currentGenerator.cancel(true)) {
			generatorReady = true;
			return true;
		}
		if (!currentPainter.isDone() && currentPainter.cancel(true)) {
			painterReady = true;
			return true;
		}
		return false;
	}
}
