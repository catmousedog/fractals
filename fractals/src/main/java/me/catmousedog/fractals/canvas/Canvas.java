package me.catmousedog.fractals.canvas;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.fractals.LinearTransform;
import me.catmousedog.fractals.fractals.Pixel;
import me.catmousedog.fractals.main.Logger;
import me.catmousedog.fractals.ui.JPInterface;

/**
 * Represents a 2D plane of processed values by the function
 * {@link Canvas#fractal}
 */
@SuppressWarnings("serial")
public class Canvas extends JPanel {

	/**
	 * The current {@link Configuration} of the canvas.
	 * <p>
	 * Not final as this can change when using {@link Canvas#undo()}
	 */
	private Configuration config;

	/**
	 * The previous {@link Configuration} of the canvas. <br>
	 * Not final as this gets set to {@link Canvas#config} every
	 * {@link Canvas#render(JPInterface)}
	 */
	private Configuration prevConfig;

	/**
	 * The latest {@link SwingWorker} responsible for generating the fractal.
	 */
	private SwingWorker<Void, Void> generator;

	/**
	 * the mouse listener
	 */
	private final Mouse mouse;

	/**
	 * used to log progress to the user
	 */
	private final Logger logger;

	/**
	 * a list of all pixels
	 */
	private List<Pixel> field;

	/**
	 * image displayed on this instance of {@link JPanel}
	 */
	private BufferedImage img;

	/**
	 * the instance of the {@link JPInterface}
	 */
	private JPInterface jpi;

	/**
	 * the current known width and height<br>
	 * this might be different from the actual JPanel width and height
	 */
	private int width, height;

	/**
	 * creates the Canvas
	 * 
	 * @param width   initial width
	 * @param height  initial height
	 * @param fractal Fractal containing the fractal function
	 * @param jpi     the user interface, used for saving and updating
	 * @param logger  the logger instance
	 */
	public Canvas(int width, int height, Fractal fractal, Logger logger) {
		this.logger = logger;
		config = new Configuration(new LinearTransform(), fractal, 2);

		setBorder(BorderFactory.createLoweredBevelBorder());
		mouse = new Mouse(this);
		addMouseListener(mouse);

		setPanelSize(width, height);
		savePrevConfig();
	}

	/**
	 * Displays the current {@link Canvas#img}
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, null);
	}

	/**
	 * generates the image using a {@link SwingWorker} and calls the
	 * {@link Canvas#colourAndPaint()}.
	 * <p>
	 * This method does not have a cool down and should only be used by the
	 * {@link JPInterface}.
	 */
	public void render() {
		generator = new Generator(this, jpi, logger);
		generator.execute();
	}

	/**
	 * Colours the image and paints it using a {@link SwingWorker}.
	 */
	public void colourAndPaint() {
		new Painter(this, jpi, logger).execute();
	}

	/**
	 * Sets the size of the canvas and all of its components reliant on that size.
	 * 
	 * @param w new width
	 * @param h new height
	 */
	public void setPanelSize(int w, int h) {
		// do nothing if unchanged
		if (width == w && height == h)
			return;

		width = w;
		height = h;

		// set field size
		field = new ArrayList<Pixel>(width * height);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				field.add(new Pixel(x, y));
			}
		}

		// set image size
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// set config.getTransform()
		config.getTransform().setOrigin(width / 2, height / 2);

		// set panel size
		setPreferredSize(new Dimension(width, height));
	}

	/**
	 * set the location variables
	 * <p>
	 * <b>shouldn't need to be called from outside of this class</b>
	 * 
	 * @param dx real displacement from origin
	 * @param dy imaginary displacement from origin
	 * @param z  zoom factor
	 * @param t  counter clockwise angle
	 */
	@Deprecated
	public void setLocation(double dx, double dy, double z, double t) {
		config.getTransform().setTranslation(dx, dy);
		config.getTransform().setScalar(1 / z, 1 / z);
		config.getTransform().setRot(t);
	}

	/**
	 * Essentially the opposite of {@link Canvas#undo()}. This should be called
	 * right before saving changes to the {@link Canvas#config}.
	 */
	public void savePrevConfig() {
		prevConfig = config.clone();
	}

	/**
	 * Sets the {@link Canvas#config} to the {@link Canvas#prevConfig}, reverting
	 * any changes saved to it after the last time {@link Canvas#savePrevConfig()}
	 * was called.
	 */
	public void undo() {
		config = prevConfig.clone();
	}

	/**
	 * sets the instance of the {@link JPInterface} so the Canvas can save and
	 * update the user input
	 * 
	 * @param jpi the instance of {@link JPInterface}
	 */
	public void setJPI(@NotNull JPInterface jpi) {
		this.jpi = jpi;
		mouse.setJPI(jpi);
	}

	public SwingWorker<Void, Void> getWorker() {
		return generator;
	}

	public Mouse getMouse() {
		return mouse;
	}

	public List<Pixel> getField() {
		return field;
	}

	/**
	 * @return the current {@link Configuration}
	 */
	public Configuration getConfig() {
		return config;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}

}
