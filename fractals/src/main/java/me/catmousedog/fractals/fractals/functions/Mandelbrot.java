package me.catmousedog.fractals.fractals.functions;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.utils.FractalValue;

public class Mandelbrot {

	private int iterations = 100;

	private int bailout = 100;
	
	protected Function<? extends Number>[] functions;
	
	protected Function<? extends Number> function;

	public Mandelbrot() {

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
				return new FractalValue(x, y);

			x = t1 - t2 + cx;
			y = 2 * tx * y + cy;

		}
		return new FractalValue(0, 0);
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
