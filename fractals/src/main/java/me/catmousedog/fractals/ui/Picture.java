package me.catmousedog.fractals.ui;

import java.awt.image.BufferedImage;

import javax.swing.SwingWorker;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.catmousedog.fractals.canvas.Canvas;
import me.catmousedog.fractals.canvas.Field;
import me.catmousedog.fractals.canvas.Generator;
import me.catmousedog.fractals.canvas.Painter;
import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.fractals.LinearTransform;
import me.catmousedog.fractals.main.UIConsole;
import me.catmousedog.fractals.main.Settings;

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

	/**
	 * The {@link UIConsole} instance.
	 */
	private final UIConsole logger;

	/**
	 * The current {@link SwingWorker} responsible for generating the fractal.
	 */
	private Generator generator;

	/**
	 * The current {@link SwingWorker} responsible for colouring the fractal.
	 */
	private Painter painter;

	public Picture(@NotNull Canvas canvas, @NotNull JPInterface jpi, @NotNull Settings settings,
			@NotNull UIConsole logger) {
		this.canvas = canvas;
		this.jpi = jpi;
		this.settings = settings;
		this.logger = logger;
	}

	/**
	 * Generates and paints a {@link BufferedImage} using a {@link Generator} and
	 * {@link Painter} and writes it to the <code>images</code> folder.
	 * 
	 * @param width  The width of the image.
	 * @param height The height of the image.
	 * @param ext    The extension used for the image file, either 'png' or 'jpg'.
	 */
	public void newPicture(int width, int height, String ext) {
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

				painter = new Painter(field, canvas.getFractal().getFilter().clone(), () -> {
					settings.addImage(field.getImg(), ext, canvas.getFractal());
					jpi.postRender();
				}, logger);

				generator = new Generator(field, fractal, () -> {
					painter.execute();
				}, logger);
				generator.execute();
			}
		});
		t.start();
	}

	/**
	 * Attempts to cancel the current image generation.
	 * 
	 * @return true if successful.
	 */
	public boolean cancel() {
		if (generator != null && generator.cancel(true))
			return true;
		if (painter != null && painter.cancel(true))
			return true;
		return false;
	}

	/**
	 * @return The current {@link Generator}, can be null if no
	 *         {@link Picture#newPicture(int, int, String)} was called.
	 */
	@Nullable
	public Generator getGenerator() {
		return generator;
	}

	/**
	 * @return The current {@link Painter}, can be null if no
	 *         {@link Picture#newPicture(int, int, String)} was called.
	 */
	@Nullable
	public Painter getPainter() {
		return painter;
	}
}
