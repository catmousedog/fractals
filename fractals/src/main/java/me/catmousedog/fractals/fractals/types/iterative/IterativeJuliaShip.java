package me.catmousedog.fractals.fractals.types.iterative;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.fractals.abstract_fractals.Julia;
import me.catmousedog.fractals.fractals.filters.Filter;
import me.catmousedog.fractals.fractals.filters.IterativeLinearFilter;
import me.catmousedog.fractals.fractals.filters.IterativePeriodicFilter;
import me.catmousedog.fractals.main.Settings;

public class IterativeJuliaShip extends Julia {

	public IterativeJuliaShip(Settings settings) {
		super(settings);
	}

	private IterativeJuliaShip(Settings settings, Fractal fractal, double jx, double jy) {
		super(settings, fractal, jx, jy);
	}

	@Override
	public Number get(double cx, double cy) {
		double x = cx, y = cy;
		double tx;
		double t1, t2;

		for (int i = 0; i < iterations; i++) {
			tx = x;

			t1 = x * x;
			t2 = y * y;

			if (t1 + t2 > bailout)
				return 255 * (iterations - i) / iterations;

			x = t1 - t2 + jx;
			y = Math.abs(2 * tx * y) + jy;

		}
		return 0;
	}

	@Override
	protected void initFractal() {
		filters = new Filter[] { new IterativeLinearFilter(this), new IterativePeriodicFilter(this) };
		filter = filters[0];
		super.initFractal();
	}

	@Override
	public @NotNull String informalName() {
		return "Iterative Julia Ship";
	}

	@Override
	public @NotNull String fileName() {
		return "IterativeJuliaShip";
	}

	@Override
	public @NotNull String getTip() {
		return "<html>TODO</html>";
	}

	@Override
	public @NotNull Fractal clone() {
		return new IterativeJuliaShip(settings, this, jx, jy);
	}

}
