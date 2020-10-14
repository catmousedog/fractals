package me.catmousedog.fractals.fractals.types.potential;

import java.util.Properties;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.fractals.abstract_fractals.Julia;
import me.catmousedog.fractals.fractals.filters.Filter;
import me.catmousedog.fractals.fractals.filters.LogPeriodicFilter;

/**
 * Number = Integer
 */
public final class PotentialJuliaShip extends Julia {

	private int offset;

	public PotentialJuliaShip() {
		super();
	}

	private PotentialJuliaShip(Fractal fractal, double jx, double jy, int offset) {
		super(fractal, jx, jy);
		this.offset = offset;
	}

	@Override
	public Number get(double cx, double cy) {
		double x = cx, y = cy;
		double tx;
		double t1, t2;
		double p;

		for (int i = 0; i < iterations; i++) {
			tx = x;

			t1 = x * x;
			t2 = y * y;

			p = Math.log(t1 + t2) / 2;

			if (p > bailout) {
				return p / Math.pow(2, i);
			}

			x = t1 - t2 + jx;
			y = Math.abs(2 * tx * y) + jy;
		}
		return 0;
	}

	@Override
	public String informalName() {
		return "Potential Julia Ship";
	}

	@Override
	public String fileName() {
		return "PotentialJuliaShip";
	}

	@Override
	public String getTip() {
		return "<html>TODO</html>";
	}

	@Override
	protected void initFractal() {
		filters = new Filter[] { new LogPeriodicFilter(this) };
		filter = filters[0];
		super.initFractal();
	}

	@Override
	public void setProperties(Properties properties) {
		super.setProperties(properties);
		offset = Integer.parseInt(properties.getProperty("offset"));
	}

	@Override
	public Fractal clone() {
		return new PotentialJuliaShip(this, jx, jy, offset);
	}
}
