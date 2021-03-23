package fractals.analytical;

import java.util.ArrayList;
import java.util.List;

import fractals.rational.Complex;
import me.catmousedog.fractals.data.FractalValue;
import me.catmousedog.fractals.paneloperators.fractals.Fractal;
import me.catmousedog.fractals.paneloperators.functions.Function;
import me.catmousedog.fractals.paneloperators.functions.IterativeFunction;

public class AnalFractal extends Fractal {

	public AnalFractal() {
		functions = new Function[] { new IterativeFunction(this) };
		function = functions[0];

		add(1, 0);
		add(-0.1, 0.23);
		
		for (int i = 0; i < r.length; i++) {
			r[i] = f(new Complex(2 * Math.PI * i / (double) N));
		}
	}

	private AnalFractal(AnalFractal fractal) {
		super(fractal);
		for (Complex c : fractal.coefficients) {
			coefficients.add(c.clone());
		}
		for (int i = 0; i < r.length; i++) {
			r[i] = f(new Complex(2 * Math.PI * i / (double) N));
		}
	}

	{
		iterations = 10;
	}
	
	public static final int N = 100;
	public static final Complex[] r = new Complex[N];

	/**
	 * coefficients of laurent series 1, 0, -1, ...
	 */
	public final List<Complex> coefficients = new ArrayList<Complex>();

	@Override
	public FractalValue get(double cx, double cy) {
		Complex q = new Complex(cx, cy);

		for (int i = 0; i < iterations; i++) {

			if (q.mag() > bailout) {
				return new FractalValue(q.x, q.y, 0, 0, i, iterations);
			}

			Complex omega = new Complex(1, 0);
			for (int j = 0; j < N; j++) {
				omega = omega.multiply(q.subtract(r[j]));
			}
			omega = omega.multiply(coefficients.get(0).power(-N));
			omega = q.multiply(omega.add(new Complex(1, 0)));
			q = omega.clone();
		}
		return new FractalValue(0, 0, 0, 0, iterations, iterations);
	}

	private Complex f(Complex z) {
		Complex sum = new Complex(0, 0);
		for (int i = 0; i < coefficients.size(); i++) {
			sum = sum.add(z.power(1 - i).multiply(coefficients.get(i)));
		}
		return sum;
	}

	public void add(double x, double y) {
		coefficients.add(new Complex(x, y));
	}

	@Override
	public Fractal clone() {
		return new AnalFractal(this);
	}

	@Override
	public String informalName() {
		return null;
	}

	@Override
	public String fileName() {
		return null;
	}

	@Override
	public String getTip() {
		return null;
	}

}
