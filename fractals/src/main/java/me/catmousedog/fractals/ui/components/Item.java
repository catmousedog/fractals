package me.catmousedog.fractals.ui.components;

import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 * Represents a group of {@link JComponent}s in the user interface.
 * <p>
 * <b>Active</b> {@link Item}s have an {@link ActionListener} and perform an
 * action themselves.
 * <p>
 * <b>Passive</b> {@link Item}s don't perform an action.
 */
public abstract class Item implements Savable {

	/**
	 * Creates a {@link Component} with this specific {@link Item} inside. <br>
	 * The Component might contain multiple {@link JComponent}s such as additional
	 * {@link JLabel}s.
	 * 
	 * @return the Component containing this {@link Item}
	 */
	public abstract Component panel();

	/**
	 * Takes the user entered data from this Item and saves it to {@link Data#data}
	 * <p>
	 * If the concrete class extends {@link Item} and not {@link Data}, this method
	 * is obsolete and should be left empty.
	 */
	@Override
	public abstract void save();

	/**
	 * Takes the {@link Data#data} and saves it to this Item.
	 * <p>
	 * If the concrete class extends {@link Item} and not {@link Data}, this method
	 * is obsolete and should be left empty.
	 */
	@Override
	public abstract void update();
}
