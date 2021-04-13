package me.catmousedog.fractals.paneloperators.functions;

import java.util.logging.Level;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.catmousedog.fractals.data.FractalValue;
import me.catmousedog.fractals.main.Settings;
import me.catmousedog.fractals.paneloperators.PanelOperator;
import me.catmousedog.fractals.paneloperators.filters.Filter;
import me.catmousedog.fractals.paneloperators.fractals.Fractal;

/**
 * An operator applied after the {@link Fractal#get(double, double)}.
 */
public abstract class Function extends PanelOperator {

	/**
	 * True if this <code>Function</code> utilises the derivative.<br>
	 * Only matters for the original functions and not clones of it.
	 */
	protected boolean usesDerivative = false;

	/**
	 * {@link Fractal#getDegree()}
	 */
	protected double degree;
	
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
	 * <li>the {@link PanelOperator#items}
	 * <li>the {@link Function#filters} and the {@link Function#filter}
	 * <li>the specific fields belonging to this <code>Function</code>
	 * </ul>
	 * 
	 * @param fractal the <code>Fractal</code> to which this <code>Function</code>
	 *                belongs.
	 */
	protected Function(Fractal fractal) {
		super();
		logger.log(Level.FINER, "Function " + className + " init");
		this.fractal = fractal;
		this.degree = fractal.getDegree();
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
		logger.log(Level.FINEST, "Function " + className + " clone");
		filters = null;
		filter = function.getFilter().clone();
		degree = function.degree;
	}

	@NotNull
	public abstract Number apply(FractalValue v);

	/**
	 * Saves the
	 * <ul>
	 * <li>{@link Function#filter}
	 * </ul>
	 * Any implementation should save the {@link PanelOperator#items} if not null.
	 */
	@Override
	public void save() {
		degree = fractal.getDegree();
		filter.save();
	}

	/**
	 * Updates the
	 * <ul>
	 * <li>{@link Function#filter}
	 * </ul>
	 * Any implementation should update the {@link PanelOperator#items} if not null.
	 */
	@Override
	public void update() {
		filter.update();
	}

	/**
	 * Prerenders the
	 * <ul>
	 * <li>{@link PanelOperator#items}
	 * <li>{@link Function#filter}
	 * </ul>
	 */
	@Override
	public void preRender() {
		super.preRender();
		filter.preRender();
	}

	/**
	 * Postrenders the
	 * <ul>
	 * <li>{@link PanelOperator#items}
	 * <li>{@link Function#filter}
	 * </ul>
	 */
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

	public boolean isUsingDerivative() {
		return usesDerivative;
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
		logger.log(Level.FINEST, "Function " + className + ".pickFilter " + clazz.getSimpleName());
		for (Filter f : filters) {
			if (f.getClass().equals(clazz)) {
				this.filter = f;
				return;
			}
		}
	}

}
