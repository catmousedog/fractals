package me.catmousedog.fractals.paneloperators.fractals.inversemandelbrot;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.data.FractalValue;
import me.catmousedog.fractals.paneloperators.fractals.Fractal;
import me.catmousedog.fractals.paneloperators.functions.BinaryFunction;
import me.catmousedog.fractals.paneloperators.functions.EscapeAngleFunction;
import me.catmousedog.fractals.paneloperators.functions.Function;
import me.catmousedog.fractals.paneloperators.functions.IterativeFunction;
import me.catmousedog.fractals.paneloperators.functions.NormalizedFunction;
import me.catmousedog.fractals.paneloperators.functions.PotentialFunction;

public class InverseMandelbrot extends Fractal {

	public InverseMandelbrot() {
		super();
		items = null;
		functions = new Function[] { new IterativeFunction(this), new NormalizedFunction(this),
				new PotentialFunction(this), new EscapeAngleFunction(this), new BinaryFunction(this) };
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
		double tx;
		double t1, t2;

		for (int i = 0; i < iterations; i++) {
			tx = x;

			t1 = x * x;
			t2 = y * y;

			if (t1 + t2 > bailout)
				return new FractalValue(x, y, i, iterations);

			x = t1 - t2 + kx;
			y = 2 * tx * y + ky;

		}
		return new FractalValue(x, y, iterations, iterations);
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
