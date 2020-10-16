package me.catmousedog.fractals.fractals.filters;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.fractals.functions.Function;
import me.catmousedog.fractals.main.Settings;
import me.catmousedog.fractals.ui.Savable;
import me.catmousedog.fractals.ui.components.Item;
import me.catmousedog.fractals.ui.components.UI;

/**
 * An abstract colour {@link Filter} including the {@link Filter#apply(Number)}
 * function.
 * <p>
 * Each implementation of this class must define:
 * <ul>
 * <li>The necessary fields without assigning their values. (the latter is done
 * in the initialising constructor.
 * <li>The initialising constructor for creating the {@link Filter}.
 * <li>The constructor for cloning, taking a {@link Filter} as parameter.
 * <li>The colour function {@link Filter#apply(Number)}
 * <li>{@link Filter#setFilter(Filter)} for cloning.
 * <li>All methods from the {@link Savable} interface.
 * <li>The {@link Filter#clone()} method
 * <li>The {@link Filter#initPanel()} and all of its components.
 * </ul>
 */
public abstract class Filter extends UI implements Savable {

	/**
	 * The <code>Fractal</code> to which this <code>Filter</code> belongs to.
	 * <p>
	 * Null for clones.
	 * 
	 */
	@Nullable
	protected Fractal fractal;

//	/**
//	 * Creates a new <code>Filter</code>, should only be used once for each
//	 * <code>Filter</code> of each <code>Fractal</code>.
//	 * <p>
//	 * This constructor should initialise all of the fields that are copied when
//	 * {@link Filter#clone()} is called. <br>
//	 * These fields should <b>NOT</b> be given a default value any other way.
//	 * 
//	 * @param fractal the <code>Fractal</code> to which this <code>Filter</code>
//	 *                belongs.
//	 */
//	protected Filter(@NotNull Fractal fractal) {
//		this.fractal = fractal;
//		initPanel();
//	}
	
	/**
	 * Constructor used to initialise the <code>Function</code>.<br>
	 * Only used once for each <code>Function</code> in the {@link Settings}.
	 * <p>
	 * This constructor should initialise
	 * <ul>
	 * <li>the {@link UI#items}
	 * <li>the {@link Function#filters} and the {@link Function#filter}
	 * </ul>
	 * 
	 * @param settings
	 */
	protected Filter(@NotNull Fractal fractal, @NotNull Item[] items) {
		super(items);
		this.fractal = fractal;
	}

	/**
	 * Constructor used to create a clone.<br>
	 * This constructor must be overridden in the child class so it takes itself as
	 * the parameter <code>function</code>. This way it can make an exact copy
	 * without any reference to the original <code>Function</code>.
	 * 
	 * @param function the <code>Function</code> it should copy.
	 */
	protected Filter(@NotNull Filter filter) {
		super();
	}

	/**
	 * Applies the calculated value 'v' to a colour filter and returns an integer
	 * rgb value.
	 * 
	 * @param v value calculated by the fractal
	 * 
	 * @return the rgb value as an integer
	 */
	public abstract int apply(@NotNull Number v);

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
	 * @return A {@link String} used as a tooltip for the user to read.
	 */
	public abstract String getTip();

	@Override
	public abstract String toString();

}
