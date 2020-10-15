package me.catmousedog.fractals.fractals.types.normalized;

import java.util.Properties;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.fractals.abstract_fractals.Julia;
import me.catmousedog.fractals.fractals.filters.Filter;
import me.catmousedog.fractals.fractals.filters.LogPeriodicFilter;
import me.catmousedog.fractals.main.Settings;

public class NormalizedJuliaShip extends Julia {

	private int offset;

	public NormalizedJuliaShip() {
		super();
	}

	private NormalizedJuliaShip(Fractal fractal, double jx, double jy, int offset) {
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
				return i + offset - Math.log(p) / Math.log(2);
			}

			x = t1 - t2 + jx;
			y = Math.abs(2 * tx * y) + jy;
		}
		return 0;
	}

	@Override
	protected void initFractal() {
		filters = new Filter[] { new LogPeriodicFilter(this) };
		filter = filters[0];
		super.initFractal();
	}

	@Override
	public void setProperties(@NotNull Settings settings, @NotNull Properties properties) {
		super.setProperties(settings, properties);
		offset = Integer.parseInt(properties.getProperty("offset"));
	}

	@Override
	public @NotNull String informalName() {
		return "Normalized Julia Ship";
	}

	@Override
	public @NotNull String fileName() {
		return "NormalizedJuliaShip";
	}

	@Override
	public @NotNull String getTip() {
		return "<html>TODO</html>";
	}

	@Override
	public @NotNull Fractal clone() {
		return new NormalizedJuliaShip(this, jx, jy, offset);
	}

}
