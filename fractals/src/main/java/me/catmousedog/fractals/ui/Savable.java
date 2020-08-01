package me.catmousedog.fractals.ui;

import me.catmousedog.fractals.ui.components.Data;

/**
 * Interface representing an object that can be interacted with by the user. The
 * user entered data can be saved or updated to the internal data.
 */
public interface Savable {

	/**
	 * Will apply all of the user entered data.
	 * <p>
	 * If the {@link Savable} contains only {@link Data}, the
	 * {@link Data#saveAndGet()} is used for each {@link Data} object.
	 */
	public void save();

	/**
	 * Will update the enterable data to the saved internal data.
	 * <p>
	 * If the {@link Savable} contains only {@link Data}, the {@link Data#update()}
	 * is used for each {@link Data} object.
	 */
	public void update();

	/**
	 * Will perform any actions required pre render.
	 */
	public void preRender();

	/**
	 * Will perform any actions required post render.
	 */
	public void postRender();

}
