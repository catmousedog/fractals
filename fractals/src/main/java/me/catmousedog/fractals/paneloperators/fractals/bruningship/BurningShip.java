package me.catmousedog.fractals.paneloperators.fractals.bruningship;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.data.FractalValue;
import me.catmousedog.fractals.paneloperators.fractals.Fractal;
import me.catmousedog.fractals.paneloperators.functions.BinaryFunction;
import me.catmousedog.fractals.paneloperators.functions.EscapeAngleFunction;
import me.catmousedog.fractals.paneloperators.functions.Function;
import me.catmousedog.fractals.paneloperators.functions.IterativeFunction;
import me.catmousedog.fractals.paneloperators.functions.NormalizedFunction;
import me.catmousedog.fractals.paneloperators.functions.PotentialFunction;

public class BurningShip extends Fractal {

	public BurningShip() {
		super();
		items = null;
		functions = new Function[] { new IterativeFunction(this), new NormalizedFunction(this),
				new PotentialFunction(this), new EscapeAngleFunction(this), new BinaryFunction(this) };
		function = functions[0];
		mouse = null;
	}

	private BurningShip(Fractal fractal) {
		super(fractal);
	}

	public FractalValue get(double cx, double cy) {
		double x = cx, y = cy;
		double tx;
		double s1, s2;

		for (int i = 0; i < iterations; i++) {
			tx = x;

			s1 = tx * tx;
			s2 = y * y;

			if (s1 + s2 > bailout)
				return new FractalValue(x, y, i, iterations);

			x = s1 - s2 + cx;
			y = Math.abs(2 * tx * y) + cy;

		}
		return new FractalValue(x, y, iterations, iterations);
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
