package me.catmousedog.fractals.ui.components;

import javax.swing.JPanel;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.catmousedog.fractals.ui.SafeSavable;

/**
 * Represents a panel containing {@link Item}s.
 */
public abstract class UI implements SafeSavable {

	/**
	 * The array of <code>Items</code> in order of addition.<br>
	 * Null for clones.
	 */
	@Nullable
	protected final Item[] items;

	/**
	 * Used for disabling listeners in update calls. When a listener is fired it
	 * should always check this first to make sure it is allowed to fire.
	 */
	protected boolean allowListeners = true;

	/**
	 * Constructor used to initialise the object including the <code>Items</code>
	 * array.
	 * 
	 * @param items the array of <code>Items</code>.
	 */
	public UI(@Nullable Item[] items) {
		this.items = items;
	}

	/**
	 * Constructor used for cloning, leaves the <code>Items</code> as null.
	 */
	public UI() {
		items = null;
	}

	@Override
	public void save() {
		if (items != null) {
			for (Item item : items)
				item.save();
		}
	}

	@Override
	public void update() {
		if (items != null) {
			for (Item item : items)
				item.update();
		}
	}

	@Override
	public void preRender() {
		if (items != null) {
			for (Item item : items)
				item.preRender();
		}
	}

	@Override
	public void postRender() {
		if (items != null) {
			for (Item item : items)
				item.postRender();
		}
	}

	@Override
	public void safeUpdate() {
		allowListeners = false;
		update();
		allowListeners = true;
	}

	/**
	 * Adds all the <code>Items</code> from {@link UI#items} to the <code>jp</code>.
	 * 
	 * @param jp the <code>JPanel</code> to which the <code>Items</code> should be
	 *           added.
	 */
	public void addPanel(@NotNull JPanel jp) {
		if (items != null) {
			for (Item i : items)
				jp.add(i.panel());
		}
	}

}
