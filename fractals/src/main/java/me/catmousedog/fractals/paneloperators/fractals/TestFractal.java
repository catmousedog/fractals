package me.catmousedog.fractals.paneloperators.fractals;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.data.FractalValue;
import me.catmousedog.fractals.paneloperators.functions.BinaryFunction;
import me.catmousedog.fractals.paneloperators.functions.EscapeAngleFunction;
import me.catmousedog.fractals.paneloperators.functions.Function;
import me.catmousedog.fractals.paneloperators.functions.IterativeFunction;
import me.catmousedog.fractals.paneloperators.functions.NormalizedFunction;
import me.catmousedog.fractals.paneloperators.functions.PotentialFunction;
import me.catmousedog.fractals.paneloperators.functions.TestFunction;

public class TestFractal extends MouseFractal {

	public TestFractal() {
		super();

		functions = new Function[] { new IterativeFunction(this), new NormalizedFunction(this),
				new PotentialFunction(this), new EscapeAngleFunction(this), new BinaryFunction(this),
				new TestFunction(this) };
		function = functions[0];
	}

	private TestFractal(MouseFractal fractal) {
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

			s1 = tx * tx;
			s2 = y * y;

			if (s1 + s2 > bailout) {
				return new FractalValue(x, y, dx, dy, 0, iterations);
			}

			dx = 2 * (tx * tdx - y * dy + 1);
			dy = 2 * (y * tdx + tx * dy);

			x = s1 - s2 + cx;
			y = 2 * tx * y + cy;
		}
		return new FractalValue(0, 0, 0, 0, 0, iterations);
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
