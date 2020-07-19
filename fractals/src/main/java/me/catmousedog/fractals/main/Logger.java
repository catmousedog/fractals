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

/**
 * This class is used to log messages to the user and show any outputs
 */
@SuppressWarnings("serial")
public class Logger extends JPanel {

	/**
	 * amount of logged messages displayed
	 */
	private int m = 5;

	/**
	 * array of JLabels representing the logged messages
	 */
	private final JLabel[] logs = new JLabel[m];

	/**
	 * Stack of Strings representing the first to last logged messages
	 */
	private final Vector<String> logMessages = new Vector<String>();

	/**
	 * label above the progress bar
	 */
	private final JLabel progress;

	/**
	 * the progress bar at the bottom of the feedback panel
	 */
	private final JProgressBar jpb;

	public Logger(int w, int h) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(w, h));
		setBorder(BorderFactory.createLineBorder(Color.GRAY));

		// log labels
		for (int j = 0; j < m; j++) {
			logs[j] = new JLabel();
			logs[j].setAlignmentX(0.5F);
			add(logs[j]);
		}

		add(Box.createVerticalGlue());

		// progress label
		progress = new JLabel();
		progress.setAlignmentX(0.5F);
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
		
		logMessages.add(message);

		if (logMessages.size() > m)
			logMessages.remove(0);

		for (int j = 0; j < logMessages.size(); j++) {
			logs[m - j - 1].setText(logMessages.get(j));
			logs[m - j - 1].setToolTipText(logMessages.get(j));
		}

	}

	/**
	 * logs a message with 'exception' in front of it
	 * 
	 * @param e
	 */
	public void exception(@NotNull Exception e) {
		log("exception: " + e.getMessage());
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
}
