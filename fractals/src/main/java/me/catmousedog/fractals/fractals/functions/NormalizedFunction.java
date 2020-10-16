package me.catmousedog.fractals.fractals.functions;

import me.catmousedog.fractals.fractals.FractalValue;
import me.catmousedog.fractals.fractals.abstract_fractals.Fractal;
import me.catmousedog.fractals.fractals.filters.Filter;
import me.catmousedog.fractals.fractals.filters.LogPeriodicFilter;

public class NormalizedFunction extends Function {

	private int offset;

	public NormalizedFunction(Fractal fractal) {
		super(fractal);
		items = null;
		filters = new Filter[] { new LogPeriodicFilter(fractal) };
		filter = filters[0];
		offset = 1;
	}

	private NormalizedFunction(Function function, int offset) {
		super(function);
		this.offset = offset;
	}

	@Override
	public Double apply(FractalValue v) {
		return v.i + offset - Math.log(Math.log(v.x * v.x + v.y * v.y) / 2) / Math.log(2);
	}

	@Override
	public Function clone() {
		return new NormalizedFunction(this, offset);
	}

	@Override
	public String informalName() {
		return "Normalized";
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
