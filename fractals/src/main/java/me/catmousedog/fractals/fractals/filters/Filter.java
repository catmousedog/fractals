package me.catmousedog.fractals.fractals.filters;

import java.awt.Font;

import javax.swing.JPanel;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.ui.JPInterface;
import me.catmousedog.fractals.ui.Savable;
import me.catmousedog.fractals.ui.components.Item;
import me.catmousedog.fractals.ui.components.concrete.Label;

/**
 * An abstract colour {@link Filter} including the {@link Filter#get(Number)}
 * function.
 * <p>
 * Each implementation of this class must define:
 * <ul>
 * <li>The necessary fields without assigning their values. (the latter is done
 * in the initialising constructor.
 * <li>The initialising constructor for creating the {@link Filter}.
 * <li>The constructor for cloning, taking a {@link Filter} as parameter.
 * <li>The colour function {@link Filter#get(Number)}
 * <li>{@link Filter#setFilter(Filter)} for cloning.
 * <li>All methods from the {@link Savable} interface.
 * <li>The {@link Filter#clone()} method
 * <li>The {@link Filter#initPanel()} and all of its components.
 * </ul>
 */
public abstract class Filter implements Savable {

	/**
	 * Array of all {@link Item}s in order of addition.<br>
	 * Only not null for the original {@link Filter}, not a clone of it.
	 */
	@Nullable
	protected Item[] items;

	/**
	 * The {@link Fractal} to which this {@link Filter} belongs to.<br>
	 * Only not null for the original {@link Filter}, not a clone of it.
	 */
	@Nullable
	protected Fractal fractal;

	/**
	 * Creates a new {@link Filter}, should only be used once for each
	 * {@link Filter}.
	 * <p>
	 * This constructor should initialise all of the fields that are copied when
	 * {@link Filter#clone()} is called. <br>
	 * These fields should <b>NOT</b> be given a default value any other way.
	 */
	protected Filter(@NotNull Fractal fractal) {
		this.fractal = fractal;
		initPanel();
	}

	/**
	 * Creates a new {@link Filter} without calling {@link Filter#initPanel()} that
	 * is an exact copy but has no reference to the original <code>filter</code>.
	 * 
	 * @param filter
	 */
	protected Filter(@NotNull Filter filter) {
		setFilter(filter);
	}

	/**
	 * Applies the calculated value 'v' to a colour filter and returns an integer
	 * rgb value.
	 * 
	 * @param v value calculated by the fractal
	 * @return the rgb value as an integer
	 */
	public abstract int get(@NotNull Number v);

	/**
	 * Change this {@link Filter} to equal the given <code>filter</code>.
	 * 
	 * @param filter
	 */
	public abstract void setFilter(@NotNull Filter filter);

	/**
	 * Clones the {@link Filter}.<br>
	 * A clone should have no reference to the original and shouldn't call
	 * {@link Filter#initPanel()}.
	 */
	@Override
	public abstract Filter clone();

	/**
	 * Initialises the <code>Filter's</code> panel. This includes creating all the
	 * <code>JComponents</code> and creating the {@link Filter#items}.<br>
	 * This is only done once for each <code>Filter</code>, so not when the
	 * <code>Filter</code> is cloned.
	 */
	protected abstract void initPanel();

	private Label fi = new Label("Filter", "Settings for the current filter.", Font.BOLD, 12);
	
	/**
	 * Adds all the necessary components to a given {@link JPanel} on the
	 * {@link JPInterface}.
	 * 
	 * @param jp The JPanel to add the {@link Item}s to.
	 */
	 public void addPanel(@NotNull JPanel jp) {
		jp.add(fi.panel());
		for (Item i : items)
			jp.add(i.panel());
	}

	/**
	 * Calls the {@link Savable#preRender()} for all {@link Item}s inside
	 * {@link Filter#items}.
	 */
	@Override
	public void preRender() {
		for (Item i : items)
			i.preRender();
	}

	/**
	 * Calls the {@link Savable#postRender()} for all {@link Item}s inside
	 * {@link Filter#items}.
	 */
	@Override
	public void postRender() {
		for (Item i : items)
			i.postRender();
	}

	/**
	 * @return A {@link String} used as a tooltip for the user to read.
	 */
	public abstract String getTip();

	@Override
	public abstract String toString();

}
