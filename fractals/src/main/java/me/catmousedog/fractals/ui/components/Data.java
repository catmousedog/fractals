package me.catmousedog.fractals.ui.components;

import javax.swing.JTextField;

/**
 * A data container extending the {@link Item} class. This class just represents
 * an {@link Item} that holds data.<br>
 * 
 * @param <T> The type of the stored data. <br>
 *            <b>For example:</b><br>
 *            a {@link Double} for a {@link JTextField}.
 */
public abstract class Data<T> extends Item {

	protected T data;

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
	 * Sets the data and calls {@link Data#update()}.
	 * 
	 * @param data the new data
	 */
	public void setData(T data) {
		this.data = data;
		update();
	}

}
