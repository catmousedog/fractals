package me.catmousedog.fractals.fractals.functions;

import me.catmousedog.fractals.fractals.FractalValue;
import me.catmousedog.fractals.fractals.abstract_fractals.Fractal;
import me.catmousedog.fractals.fractals.filters.Filter;
import me.catmousedog.fractals.fractals.filters.LogPeriodicFilter;

public class PotentialFunction extends Function {

	public PotentialFunction(Fractal fractal) {
		super(fractal);
		items = null;
		filters = new Filter[] { new LogPeriodicFilter(fractal) };
		filter = filters[0];
	}

	private PotentialFunction(Function function) {
		super(function);
	}

	@Override
	public Double apply(FractalValue v) {
		return Math.log(v.x * v.x + v.y * v.y) / Math.pow(2, v.i);
	}

	@Override
	public Function clone() {
		return new PotentialFunction(this);
	}

	@Override
	public String informalName() {
		return "Potential";
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
