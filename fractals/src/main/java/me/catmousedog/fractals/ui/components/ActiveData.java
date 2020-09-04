package me.catmousedog.fractals.ui.components;

import java.util.EventObject;

import javax.swing.JTextField;

import me.catmousedog.fractals.ui.SafeSavable;
import me.catmousedog.fractals.ui.Savable;

/**
 * Represents {@link Data} that has at least one 'active' listener.
 * <p>
 * An 'active' listener is a listener that gets called upon calling
 * {@link Data#update()}. <br>
 * An 'active' listener should call {@link ActiveData#event(EventObject)} and
 * the actual event should be implemented through
 * {@link ActiveData#concreteEvent(EventObject)}
 * <p>
 * This class implements new safe methods such as
 * {@link ActiveData#safeUpdate()} which prevent any listeners from firing.
 *
 * @param <T> The type of the stored data. <br>
 *            <b>For example:</b><br>
 *            a {@link Double} for a {@link JTextField}.
 */
public abstract class ActiveData<T> extends Data<T> implements SafeSavable {

	protected boolean allowListeners = true;

	/**
	 * Event that each listener should call that is considered an 'active' listener.
	 * <p>
	 * <b>For example:</b><br>
	 * <code>c.addActionListener(a -> event(a));</code><br>
	 * <code>c.addChangeListener(c -> event(c));</code>
	 * <p>
	 * The actual event code should be done in
	 * {@link ActiveData#concreteEvent(EventObject)}.
	 * 
	 * @param e the {@link EventObject} passed by the original event.
	 */
	protected void event(EventObject e) {
		if (allowListeners)
			concreteEvent(e);
			
	}

	/**
	 * This event will be called when an 'active' listener fires.<br>
	 * This event will not fire if the listener was fired through
	 * {@link ActiveData#safeUpdate()}.
	 * 
	 * @param e the {@link EventObject} passed by the original event.
	 */
	protected abstract void concreteEvent(EventObject e);

	/**
	 * Calls {@link Savable#update()} without calling any of the listeners.
	 */
	@Override
	public void safeUpdate() {
		allowListeners = false;
		update();
		allowListeners = true;
	}

	/**
	 * Same as {@link Data#setData(Object)} but calls
	 * {@link ActiveData#safeUpdate()} instead of {@link Data#update()}
	 * 
	 * @param data the new data
	 */
	public void setDataSafe(T data) {
		this.data = data;
		safeUpdate();
	}

}
