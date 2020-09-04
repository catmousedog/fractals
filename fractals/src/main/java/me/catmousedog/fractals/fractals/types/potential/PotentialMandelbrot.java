package me.catmousedog.fractals.fractals.types.potential;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.fractals.filters.Filter;
import me.catmousedog.fractals.fractals.filters.NormalizedFilter;
import me.catmousedog.fractals.main.Settings;
import me.catmousedog.fractals.ui.Savable;

public class PotentialMandelbrot extends Fractal implements Savable {

	public PotentialMandelbrot(Settings settings) {
		super(settings);
	}

	private PotentialMandelbrot(Settings settings, Fractal fractal) {
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
			y = 2 * tx * y + cy;

			p = Math.log(t1 + t2);

			if (p > bailout) {
				return p / Math.pow(2, i);
			}
		}
		return 0;
	}

	@Override
	public String informalName() {
		return "Potential Mandelbrot";
	}

	@Override
	public String fileName() {
		return "PotentialMandelbrot";
	}

	@Override
	public String getTip() {
		return "<html>The mandelbrot generated using an electrostatic approximation. <br>This allows smooth pictures but longer generating times. <br>Not great for deep zooms.</html>";
	}

	@Override
	protected void initFilters() {
		filters = new Filter[] { new NormalizedFilter(this) };
		filter = filters[0];
	}

	@Override
	public Fractal clone() {
		return new PotentialMandelbrot(settings, this);
	}
}
