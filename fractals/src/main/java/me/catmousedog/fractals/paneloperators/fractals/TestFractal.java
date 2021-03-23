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

		for (int i = 0; i < n; i++) {
			double t = T * i / (double) (n + 1);
			r[i] = f(Complex.exp(t));
		}

		// test limit
		Complex q = new Complex(0.7, -0.22);
		Complex omega = new Complex(1, 0);
		for (int j = 0; j < n; j++) {
			omega = omega.multiply(q.subtract(r[j]));
		}
		omega = omega.multiply(C);
//		System.out.println(omega);
		//

		jx = a;
		jy = 0.0;
	}

	private TestFractal(TestFractal fractal) {
		super(fractal);
		for (int i = 0; i < n; i++) {
			r[i] = fractal.r[i];
		}
	}

	private double T = 2 * Math.PI;
	private int n = 100;
	private double a = 1;
	private Complex C = new Complex(1, 0);

	private Complex[] r = new Complex[n];

	private Complex f(Complex z) {
		return new Complex(Math.sinh(z.x), Math.sinh(z.x + z.y));
	}

	@Override
	public FractalValue get(double cx, double cy) {
		Complex q = new Complex(cx, cy);

		for (int i = 0; i < iterations; i++) {

			if (q.mag() > bailout) {
				return new FractalValue(q.x, q.y, 0, 0, i, iterations);
			}

			Complex omega = new Complex(1, 0);
			for (int j = 0; j < n; j++) {
				omega = omega.multiply(q.subtract(r[j]));
			}
			omega = omega.multiply(C.power(-n));
			omega = q.multiply(omega.add(new Complex(1, 0)));
			q = omega.clone();
		}
		return new FractalValue(0, 0, 0, 0, iterations, iterations);
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

	private static class Complex {
		private double x, y;

		public Complex(double x, double y) {
			this.x = x;
			this.y = y;
		}

		public Complex divide(Complex c) {
			double Y = c.x * c.x + c.y * c.y;
			double tx = (c.x * x + c.y * y) / Y;
			double ty = (c.x * y - c.y * x) / Y;
			return new Complex(tx, ty);
		}

		public Complex multiply(Complex c) {
			double tx = x * c.x - y * c.y;
			double ty = y * c.x + x * c.y;
			return new Complex(tx, ty);
		}

		public Complex subtract(Complex c) {
			return new Complex(x - c.x, y - c.y);
		}

		public Complex add(Complex c) {
			return new Complex(x + c.x, y + c.y);
		}

		public Complex power(double a) {
			double theta = Math.atan2(y, x);
			double Z = Math.exp(Math.log(x * x + y * y) * a / 2);
			double tx = Math.cos(a * theta) * Z;
			double ty = Math.sin(a * theta) * Z;
			return new Complex(tx, ty);
		}

		public static Complex exp(double a) {
			return new Complex(Math.cos(a), Math.sin(a));
		}

		public double mag() {
			return x * x + y * y;
		}

		@Override
		public String toString() {
			return String.format("%f\t%f", x, y);
		}

		@Override
		public Complex clone() {
			return new Complex(x, y);
		}

	}
}
