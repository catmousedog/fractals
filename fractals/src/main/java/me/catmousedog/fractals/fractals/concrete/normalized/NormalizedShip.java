package me.catmousedog.fractals.fractals.concrete.normalized;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.fractals.LinearTransform;
import me.catmousedog.fractals.main.Settings;
import me.catmousedog.fractals.ui.Savable;

public class NormalizedShip extends NormalizedFractal implements Savable {

	public NormalizedShip(Settings settings) {
		super(settings);
	}

	private NormalizedShip(Settings settings, int iterations, LinearTransform transform, Location[] locations,
			double K) {
		super(settings, iterations, transform, locations, K);
	}

	@Override
	public Number get(double cx, double cy) {

		double x = 0, y = 0;
		double tx;
		double t1, t2;

		double p = 0;

		for (int i = 0; i < iterations; i++) {
			tx = x;

			t1 = x * x;
			t2 = y * y;

			x = t1 - t2 + cx;
			y = Math.abs(2 * tx * y) + cy;

			p = Math.log(t1 + t2);

			if (p > bailout) {
				return i + 1 - Math.log(p) / Math.log(2);
			}
		}
		return 0;
	}

	@Override
	public String informalName() {
		return "Normalized Burning Ship";
	}

	@Override
	public String formalName() {
		return "NormalizedShip";
	}

	@Override
	public String getTip() {
		return "<html>The burning ship generated using 'normalized iteration count'. <br>This allows for deep zooms and smooth pictures but long generating times.</html>";
	}

	@Override
	public Fractal clone() {
		return new NormalizedShip(settings, iterations, transform.clone(), locations, K);
	}
}
