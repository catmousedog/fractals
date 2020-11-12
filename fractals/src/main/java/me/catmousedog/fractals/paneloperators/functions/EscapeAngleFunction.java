package me.catmousedog.fractals.paneloperators.functions;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.data.FractalValue;
import me.catmousedog.fractals.paneloperators.filters.Filter;
import me.catmousedog.fractals.paneloperators.filters.HueFilter;
import me.catmousedog.fractals.paneloperators.fractals.Fractal;
import me.catmousedog.fractals.ui.components.Item;
import me.catmousedog.fractals.ui.components.concrete.SliderDouble;
import me.catmousedog.fractals.ui.components.concrete.TextFieldDouble;

public class EscapeAngleFunction extends Function {

	public EscapeAngleFunction(Fractal fractal) {
		super(fractal);

		filters = new Filter[] { new HueFilter(fractal) };
		filter = filters[0];
	}

	private EscapeAngleFunction(Function function) {
		super(function);
	}

	@Override
	public @NotNull Double apply(FractalValue v) {
		return (Math.atan2(v.y, v.x) + Math.PI) / (2 * Math.PI);
	}

//	if (v.x == 0 && v.y == 0)
//	return 0d;
//	return v.i + offset - Math.log(Math.log(v.x * v.x + v.y * v.y) / 2) / Math.log(2);

//	private double angle(double x, double y) {
//		double t = Math.atan(y / x);
//		if (x < 0)
//			return t + Math.PI;
//		else if (y < 0)
//			return t + 2 * Math.PI;
//		return t;
//	}

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
