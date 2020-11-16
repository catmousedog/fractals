package me.catmousedog.fractals.paneloperators.functions;

import me.catmousedog.fractals.data.FractalValue;
import me.catmousedog.fractals.paneloperators.filters.Filter;
import me.catmousedog.fractals.paneloperators.filters.IterativeLinearFilter;
import me.catmousedog.fractals.paneloperators.filters.IterativePeriodicFilter;
import me.catmousedog.fractals.paneloperators.filters.TestFilter;
import me.catmousedog.fractals.paneloperators.fractals.Fractal;

public class TestFunction extends Function {

	public TestFunction(Fractal fractal) {
		super(fractal);
		items = null;
		filters = new Filter[] { new IterativeLinearFilter(fractal), new IterativePeriodicFilter(fractal),
				new TestFilter(fractal) };
		filter = filters[0];
	}

	private TestFunction(Function function) {
		super(function);
	}

	@Override
	public Double apply(FractalValue v) {
		return v.y;
	}

	@Override
	public Function clone() {
		return new TestFunction(this);
	}

	@Override
	public String informalName() {
		return "Test Function";
	}

	@Override
	public String fileName() {
		return "TestFunction";
	}

	@Override
	public String getTip() {
		return "TEST FUNCTION";
	}
}
