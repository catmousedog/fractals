package me.catmousedog.fractals.paneloperators.fractals;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.data.FractalValue;
import me.catmousedog.fractals.paneloperators.functions.EscapeAngleFunction;
import me.catmousedog.fractals.paneloperators.functions.Function;
import me.catmousedog.fractals.paneloperators.functions.IterativeFunction;
import me.catmousedog.fractals.paneloperators.functions.NormalizedFunction;
import me.catmousedog.fractals.paneloperators.functions.PotentialFunction;

public class TestFractal extends MouseFractal {

	public TestFractal() {
		super();

		functions = new Function[] { new IterativeFunction(this), new NormalizedFunction(this),
				new PotentialFunction(this), new EscapeAngleFunction(this) };
		function = functions[0];
	}

	private TestFractal(MouseFractal fractal) {
		super(fractal);
	}

	@Override
	public FractalValue get(double cx, double cy) {
		double x = cx, y = cy;
		double s;

		double C, ln, theta, t;

		for (int i = 0; i < iterations; i++) {

			s = x * x + y * y;

			if (s > bailout)
				return new FractalValue(x, y, i, iterations);

			ln = Math.log(s) / 2;

			theta = angle(x, y);

			C = Math.exp(jx * ln - theta * jy);

			t = jy * ln + theta * jx;

			x = C * Math.cos(t) + cx;
			y = C * Math.sin(t) + cy;

		}
		return new FractalValue(x, y, iterations, iterations);
	}

	private double angle(double x, double y) {
		double t = Math.atan(y / x);
		if (x < 0)
			return t + Math.PI;
		else if (y < 0)
			return t + 2 * Math.PI;
		return t;
	}

	@Override
	public @NotNull String informalName() {
		return "Test Fractal";
	}

	@Override
	public @NotNull String fileName() {
		return "TestFractal";
	}

	@Override
	public @NotNull String getTip() {
		return "TODO";
	}

	@Override
	public @NotNull Fractal clone() {
		return new TestFractal(this);
	}
}
