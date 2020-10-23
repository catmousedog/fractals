package me.catmousedog.fractals.paneloperators.filters;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.catmousedog.fractals.main.Settings;
import me.catmousedog.fractals.paneloperators.PanelOperator;
import me.catmousedog.fractals.paneloperators.fractals.Fractal;

public abstract class Filter extends PanelOperator {

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
	 * <li>the {@link PanelOperator#items}
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
	 * <p>
	 * Any implementation that uses this constructor should initialise:
	 * <ul>
	 * <li>the specific fields belonging to this <code>Filter</code>.
	 * </ul>
	 * 
	 * @param filter the <code>Filter</code> it should copy.
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
	 * Any implementation should save the {@link PanelOperator#items} if not null.
	 */
	public abstract void save();
	
	/**
	 * Any implementation should update the {@link PanelOperator#items} if not null.
	 */
	public abstract void update();
	
	@Override
	public abstract Filter clone();

}
