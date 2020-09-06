package me.catmousedog.fractals.main;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.main.Main.InitialSize;

/**
 * This class is used to log messages to the user and show any outputs
 */
@SuppressWarnings("serial")
public class Logger extends JPanel {

	/**
	 * amount of logged messages displayed
	 */
	private int m = 6;

	/**
	 * array of JLabels representing the logged messages
	 */
	private final JLabel[] logs = new JLabel[m];

	/**
	 * Stack of Strings representing the first to last logged messages
	 */
	private final Vector<String> logMessages = new Vector<String>();

	/**
	 * {@link JLabel} displaying the last render's generating time
	 */
	private final JLabel generateTime;

	/**
	 * {@link JLabel} displaying the last render's colouring time
	 */
	private final JLabel colourTime;

	/**
	 * label above the progress bar
	 */
	private final JLabel progress;

	/**
	 * the progress bar at the bottom of the feedback panel
	 */
	private final JProgressBar jpb;

	public Logger(InitialSize size) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(size.getIwidth() + 2 * size.getHgap(), size.getFeedbackheight()));
		setBorder(BorderFactory.createLineBorder(Color.GRAY));

		// log labels
		for (int j = 0; j < m; j++) {
			logs[j] = new JLabel();
			logs[j].setAlignmentX(JLabel.CENTER_ALIGNMENT);
			add(logs[j]);
		}

		add(Box.createVerticalGlue());

		// time labels
		generateTime = new JLabel("not yet generated");
		generateTime.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		add(generateTime);

		colourTime = new JLabel("not yet coloured");
		colourTime.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		add(colourTime);

		add(Box.createVerticalStrut(5));

		// progress label
		progress = new JLabel();
		progress.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		add(progress);

		add(Box.createVerticalStrut(2));

		// progress bar
		jpb = new JProgressBar();
		add(jpb);
		setProgress("no calculations", 0);
	}

	/**
	 * Log message so the user can see it in the feedback panel
	 * 
	 * @param message to be logged
	 */
	public void log(@NotNull String message) {
		System.out.println("logger: " + message);
		logMessage(message);
	}

	/**
	 * logs a message with 'exception' in front of it
	 * 
	 * @param e
	 */
	public void exception(@NotNull Exception e) {
		String m = "exception: " + e.getMessage();
		System.err.println(m);
		logMessage(m);
	}

	private void logMessage(@NotNull String message) {
		logMessages.add(message);

		if (logMessages.size() > m)
			logMessages.remove(0);

		for (int j = 0; j < logMessages.size(); j++) {
			logs[m - j - 1].setText(logMessages.get(j));
			logs[m - j - 1].setToolTipText(logMessages.get(j));
		}
	}

	/**
	 * sets the progress message and the progress bar value
	 * 
	 * @param progressMessage the message above the progress bar without the
	 *                        progress value
	 * @param p               percentage of the progress bar
	 */
	public void setProgress(@NotNull String progressMessage, int p) {
		jpb.setValue(p);
		progress.setText(String.format("%s - %d%s", progressMessage, p, "%"));
	}

	/**
	 * Display the time it took to generate
	 * 
	 * @param ms time in milliseconds
	 */
	public void setGenerated(long ms) {
		generateTime.setText(String.format("generated in %d ms!", ms));
	}

	/**
	 * Display the time it took to colour
	 * 
	 * @param ms time in milliseconds
	 */
	public void setColoured(long ms) {
		colourTime.setText(String.format("coloured in %d ms!", ms));
	}

}
