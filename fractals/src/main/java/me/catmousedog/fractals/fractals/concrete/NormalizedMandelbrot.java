package me.catmousedog.fractals.fractals.concrete;

import java.awt.Color;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.main.Settings;
import me.catmousedog.fractals.ui.Savable;
import me.catmousedog.fractals.ui.components.Data;
import me.catmousedog.fractals.ui.components.Item;
import me.catmousedog.fractals.ui.components.concrete.Padding;
import me.catmousedog.fractals.ui.components.concrete.SliderDouble;
import me.catmousedog.fractals.ui.components.concrete.SubTitle;
import me.catmousedog.fractals.ui.components.concrete.TextFieldDouble;

public class NormalizedMandelbrot extends Fractal implements Savable {

	public NormalizedMandelbrot(Settings settings) {
		super(settings);
	}

	private NormalizedMandelbrot(Settings settings, double K) {
		super(settings);
		this.K = K;
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
			y = 2 * tx * y + cy;

			p = Math.log(t1 + t2);


			if (p > 10) {
				return i + 1 - Math.log(p) / Math.log(2);
			}
		}
		return 0;
	}

	private double ra = 1;
	private double ga = 1;
	private double ba = 1.1;

	private double rd = -0.5;
	private double gd = 0;
	private double bd = 0.5;
	
	@Override
	public int filter(Number v) {
		int r = curve(ra, rd, v.doubleValue());
		int g = curve(ga, gd, v.doubleValue());
		int b = curve(ba, bd, v.doubleValue());
		return new Color(r, g, b).getRGB();
	}

	private int curve(double a, double d, double v) {
		return (int) (127.5 * (1.0 + (1) * Math.cos(a * Math.log(v) / K - d)));
	}

	private double K = 1;

	@Override
	public void save() {
		K = kjtf.saveAndGet();
	}

	@Override
	public void update() {
		kjs.setData(K);
	}

	@Override
	public void preRender() {
	}

	@Override
	public void postRender() {
	}

	private String name = "Normalized Mandelbrot";

	@Override
	public String toString() {
		return name;
	}

	@Override
	public Fractal clone() {
		return new NormalizedMandelbrot(settings, K);
	}

	/*
	 * Panel
	 */

	/*
	 * Caclulation
	 */
	private final SubTitle calculation = new SubTitle("Calculation",
			"<html>This section contains the fractal specific calculation settings.</html>");

	/*
	 * Colour
	 */
	private final SubTitle colour = new SubTitle("Colour",
			"<html>This section contains the fractal specific colour settings.</html>");

	private final TextFieldDouble kjtf = new TextFieldDouble.Builder().setLabel("K").setTip("red").build();

	private final SliderDouble kjs = new SliderDouble.Builder().setTip("red").setChange(c -> {
		kjtf.setData(getKjs().saveAndGet());
		saveAndColour();
	}).build();

	public Data<Double> getKjs() {
		return kjs;
	}

	private final Padding p5 = new Padding(5);

	{
		items = new Item[] { calculation, p5, colour, p5, kjtf, kjs };
	}

}
