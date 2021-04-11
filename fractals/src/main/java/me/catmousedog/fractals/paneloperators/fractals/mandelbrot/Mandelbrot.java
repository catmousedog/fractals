package me.catmousedog.fractals.paneloperators.fractals.mandelbrot;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.data.FractalValue;
import me.catmousedog.fractals.paneloperators.fractals.Fractal;
import me.catmousedog.fractals.paneloperators.functions.BinaryFunction;
import me.catmousedog.fractals.paneloperators.functions.DistanceEstimator;
import me.catmousedog.fractals.paneloperators.functions.EscapeAngleFunction;
import me.catmousedog.fractals.paneloperators.functions.Function;
import me.catmousedog.fractals.paneloperators.functions.IterativeFunction;
import me.catmousedog.fractals.paneloperators.functions.LambertFunction;
import me.catmousedog.fractals.paneloperators.functions.NormalizedFunction;
import me.catmousedog.fractals.paneloperators.functions.PotentialFunction;

public class Mandelbrot extends Fractal {

	public Mandelbrot() {
		super();
		items = null;
		functions = new Function[] { new IterativeFunction(this), new NormalizedFunction(this),
				new PotentialFunction(this), new EscapeAngleFunction(this), new BinaryFunction(this),
				new LambertFunction(this), new DistanceEstimator(this) };
		function = functions[0];
		mouse = null;
	}

	private Mandelbrot(Fractal fractal) {
		super(fractal);
	}

	public FractalValue get(double cx, double cy) {
		double x = cx, y = cy;
		double dx = 1, dy = 0;
		double tx, tdx;
		double s1, s2;

		for (int i = 0; i < iterations; i++) {
			tx = x;
			tdx = dx;

			s1 = tx * tx;
			s2 = y * y;

			if (s1 + s2 > bailout)
				return new FractalValue(x, y, dx, dy, i, iterations);

			if (usingDerivative) {
				dx = 2 * (tx * tdx - y * dy) + 1;
				dy = 2 * (y * tdx + tx * dy);
			}

			x = s1 - s2 + cx;
			y = 2 * tx * y + cy;

		}
		return new FractalValue(x, y, dx, dy, iterations, iterations);
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
