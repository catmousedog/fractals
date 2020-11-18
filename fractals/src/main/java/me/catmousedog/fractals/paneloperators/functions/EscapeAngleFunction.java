package me.catmousedog.fractals.paneloperators.functions;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.data.FractalValue;
import me.catmousedog.fractals.paneloperators.filters.Filter;
import me.catmousedog.fractals.paneloperators.filters.HueFilter;
import me.catmousedog.fractals.paneloperators.fractals.Fractal;

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
		return Math.atan2(v.y, v.x);
	}

	@Override
	public Function clone() {
		return new EscapeAngleFunction(this);
	}

	@Override
	public String informalName() {
		return "EscapeAngle";
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
