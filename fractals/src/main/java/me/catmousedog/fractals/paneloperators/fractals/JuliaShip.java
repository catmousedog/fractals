package me.catmousedog.fractals.paneloperators.fractals;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.data.FractalValue;
import me.catmousedog.fractals.paneloperators.functions.Function;
import me.catmousedog.fractals.paneloperators.functions.IterativeFunction;
import me.catmousedog.fractals.paneloperators.functions.NormalizedFunction;
import me.catmousedog.fractals.paneloperators.functions.PotentialFunction;

public class JuliaShip extends MouseFractal {

	public JuliaShip() {
		super();
		functions = new Function[] { new IterativeFunction(this), new NormalizedFunction(this), new PotentialFunction(this) };
		function = functions[0];
	}
	
	private JuliaShip(MouseFractal fractal) {
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
			y = Math.abs(2 * tx * y) + jy;

		}
		return new FractalValue(x, y, iterations, iterations);
	}

	@Override
	public @NotNull String informalName() {
		return "Julia Ship";
	}

	@Override
	public @NotNull String fileName() {
		return "JuliaShip";
	}

	@Override
	public @NotNull String getTip() {
		return "TODO";
	}

	@Override
	public @NotNull Fractal clone() {
		return new JuliaShip(this);
	}

}
