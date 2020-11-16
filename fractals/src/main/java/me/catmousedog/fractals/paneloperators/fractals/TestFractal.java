package me.catmousedog.fractals.paneloperators.fractals;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.data.FractalValue;
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
				new PotentialFunction(this), new EscapeAngleFunction(this), new TestFunction(this) };
		function = functions[0];
	}

	private TestFractal(MouseFractal fractal) {
		super(fractal);
	}

	@Override
	public FractalValue get(double cx, double cy) {
		double kx = cx;
		double ky = cy;

		double x = kx, y = ky;
		double tx;
		double t1 = 0, t2 = 0;
		double tdx, dx = 0, dy = 0;

		for (int i = 0; i < iterations; i++) {
			tx = x;
			tdx = dx;

			t1 = tx * tx;
			t2 = y * y;

			if (t1 + t2 > bailout)
				break;

			dx = 2 * (tx * tdx - y * dy + 1);
			dy = 2 * (y * tdx + tx * dy);

			x = t1 - t2 + cx;
			y = 2 * tx * y + cy;
		}

//		double s = Math.sqrt(t1 + t2);
//		s * Math.log(s) / Math.sqrt(dx * dx + dy * dy)

		if ((t1 + t2) * Math.log(t1 + t2) * Math.log(t1 + t2) < 1.4
				* Math.sqrt(getTransform().getm() * (dx * dx + dy * dy))) {

			return new FractalValue(0, y, iterations, iterations);
		}
		return new FractalValue(1, y, iterations, iterations);

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
