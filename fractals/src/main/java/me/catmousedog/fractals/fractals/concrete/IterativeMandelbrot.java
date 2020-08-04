package me.catmousedog.fractals.fractals.concrete;

import java.awt.Color;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.ui.components.Item;
import me.catmousedog.fractals.ui.components.concrete.SliderDouble;

/**
 * Number = Integer
 */
public final class IterativeMandelbrot extends Fractal {

	public IterativeMandelbrot() {
	}

	private IterativeMandelbrot(double r, double g, double b) {
		this();
		this.r = r;
		this.g = g;
		this.b = b;
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

	private double r = 1, g = 1, b = 1;

	@Override
	public int filter(Number V) {
		int v = (int) V;
		return new Color((int) (v * r), (int) (v * g), (int) (v * b)).getRGB();
	}

	@Override
	public void save() {
		r = rjs.saveAndGet();
		g = gjs.saveAndGet();
		b = bjs.saveAndGet();
	}

	@Override
	public void update() {
		rjs.setData(r);
		gjs.setData(g);
		bjs.setData(b);
	}

	@Override
	public void preRender() {
	}

	@Override
	public void postRender() {
	}

	@Override
	public String toString() {
		return "Iterative Mandelbrot";
	}

	@Override
	public Fractal clone() {
		return new IterativeMandelbrot(r, g, b);
	}

	private final SliderDouble rjs = new SliderDouble.Builder().setLabel("red").setTip("red").setChange(c -> {
		saveAndColour();
	}).build();

	private final SliderDouble gjs = new SliderDouble.Builder().setLabel("green").setTip("red").setChange(c -> {
		saveAndColour();
	}).build();

	private final SliderDouble bjs = new SliderDouble.Builder().setLabel("blue").setTip("red").setChange(c -> {
		saveAndColour();
	}).build();

	{
		items = new Item[] { rjs, gjs, bjs };
	}

}
