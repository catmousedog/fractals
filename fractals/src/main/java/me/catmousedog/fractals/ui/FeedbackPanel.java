package me.catmousedog.fractals.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.main.Settings;

public class FeedbackPanel extends Handler implements Runnable {

	private static FeedbackPanel FEEDBACK_PANEL;

	private final Settings settings = Settings.getInstance();

	private final Logger logger = Logger.getLogger("fractals");

	/**
	 * The <code>JPanel</code> in which all the feedback is displayed.
	 */
	private JPanel panel;

	/**
	 * <code>JLabel</code> displaying the last generating time.
	 */
	private JLabel generateTime;

	/**
	 * <code>JLabel</code> displaying the last colouring time.
	 */
	private JLabel colourTime;

	/**
	 * <code>JLabel</code> above the progress bar.
	 */
	private JLabel lblGenerator;

	/**
	 * The progress bar at the bottom of the feedback panel.
	 */
	private JProgressBar jpbGenerator;

	private JLabel lblPainter;

	private JProgressBar jpbPainter;

	/**
	 * @return the instance of the <code>FeedbackPanel</code>.<
	 */
	@NotNull
	public static FeedbackPanel getInstance() {
		if (FEEDBACK_PANEL == null)
			FEEDBACK_PANEL = new FeedbackPanel();
		return FEEDBACK_PANEL;
	}

	private FeedbackPanel() {
		logger.log(Level.FINER, "FeedbackPanel init");

		if (!EventQueue.isDispatchThread()) {
			try {
				EventQueue.invokeAndWait(this);
			} catch (InvocationTargetException | InterruptedException e) {
				logger.log(Level.SEVERE, "FeedbackPanel init failed", e);
			}
		} else {
			run();
		}
	}

	/**
	 * Initialises the <code>FeedbackPanel</code>, run on the EDT.
	 */
	@Override
	public void run() {
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setPreferredSize(
				new Dimension(settings.getIwidth() + 2 * settings.getHgap(), settings.getFeedbackheight()));
		panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		setLevel(Level.FINE);

		// log labels
		for (int j = 0; j < maxLogs; j++) {
			logs[j] = new LogMessage();
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

		// generator
		lblGenerator = new JLabel("not generated");
		lblGenerator.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		panel.add(lblGenerator);

		panel.add(Box.createVerticalStrut(2));

		jpbGenerator = new JProgressBar();
		panel.add(jpbGenerator);

		// painter
		lblPainter = new JLabel("not painted");
		lblPainter.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		panel.add(lblPainter);

		panel.add(Box.createVerticalStrut(2));

		jpbPainter = new JProgressBar();
		panel.add(jpbPainter);

		setGeneratorProgress("no calculations", 0);
	}

	/**
	 * sets the progress message and the progress bar value for the
	 * <code>generator</code>.<br>
	 * Must be run on the EDT.
	 * 
	 * @param progressMessage the message above the progress bar without the
	 *                        progress value
	 * @param p               percentage of the progress bar
	 */
	public void setGeneratorProgress(@NotNull String progressMessage, int p) {
		jpbGenerator.setValue(p);
		lblGenerator.setText(String.format("%s - %d%%", progressMessage, p));
	}

	/**
	 * sets the progress message and the progress bar value for the
	 * <code>painter</code>.<br>
	 * Must be run on the EDT.
	 * 
	 * @param progressMessage the message above the progress bar without the
	 *                        progress value
	 * @param p               percentage of the progress bar
	 */
	public void setPainterProgress(@NotNull String progressMessage, int p) {
		jpbPainter.setValue(p);
		lblPainter.setText(String.format("%s - %d%%", progressMessage, p));
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

		EventQueue.invokeLater(() -> logMessage(record.getMessage()));
	}

	private void logMessage(@NotNull String message) {
		logMessages.add(message);

		if (logMessages.size() > maxLogs) {
			logMessages.remove(0);
		}

		// move logs down one
		for (int j = maxLogs - 1; j > 0; j--) {
			if (logs[j - 1].timer.isRunning()) {
				logs[j].set(logs[j - 1]);
			}
		}

		// insert top log
		logs[0].start(logMessages.get(logMessages.size() - 1));
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

	/**
	 * Maximum amount of logged messages displayed.
	 */
	private int maxLogs = 3;

	private final LogMessage[] logs = new LogMessage[maxLogs];

	private final Vector<String> logMessages = new Vector<String>();

	@SuppressWarnings("serial")
	private class LogMessage extends JLabel implements ActionListener {

		private final Timer timer = new Timer(50, null);

		private float alpha;

		private int b, i, f;

		private LogMessage() {
			setAlignmentX(JLabel.CENTER_ALIGNMENT);
			setFont(new Font(null, Font.PLAIN, 11));
			timer.setRepeats(true);
			timer.addActionListener(this);
		}

		private void start(String text) {
			b = 100;
			f = 20;
			i = b;
			setText(text);
			setAlpha(0.99f);
			timer.start();
		}

		private void stop() {
			logMessages.remove(getText());
			setText(null);
			setAlpha(0f);
			timer.stop();
		}

		@Override
		public void actionPerformed(ActionEvent a) {
			i--;

			if (i < f) {
				setAlpha(i / (float) f);
			}

			if (i < 0) {
				stop();
			}
		}

		/**
		 * Change this <code>LogMessage</code> to correspond to the given. Used for
		 * moving <code>LogMessages</code> down.
		 * <p>
		 * Can only be used for active <code>LogMessages</code>.
		 * 
		 * @param logMessage
		 */
		private void set(LogMessage logMessage) {
			if (logMessage.timer.isRunning()) {
				b = logMessage.b;
				i = logMessage.i;
				f = logMessage.f;
				setText(logMessage.getText());
				setAlpha(logMessage.alpha);
				timer.start();
			}
		}

		private void setAlpha(float alpha) {
			this.alpha = alpha;
			if (0 <= alpha && alpha <= 1)
				setForeground(new Color(0f, 0f, 0f, alpha));
		}

		@Override
		public void setText(String text) {
			super.setText(text);
			setToolTipText(text);
		}
	}
}
