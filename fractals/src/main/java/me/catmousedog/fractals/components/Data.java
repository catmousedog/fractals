package me.catmousedog.fractals.components;

import java.awt.event.ActionListener;

import javax.swing.JComponent;

/**
 * A data container in the form of one or multiple {@link JComponent}s.<br>
 * An instance of {@link Data} can be 'active', meaning it takes in a
 * {@link ActionListener} as a parameter to the constructor i.e. it actively
 * performs an action. Or it can be 'passive', meaning the data has to be
 * retrieved manually through the {@link Data#getData()} when needed.
 * 
 * @param <T> The type of the stored data. <br>
 *            For example: a {@link Double} for a textfield.
 */
public abstract class Data<T> extends Item {

	protected T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
		update();
	}

	public T saveAndGet() {
		save();
		return data;
	}
}
