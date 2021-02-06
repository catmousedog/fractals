package me.catmousedog.fractals.paneloperators.fractals;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.data.FractalValue;
import me.catmousedog.fractals.paneloperators.functions.BinaryFunction;
import me.catmousedog.fractals.paneloperators.functions.EscapeAngleFunction;
import me.catmousedog.fractals.paneloperators.functions.Function;
import me.catmousedog.fractals.paneloperators.functions.IterativeFunction;
import me.catmousedog.fractals.paneloperators.functions.NormalizedFunction;
import me.catmousedog.fractals.paneloperators.functions.PotentialFunction;
import me.catmousedog.fractals.paneloperators.functions.TestFunction;
import me.catmousedog.fractals.ui.components.Item;
import me.catmousedog.fractals.ui.components.concrete.SliderDouble;

public class TestFractal extends MouseFractal {

	public TestFractal() {
		super();

		List<Item> l = new ArrayList<Item>(m + 1);
		l.add(new SliderDouble.Builder().setLabel("C").setMin(-10).setMax(10).setChange(c -> changePole()).build());
		for (int i = 0; i < m; i++) {
			l.add(new SliderDouble.Builder().setLabel(Integer.toString(i)).setMin(-3).setMax(3)
					.setChange(c -> changePole()).build());
		}
		items = new Item[m + 1];
		l.toArray(items);

		functions = new Function[] { new IterativeFunction(this), new NormalizedFunction(this),
				new PotentialFunction(this), new EscapeAngleFunction(this), new BinaryFunction(this),
				new TestFunction(this) };
		function = functions[0];
	}

	private TestFractal(TestFractal fractal) {
		super(fractal);

		for (int j = 0; j < m; j++) {
			t[j] = fractal.t[j];
			T[j] = fractal.T[j];
		}
		C = fractal.C;
	}

	// poles
	private int m = 2;
	private Complex[] t;
	// powers
	private double[] T;
	// extra parameter
	private double C = Math.exp(0);

	{
		jx = 1;
		t = new Complex[m];
		T = new double[m];

		t[0] = new Complex(1, 0);
		t[1] = new Complex(-1, 0);
		for (int j = 0; j < m; j++) {
			T[j] = 1;
		}
	}

	@Override
	public FractalValue get(double cx, double cy) {
		Complex q = new Complex(cx, cy);

		for (int i = 0; i < iterations; i++) {

			if (q.mag() > bailout) {
				return new FractalValue(q.x, q.y, 0, 0, i, iterations);
			}

			Complex PT = new Complex(1, 0), PB = new Complex(1, 0);
			for (int j = 0; j < m; j++) {
				PT.multiply(q.poly(t[j], T[j]));
			}

			q = PT;
			q.multiply(new Complex(C, 0));
			q.divide(PB);
		}
		return new FractalValue(0, 0, 0, 0, iterations, iterations);
	}

	@Override
	public void save() {
		super.save();

		C = Math.exp(((SliderDouble) items[0]).saveAndGet());
		for (int i = 0; i < m; i++)
			T[i] = ((SliderDouble) items[i + 1]).saveAndGet();
	}

	@Override
	public void update() {
		super.update();
		
		((SliderDouble) items[0]).setDataSafe(Math.log(C));
		for (int i = 0; i < m; i++)
			((SliderDouble) items[i + 1]).setDataSafe(T[i]);
	}

	private void changePole() {
		saveAndRender();
		update();
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

	private class Complex {
		private double x, y;

		public Complex(double x, double y) {
			this.x = x;
			this.y = y;
		}

		public Complex poly(Complex t, double T) {
			Complex c = new Complex(x, y);
			c.subtract(t);
			c.power(T);
			return c;
		}

		public Complex divide(Complex c) {
			double Y = c.x * c.x + c.y * c.y;
			double tx = (c.x * x + c.y * y) / Y;
			double ty = (c.x * y - c.y * x) / Y;
			return new Complex(tx, ty);
		}

		public void multiply(Complex c) {
			double tx = x * c.x - y * c.y;
			y = y * c.x + x * c.y;
			x = tx;
		}

		public void subtract(Complex c) {
			x -= c.x;
			y -= c.y;
		}

		public void power(double a) {
			double theta = Math.atan2(y, x);
//			double Z = Math.pow(x * x + y * y, a / 2);
			double Z = Math.exp(Math.log(x * x + y * y) * a / 2);
			x = Math.cos(a * theta) * Z;
			y = Math.sin(a * theta) * Z;
		}

		public double mag() {
			return x * x + y * y;
		}

		@Override
		public Complex clone() {
			return new Complex(x, y);
		}

	}
}
