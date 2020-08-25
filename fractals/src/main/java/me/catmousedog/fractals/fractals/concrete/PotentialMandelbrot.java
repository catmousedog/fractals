package me.catmousedog.fractals.fractals.concrete;

import java.awt.Color;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.fractals.LinearTransform;
import me.catmousedog.fractals.main.Settings;
import me.catmousedog.fractals.ui.Savable;
import me.catmousedog.fractals.ui.components.Data;
import me.catmousedog.fractals.ui.components.Item;
import me.catmousedog.fractals.ui.components.concrete.Padding;
import me.catmousedog.fractals.ui.components.concrete.SliderDouble;
import me.catmousedog.fractals.ui.components.concrete.SubTitle;
import me.catmousedog.fractals.ui.components.concrete.TextFieldDouble;

public class PotentialMandelbrot extends Fractal implements Savable {

	public PotentialMandelbrot(Settings settings, int iterations) {
		super(settings, iterations);
	}

	private PotentialMandelbrot(Settings settings, int iterations, LinearTransform transform, Location[] locations,
			double K) {
		super(settings, iterations, transform, locations);
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
				return p / Math.pow(2, i);
			}
		}
		return 0;
	}

	private double ra = 1;
	private double ga = 1;
	private double ba = 1;

	private double rd = 0.5;
	private double gd = 0;
	private double bd = -0.5;

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

	@Override
	public String informalName() {
		return "Potential Mandelbrot";
	}

	@Override
	public String formalName() {
		return "PotentialMandelbrot";
	}

	@Override
	public String getTip() {
		return "<html>The mandelbrot generated using an electrostatic approximation. <br>This allows smooth pictures but longer generating times. <br>Not great for deep zooms.</html>";
	}

	@Override
	public Fractal clone() {
		return new PotentialMandelbrot(settings, iterations, transform.clone(), locations, K);
	}

	/*
	 * Panel
	 */

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
		items = new Item[] { colour, p5, kjtf, kjs };
	}

}
