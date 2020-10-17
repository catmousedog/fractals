package me.catmousedog.fractals.ui.components;

import java.util.logging.Logger;

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
	protected Item[] items;

	@NotNull
	protected final Logger logger = Logger.getLogger("fractals");

	/**
	 * Used for disabling listeners in update calls. When a listener is fired it
	 * should always check this first to make sure it is allowed to fire.
	 */
	protected boolean allowListeners = true;

	/**
	 * Constructor used for both cloning and initialisation.<br>
	 * The <code>items</code> array can only be null for clones.
	 */
	protected UI() {
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

	/**
	 * @return the {@link #UI()#informalName()}.
	 */
	@Override
	public String toString() {
		return informalName();
	}

	/**
	 * @return the <code>String</code> for the user to read. Beware that too long
	 *         <code>Strings</code> can cause issues.
	 */
	public abstract String informalName();

	/**
	 * @return the file name, usually the <code>informalName</code> without spaces.
	 */
	public abstract String fileName();

	/**
	 * @return a description for the user to read.
	 */
	public abstract String getTip();

	/**
	 * @return A clone of this <code>UI</code>. <br>
	 *         A clone should have no reference to the original.
	 */
	@Override
	public abstract UI clone();

	/**
	 * True if the given <code>object</code> is of the same <code>Class</code> as
	 * this instance. <br>
	 * The parameter can not be null.
	 */
	@Override
	public boolean equals(@NotNull Object object) {
		return object.getClass().equals(getClass());
	}

}
