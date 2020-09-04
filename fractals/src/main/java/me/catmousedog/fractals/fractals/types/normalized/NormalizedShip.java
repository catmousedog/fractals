package me.catmousedog.fractals.fractals.types.normalized;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.fractals.filters.Filter;
import me.catmousedog.fractals.fractals.filters.NormalizedFilter;
import me.catmousedog.fractals.main.Settings;
import me.catmousedog.fractals.ui.Savable;

public class NormalizedShip extends Fractal implements Savable {

	public NormalizedShip(Settings settings) {
		super(settings);
	}

	private NormalizedShip(Settings settings, Fractal fractal) {
		super(settings, fractal);
	}

	@Override
	public Number get(double cx, double cy) {

		double x = 0, y = 0;
		double tx;
		double t1, t2;

		double p = 0;

		for (int i = 0; i < iterations; i++) {
			tx = x;

			t1 = x * x;
			t2 = y * y;

			x = t1 - t2 + cx;
			y = Math.abs(2 * tx * y) + cy;

			p = Math.log(t1 + t2);

			if (p > bailout) {
				return i + 1 - Math.log(p) / Math.log(2);
			}
		}
		return 0;
	}

	@Override
	public String informalName() {
		return "Normalized Burning Ship";
	}

	@Override
	public String fileName() {
		return "NormalizedShip";
	}

	@Override
	public String getTip() {
		return "<html>The burning ship generated using 'normalized iteration count'. <br>This allows for deep zooms and smooth pictures but long generating times.</html>";
	}

	@Override
	protected void initFilters() {
		filters = new Filter[] { new NormalizedFilter(this) };
		filter = filters[0];
	}

	@Override
	public Fractal clone() {
		return new NormalizedShip(settings, this);
	}
}
