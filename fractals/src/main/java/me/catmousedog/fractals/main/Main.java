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
import me.catmousedog.fractals.ui.JPInterface;

/**
 * the main class
 */
public class Main implements Runnable {

	/**
	 * Settings object for storing all the settings stored outside the application.
	 */
	private final Settings settings = new Settings(this);

	/**
	 * Array of all enabled fractals
	 */
	private final Fractal[] fractals = settings.getFractals();

	/**
	 * Object for storing all the initial dimensions
	 */
	private final InitialSize size = new InitialSize();

	/**
	 * Logger containing a JPanel to display any feedback such as progress bars
	 */
	private final Logger logger = new Logger(size);

	private JFrame frame;

	/**
	 * JPanel where the images are rendered
	 */
	private Canvas canvas;

	/**
	 * master panel on the right side containing {@link Canvas#jsp} and
	 * {@link Main#logger}
	 */
	private JPanel rpanel;

	/**
	 * JPanel interface for the user
	 */
	private JPInterface jpi;

	/**
	 * ScrollPane which contains {@link Main#jpi}
	 */
	private JScrollPane jsp;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Main());
	}

	private Main() {
	}

	/**
	 * ran on the EDT, creates all the swing components
	 */
	@Override
	public void run() {

		ToolTipManager.sharedInstance().setInitialDelay(200);
		ToolTipManager.sharedInstance().setDismissDelay(60000);

		// create JFrame
		frame = new JFrame(settings.getArtifact_id() + "-" + settings.getVersion());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		// create canvas
		canvas = new Canvas(size, settings.getDefaultFractal(), logger);
		for (Fractal f : fractals)
			f.setCanvas(canvas);
		frame.getContentPane().add(canvas, BorderLayout.CENTER);

		// create right panel (containing logger and jpi)
		rpanel = new JPanel();
		rpanel.setPreferredSize(new Dimension(size.getJPIWidth(), size.getHeight()));
		rpanel.setLayout(new BorderLayout());

		// create interface panel
		jpi = new JPInterface(size, this, canvas, logger, settings);

		// add jpi to canvas
		canvas.setJPI(jpi);

		// create scroll panel
		jsp = new JScrollPane(jpi, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.setPreferredSize(new Dimension(size.getJPIWidth(), size.getHeight() - size.getFeedbackheight()));
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
		jpi.update();

		// set all visible
		frame.setVisible(true);

		// make sure all components are displayed
		frame.validate();

		// initial render
		if (settings.isRender_on_changes())
			jpi.renderNow();
	}

	public void setSize(int w, int h) {
		rpanel.setPreferredSize(new Dimension(size.getJPIWidth(), h));
		jsp.setPreferredSize(new Dimension(size.getJPIWidth(), h - size.getFeedbackheight()));
		canvas.setPanelSize(w, h);
		frame.pack();
		frame.validate();
	}

	/**
	 * Class for keeping the initial dimension constants.
	 */
	public class InitialSize {

		/**
		 * Width and height of the canvas.
		 * <p>
		 * The default size is set in the {@code settings.properties} file
		 */
		private int width = settings.getWidth(), height = settings.getHeight();

		/**
		 * The interface width, this remains constant unless the frame is smaller than
		 * this width.<br>
		 * Note that this is not the width of the {@linkplain Main#jpi}, as this is
		 * usually smaller.
		 */
		private int iwidth = 200;

		/**
		 * height of the feedback box
		 */
		private int feedbackheight = 150;

		/**
		 * vertical gap for the jpi border
		 */
		private int vgap = 10;

		/**
		 * horizontal gap for the jpi border
		 */
		private int hgap = 4;

		/**
		 * @return the width
		 */
		public int getWidth() {
			return width;
		}

		/**
		 * @return the height
		 */
		public int getHeight() {
			return height;
		}

		/**
		 * @return the iwidth
		 */
		public int getIwidth() {
			return iwidth;
		}

		/**
		 * @return the feedbackheight
		 */
		public int getFeedbackheight() {
			return feedbackheight;
		}

		/**
		 * @return the vgap
		 */
		public int getVgap() {
			return vgap;
		}

		/**
		 * @return the hgap
		 */
		public int getHgap() {
			return hgap;
		}

		public int getJPIWidth() {
			return iwidth + 2 * hgap;
		}

	}

	/**
	 * @return Get the array of the active {@link Fractal}s
	 */
	public Fractal[] getFractals() {
		return fractals;
	}

}
