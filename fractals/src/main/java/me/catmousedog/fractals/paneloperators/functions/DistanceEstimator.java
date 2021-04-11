package me.catmousedog.fractals.paneloperators.functions;

import me.catmousedog.fractals.data.FractalValue;
import me.catmousedog.fractals.paneloperators.filters.Filter;
import me.catmousedog.fractals.paneloperators.filters.LinearFilter;
import me.catmousedog.fractals.paneloperators.filters.LogPeriodicFilter;
import me.catmousedog.fractals.paneloperators.filters.PeriodicFilter;
import me.catmousedog.fractals.paneloperators.fractals.Fractal;

public class DistanceEstimator extends Function {

	{
		usesDerivative = true;
	}

	public DistanceEstimator(Fractal fractal) {
		super(fractal);
		items = null;
		filters = new Filter[] { new LinearFilter(fractal), new PeriodicFilter(fractal),
				new LogPeriodicFilter(fractal) };
		filter = filters[2];
	}

	private DistanceEstimator(DistanceEstimator function) {
		super(function);
	}

	@Override
	public Double apply(FractalValue v) {
		if (v.isConvergent())
			return 0d;
		double z = Math.sqrt(v.x * v.x + v.y * v.y);
		return 1 - Math.exp(-z * Math.log(z) / Math.sqrt(v.dx * v.dx + v.dy * v.dy));
	}

	@Override
	public Function clone() {
		return new DistanceEstimator(this);
	}

	@Override
	public String informalName() {
		return "Distance Estimator";
	}

	@Override
	public String fileName() {
		return "DistanceEstimator";
	}

	@Override
	public String getTip() {
		return "TODO";
	}
}
