package me.catmousedog.fractals.paneloperators.fractals.inversemandelbrot;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.data.FractalValue;
import me.catmousedog.fractals.paneloperators.fractals.Fractal;
import me.catmousedog.fractals.paneloperators.functions.BinaryFunction;
import me.catmousedog.fractals.paneloperators.functions.EscapeAngleFunction;
import me.catmousedog.fractals.paneloperators.functions.Function;
import me.catmousedog.fractals.paneloperators.functions.IterativeFunction;
import me.catmousedog.fractals.paneloperators.functions.LambertFunction;
import me.catmousedog.fractals.paneloperators.functions.NormalizedFunction;
import me.catmousedog.fractals.paneloperators.functions.PotentialFunction;

public class InverseMandelbrot extends Fractal {

	public InverseMandelbrot() {
		super();
		items = null;
		functions = new Function[] { new IterativeFunction(this), new NormalizedFunction(this),
				new PotentialFunction(this), new EscapeAngleFunction(this), new BinaryFunction(this),
				new LambertFunction(this) };
		function = functions[0];
		mouse = null;
	}

	private InverseMandelbrot(Fractal fractal) {
		super(fractal);
	}

	@Override
	public FractalValue get(double cx, double cy) {
		double kx = cx / (cx * cx + cy * cy);
		double ky = -cy / (cx * cx + cy * cy);

		double x = kx, y = ky;
		double dx = 1, dy = 0;
		double tx, tdx;
		double s1, t2;

		for (int i = 0; i < iterations; i++) {
			tx = x;
			tdx = dx;

			s1 = x * x;
			t2 = y * y;

			if (s1 + t2 > bailout)
				return new FractalValue(x, y, dx, dy, i, iterations);

			if (usingDerivative) {
				dx = 2 * (tx * tdx - y * dy) + 1;
				dy = 2 * (y * tdx + tx * dy);
			}

			x = s1 - t2 + kx;
			y = 2 * tx * y + ky;

		}
		return new FractalValue(x, y, dx, dy, iterations, iterations);
	}

	@Override
	public @NotNull String informalName() {
		return "Inverse Mandelbrot";
	}

	@Override
	public @NotNull String fileName() {
		return "InverseMandelbrot";
	}

	@Override
	public @NotNull String getTip() {
		return "TODO";
	}

	@Override
	public @NotNull Fractal clone() {
		return new InverseMandelbrot(this);
	}

}
