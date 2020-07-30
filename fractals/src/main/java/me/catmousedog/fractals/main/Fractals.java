package me.catmousedog.fractals.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ToolTipManager;

import me.catmousedog.fractals.canvas.Canvas;
import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.fractals.concrete.IterativeMandelbrot;
import me.catmousedog.fractals.ui.JPInterface;

/**
 * the main class
 */
public class Fractals implements Runnable {

	/**
	 * Settings object for storing all the settings
	 */
	private final Settings settings = new Settings(this);

	private JFrame frame;

	/**
	 * the Fractal containing the fractal function
	 */
	private Fractal fractal;

	/**
	 * JPanel where the images are rendered
	 */
	private Canvas canvas;

	/**
	 * master panel on the right side containing {@link Canvas#jsp} and
	 * {@link Fractals#logger}
	 */
	private JPanel rpanel;

	/**
	 * Logger containing a JPanel to display any feedback such as progress bars
	 */
	private Logger logger;

	/**
	 * JPanel interface for the user
	 */
	private JPInterface jpi;

	/**
	 * ScrollPane which contains {@link Fractals#jpi}
	 */
	private JScrollPane jsp;

	/**
	 * Width and height of the canvas.
	 * <p>
	 * The default size is set in the {@code settings.properties} file
	 */
	private int width = settings.getWidth(), height = settings.getHeight();

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
	 * Initialises the frame, panel and JComponents
	 */
	public Fractals() {
		EventQueue.invokeLater(this);
	}

	/**
	 * ran on the EDT, creates all the swing components
	 */
	@Override
	public void run() {

		ToolTipManager.sharedInstance().setInitialDelay(200);

		// create logger
		logger = new Logger(iwidth + 2 * hgap, feedbackheight);

		// create fractal
		fractal = new IterativeMandelbrot();

		// create JFrame
		frame = new JFrame(settings.getArtifact_id() + "-" + settings.getVersion());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		// create canvas
		canvas = new Canvas(width, height, fractal, logger);
		frame.getContentPane().add(canvas, BorderLayout.CENTER);

		// create right panel (containing logger and jpi)
		rpanel = new JPanel();
		rpanel.setPreferredSize(new Dimension(iwidth + 2 * hgap, height));
		rpanel.setLayout(new BorderLayout());

		// create interface panel
		jpi = new JPInterface(iwidth, vgap, hgap, this, canvas, logger, settings);

		// add jpi to canvas
		canvas.setJPI(jpi);

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
		frame.pack();

		// add components
		jpi.addComponents();

		// set all visible
		frame.setVisible(true);

		// make sure all components are displayed
		frame.validate();

		// initial render
		if (settings.isRender_on_changes())
			jpi.renderNow();
	}

	public void setSize(int w, int h) {
		rpanel.setPreferredSize(new Dimension(iwidth + 2 * hgap, h));
		jsp.setPreferredSize(new Dimension(iwidth + 2 * hgap, h - feedbackheight));
		canvas.setPanelSize(w, h);
		frame.pack();
		frame.validate();
	}
}
