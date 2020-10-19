package me.catmousedog.fractals.fractals.concrete_fractals;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.fractals.FractalValue;
import me.catmousedog.fractals.fractals.abstract_fractals.Fractal;
import me.catmousedog.fractals.fractals.abstract_fractals.MouseFractal;
import me.catmousedog.fractals.fractals.functions.Function;
import me.catmousedog.fractals.fractals.functions.IterativeFunction;
import me.catmousedog.fractals.fractals.functions.NormalizedFunction;
import me.catmousedog.fractals.fractals.functions.PotentialFunction;

public class JuliaSet extends MouseFractal {

	public JuliaSet() {
		super();
		functions = new Function[] { new IterativeFunction(this), new NormalizedFunction(this), new PotentialFunction(this) };
		function = functions[0];
	}
	
	private JuliaSet(MouseFractal fractal) {
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
				return new FractalValue(x, y, i, iterations);

			x = t1 - t2 + jx;
			y = 2 * tx * y + jy;

		}
		return new FractalValue(0, 0, iterations, iterations);
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
