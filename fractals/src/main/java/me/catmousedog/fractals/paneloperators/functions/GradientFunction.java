package me.catmousedog.fractals.paneloperators.functions;

import me.catmousedog.fractals.data.FractalValue;
import me.catmousedog.fractals.paneloperators.filters.Filter;
import me.catmousedog.fractals.paneloperators.filters.LogPeriodicFilter;
import me.catmousedog.fractals.paneloperators.fractals.Fractal;

public class GradientFunction extends Function {

	{
		usesDerivative = true;
	}
	
	public GradientFunction(Fractal fractal) {
		super(fractal);
		items = null;
		filters = new Filter[] { new LogPeriodicFilter(fractal) };
		filter = filters[0];
	}

	private GradientFunction(GradientFunction function) {
		super(function);
	}

	@Override
	public Double apply(FractalValue v) {
		if (v.isConvergent())
			return 0d;
		return (0.5 * Math.log(v.x * v.x + v.y * v.y) / Math.pow(degree, v.i));
	}

	@Override
	public Function clone() {
		return new GradientFunction(this);
	}

	@Override
	public String informalName() {
		return "Gradient";
	}

	@Override
	public String fileName() {
		return informalName();
	}

	@Override
	public String getTip() {
		return "The gradient of the potential.";
	}
}
