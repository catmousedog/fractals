package me.catmousedog.fractals.paneloperators.functions;

import me.catmousedog.fractals.data.FractalValue;
import me.catmousedog.fractals.paneloperators.filters.Filter;
import me.catmousedog.fractals.paneloperators.filters.LogPeriodicFilter;
import me.catmousedog.fractals.paneloperators.fractals.Fractal;

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
