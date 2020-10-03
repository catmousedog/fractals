package me.catmousedog.fractals.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;
import java.util.Vector;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.main.Main.InitialSize;

public class UIConsoleHandler extends Handler {

	protected final JPanel panel;

	/**
	 * amount of logged messages displayed
	 */
	private int m = 3;

	/**
	 * array of JLabels representing the logged messages
	 */
	private final JLabel[] logs = new JLabel[m];

	/**
	 * Stack of Strings representing the first to last logged messages
	 */
	private final Vector<String> logMessages = new Vector<String>();

	public UIConsoleHandler(InitialSize size) {
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setPreferredSize(new Dimension(size.getIwidth() + 2 * size.getHgap(), size.getFeedbackheight()));
		panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

		setLevel(Level.INFO);

		try {
			EventQueue.invokeAndWait(() -> {
				// log labels
				for (int j = 0; j < m; j++) {
					logs[j] = new JLabel();
					logs[j].setAlignmentX(JLabel.CENTER_ALIGNMENT);
					panel.add(logs[j]);
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			Logger.getLogger("fractals").severe("could not create logs array");
			e.printStackTrace();
		}
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

	@Override
	public void publish(LogRecord record) {
		if (!isLoggable(record))
			return;
		
		logMessage(record.getMessage());
	}

	@Override
	public void flush() {
		logMessages.clear();
	}

	@Override
	public void close() throws SecurityException {
		flush();
	}

	public JPanel getPanel() {
		return panel;
	}

}
