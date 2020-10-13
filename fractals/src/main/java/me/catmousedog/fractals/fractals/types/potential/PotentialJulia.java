package me.catmousedog.fractals.fractals.types.potential;

import java.util.Properties;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.fractals.abstract_fractals.Julia;
import me.catmousedog.fractals.main.Settings;

/**
 * Number = Integer
 */
public final class PotentialJulia extends Julia {

	private int offset;

	public PotentialJulia(Settings settings) {
		super(settings);
	}

	private PotentialJulia(Settings settings, Fractal fractal, double jx, double jy, int offset) {
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
				return p / Math.pow(2, i);
			}

			x = t1 - t2 + jx;
			y = 2 * tx * y + jy;
		}
		return 0;
	}

	@Override
	public String informalName() {
		return "Potential Julia Set";
	}

	@Override
	public String fileName() {
		return "PotentialJulia";
	}

	@Override
	public String getTip() {
		return "<html>The second order julia set (<i>z²+c<i/>) generated using an electro static approximation."
				+ "<br>This doesn't allows for very deep zooms and is slower."
				+ "<br>Dragging the mouse will change the fixed point used to generate this set.</html>";
	}

	@Override
	public void setProperties(Properties properties) {
		super.setProperties(properties);
		offset = Integer.parseInt(properties.getProperty("offset"));
	}

	@Override
	public Fractal clone() {
		return new PotentialJulia(settings, this, jx, jy, offset);
	}
}
