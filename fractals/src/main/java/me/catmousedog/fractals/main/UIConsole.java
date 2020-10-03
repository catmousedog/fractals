package me.catmousedog.fractals.main;

import java.awt.EventQueue;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JProgressBar;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.main.Main.InitialSize;

/**
 * This class is used to log messages to the user and show any outputs
 */
public class UIConsole extends UIConsoleHandler {

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

	public UIConsole(InitialSize size) {
		super(size);

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

		EventQueue.invokeLater(() -> {
			setProgress("no calculations", 0);
		});
	}

	public void log(String m) {

	}

	public void exception(Exception e) {

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
	 * This method does not need to be called from the EDT.
	 * 
	 * @param ms time in milliseconds
	 */
	public void setGenerated(long ms) {
		EventQueue.invokeLater(() -> {
			generateTime.setText(String.format("generated in %d ms!", ms));
		});
	}

	/**
	 * Display the time it took to colour.<br>
	 * This method does not need to be called from the EDT.
	 * 
	 * @param ms time in milliseconds
	 */
	public void setColoured(long ms) {
		EventQueue.invokeLater(() -> {
			colourTime.setText(String.format("coloured in %d ms!", ms));
		});
	}

}
