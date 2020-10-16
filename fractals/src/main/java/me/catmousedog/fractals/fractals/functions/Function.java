package me.catmousedog.fractals.fractals.functions;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.fractals.FractalValue;
import me.catmousedog.fractals.fractals.filters.Filter;
import me.catmousedog.fractals.main.Settings;
import me.catmousedog.fractals.ui.Savable;
import me.catmousedog.fractals.ui.components.Item;
import me.catmousedog.fractals.ui.components.UI;

public abstract class Function extends UI implements Savable {

	/**
	 * The <code>Fractal</code> to which this <code>Function</code> belongs to.
	 * <p>
	 * Null for clones.
	 */
	@Nullable
	protected Fractal fractal;

	/**
	 * All the <code>Filters</code> belonging to this <code>Function</code>.
	 * <p>
	 * Null for clones.
	 */
	@Nullable
	protected Filter[] filters;

	/**
	 * The current <code>Filter</code>.
	 */
	@NotNull
	protected Filter filter;

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
	protected Function(@NotNull Item[] items) {
		super(items);
	}

	/**
	 * Constructor used to create a clone.<br>
	 * This constructor must be overridden in the child class so it takes itself as
	 * the parameter <code>function</code>. This way it can make an exact copy
	 * without any reference to the original <code>Function</code>.
	 * 
	 * @param function the <code>Function</code> it should copy.
	 */
	protected Function(@NotNull Function function) {
		super();
		filters = null;
		filter = function.getFilter().clone();
	}

	public abstract Number apply(FractalValue v);

	@Override
	public void save() {
		super.save();
		filter.save();
	}

	@Override
	public void update() {
		super.update();
		filter.update();
	}

	@Override
	public void preRender() {
		super.preRender();
		filter.preRender();
	}

	@Override
	public void postRender() {
		super.postRender();
		filter.postRender();
	}

	public abstract String getTip();

	@Override
	public abstract Function clone();

	public Filter[] getFilters() {
		return filters;
	}

	/**
	 * Sets the {@link Function#filter} to the <code>Filter</code> whose class
	 * equals the given <code>clazz</code>.
	 * 
	 * @param clazz the <code>Class</code> of the <code>Filter</code>.
	 */
	public void pickFilter(Class<? extends Filter> clazz) {
		for (Filter f : filters) {
			if (f.getClass().equals(clazz)) {
				this.filter = f;
				return;
			}
		}
	}

	public Filter getFilter() {
		return filter;
	}

}
