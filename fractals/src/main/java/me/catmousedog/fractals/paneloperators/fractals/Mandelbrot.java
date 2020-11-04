package me.catmousedog.fractals.paneloperators.fractals;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.data.FractalValue;
import me.catmousedog.fractals.paneloperators.functions.Function;
import me.catmousedog.fractals.paneloperators.functions.IterativeFunction;
import me.catmousedog.fractals.paneloperators.functions.NormalizedFunction;
import me.catmousedog.fractals.paneloperators.functions.PotentialFunction;

public class Mandelbrot extends Fractal {

	public Mandelbrot() {
		super();
		items = null;
		functions = new Function[] { new IterativeFunction(this), new NormalizedFunction(this),
				new PotentialFunction(this) };
		function = functions[0];
		mouse = null;
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
				return new FractalValue(x, y, i, iterations);

			x = t1 - t2 + cx;
			y = 2 * tx * y + cy;

		}
		return new FractalValue(0, 0, iterations, iterations);
	}

	public @NotNull String informalName() {
		return "Mandelbrot";
	}

	public @NotNull String fileName() {
		return informalName();
	}

	public @NotNull String getTip() {
		return "<html>TODO</html>";
	}

	public @NotNull Fractal clone() {
		return new Mandelbrot(this);
	}

}
