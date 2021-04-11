package me.catmousedog.fractals.paneloperators.fractals.mandelbrot;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.data.FractalValue;
import me.catmousedog.fractals.paneloperators.fractals.Fractal;
import me.catmousedog.fractals.paneloperators.fractals.MouseFractal;
import me.catmousedog.fractals.paneloperators.functions.BinaryFunction;
import me.catmousedog.fractals.paneloperators.functions.DistanceEstimator;
import me.catmousedog.fractals.paneloperators.functions.EscapeAngleFunction;
import me.catmousedog.fractals.paneloperators.functions.Function;
import me.catmousedog.fractals.paneloperators.functions.IterativeFunction;
import me.catmousedog.fractals.paneloperators.functions.LambertFunction;
import me.catmousedog.fractals.paneloperators.functions.NormalizedFunction;
import me.catmousedog.fractals.paneloperators.functions.PotentialFunction;

public class JuliaSet extends MouseFractal {

	public JuliaSet() {
		super();
		functions = new Function[] { new IterativeFunction(this), new NormalizedFunction(this),
				new PotentialFunction(this), new EscapeAngleFunction(this), new BinaryFunction(this),
				new LambertFunction(this), new DistanceEstimator(this) };
		function = functions[0];
	}

	private JuliaSet(MouseFractal fractal) {
		super(fractal);
	}

	@Override
	public FractalValue get(double cx, double cy) {
		double x = cx, y = cy;
		double dx = 1, dy = 0;
		double tx, tdx;
		double s1, s2;

		for (int i = 0; i < iterations; i++) {
			tx = x;
			tdx = dx;

			s1 = x * x;
			s2 = y * y;

			if (s1 + s2 > bailout)
				return new FractalValue(x, y, dx, dy, i, iterations);

			if (usingDerivative) {
				dx = 2 * (tx * tdx - y * dy) + 1;
				dy = 2 * (y * tdx + tx * dy);
			}

			x = s1 - s2 + jx;
			y = 2 * tx * y + jy;

		}
		return new FractalValue(x, y, dx, dy, iterations, iterations);
	}

	@Override
	public @NotNull String informalName() {
		return "Julia Set";
	}

	@Override
	public @NotNull String fileName() {
		return "JuliaSet";
	}

	@Override
	public @NotNull String getTip() {
		return "TODO";
	}

	@Override
	public @NotNull Fractal clone() {
		return new JuliaSet(this);
	}

}
