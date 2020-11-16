package me.catmousedog.fractals.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ToolTipManager;

import me.catmousedog.fractals.paneloperators.fractals.Fractal;
import me.catmousedog.fractals.ui.Canvas;
import me.catmousedog.fractals.ui.FeedbackPanel;
import me.catmousedog.fractals.ui.JPInterface;
import me.catmousedog.fractals.utils.FileFormatter;

/**
 * the main class
 */
public class Main implements Runnable, UncaughtExceptionHandler {

	/**
	 * The <code>Logger</code> used to log messages to the user and log files.
	 */
	private final Logger logger = Logger.getLogger("fractals");

	{
		// add handlers to logger
		logger.setLevel(Level.ALL);

		try {
			File logs = new File("./logs");
			if (!logs.exists())
				logs.mkdirs();
			FileHandler fileHandler = new FileHandler("logs/fractals.log", 1024 * 1024, 2, false);
			fileHandler.setLevel(Level.ALL);
			fileHandler.setFormatter(new FileFormatter());
			logger.addHandler(fileHandler);
		} catch (SecurityException | IOException e) {
			logger.log(Level.WARNING, "could not add FileHandler to Logger", e);
		}
	}

	/**
	 * Settings object for storing all the settings stored outside the application.
	 */
	private final Settings settings = Settings.getInstance();

	/**
	 * The panel containing all the feedback.<br>
	 * This is also a {@link Handler} so any loggable messages will appear on this
	 * panel.
	 * <p>
	 * The first instance of this must be created after the <code>Settings</code>
	 * was created.
	 */
	private final FeedbackPanel feedback = FeedbackPanel.getInstance();

	private JFrame frame;

	/**
	 * JPanel where the images are rendered
	 */
	private Canvas canvas;

	/**
	 * master panel on the right side containing {@link Canvas#jsp} and
	 * {@link Main#feedback}
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
		Thread.setDefaultUncaughtExceptionHandler(this);

		ToolTipManager.sharedInstance().setInitialDelay(1000);
		ToolTipManager.sharedInstance().setDismissDelay(60000);

		// add feedback panel
		logger.addHandler(feedback);

		// create JFrame
		frame = new JFrame(settings.getArtifact_id() + "-" + settings.getVersion());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		// create canvas
		canvas = new Canvas(settings.getDefaultFractal());

		frame.getContentPane().add(canvas, BorderLayout.CENTER);

		// create right panel (containing logger and jpi)
		rpanel = new JPanel();
		rpanel.setPreferredSize(new Dimension(settings.getJPIWidth(), settings.getHeight()));
		rpanel.setLayout(new BorderLayout());

		// create interface panel
		jpi = new JPInterface(this, canvas);

		for (Fractal f : settings.getFractals()) {
			f.setCanvas(canvas);
			f.setJPI(jpi);
		}

		// add jpi to canvas
		canvas.setJPI(jpi);

		// create scroll panel
		jsp = new JScrollPane(jpi, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.setPreferredSize(
				new Dimension(settings.getJPIWidth(), settings.getHeight() - settings.getFeedbackheight()));
		jsp.getVerticalScrollBar().setUnitIncrement(16);
		rpanel.add(jsp, BorderLayout.CENTER);

		// create feedback panel
		rpanel.add(feedback.getPanel(), BorderLayout.PAGE_END);

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

		// set keybinds
		try {
			settings.initKeybinds(jpi.getGUI());
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Settings.initKeybinds IOException", e);
		}

		logger.log(Level.FINER, "initial render\n");

		// initial render
		if (settings.isRender_on_changes())
			jpi.renderNow();
	}

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		logger.log(Level.SEVERE, "uncaught exception in thread " + t.getName(), e);
	}

	public void setSize(int w, int h) {
		rpanel.setPreferredSize(new Dimension(settings.getJPIWidth(), h));
		jsp.setPreferredSize(new Dimension(settings.getJPIWidth(), h - settings.getFeedbackheight()));
		canvas.setPanelSize(w, h);
		frame.pack();
		frame.validate();
	}
}
