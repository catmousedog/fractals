package me.catmousedog.fractals.paneloperators.functions;

import me.catmousedog.fractals.data.FractalValue;
import me.catmousedog.fractals.paneloperators.filters.BinaryFilter;
import me.catmousedog.fractals.paneloperators.filters.Filter;
import me.catmousedog.fractals.paneloperators.fractals.Fractal;

public class BinaryFunction extends Function {

	public BinaryFunction(Fractal fractal) {
		super(fractal);
		items = null;
		filters = new Filter[] { new BinaryFilter(fractal) };
		filter = filters[0];
	}

	private BinaryFunction(BinaryFunction function) {
		super(function);
	}

	@Override
	public Byte apply(FractalValue v) {
		return (byte) (v.i % 2);
	}

	@Override
	public Function clone() {
		return new BinaryFunction(this);
	}

	@Override
	public String informalName() {
		return "Binary Function";
	}

	@Override
	public String fileName() {
		return "BinaryFunction";
	}

	@Override
	public String getTip() {
		return "TODO";
	}

}
