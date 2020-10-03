package me.catmousedog.fractals.canvas;

import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import javax.swing.SwingWorker;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.fractals.filters.Filter;
import me.catmousedog.fractals.ui.JPInterface;
import me.catmousedog.fractals.utils.FeedbackPanel;

public class Painter extends SwingWorker<Void, Void> implements PropertyChangeListener {

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
	 * The {@link Runnable} run when the {@link SwingWorker} is done.
	 */
	private final Runnable runnable;

	/**
	 * The logger instance.
	 */
	private final FeedbackPanel logger = FeedbackPanel.getInstance();

	private AtomicInteger i, q;

	private boolean repainted = false;

	/**
	 * Creates a new {@link Painter} that changes the {@link Field}'s
	 * {@link BufferedImage}.
	 * 
	 * @param field    the {@link Field} used for storing the {@link BufferedImage}
	 *                 and the {@link Pixel}s.
	 * @param filter   a clone of the {@link Filter} containing the
	 *                 {@link Filter#get(Number)}.
	 * @param runnable the {@link Runnable} run when the {@link Painter} is done on the EDT.
	 * @param jpi      the {@link JPInterface} instance.
	 * @param logger   the {@link FeedbackPanel} instance.
	 */
	public Painter(@NotNull Field field, @NotNull Filter filter, @NotNull Runnable runnable) {
		img = field.getImg();
		this.pixels = field.getPixels();
		this.filter = filter;
		this.runnable = runnable;
		addPropertyChangeListener(this);
	}

	/**
	 * Will apply the {@link Filter#get(Number)} to each {@link Pixel} in
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
				img.setRGB(p.x, p.y, filter.get(p.v));

			// each 100th of all pixels the progress bar updates
			if (i.incrementAndGet() % (pixels.length / 100) == 0)
				setProgress(q.incrementAndGet());
		});

		// end time
		long e = System.nanoTime();

		// log time
		logger.setColoured((e - b) / 1000000);

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
			logger.setProgress("colouring fractal", (Integer) evt.getNewValue());
		} else if (evt.getNewValue().equals(StateValue.DONE)) {
			runnable.run();
			logger.setProgress("done!", 100);
			repainted = true;
		}
	}

	/**
	 * Cancels the {@link SwingWorker} and sets {@link Painter#repainted} to true so
	 * a new Painter can be created in {@link Canvas}.
	 * 
	 * @return {@link SwingWorker#cancel(boolean)}
	 */
	public boolean cancel() {
		repainted = true;
		return super.cancel(true);
	}

	/**
	 * @return true if the image has been coloured and repainted.
	 */
	public boolean isRepainted() {
		return repainted;
	}

}
