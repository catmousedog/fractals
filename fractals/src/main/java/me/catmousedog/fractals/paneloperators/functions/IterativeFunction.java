package me.catmousedog.fractals.paneloperators.functions;

import me.catmousedog.fractals.data.FractalValue;
import me.catmousedog.fractals.paneloperators.filters.Filter;
import me.catmousedog.fractals.paneloperators.filters.HueFilter;
import me.catmousedog.fractals.paneloperators.filters.LinearFilter;
import me.catmousedog.fractals.paneloperators.filters.PeriodicFilter;
import me.catmousedog.fractals.paneloperators.fractals.Fractal;

public class IterativeFunction extends Function {

	public IterativeFunction(Fractal fractal) {
		super(fractal);
		items = null;
		filters = new Filter[] { new LinearFilter(fractal), new PeriodicFilter(fractal), new HueFilter(fractal) };
		filter = filters[0];
	}

	private IterativeFunction(IterativeFunction function) {
		super(function);
	}

	@Override
	public Double apply(FractalValue v) {
		return (v.I - v.i) / (double) v.I;
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
