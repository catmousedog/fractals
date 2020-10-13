package me.catmousedog.fractals.fractals.types.normalized;

import java.util.Properties;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.fractals.abstract_fractals.Julia;
import me.catmousedog.fractals.main.Settings;

/**
 * Number = Integer
 */
public final class NormalizedJulia extends Julia {

	private int offset;

	public NormalizedJulia(Settings settings) {
		super(settings);
	}

	private NormalizedJulia(Settings settings, Fractal fractal, double jx, double jy, int offset) {
		super(settings, fractal, jx, jy);
		this.offset = offset;
	}

	@Override
	public Number get(double cx, double cy) {
		double x = cx, y = cy;
		double tx;
		double t1, t2;
		double p;

		for (int i = 0; i < iterations; i++) {
			tx = x;

			t1 = x * x;
			t2 = y * y;

			p = Math.log(t1 + t2) / 2;

			if (p > bailout) {
				return i + offset - Math.log(p) / Math.log(2);
			}

			x = t1 - t2 + jx;
			y = 2 * tx * y + jy;
		}
		return 0;
	}

	@Override
	public String informalName() {
		return "Normalized Julia Set";
	}

	@Override
	public String fileName() {
		return "NormalizedJulia";
	}

	@Override
	public String getTip() {
		return "<html>The second order julia set (<i>z²+c<i/>) generated using an normalized iteration counts."
				+ "<br>This allows for deep zooms but is slower."
				+ "<br>Dragging the mouse will change the fixed point used to generate this set.</html>";
	}

	@Override
	public void setProperties(Properties properties) {
		super.setProperties(properties);
		offset = Integer.parseInt(properties.getProperty("offset"));
	}

	@Override
	public Fractal clone() {
		return new NormalizedJulia(settings, this, jx, jy, offset);
	}
}
