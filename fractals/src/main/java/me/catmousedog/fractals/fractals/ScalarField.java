package me.catmousedog.fractals.fractals;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import me.catmousedog.fractals.main.Logger;

/**
 * Represents a 2D plane of processed values by the function
 * {@link ScalarField#f}
 */
@SuppressWarnings("serial")
public class ScalarField extends JPanel {

	/**
	 * the linear transformation used to get the actual coordinates the pixels point
	 * to
	 */
	private final LinearTransform matrix = new LinearTransform();

	/**
	 * iterative fractal function
	 */
	private final Fractal f;

	/**
	 * used to log progress to the user
	 */
	private final Logger logger;

	/**
	 * a list of all points
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

	private int i = 0;

	public ScalarField(int width, int height, Fractal f, Logger logger) {
		this.f = f;

		this.width = width;
		this.height = height;
		this.logger = logger;

		setSize(width, height);

		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		setPreferredSize(new Dimension(width, height));
		setBorder(BorderFactory.createLoweredBevelBorder());
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}

	/**
	 * takes the {@link ScalarField#field} and passes it through a color filter
	 */
	private void draw(Graphics g) {
		field.parallelStream().forEach(p -> {
			img.setRGB(p.x, p.y, (int) (p.v * 100000));
		});

		g.drawImage(img, 0, 0, null);
	}

	/**
	 * generates the {@link ScalarField#field} by applying
	 * {@link ScalarField#iterate(double, double)} over all pixels
	 */
	public void generate() {

		// resize
		if (width != getWidth() || height != getHeight()) {
			System.out.printf("w:%d\tgw:%d\n", width, getWidth());
			width = getWidth();
			height = getHeight();
			setSize(width, height);
		}

		// start time
		long b = System.nanoTime();

		// linear transformation to determine actual coordinate and input to field x, y
		field.parallelStream().forEach(p -> {
			double[] t = matrix.transform(p.x, p.y);
			p.tx = t[0];
			p.ty = t[1];
		});

		// for each in field, calculate v
		field.parallelStream().forEach(p -> {
			p.v = f.get(p.tx, p.ty);

			// log
			i++;
			if (i > field.size() / 100) {
				logger.addProgress();
				logger.showMessage();
				i = 0;
			}
		});
		i = 0;

		// end time
		long e = System.nanoTime();

		// log time
		logger.log(String.format("Done in %d ms!", (e - b) / 1000000));
	}

	/**
	 * changes the image size
	 * 
	 * @param width  width
	 * @param height height
	 */
	public void setSize(int width, int height) {
		field = new ArrayList<Pixel>(width * height);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				field.add(new Pixel(x, y));
			}
		}

		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	}

	/**
	 * set the location variables
	 * 
	 * @param dx real displacement from origin
	 * @param dy imaginary displacement from origin
	 * @param m  x scale factor
	 * @param n  y scale factor
	 * @param t  counter clockwise angle
	 */
	public void setLocation(double dx, double dy, double m, double n, double t) {
		matrix.setAll(width / 2, height / 2, dx, dy, m, n, t);
	}

	/**
	 * @return the {@link LinearTransform} corresponding with the current location
	 */
	public LinearTransform getMatrix() {
		return matrix;
	}

}
