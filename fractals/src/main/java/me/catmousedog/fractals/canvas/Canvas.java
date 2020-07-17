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
import me.catmousedog.fractals.main.JPInterface;
import me.catmousedog.fractals.main.Logger;

/**
 * Represents a 2D plane of processed values by the function
 * {@link Canvas#fractal}
 */
@SuppressWarnings("serial")
public class Canvas extends JPanel {

	/**
	 * the mouse listener
	 */
	private final Mouse mouse;

	/**
	 * the linear transformation used to get the actual coordinates the pixels point
	 * to
	 */
	private final LinearTransform transform = new LinearTransform();

	/**
	 * iterative fractal function
	 */
	private final Fractal fractal;

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
		this.fractal = fractal;
		this.logger = logger;

		setPanelSize(width, height);

		setBorder(BorderFactory.createLoweredBevelBorder());
		mouse = new Mouse(this);
		addMouseListener(mouse);
	}

	/**
	 * takes the {@link Canvas#field} and passes it through a color filter
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		field.parallelStream().forEach(p -> {
			img.setRGB(p.x, p.y, (int) (p.v * 100000));
		});

		g.drawImage(img, 0, 0, null);
	}

	/**
	 * generates the image using a {@link SwingWorker} and paints it
	 * 
	 * @param jpi the user interface containing the
	 *            {@link JPInterface#afterRender()} method
	 */
	public void render(@NotNull JPInterface jpi) {
		new Generator(this, jpi, logger).execute();
	}

	/**
	 * Sets the size of the canvas and all of its components reliant on that size.
	 * 
	 * @param w new width
	 * @param h new height
	 */
	public synchronized void setPanelSize(int w, int h) {
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

		// set transform
		transform.setOrigin(width / 2, height / 2);

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
		transform.setTranslation(dx, dy);
		transform.setScalar(1 / z, 1 / z);
		transform.setTheta(t);
	}

	/**
	 * sets the instance of the {@link JPInterface} so the Canvas can save and
	 * update the user input
	 * 
	 * @param jpi the instance of {@link JPInterface}
	 */
	public void setJPI(@NotNull JPInterface jpi) {
		mouse.setJPI(jpi);
	}

	/**
	 * @return the {@link LinearTransform} corresponding with the current location
	 */
	public LinearTransform getTransform() {
		return transform;
	}

	public Fractal getFractal() {
		return fractal;
	}

	public List<Pixel> getField() {
		return field;
	}
}
