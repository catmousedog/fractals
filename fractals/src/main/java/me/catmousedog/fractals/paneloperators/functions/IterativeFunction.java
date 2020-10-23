package me.catmousedog.fractals.paneloperators.functions;

import me.catmousedog.fractals.data.FractalValue;
import me.catmousedog.fractals.paneloperators.filters.Filter;
import me.catmousedog.fractals.paneloperators.filters.IterativeLinearFilter;
import me.catmousedog.fractals.paneloperators.filters.IterativePeriodicFilter;
import me.catmousedog.fractals.paneloperators.fractals.Fractal;

public class IterativeFunction extends Function {

	public IterativeFunction(Fractal fractal) {
		super(fractal);
		items = null;
		filters = new Filter[] { new IterativeLinearFilter(fractal), new IterativePeriodicFilter(fractal) };
		filter = filters[0];
	}

	private IterativeFunction(Function function) {
		super(function);
	}

	@Override
	public Integer apply(FractalValue v) {
		return 255 * (v.I - v.i) / v.I;
	}

	@Override
	public Function clone() {
		return new IterativeFunction(this);
	}

	@Override
	public String informalName() {
		return "Iterative";
	}

	@Override
	public String fileName() {
		return informalName();
	}

	@Override
	public String getTip() {
		return "TODO";
	}

}