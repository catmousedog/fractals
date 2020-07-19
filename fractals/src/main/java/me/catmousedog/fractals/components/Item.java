package me.catmousedog.fractals.components;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 * Represents a group of {@link JComponent}s in the user interface.
 */
public abstract class Item {

	/**
	 * Creates a {@link Component} with this specific {@link Item} inside. <br>
	 * The Component might contain multiple {@link JComponent}s such as additional
	 * {@link JLabel}s.
	 * 
	 * @return the Component containing this {@link Item}
	 */
	public abstract Component panel();

	/**
	 * Takes the user entered data from the {@link Item#comp} and saves it to
	 * {@link Item#data}
	 */
	public abstract void save();

	/**
	 * Takes the {@link Item#data} and saves it to the {@link Item#comp}.
	 */
	public abstract void update();


}
