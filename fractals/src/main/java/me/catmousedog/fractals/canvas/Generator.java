package me.catmousedog.fractals.canvas;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.SwingWorker;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.fractals.LinearTransform;
import me.catmousedog.fractals.fractals.Pixel;
import me.catmousedog.fractals.main.Logger;
import me.catmousedog.fractals.ui.JPInterface;

/**
 * swing worker used for generating the image and updating at the same time
 */
public class Generator extends SwingWorker<Void, Void> implements PropertyChangeListener {

	/**
	 * the canvas instance this generator belongs to
	 */
	private final Canvas canvas;

	/**
	 * the user interface containing the {@link JPInterface#postRender()} method
	 */
	private final JPInterface jpi;

	/**
	 * the current known {@link Configuration} of the canvas
	 */
	private final Configuration config;

	private final LinearTransform transform;

	/**
	 * the fractal instance containg the fractal function
	 */
	private final Fractal fractal;

	/**
	 * the list of pixels
	 */
	private final List<Pixel> field;

	/**
	 * the logger instance
	 */
	private final Logger logger;

	/*
	 * atomic counters for keeping calculation progress
	 */
	private AtomicInteger i, q;

	public Generator(@NotNull Canvas canvas, @NotNull JPInterface jpi, @NotNull Logger logger) {
		this.canvas = canvas;
		config = canvas.getConfig();
		fractal = config.getFractal();
		transform = config.getTransform();
		field = canvas.getField();
		this.jpi = jpi;
		this.logger = logger;
		addPropertyChangeListener(this);
	}

	/**
	 * Will generate the image by applying the fractal function and at the same time
	 * update the progress bar. This method will then proceed to paint the image.
	 * <br>
	 * The final time is logged to the user.
	 * <p>
	 * This is not run on the EDT but on a worker thread.
	 */
	@Override
	protected Void doInBackground() throws Exception {
		// begin time
		long b = System.nanoTime();

		// linear transformation to determine actual coordinates
		field.parallelStream().forEach(p -> {
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
		field.parallelStream().forEach(p -> {

			if (!super.isCancelled())
				p.v = fractal.get(p.tx, p.ty);

			// each 100th of all pixels the progress bar updates
			if (i.incrementAndGet() % (field.size() / 100) == 0)
				setProgress(q.incrementAndGet());

		});

		// end time
		long e = System.nanoTime();

		// log time
		logger.log("rendered in " + (e - b) / 1000000 + " ms!");

		return null;
	}

	/**
	 * Called each percent of completion, updates the progress bar and runs the
	 * {@link JPInterface#postRender()} method.
	 * <p>
	 * Ran on the EDT
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getNewValue() instanceof Integer) {
			logger.setProgress("calculating fractal", (Integer) evt.getNewValue());
		} else if (evt.getNewValue().equals(StateValue.DONE)) {
			if (!super.isCancelled())
				canvas.colourAndPaint();
			else {
				logger.setProgress("cancelled!", 0);
				jpi.postRender();
			}
		}
	}

}
