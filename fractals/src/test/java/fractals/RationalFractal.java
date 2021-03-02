package fractals;

import java.util.List;

import me.catmousedog.fractals.data.FractalValue;
import me.catmousedog.fractals.paneloperators.fractals.Fractal;
import me.catmousedog.fractals.paneloperators.functions.Function;
import me.catmousedog.fractals.paneloperators.functions.IterativeFunction;

public class RationalFractal extends Fractal {

	private final List<Pole> poles;

	private double C = 1;

	public RationalFractal(List<Pole> poles) {
		super();

		this.poles = poles;

		functions = new Function[] { new IterativeFunction(this) };
		function = functions[0];
	}

	private RationalFractal(RationalFractal fractal) {
		super(fractal);
		poles = fractal.poles;
		C = fractal.C;
	}

	{
		iterations = 20;
		bailout = 10000;
	}

	@Override
	public FractalValue get(double cx, double cy) {
		Complex q = new Complex(cx, cy);

		for (int i = 0; i < iterations; i++) {

			if (q.mag() > bailout) {
				return new FractalValue(q.x, q.y, 0, 0, i, iterations);
			}

			// calculate R(q)
			Complex P = new Complex(1, 0);
			for (Pole p : poles) {
				P.multiply(p.poly(q));
			}

			P.multiply(new Complex(C, 0));
			q = P;
		}
		return new FractalValue(0, 0, 0, 0, iterations, iterations);
	}

	@Override
	public Fractal clone() {
		return new RationalFractal(this);
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

	public void setC(double c) {
		C = c;
	}

}
