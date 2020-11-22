package me.catmousedog.fractals.paneloperators.functions;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.data.FractalValue;
import me.catmousedog.fractals.paneloperators.filters.BrightnessFilter;
import me.catmousedog.fractals.paneloperators.filters.Filter;
import me.catmousedog.fractals.paneloperators.filters.HueFilter;
import me.catmousedog.fractals.paneloperators.filters.LinearFilter;
import me.catmousedog.fractals.paneloperators.filters.PeriodicFilter;
import me.catmousedog.fractals.paneloperators.fractals.Fractal;

public class EscapeAngleFunction extends Function {

	public EscapeAngleFunction(Fractal fractal) {
		super(fractal);

		filters = new Filter[] { new LinearFilter(fractal), new BrightnessFilter(fractal), new PeriodicFilter(fractal),
				new HueFilter(fractal) };
		filter = filters[0];
	}

	private EscapeAngleFunction(EscapeAngleFunction function) {
		super(function);
	}

	@Override
	public @NotNull Double apply(FractalValue v) {
		return (Math.atan2(v.y, v.x) + Math.PI) / (2 * Math.PI);
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
