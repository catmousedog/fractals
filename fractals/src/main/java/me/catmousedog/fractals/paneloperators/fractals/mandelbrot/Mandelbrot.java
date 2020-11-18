package me.catmousedog.fractals.paneloperators.fractals.mandelbrot;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.data.FractalValue;
import me.catmousedog.fractals.paneloperators.fractals.Fractal;
import me.catmousedog.fractals.paneloperators.functions.BinaryFunction;
import me.catmousedog.fractals.paneloperators.functions.EscapeAngleFunction;
import me.catmousedog.fractals.paneloperators.functions.Function;
import me.catmousedog.fractals.paneloperators.functions.IterativeFunction;
import me.catmousedog.fractals.paneloperators.functions.LambertFunction;
import me.catmousedog.fractals.paneloperators.functions.NormalizedFunction;
import me.catmousedog.fractals.paneloperators.functions.PotentialFunction;

public class Mandelbrot extends Fractal {

	public Mandelbrot() {
		super();
		items = null;
		functions = new Function[] { new IterativeFunction(this), new NormalizedFunction(this),
				new PotentialFunction(this), new EscapeAngleFunction(this), new BinaryFunction(this),
				new LambertFunction(this) };
		function = functions[0];
		mouse = null;
	}

	private Mandelbrot(Fractal fractal) {
		super(fractal);
	}

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

			if (s1 + s2 > bailout)
				return new FractalValue(x, y, dx, dy, i, iterations);

			if (usingDerivative) {
				dx = 2 * (tx * tdx - y * dy) + 1;
				dy = 2 * (y * tdx + tx * dy);
			}

			x = s1 - s2 + cx;
			y = 2 * tx * y + cy;

		}
		return new FractalValue(x, y, dx, dy, iterations, iterations);
	}

	/**
	 * tx = x; tdx = dx;
	 * 
	 * t1 = tx * tx; t2 = y * y; t = t1 + t2;
	 * 
	 * if (t > bailout) { double b = dx * dx + dy * dy; double ux = (x * dx + y *
	 * dy) / b; double uy = (y * dx - x * dy) / b; ux /= Math.sqrt(ux * ux + uy *
	 * uy); uy /= Math.sqrt(ux * ux + uy * uy); double h = Math.sqrt(jx * jx + jy *
	 * jy); double l = ux * jx / h + uy * jy / h + h; l /= (1 + h); if (l > 1) l =
	 * 1; if (l < 0) l = 0; return new FractalValue(ux, uy, 0, iterations); }
	 * 
	 * dx = 2 * (tx * tdx - y * dy + 1); dy = 2 * (y * tdx + tx * dy);
	 * 
	 * x = t1 - t2 + cx; y = 2 * tx * y + cy;
	 */

	public boolean ba = true;

	public @NotNull String informalName() {
		return "Mandelbrot";
	}

	public @NotNull String fileName() {
		return informalName();
	}

	public @NotNull String getTip() {
		return "<html>TODO</html>";
	}

	public @NotNull Fractal clone() {
		return new Mandelbrot(this);
	}

}
