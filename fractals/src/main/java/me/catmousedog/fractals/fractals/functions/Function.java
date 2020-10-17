package me.catmousedog.fractals.fractals.functions;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.catmousedog.fractals.fractals.FractalValue;
import me.catmousedog.fractals.fractals.abstract_fractals.Fractal;
import me.catmousedog.fractals.fractals.filters.Filter;
import me.catmousedog.fractals.main.Settings;
import me.catmousedog.fractals.ui.components.PanelConstruct;

public abstract class Function extends PanelConstruct {

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
	 * Any implementation that uses this constructor should initialise:
	 * <ul>
	 * <li>the {@link PanelConstruct#items}
	 * <li>the {@link Function#filters} and the {@link Function#filter}
	 * <li>the specific fields belonging to this <code>Function</code>
	 * </ul>
	 * 
	 * @param fractal the <code>Fractal</code> to which this <code>Function</code>
	 *                belongs.
	 */
	protected Function(Fractal fractal) {
		super();
		this.fractal = fractal;
	}

	/**
	 * Constructor used to create a clone.<br>
	 * This constructor must be overridden in the child class so it takes itself as
	 * the parameter <code>function</code>. This way it can make an exact copy
	 * without any reference to the original <code>Function</code>.
	 * <p>
	 * Any implementation that uses this constructor should initialise:
	 * <ul>
	 * <li>the specific fields belonging to this <code>Function</code>.
	 * </ul>
	 * 
	 * @param function the <code>Function</code> it should copy.
	 */
	protected Function(@NotNull Function function) {
		super();
		filters = null;
		filter = function.getFilter().clone();
	}

	@NotNull
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

	/**
	 * @return the array of active <code>Filters</code>.
	 */
	@Nullable
	public Filter[] getFilters() {
		return filters;
	}

	/**
	 * @return the current <code>Filter</code>.
	 */
	@NotNull
	public Filter getFilter() {
		return filter;
	}

	@Override
	public abstract Function clone();

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

}