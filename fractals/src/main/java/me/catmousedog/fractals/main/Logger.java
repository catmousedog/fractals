package me.catmousedog.fractals.main;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.jetbrains.annotations.NotNull;

@SuppressWarnings("serial")
public class Logger extends JPanel {
	
	private final JLabel log;
	
	private final JLabel progress;
	
	private final JProgressBar jpb;
	
	public Logger(int w, int h) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setPreferredSize(new Dimension(w, h));
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		log = new JLabel();
		log.setAlignmentX(0.5F);
		add(log);
		
		add(Box.createVerticalGlue());
		
		progress = new JLabel();
		progress.setAlignmentX(0.5F);
		add(progress);
		jpb = new JProgressBar();
		add(jpb);
		
		setProgress(0);
	}
	
	/**
	 * Log message so the user can see it in the feedback panel
	 * @param message to be logged
	 */
	public void log(@NotNull String message) {
		log.setText(message);
	}
	
	/**
	 * sets the progress message
	 * @param progressMessage
	 */
	public void setProgressMessage(@NotNull String progressMessage) {
		progress.setText(progressMessage);
	}
	
	/**
	 * sets the progress bars' value
	 * @param value int between 0 - 100 (inclusive)
	 */
	public void setProgress(int value) {
		jpb.setValue(value);
		progress.setText(String.format("%s %d%s", progress.getText(), value, "%"));
	}
}
