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

	private final JLabel[] logs = new JLabel[m];

	/**
	 * Stack of JLabels representing the first to last logged messages
	 */
	private final Vector<String> logMessages = new Vector<String>();

	/**
	 * label above the progress bar
	 */
	private final JLabel progress;

	private String progressMessage = "";

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
		setProgress(0);
	}

	/**
	 * Log message so the user can see it in the feedback panel
	 * 
	 * @param message to be logged
	 */
	public void log(@NotNull String message) {

		logMessages.add(message);

		if (logMessages.size() > m)
			logMessages.remove(0);

		for (int j = 0; j < logMessages.size(); j++) {
			logs[m - j - 1].setText(logMessages.get(j));
			logs[m - j - 1].setToolTipText(logMessages.get(j));
		}

	}

	/**
	 * sets the progress message
	 * 
	 * @param progressMessage
	 */
	public void setProgressMessage(@NotNull String progressMessage) {
		this.progressMessage = progressMessage;
	}

	/**
	 * sets the progress bars' value
	 * 
	 * @param value int between 0 - 100 (inclusive)
	 */
	public void setProgress(int value) {
		jpb.setValue(value);
	}

	/**
	 * add 1 percent to the progress bar
	 */
	public void addProgress() {
		jpb.setValue(jpb.getValue() + 1);
	}

	/**
	 * sets the progress bar message
	 */
	public void showMessage() {
		progress.setText(String.format("%s - %d%s", progressMessage, jpb.getValue(), "%"));
	}
}
