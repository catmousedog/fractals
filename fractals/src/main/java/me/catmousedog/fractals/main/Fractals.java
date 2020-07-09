package me.catmousedog.fractals.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ToolTipManager;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.fractals.IterativeMandelbrot;
import me.catmousedog.fractals.fractals.Canvas;

public class Fractals {

	private final Properties properties = new Properties();

	private final JFrame frame;

	private final Fractal fractal;

	/**
	 * JPanel where the images are rendered
	 */
	private final Canvas canvas;

	/**
	 * master panel on the right side containing {@link Canvas#jsp} and
	 * {@link Fractals#logger}
	 */
	private final JPanel rpanel;

	/**
	 * Logger containing a JPanel to display any feedback such as progress bars
	 */
	private final Logger logger;

	/**
	 * JPanel interface for the user
	 */
	private final JPInterface jpi;

	/**
	 * ScrollPane which contains {@link Fractals#jpi}
	 */
	private final JScrollPane jsp;

	/**
	 * default width and height of the {@link Fractals#canvas}
	 */
	private int width = 1000, height = 1000;

	/**
	 * The interface width, this remains constant unless the frame is smaller than
	 * this width.<br>
	 * Note that this is not the width of the {@linkplain Fractals#jpi}, as this is
	 * usually smaller.
	 */
	private int iwidth = 200;

	/**
	 * height of the feedback box
	 */
	private int feedbackheight = 150;

	/**
	 * vertical and horizontal gap for the jpi
	 */
	private int vgap = 10, hgap = 4;

	public static void main(String[] args) {
		new Fractals();
	}

	/**
	 * intialises the frame, panel and JComponents
	 */
	public Fractals() {

		// load properties
		try {
			properties.load(this.getClass().getClassLoader().getResourceAsStream("project.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		ToolTipManager.sharedInstance().setInitialDelay(200);

		// create fractal
		fractal = new IterativeMandelbrot();
		fractal.setIterations(102);

		// create logger
		logger = new Logger(iwidth + 2 * hgap, feedbackheight);

		// create JFrame
		frame = new JFrame(properties.getProperty("artifactId") + " - " + properties.getProperty("version"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		// create canvas
		canvas = new Canvas(width, height, fractal, logger);
		frame.getContentPane().add(canvas.getPanel(), BorderLayout.CENTER);

		// create right panel (containing logger and jpi)
		rpanel = new JPanel();
		rpanel.setPreferredSize(new Dimension(iwidth + 2 * hgap, height));
		rpanel.setLayout(new BorderLayout());

		// create interface panel
		jpi = new JPInterface(iwidth, vgap, hgap, this);

		// create scroll panel
		jsp = new JScrollPane(jpi, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.setPreferredSize(new Dimension(iwidth + 2 * hgap, height - feedbackheight));
		jsp.getVerticalScrollBar().setUnitIncrement(16);
		rpanel.add(jsp, BorderLayout.CENTER);

		// create feedback panel
		rpanel.add(logger, BorderLayout.PAGE_END);

		// add right panel
		frame.getContentPane().add(rpanel, BorderLayout.LINE_END);

		// set size
		frame.setVisible(true);
		frame.pack();

		// add components
		jpi.addComponents();

		// make sure all components are displayed
		frame.validate();

		canvas.setLocation(0.5, 0, 0.005, 0.005, 0);
	}

	public Canvas getCanvas() {
		return canvas;
	}

	public Logger getLogger() {
		return logger;
	}

	public JFrame getFrame() {
		return frame;
	}

	/**
	 * sets the size of the panels and frame so the canvas has the given size
	 * 
	 * @param w width of the canvas
	 * @param h height of the canvas
	 */
	public void setSize(int w, int h) {
		rpanel.setPreferredSize(new Dimension(iwidth + 2 * hgap, h));
		jsp.setPreferredSize(new Dimension(iwidth + 2 * hgap, h - feedbackheight));
		canvas.setSize(w, h);
		frame.pack();
		frame.validate();
	}

}
