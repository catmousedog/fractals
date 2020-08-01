package me.catmousedog.fractals.canvas;

import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.SwingWorker;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.fractals.Pixel;
import me.catmousedog.fractals.main.Logger;
import me.catmousedog.fractals.ui.JPInterface;

public class Painter extends SwingWorker<Void, Void> implements PropertyChangeListener {
	/**
	 * the canvas instance this generator belongs to
	 */
	private final Canvas canvas;

	/**
	 * the user interface containing the {@link JPInterface#postRender()} method
	 */
	private final JPInterface jpi;

	/**
	 * the list of pixels
	 */
	private final List<Pixel> field;

	private final Fractal fractal;

	/**
	 * the logger instance
	 */
	private final Logger logger;
	
	private AtomicInteger i, q;

	public Painter(@NotNull Canvas canvas, @NotNull JPInterface jpi, @NotNull Logger logger) {
		this.canvas = canvas;
		field = canvas.getField();
		fractal = canvas.getConfig().getFractal();
		this.jpi = jpi;
		this.logger = logger;
		addPropertyChangeListener(this);
	}

	@Override
	protected Void doInBackground() throws Exception {
		// begin time
		long b = System.nanoTime();

		BufferedImage img = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_BGR);

		i = new AtomicInteger();
		q = new AtomicInteger();
		
		field.parallelStream().forEach(p -> {
			if (!super.isCancelled())
				img.setRGB(p.x, p.y, fractal.filter(p.v));

			// each 100th of all pixels the progress bar updates
			if (i.incrementAndGet() % (field.size() / 100) == 0)
				setProgress(q.incrementAndGet());

		});

		canvas.setImg(img);

		// end time
		long e = System.nanoTime();

		// log time
		logger.log("coloured in " + (e - b) / 1000000 + " ms!");

		return null;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getNewValue() instanceof Integer) {
			logger.setProgress("colouring fractal", (Integer) evt.getNewValue());
		} else if (evt.getNewValue().equals(StateValue.DONE)) {
			canvas.repaint();
			jpi.postRender();
			logger.setProgress("done!", 100);
		}
	}

}
