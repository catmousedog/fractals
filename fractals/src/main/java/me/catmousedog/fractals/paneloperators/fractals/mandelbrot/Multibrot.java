package me.catmousedog.fractals.paneloperators.fractals.mandelbrot;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.data.FractalValue;
import me.catmousedog.fractals.paneloperators.fractals.Fractal;
import me.catmousedog.fractals.paneloperators.functions.BinaryFunction;
import me.catmousedog.fractals.paneloperators.functions.DistanceEstimator;
import me.catmousedog.fractals.paneloperators.functions.EscapeAngleFunction;
import me.catmousedog.fractals.paneloperators.functions.Function;
import me.catmousedog.fractals.paneloperators.functions.IterativeFunction;
import me.catmousedog.fractals.paneloperators.functions.LambertFunction;
import me.catmousedog.fractals.paneloperators.functions.NormalizedFunction;
import me.catmousedog.fractals.paneloperators.functions.PotentialFunction;
import me.catmousedog.fractals.ui.components.Item;
import me.catmousedog.fractals.ui.components.concrete.SliderDouble;
import me.catmousedog.fractals.ui.components.concrete.TextFieldDouble;

public class Multibrot extends Fractal {

	// field is Fractal.degree

	private TextFieldDouble ajtf;
	private SliderDouble ajs;

	private void changeA() {
		ajtf.setData(ajs.saveAndGet());
		saveAndRender();
	}

	public Multibrot() {
		super();
		
		String tipA = "TODO";
		ajtf = new TextFieldDouble.Builder().setLabel("exponent").setTip(tipA).build();
		ajs = new SliderDouble.Builder().setMin(-5).setMax(5).setChange(c -> changeA()).setTip(tipA).build();
		items = new Item[] { ajtf, ajs };

		functions = new Function[] { new IterativeFunction(this), new NormalizedFunction(this),
				new PotentialFunction(this), new EscapeAngleFunction(this), new BinaryFunction(this),
				new LambertFunction(this), new DistanceEstimator(this) };
		function = functions[0];
	}

	private Multibrot(Multibrot fractal) {
		super(fractal);
	}

	@Override
	public FractalValue get(double cx, double cy) {
		double x = cx, y = cy;
		double dx = 1, dy = 0;
		double tx, tdx;
		double s;

		for (int i = 0; i < iterations; i++) {
			tx = x;
			tdx = dx;

			s = x * x + y * y;

			if (s > bailout)
				return new FractalValue(x, y, dx, dy, i, iterations);

			if (usingDerivative) {
				double D = degree * Math.exp((degree - 1) * Math.log(s) / 2);
				double d = (degree - 1) * Math.atan2(y, tx);

				dx = D * (tdx * Math.cos(d) - dy * Math.sin(d));
				dy = D * (dy * Math.cos(d) + tdx * Math.sin(d));
			}

			double K = Math.pow(s, degree / 2);
			double k = degree * Math.atan2(y, x);

			x = K * Math.cos(k) + cx;
			y = K * Math.sin(k) + cy;
		}
		return new FractalValue(x, y, dx, dy, iterations, iterations);
	}

	@Override
	public void save() {
		degree = ajtf.saveAndGet();
		super.save();
	}

	@Override
	public void update() {
		ajtf.setData(degree);
		ajs.setDataSafe(degree);
		super.update();
	}

	@Override
	public @NotNull String informalName() {
		return "Multibrot";
	}

	@Override
	public @NotNull String fileName() {
		return informalName();
	}

	@Override
	public @NotNull String getTip() {
		return "TODO";
	}

	@Override
	public @NotNull Fractal clone() {
		return new Multibrot(this);
	}
}
