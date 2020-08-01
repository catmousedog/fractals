package me.catmousedog.fractals.fractals.concrete;

import java.awt.Color;

import javax.swing.JPanel;

import me.catmousedog.fractals.fractals.Fractal;

/**
 * Number = Integer
 */
public final class IterativeMandelbrot extends Fractal {

	@Override
	public void save() {
	}

	@Override
	public void update() {
	}

	@Override
	public void preRender() {
	}

	@Override
	public void postRender() {
	}

	/**
	 * returns 0 - 255
	 */
	@Override
	public Number get(double cx, double cy) {

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
				return 255 - (255 * i) / iterations;

		}

		return 0;
	}

	private int r = 1, g = 1, b = 1;

	@Override
	public int filter(Number V) {
		int v = (int) V;
		return new Color(v / r, v / g, v / b).getRGB();
	}

	@Override
	public void addFilter(JPanel jp) {
	}

	@Override
	public String toString() {
		return "Iterative Mandelbrot";
	}
}
