package me.catmousedog.fractals.fractals.functions;

import me.catmousedog.fractals.fractals.FractalValue;
import me.catmousedog.fractals.fractals.abstract_fractals.Fractal;
import me.catmousedog.fractals.fractals.filters.Filter;
import me.catmousedog.fractals.fractals.filters.IterativeLinearFilter;
import me.catmousedog.fractals.fractals.filters.IterativePeriodicFilter;

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
