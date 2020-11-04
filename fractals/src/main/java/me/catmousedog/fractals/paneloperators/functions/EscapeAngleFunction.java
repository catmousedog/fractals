package me.catmousedog.fractals.paneloperators.functions;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.data.FractalValue;
import me.catmousedog.fractals.paneloperators.filters.Filter;
import me.catmousedog.fractals.paneloperators.filters.IterativeLinearFilter;
import me.catmousedog.fractals.paneloperators.fractals.Fractal;

public class EscapeAngleFunction extends Function {

	public EscapeAngleFunction(Fractal fractal) {
		super(fractal);
		items = null;
		filters = new Filter[] { new IterativeLinearFilter(fractal) };
		filter = filters[0];
	}

	private EscapeAngleFunction(Function function) {
		super(function);
	}

	@Override
	public @NotNull Number apply(FractalValue v) {
		return (int) (255 * angle(v.x, v.y) / (2 * Math.PI));
	}

	private double angle(double x, double y) {
		double t = Math.atan(y / x);
		if (x < 0)
			return t + Math.PI;
		else if (y < 0)
			return t + 2 * Math.PI;
		return t;
	}

	@Override
	public Function clone() {
		return new EscapeAngleFunction(this);
	}

	@Override
	public String informalName() {
		return "Escape Angle";
	}

	@Override
	public String fileName() {
		return "EscapeAngle";
	}

	@Override
	public String getTip() {
		return "TODO";
	}

}
