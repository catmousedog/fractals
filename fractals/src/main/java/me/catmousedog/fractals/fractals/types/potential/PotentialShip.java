package me.catmousedog.fractals.fractals.types.potential;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.fractals.filters.Filter;
import me.catmousedog.fractals.fractals.filters.LogPeriodicFilter;
import me.catmousedog.fractals.main.Settings;

public class PotentialShip extends Fractal {

	public PotentialShip(Settings settings) {
		super(settings);
	}

	private PotentialShip(Settings settings, Fractal fractal) {
		super(settings, fractal);
	}

	@Override
	public Number get(double cx, double cy) {
		double x = cx, y = cy;
		double tx;
		double t1, t2;

		double p = 0;

		for (int i = 0; i < iterations; i++) {
			tx = x;

			t1 = x * x;
			t2 = y * y;

			x = t1 - t2 + cx;
			y = Math.abs(2 * tx * y) + cy;

			p = Math.log(t1 + t2)/2;

			if (p > bailout) {
				return p / Math.pow(2, i);
			}
		}
		return 0;
	}

	@Override
	public String informalName() {
		return "Potential Burning Ship";
	}

	@Override
	public String fileName() {
		return "PotentialShip";
	}

	@Override
	public String getTip() {
		return "<html>The burning ship generated using an electrostatic approximation."
				+ "<br>This allows for smooth pictures but longer generating times."
				+ "<br>This generation type is very similar to the 'normalized iteration count', "
				+ "<br>but tends to major its frequency in deep zooms."
				+ "<br>Not great for deep zooms as the value quickly approaches zero in deep zooms.</html>";
	}

	@Override
	protected void initFractal() {
		filters = new Filter[] { new LogPeriodicFilter(this) };
		filter = filters[0];
	}

	@Override
	public Fractal clone() {
		return new PotentialShip(settings, this);
	}
}
