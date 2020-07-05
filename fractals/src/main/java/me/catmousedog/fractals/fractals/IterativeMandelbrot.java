package me.catmousedog.fractals.fractals;

public final class IterativeMandelbrot extends Fractal {

	@Override
	public double get(double cx, double cy) {
		
		double x = 0, y = 0;
		
		double tx;
		
		double t1, t2;
		
		for (int i = 0; i < iterations; i++) {

			tx = x;

			t1 = x * x;
			t2 = y * y;

			x = t1 - t2 + cx;
			y = 2 * tx * y + cy;

			if (t1 + t2 > 4)
				return i;

		}

		return 0;
	}

}
