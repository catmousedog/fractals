package me.catmousedog.fractals.workers;

import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.canvas.Canvas;
import me.catmousedog.fractals.canvas.Field;
import me.catmousedog.fractals.canvas.Pixel;
import me.catmousedog.fractals.fractals.filters.Filter;
import me.catmousedog.fractals.ui.JPInterface;
import me.catmousedog.fractals.utils.FeedbackPanel;

public class Painter extends GlobalWorker {

	/**
	 * {@link BufferedImage} to be edited.
	 */
	private final BufferedImage img;

	/**
	 * The list of pixels used to calculate the image.
	 */
	private final Pixel[] pixels;

	/**
	 * The {@link Filter} used to calculate the image.
	 */
	private final Filter filter;

	/**
	 * The logger instance.
	 */
	private final FeedbackPanel feedback = FeedbackPanel.getInstance();

	private AtomicInteger i, q;

	/**
	 * Creates a new {@link Painter} that changes the {@link Field}'s
	 * {@link BufferedImage}.
	 * 
	 * @param field    the {@link Field} used for storing the {@link BufferedImage}
	 *                 and the {@link Pixel}s.
	 * @param filter   a clone of the {@link Filter} containing the
	 *                 {@link Filter#apply(Number)}.
	 * @param runnable the {@link Runnable} run when the {@link Painter} is done.
	 *                 Run on the EDT.
	 */
	Painter(@NotNull Field field, @NotNull Filter filter, @NotNull Runnable runnable) {
		super(runnable);
		img = field.getImg();
		pixels = field.getPixels();
		this.filter = filter;
	}

	private long ms;

	/**
	 * Will apply the {@link Filter#apply(Number)} to each {@link Pixel} in
	 * {@link Canvas#pixels} to colour the image.
	 * 
	 * @return null
	 */
	@Override
	protected Void doInBackground() {
		// begin time
		long b = System.nanoTime();

		i = new AtomicInteger();
		q = new AtomicInteger();

		Stream.of(pixels).parallel().forEach(p -> {
			if (!super.isCancelled())
				img.setRGB(p.x, p.y, filter.apply(p.v));

			// each 100th of all pixels the progress bar updates
			if (i.incrementAndGet() % (pixels.length / 100) == 0)
				setProgress(q.incrementAndGet());
		});

		// end time
		long e = System.nanoTime();

		// log time
		ms = (e - b) / 1000000;
		
		return null;
	}

	/**
	 * Called each percent of completion, updates the progress bar and runs the
	 * {@link JPInterface#postRender()} method when done.
	 * <p>
	 * Ran on the EDT
	 */
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (super.isCancelled())
			return;

		if (evt.getNewValue() instanceof Integer) {
			feedback.setPainterProgress("colouring fractal", (Integer) evt.getNewValue());
		} else if (evt.getNewValue().equals(StateValue.DONE)) {
			runnable.run();
			feedback.setColoured(ms);
			feedback.setPainterProgress("done!", 100);
		}
	}

}
