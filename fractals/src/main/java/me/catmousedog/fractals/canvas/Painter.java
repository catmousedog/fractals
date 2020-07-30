package me.catmousedog.fractals.canvas;

import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.SwingWorker;

import org.jetbrains.annotations.NotNull;

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

	/**
	 * the logger instance
	 */
	private final Logger logger;

	public Painter(@NotNull Canvas canvas, @NotNull JPInterface jpi, @NotNull Logger logger) {
		this.canvas = canvas;
		field = canvas.getField();
		this.jpi = jpi;
		this.logger = logger;
		addPropertyChangeListener(this);
	}

	@Override
	protected Void doInBackground() throws Exception {
		// begin time
		long b = System.nanoTime();
		
		BufferedImage img = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_BGR);
		
		field.parallelStream().forEach(p -> {
			img.setRGB(p.x, p.y, (int) (p.v * 100000));
		});
		
		canvas.setImg(img);
		canvas.repaint();
		
		// end time
		long e = System.nanoTime();

		// log time
		logger.log("coloured in " + (e - b) / 1000000 + " ms!");

		
		return null;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getNewValue().equals(StateValue.DONE)) {
			logger.setProgress("done!", 100);
			jpi.postRender();
		}
	}

}
