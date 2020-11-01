package me.catmousedog.fractals.ui;

import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.data.Field;
import me.catmousedog.fractals.data.LinearTransform;
import me.catmousedog.fractals.main.Settings;
import me.catmousedog.fractals.paneloperators.fractals.Fractal;
import me.catmousedog.fractals.workers.Generator;
import me.catmousedog.fractals.workers.Painter;
import me.catmousedog.fractals.workers.RenderWorker;

/**
 * Class for creating images.
 */
public class Picture {

	/**
	 * The {@link Canvas} instance.
	 */
	private final Canvas canvas;

	/**
	 * The {@link JPInterface} instance.
	 */
	private final JPInterface jpi;

	/**
	 * The {@link Settings} instance.
	 */
	private final Settings settings;

	private final RenderWorker renderer = RenderWorker.getInstance();

	private final Logger logger = Logger.getLogger("fractals");

	private boolean generating = false;

	public Picture(@NotNull Canvas canvas, @NotNull JPInterface jpi, @NotNull Settings settings) {
		logger.log(Level.FINER, "Picture init");

		this.canvas = canvas;
		this.jpi = jpi;
		this.settings = settings;
	}

	/**
	 * Generates and paints a {@link BufferedImage} using a {@link Generator} and
	 * {@link Painter} and writes it to the <code>images</code> folder.
	 * 
	 * @param width  The width of the image.
	 * @param height The height of the image.
	 * @param ext    The extension used for the image file, either 'png' or 'jpg'.
	 */
	public synchronized void newPicture(int width, int height, String ext) {
		if (generating)
			return;

		generating = true;

		logger.log(Level.FINEST, "Picture.newPicture");

		logger.log(Level.FINE, "creating new picture");

		jpi.preRender();

		Fractal fractal = canvas.getFractal().clone();
		LinearTransform transform = fractal.getTransform();
		transform.setOrigin(width / 2, height / 2);
		double a = Math.max((double) canvas.getPanelHeight() / height, (double) canvas.getPanelWidth() / width);
		transform.zoom(a);

		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				Field field = new Field(width, height);

				Thread done = new Thread(() -> {
					settings.addImage(field.getImg(), ext, fractal);
					jpi.postRender();
					generating = false;
					renderer.runScheduledGenerator();
					renderer.runScheduledPainter();
				});

				renderer.newRender(field, fractal, () -> done.start());

			}
		});
		t.start();
	}

	/**
	 * @return true if a picture is being generated.
	 */
	public boolean isGenerating() {
		return generating;
	}

	public void setGenerating(boolean generating) {
		this.generating = generating;
	}
}
