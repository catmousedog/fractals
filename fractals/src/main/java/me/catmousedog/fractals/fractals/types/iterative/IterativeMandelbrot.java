package me.catmousedog.fractals.fractals.types.iterative;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.fractals.FractalValue;
import me.catmousedog.fractals.fractals.functions.Function;

/**
 * Number = Integer
 */
public final class IterativeMandelbrot extends Fractal {

	public IterativeMandelbrot() {
		super(null, null);
		functions = new Function[] {};
		function = functions[0];
	}

	private IterativeMandelbrot(Fractal fractal) {
		super(fractal);
	}

	@Override
	public FractalValue get(double cx, double cy) {
		double x = cx, y = cy;
		double tx;
		double t1, t2;

		for (int i = 0; i < iterations; i++) {
			tx = x;

			t1 = x * x;
			t2 = y * y;

			if (t1 + t2 > bailout)
				return 255 * (iterations - i) / iterations;

			x = t1 - t2 + cx;
			y = 2 * tx * y + cy;

		}
		return 0;
	}

	@Override
	public String informalName() {
		return "Iterative Mandelbrot";
	}

	@Override
	public String fileName() {
		return "IterativeMandelbrot";
	}

	@Override
	public String getTip() {
		return "<html>The second order mandelbrot (<i>z²+c<i/>) generated using an escape time algorithm."
				+ "<br>This allows for deep zooms but creates aliasing effects, generally has the shortest generating time.</html>";
	}

	@Override
	public Fractal clone() {
		return new IterativeMandelbrot(this);
	}
}
