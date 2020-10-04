package me.catmousedog.fractals.utils;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.util.Vector;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.catmousedog.fractals.main.Main.InitialSize;

public class FeedbackPanel extends Handler {

	private static FeedbackPanel FEEDBACK_PANEL;

	/**
	 * The <code>JPanel</code> in which all the feedback is displayed.
	 */
	private final JPanel panel;

	/**
	 * <code>JLabel</code> displaying the last generating time.
	 */
	private final JLabel generateTime;

	/**
	 * <code>JLabel</code> displaying the last colouring time.
	 */
	private final JLabel colourTime;

	/**
	 * <code>JLabel</code> above the progress bar.
	 */
	private final JLabel progress;

	/**
	 * The progress bar at the bottom of the feedback panel.
	 */
	private final JProgressBar jpb;

	/**
	 * Maximum amount of logged messages displayed.
	 */
	private int m = 3;

	/**
	 * The array of JLabels representing the logged messages.
	 */
	private final JLabel[] logs = new JLabel[m];

	/**
	 * <code>Vector</code> of <code>Strings</code> representing the first to last
	 * logged messages.
	 */
	private final Vector<String> logMessages = new Vector<String>();

	/**
	 * @return the instance of the <code>FeedbackPanel</code>.<br>
	 *         Only not null if {@link FeedbackPanel#init(InitialSize)} was called.
	 */
	@Nullable
	public static FeedbackPanel getInstance() {
		return FEEDBACK_PANEL;
	}

	/**
	 * Creates the feedback panel.<br>
	 * Must be created on the EDT.
	 * 
	 * @param size
	 * 
	 * @return the static <code>FEEDBACK_PANEM</code>.
	 */
	public static FeedbackPanel init(InitialSize size) {
		if (FEEDBACK_PANEL == null)
			FEEDBACK_PANEL = new FeedbackPanel(size);
		return FEEDBACK_PANEL;
	}

	private FeedbackPanel(InitialSize size) {
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setPreferredSize(new Dimension(size.getIwidth() + 2 * size.getHgap(), size.getFeedbackheight()));
		panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		setLevel(Level.FINER);

		// log labels
		for (int j = 0; j < m; j++) {
			logs[j] = new JLabel();
			logs[j].setAlignmentX(JLabel.CENTER_ALIGNMENT);
			panel.add(logs[j]);
		}

		panel.add(Box.createVerticalGlue());

		// time labels
		generateTime = new JLabel("not yet generated");
		generateTime.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		panel.add(generateTime);

		colourTime = new JLabel("not yet coloured");
		colourTime.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		panel.add(colourTime);

		panel.add(Box.createVerticalStrut(5));

		// progress label
		progress = new JLabel();
		progress.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		panel.add(progress);

		panel.add(Box.createVerticalStrut(2));

		// progress bar
		jpb = new JProgressBar();
		panel.add(jpb);

		setProgress("no calculations", 0);
	}

	/**
	 * sets the progress message and the progress bar value.<br>
	 * Must be run on the EDT.
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
	 * Display the time it took to generate.<br>
	 * Must be run on the EDT.
	 * 
	 * @param ms time in milliseconds
	 */
	public void setGenerated(long ms) {
		generateTime.setText(String.format("generated in %d ms!", ms));
	}

	/**
	 * Display the time it took to colour.<br>
	 * Must be run on the EDT.
	 * 
	 * @param ms time in milliseconds
	 */
	public void setColoured(long ms) {
		colourTime.setText(String.format("coloured in %d ms!", ms));
	}

	/**
	 * Displays a <code>LogRecord</code> to the feedback panel.
	 */
	@Override
	public void publish(LogRecord record) {
		if (!isLoggable(record))
			return;

		logMessage(record.getMessage());
	}

	private void logMessage(@NotNull String message) {
		logMessages.add(message);

		if (logMessages.size() > m)
			logMessages.remove(0);

		EventQueue.invokeLater(() -> {
			for (int j = 0; j < logMessages.size(); j++) {
				logs[m - j - 1].setText(logMessages.get(j));
				logs[m - j - 1].setToolTipText(logMessages.get(j));
			}
		});
	}

	/**
	 * clears the {@link FeedbackPanel#logMessages} {@link Vector}.
	 */
	@Override
	public void flush() {
		logMessages.clear();
	}

	/**
	 * Calls the {@link FeedbackPanel#flush()} method.
	 */
	@Override
	public void close() throws SecurityException {
		flush();
	}

	/**
	 * @return the <code>JPanel</code> used to display all the feedback.
	 */
	@NotNull
	public JPanel getPanel() {
		return panel;
	}
}
