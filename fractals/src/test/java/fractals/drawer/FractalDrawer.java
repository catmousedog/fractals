package fractals.drawer;

import java.util.ArrayList;
import java.util.List;

import fractals.rational.Complex;
import me.catmousedog.fractals.data.FractalValue;
import me.catmousedog.fractals.paneloperators.fractals.Fractal;
import me.catmousedog.fractals.paneloperators.functions.Function;
import me.catmousedog.fractals.paneloperators.functions.IterativeFunction;

public class FractalDrawer extends Fractal {

	public static final int ALL = 0, ORIGIN = 1, SELECTED = 2, REGIONS = 3;

	public int mode = ALL;

	public FractalDrawer() {
		functions = new Function[] { new IterativeFunction(this) };
		function = functions[0];
	}

	private FractalDrawer(FractalDrawer fractal) {
		super(fractal);
		for (Region c : fractal.regions) {
			regions.add(c);
		}
	}

	{
		iterations = 5;
	}

	public final Region origin = new Region();

	public final List<Region> regions = new ArrayList<Region>();

	public Region selected;

	public void next(int i) {
		int j = regions.indexOf(selected);
		if (i > 0) {
			j++;
			j %= regions.size();
			selected = regions.get(j);
		} else {
			j--;
			if (j < 0)
				j += regions.size();
			selected = regions.get(j);
		}
	}

	@Override
	public FractalValue get(double cx, double cy) {
		Complex q = new Complex(cx, cy);

		for (int i = 0; i < iterations; i++) {

			if (q.mag() > bailout) {
				return new FractalValue(q.x, q.y, 0, 0, i, iterations);
			}

			// f(z)
			Complex sum;
			if (mode == ALL) {
				sum = origin.omega(q);
				for (Region region : regions) {
					sum = sum.add(region.omega(q));
				}
			} else if (mode == SELECTED) {
				sum = origin.omega(q);
				if (selected != null)
					sum = sum.add(selected.omega(q));
			} else if (mode == REGIONS) {
				sum = new Complex(0, 0);
				for (Region region : regions) {
					sum = sum.add(region.omega(q));
				}
			} else { // ORIGIN
				sum = origin.omega(q);
			}
			sum = sum.inverse();
			sum = q.multiply(sum);
			//
			q = sum;
		}
		return new FractalValue(0, 0, 0, 0, iterations, iterations);
	}

	@Override
	public void update() {
		origin.update();
		for (Region region : regions)
			region.update();
	}

	@Override
	public Fractal clone() {
		return new FractalDrawer(this);
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
