package me.catmousedog.fractals.ui;

public interface SafeSavable extends Savable {

	/**
	 * Safely update without calling any listeners.
	 */
	public void safeUpdate();

}
