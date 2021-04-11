package fractals.analytical;

import java.util.ArrayList;
import java.util.List;

import fractals.drawer.Region;
import fractals.rational.Complex;
import me.catmousedog.fractals.data.FractalValue;
import me.catmousedog.fractals.paneloperators.fractals.Fractal;
import me.catmousedog.fractals.paneloperators.functions.Function;
import me.catmousedog.fractals.paneloperators.functions.IterativeFunction;

public class AnalFractal extends Fractal {

	public AnalFractal() {
		functions = new Function[] { new IterativeFunction(this) };
		function = functions[0];

		regions.add(new Coefficients());
		regions.add(new Coefficients());
		regions.add(new Coefficients());
		regions.add(new Coefficients());
		regions.add(new Coefficients());

		for (Coefficients item : regions) {
			item.update();
		}
	}

	private void add(int i, double x, double y) {
		regions.get(i).add(x, y);
	}

	private AnalFractal(AnalFractal fractal) {
		super(fractal);
		for (Coefficients c : fractal.regions) {
			regions.add(c);
		}
	}

	{
		iterations = 15;
	}

	public final List<Coefficients> regions = new ArrayList<Coefficients>();

	Complex t = new Complex(0, 0);

	@Override
	public FractalValue get(double cx, double cy) {
		Complex q = new Complex(cx, cy);

		for (int i = 0; i < iterations; i++) {

			if (q.mag() > bailout) {
				return new FractalValue(q.x, q.y, 0, 0, i, iterations);
			}

			q = q.subtract(t);

			// f(z)
			Complex sum = new Complex(0, 0);
			for (Coefficients region : regions) {
				sum = sum.add(region.omega(q));
			}
			sum = sum.inverse();
			sum = q.multiply(sum);
			//
//			
//			Complex sum = new Complex(0, 0);
//			for (int l = 0; l < regions.size(); l++) {
//				Complex omega = new Complex(1, 0);
//				for (int j = 0; j < Coefficients.N; j++) {
//					omega = omega.multiply(q.subtract(regions.get(l).r[j]));
//				}
//				omega = omega.multiply(regions.get(l).get(0).power(-Coefficients.N));
//				omega = omega.add(new Complex(1, 0));
//				omega = omega.inverse();
//
//				sum = sum.add(omega);
//			}
//			sum = sum.inverse();
//			sum = q.multiply(sum);
			//
			q = sum;

			q = q.add(t);
		}
		return new FractalValue(0, 0, 0, 0, iterations, iterations);
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
