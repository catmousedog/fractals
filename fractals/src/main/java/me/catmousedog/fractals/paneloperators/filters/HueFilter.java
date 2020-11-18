package me.catmousedog.fractals.paneloperators.filters;

import java.awt.Color;

import me.catmousedog.fractals.paneloperators.fractals.Fractal;

public class HueFilter extends Filter {

	public HueFilter(Fractal fractal) {
		super(fractal);
	}

	private HueFilter(HueFilter filter) {
		super(filter);
	}

	@Override
	public int apply(Number V) {
		float v = (float) ((V.doubleValue() + Math.PI) / (2 * Math.PI));
		return Color.HSBtoRGB(v, 1f, 1f);
	}

	@Override
	public Filter clone() {
		return new HueFilter(this);
	}

	@Override
	public String informalName() {
		return "Hue";
	}

	@Override
	public String fileName() {
		return informalName();
	}

	@Override
	public String getTip() {
		return "TODO";
	}

	@Override
	public void save() {
	}

	@Override
	public void update() {
	}
}
