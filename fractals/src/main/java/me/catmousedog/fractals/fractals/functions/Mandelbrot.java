package me.catmousedog.fractals.fractals.functions;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.fractals.FractalValue;

public class Mandelbrot extends Fractal {

	public Mandelbrot() {
		super(null, null);
		functions = new Function[] {};
		function = functions[0];
	}

	private Mandelbrot(Fractal fractal) {
		super(fractal);
	}

	public FractalValue get(double cx, double cy) {
		double x = cx, y = cy;
		double tx;
		double t1, t2;

		for (int i = 0; i < iterations; i++) {
			tx = x;

			t1 = x * x;
			t2 = y * y;

			if (t1 + t2 > bailout)
				return new FractalValue(i, iterations);

			x = t1 - t2 + cx;
			y = 2 * tx * y + cy;

		}
		return new FractalValue();
	}

	protected void initFractal() {

	}

	public @NotNull String informalName() {
		return null;
	}

	public @NotNull String fileName() {
		// XXX Auto-generated method stub
		return null;
	}

	public @NotNull String getTip() {
		// XXX Auto-generated method stub
		return null;
	}

	public @NotNull Fractal clone() {
		// XXX Auto-generated method stub
		return null;
	}

}
