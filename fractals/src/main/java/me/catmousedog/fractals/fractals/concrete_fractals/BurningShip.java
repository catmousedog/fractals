package me.catmousedog.fractals.fractals.concrete_fractals;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.fractals.FractalValue;
import me.catmousedog.fractals.fractals.abstract_fractals.Fractal;
import me.catmousedog.fractals.fractals.functions.Function;
import me.catmousedog.fractals.fractals.functions.IterativeFunction;
import me.catmousedog.fractals.fractals.functions.NormalizedFunction;
import me.catmousedog.fractals.fractals.functions.PotentialFunction;

public class BurningShip extends Fractal {

	public BurningShip() {
		super();
		items = null;
		functions = new Function[] { new IterativeFunction(this), new NormalizedFunction(this), new PotentialFunction(this) };
		function = functions[0];
		mouse = null;
	}

	private BurningShip(Fractal fractal) {
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
			y = Math.abs(2 * tx * y) + cy;

		}
		return new FractalValue();
	}

	public @NotNull String informalName() {
		return "Burning Ship";
	}

	public @NotNull String fileName() {
		return "BurningShip";
	}

	public @NotNull String getTip() {
		return "TODO";
	}

	public @NotNull Fractal clone() {
		return new BurningShip(this);
	}

}
