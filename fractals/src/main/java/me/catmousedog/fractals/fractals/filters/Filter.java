package me.catmousedog.fractals.fractals.filters;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.catmousedog.fractals.fractals.abstract_fractals.Fractal;
import me.catmousedog.fractals.main.Settings;
import me.catmousedog.fractals.ui.components.UI;
import me.catmousedog.fractals.utils.Nameable;

public abstract class Filter extends UI implements Nameable {

	/**
	 * The <code>Fractal</code> to which this <code>Filter</code> belongs to.
	 * <p>
	 * Null for clones.
	 */
	@Nullable
	protected Fractal fractal;

	/**
	 * Constructor used to initialise the <code>Function</code>.<br>
	 * Only used once for each <code>Function</code> in the {@link Settings}.
	 * <p>
	 * Any implementation that uses this constructor should initialise:
	 * <ul>
	 * <li>the {@link UI#items}
	 * <li>the specific fields belonging to this <code>Filter</code>
	 * </ul>
	 * 
	 * @param fractal the <code>Fractal</code> to which this <code>Filter</code>
	 *                belongs.
	 */
	protected Filter(@NotNull Fractal fractal) {
		super();
		this.fractal = fractal;
	}

	/**
	 * Constructor used to create a clone.<br>
	 * This constructor must be overridden in the child class so it takes itself as
	 * the parameter <code>function</code>. This way it can make an exact copy
	 * without any reference to the original <code>Function</code>.
	 * 
	 * @param function the <code>Function</code> it should copy.<br>
	 *                 This parameter should
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

	@Override
	public String toString() {
		return informalName();
	}

}
