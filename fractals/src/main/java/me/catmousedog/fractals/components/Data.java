package me.catmousedog.fractals.components;

import java.awt.event.ActionListener;

/**
 * A data container extending the {@link Item} class. This class just represents
 * an {@link Item} that holds data.<br>
 * <p>
 * <b>Active</b> {@link Item}s have an {@link ActionListener} and perform an
 * action themselves.
 * <p>
 * <b>Passive</b> {@link Item}s don't perform an action.
 * 
 * @param <T> The type of the stored data. <br>
 *            For example: a {@link Double} for a textfield.
 */
public abstract class Data<T> extends Item {

	protected T data;

	/**
	 * gets the data without saving
	 * 
	 * @return the current {@link Data#data}
	 */
	public T getData() {
		return data;
	}

	/**
	 * saves and gets the data
	 * 
	 * @return calls {@link Data#save()} and then returns {@link Data#data}
	 */
	public T saveAndGet() {
		save();
		return data;
	}

	/**
	 * sets the data
	 * 
	 * @param data sets the {@link Data#data} field and calls {@link Data#update()}
	 */
	public void setData(T data) {
		this.data = data;
		update();
	}

}
