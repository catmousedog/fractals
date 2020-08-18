package me.catmousedog.fractals.fractals.concrete;

import java.awt.Color;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.main.Settings;
import me.catmousedog.fractals.ui.components.Data;
import me.catmousedog.fractals.ui.components.Item;
import me.catmousedog.fractals.ui.components.concrete.Button;
import me.catmousedog.fractals.ui.components.concrete.Padding;
import me.catmousedog.fractals.ui.components.concrete.SliderDouble;
import me.catmousedog.fractals.ui.components.concrete.SubTitle;
import me.catmousedog.fractals.ui.components.concrete.TextFieldDouble;

/**
 * Number = Integer
 */
public final class IterativeMandelbrot extends Fractal {

	public IterativeMandelbrot(Settings settings) {
		super(settings);
	}

	private IterativeMandelbrot(Settings settings, double r, double g, double b, boolean inverted) {
		this(settings);
		this.r = r;
		this.g = g;
		this.b = b;
		this.inverted = inverted;
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

			if (t1 + t2 > 100)
				return 255 - (255 * i) / iterations;

			x = t1 - t2 + cx;
			y = 2 * tx * y + cy;
			
		}
		return 0;
	}

	private double r = 1, g = 1, b = 1;

	private boolean inverted = false;

	@Override
	public int filter(Number V) {
		int v = (int) V;
		if (inverted)
			v = 255 - v;
		return new Color((int) (v * r), (int) (v * g), (int) (v * b)).getRGB();
	}

	@Override
	public void save() {
		r = rjtf.saveAndGet();
		g = gjtf.saveAndGet();
		b = bjtf.saveAndGet();
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

	private String name = "Iterative Mandelbrot";

	@Override
	public String toString() {
		return name;
	}

	@Override
	public Fractal clone() {
		return new IterativeMandelbrot(settings, r, g, b, inverted);
	}

	/*
	 * Panel
	 */

	/*
	 * Colour
	 */
	private final SubTitle colour = new SubTitle("Colour",
			"<html>This section contains the fractal specific colour settings.</html>");
	
	private final Button invertjb = new Button.Builder("Invert").setTip("Inverts the current colours.").setAction(a -> {
		inverted = !inverted;
		System.out.println(inverted);
		saveAndColour();
	}).build();

	public Data<Boolean> getInvertjb() {
		return invertjb;
	}

	private final TextFieldDouble rjtf = new TextFieldDouble.Builder().setLabel("red").setTip("red").build();

	private final SliderDouble rjs = new SliderDouble.Builder().setTip("red").setChange(c -> {
		rjtf.setData(getRjs().saveAndGet());
		saveAndColour();
	}).build();

	private final Data<Double> getRjs() {
		return rjs;
	}

	private final TextFieldDouble gjtf = new TextFieldDouble.Builder().setLabel("green").setTip("green").build();

	private final SliderDouble gjs = new SliderDouble.Builder().setTip("green").setChange(c -> {
		gjtf.setData(getGjs().saveAndGet());
		saveAndColour();
	}).build();

	public Data<Double> getGjs() {
		return gjs;
	}

	private final TextFieldDouble bjtf = new TextFieldDouble.Builder().setLabel("blue").setTip("blue").build();

	private final SliderDouble bjs = new SliderDouble.Builder().setTip("blue").setChange(c -> {
		bjtf.setData(getBjs().saveAndGet());
		saveAndColour();
	}).build();

	public Data<Double> getBjs() {
		return bjs;
	}

	private final Padding p5 = new Padding(5);

	{
		items = new Item[] { colour, p5, invertjb, p5, rjtf, rjs, p5, gjtf, gjs, p5, bjtf, bjs };
	}

}
