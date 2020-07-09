package me.catmousedog.fractals.fractals;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import me.catmousedog.fractals.main.Logger;

/**
 * Represents a 2D plane of processed values by the function
 * {@link Canvas#fractal}
 */
@SuppressWarnings("serial")
public class Canvas {

	private final JPanel panel;

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

	private int i = 0;

	public Canvas(int width, int height, Fractal fractal, Logger logger) {
		this.fractal = fractal;
		this.width = width;
		this.height = height;
		this.logger = logger;

		field = new ArrayList<Pixel>(width * height);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				field.add(new Pixel(x, y));
			}
		}

		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		panel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				draw(g);
			}
		};
		
		setSize(width, height);

		panel.setPreferredSize(new Dimension(width, height));
		panel.setBorder(BorderFactory.createLoweredBevelBorder());
		panel.addMouseListener(new Mouse());
		
	}

	/**
	 * takes the {@link Canvas#field} and passes it through a color filter
	 */
	private synchronized void draw(Graphics g) {
		field.parallelStream().forEach(p -> {
			img.setRGB(p.x, p.y, (int) (p.v * 100000));
		});

		g.drawImage(img, 0, 0, null);
	}

	/**
	 * generates the {@link Canvas#field} by applying
	 * {@link Canvas#iterate(double, double)} over all pixels
	 */
	public synchronized void generate() {

		// resize if needed
//		resize();

		// linear transformation to determine actual coordinates
		field.parallelStream().forEach(p -> {
			double[] t = transform.apply(p.x, p.y);
			p.tx = t[0];
			p.ty = t[1];
		});

		// for each in field, calculate fractal value 'v'
		field.parallelStream().forEach(p -> {
			p.v = fractal.get(p.tx, p.ty);

			// log
			i++;
			if (i % field.size() / 100 == 0) {
				logger.addProgress();
				logger.showMessage();
			}
		});

		logger.setProgress(100);
		logger.setProgressMessage("Done!");

		i = 0;
	}

	/**
	 * sets the size of the canvas and all of its components reliant on that size
	 * 
	 * @param width
	 * @param height
	 */
	public synchronized void setSize(int w, int h) {

		if (width == w && height == h)
			return;

		width = w;
		height = h;

		panel.setPreferredSize(new Dimension(width, height));

		field = new ArrayList<Pixel>(width * height);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				field.add(new Pixel(x, y));
			}
		}

		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		panel.validate();
		panel.repaint();
	}

	/**
	 * resizes the components to the current JPanel size
	 */
	public void resizeToPanel() {
		setSize(panel.getWidth(), panel.getHeight());
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
		transform.setAll(width / 2, height / 2, dx, dy, m, n, t);
	}

	/**
	 * @return the {@link LinearTransform} corresponding with the current location
	 */
	public LinearTransform getMatrix() {
		return transform;
	}

	public Fractal getFractal() {
		return fractal;
	}

	public JPanel getPanel() {
		return panel;
	}
	
	private class Mouse implements MouseListener {

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

	}
	
}
