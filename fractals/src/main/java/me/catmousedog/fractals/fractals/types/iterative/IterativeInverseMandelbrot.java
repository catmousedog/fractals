package me.catmousedog.fractals.fractals.types.iterative;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.fractals.filters.Filter;
import me.catmousedog.fractals.fractals.filters.IterativeLinearFilter;
import me.catmousedog.fractals.fractals.filters.IterativePeriodicFilter;

/**
 * Number = Integer
 */
public final class IterativeInverseMandelbrot extends Fractal {

	public IterativeInverseMandelbrot() {
		super();
	}
	
	private IterativeInverseMandelbrot(Fractal fractal) {
		super(fractal);
	}

	@Override
	public Number get(double cx, double cy) {
		double kx = cx / (cx * cx + cy * cy);
		double ky = -cy / (cx * cx + cy * cy);

		double x = kx, y = ky;
		double tx;
		double t1, t2;

		for (int i = 0; i < iterations; i++) {
			tx = x;

			t1 = x * x;
			t2 = y * y;

			if (t1 + t2 > bailout)
				return 255 * (iterations - i) / iterations;

			x = t1 - t2 + kx;
			y = 2 * tx * y + ky;

		}
		return 0;
	}

	@Override
	public String informalName() {
		return "Iterative I-Mandelbrot";
	}

	@Override
	public String fileName() {
		return "IterativeInverseMandelbrot";
	}

	@Override
	public String getTip() {
		return "<html>The second order mandelbrot with the reciprocal of the coordinate added instead. (<i>z�+1/c<i/>)"
				+ "<br>This allows for deep zooms but creates aliasing effects, generally has the shortest generating time.</html>";
	}

	@Override
	protected void initFractal() {
		filters = new Filter[] { new IterativeLinearFilter(this), new IterativePeriodicFilter(this) };
		filter = filters[0];
	}

	@Override
	public Fractal clone() {
		return new IterativeInverseMandelbrot(this);
	}
}