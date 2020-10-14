package me.catmousedog.fractals.fractals.types.iterative;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.fractals.abstract_fractals.Julia;
import me.catmousedog.fractals.fractals.filters.Filter;
import me.catmousedog.fractals.fractals.filters.IterativeLinearFilter;
import me.catmousedog.fractals.fractals.filters.IterativePeriodicFilter;

/**
 * Number = Integer
 */
public final class IterativeJulia extends Julia {

	public IterativeJulia() {
		super();
	}

	private IterativeJulia(Fractal fractal, double jx, double jy) {
		super(fractal, jx, jy);
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
			y = 2 * tx * y + jy;

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
	public String informalName() {
		return "Iterative Julia Set";
	}

	@Override
	public String fileName() {
		return "IterativeJulia";
	}

	@Override
	public String getTip() {
		return "<html>The second order julia set (<i>z�+c<i/>) generated using an escape time algorithm."
				+ "<br>This allows for deep zooms but creates aliasing effects, generally has the shortest generating time."
				+ "<br>Dragging the mouse will change the fixed point used to generate this set.</html>";
	}

	@Override
	public Fractal clone() {
		return new IterativeJulia(this, jx, jy);
	}
}
