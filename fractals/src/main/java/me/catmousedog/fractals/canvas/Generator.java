package me.catmousedog.fractals.canvas;

import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import javax.swing.SwingWorker;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.fractals.LinearTransform;
import me.catmousedog.fractals.main.Logger;
import me.catmousedog.fractals.ui.JPInterface;

/**
 * swing worker used for generating the image and updating at the same time
 */
public class Generator extends SwingWorker<Void, Void> implements PropertyChangeListener {

	/**
	 * The array of {@link Pixel}s that will be edited.
	 */
	private final Pixel[] pixels;

	/**
	 * A clone of the {@link Fractal} containing the fractal function,
	 * {@link Fractal#get(double, double)}.
	 */
	private final Fractal fractal;

	/**
	 * The {@link LinearTransform} of the cloned {@link Fractal}.
	 */
	private final LinearTransform transform;

	/**
	 * The {@link Runnable} run when the {@link Generator} is done and wasn't
	 * cancelled.
	 */
	private final Runnable runnable;

	/**
	 * The {@link Logger} instance.
	 */
	private final Logger logger;

	private boolean isGenerated = false;
	
	/**
	 * Atomic counters for keeping calculation progress when using parallel streams.
	 */
	private AtomicInteger i, q;

	/**
	 * Creates a new {@link Painter} that changes the {@link Field}'s
	 * {@link BufferedImage}.
	 * 
	 * @param field    The reference to the {@link Field} used for storing the
	 *                 {@link Pixel}s that will be edited.
	 * @param fractal  A clone of the {@link Fractal} containing the
	 *                 {@link Fractal#get(double, double)}
	 * @param runnable The {@link Runnable} run when the {@link Generator} is done
	 *                 and wans't cancelled.
	 * @param jpi      The {@link JPInterface} instance.
	 * @param logger   The {@link Logger} instance.
	 */
	public Generator(@NotNull Field field, @NotNull Fractal fractal, @NotNull Runnable runnable,
			@NotNull Logger logger) {
		this.pixels = field.getPixels();
		this.fractal = fractal;
		transform = fractal.getTransform();
		this.runnable = runnable;
		this.logger = logger;
		addPropertyChangeListener(this);
	}

	/**
	 * Will generate the image by applying the fractal function
	 * {@link Fractal#get(double, double)} and save to the given {@link Field}.
	 */
	@Override
	protected Void doInBackground() throws Exception {
		// begin time
		long b = System.nanoTime();

		// linear transformation to determine actual coordinates
		Stream.of(pixels).parallel().forEach(p -> {
			if (!super.isCancelled()) {
				double[] t = transform.apply(p.x, p.y);
				p.tx = t[0];
				p.ty = t[1];
			}
		});

		// atomic counters
		i = new AtomicInteger();
		q = new AtomicInteger();

		// for each in field, calculate fractal value 'v'
		Stream.of(pixels).parallel().forEach(p -> {

			if (!super.isCancelled())
				p.v = fractal.get(p.tx, p.ty);

			// each 100th of all pixels the progress bar updates
			if (i.incrementAndGet() % (pixels.length / 100) == 0)
				setProgress(q.incrementAndGet());

		});

		// end time
		long e = System.nanoTime();

		// log time
		logger.setGenerated((e - b) / 1000000);

		return null;
	}

	/**
	 * Called each percent of completion, updates the progress bar and runs the
	 * {@link Generator#runnable} when done.
	 * <p>
	 * Ran on the EDT
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (super.isCancelled())
			return;

		if (evt.getNewValue() instanceof Integer)
			logger.setProgress("calculating fractal", (Integer) evt.getNewValue());
		else if (evt.getNewValue().equals(StateValue.DONE)) {
			runnable.run();
			isGenerated = true;
		}
	}
	
	public boolean isGenerated() {
		return isGenerated;
	}
}
