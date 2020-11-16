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
		double kx = cx;
		double ky = cy;

		double x = kx, y = ky;
		double tx;
		double t1 = 0, t2 = 0, t = 0;
		double tdx, dx = 1, dy = 0;

		for (int i = 0; i < iterations; i++) {
			tx = x;
			tdx = dx;

			t1 = tx * tx;
			t2 = y * y;
			t = t1 + t2;

			if (t > bailout) {
				double b = dx * dx + dy * dy;
				double ux = (x * dx + y * dy) / b;
				double uy = (y * dx - x * dy) / b;
				ux /= Math.sqrt(ux * ux + uy * uy);
				uy /= Math.sqrt(ux * ux + uy * uy);
//				double h = Math.sqrt(jx * jx + jy * jy);
//				double l = ux * jx / h + uy * jy / h + h;
//				l /= (1 + h);
//				if (l > 1)
//					l = 1;
//				if (l < 0)
//					l = 0;
				return new FractalValue(ux, uy, 0, iterations);
			}

//			h2 = 1.5  # height factor of the incoming light
//			angle = 45  # incoming direction of light
//			v = exp(1j*angle*2*pi/360)  # unit 2D vector in this direction
//			# incoming light 3D vector = (v.re,v.im,h2)

//		    t = u.real*v.real + u.imag*v.imag + h2  # dot product with the incoming light
//    	    t = t/(1+h2)  # rescale so that t does not get bigger than 1

			dx = 2 * (tx * tdx - y * dy + 1);
			dy = 2 * (y * tdx + tx * dy);

			x = t1 - t2 + cx;
			y = 2 * tx * y + cy;
		}

		// convergent
		return new FractalValue(0, y, 0, iterations);
	}

//	t * Math.log(t) / (2 * Math.sqrt(dx * dx + dy * dy) * 1000 * transform.getm()) < 1

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
