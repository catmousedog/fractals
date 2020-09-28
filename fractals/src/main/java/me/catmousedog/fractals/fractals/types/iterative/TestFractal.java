package me.catmousedog.fractals.fractals.types.iterative;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.fractals.filters.Filter;
import me.catmousedog.fractals.fractals.filters.IterativeLinearFilter;
import me.catmousedog.fractals.fractals.filters.IterativePeriodicFilter;
import me.catmousedog.fractals.fractals.filters.LogPeriodicFilter;
import me.catmousedog.fractals.main.Settings;

public class TestFractal extends Fractal {

	public TestFractal(Settings settings) {
		super(settings);
	}

	private TestFractal(Settings settings, Fractal fractal) {
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
			y = 2 * tx * y + cy;

			p = Math.log(t1 + t2) / 2;

			if (p > bailout) {
				return i * Math.log(2) + 1 - Math.log(p);
			}
		}
		return 0;
	}

	@Override
	protected void initFractal() {
		filters = new Filter[] { new IterativeLinearFilter(this), new IterativePeriodicFilter(this),
				new LogPeriodicFilter(this) };
		filter = filters[0];
	}

	@Override
	public @NotNull String informalName() {
		return "Test Fractal";
	}

	@Override
	public @NotNull String fileName() {
		return "TestFractal";
	}

	@Override
	public @NotNull String getTip() {
		return "This is a test fractal and should be disabled";
	}

	@Override
	public @NotNull Fractal clone() {
		return new TestFractal(settings, this);
	}

}
