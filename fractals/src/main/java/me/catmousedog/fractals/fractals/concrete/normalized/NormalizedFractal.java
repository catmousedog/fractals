package me.catmousedog.fractals.fractals.concrete.normalized;

import java.awt.Color;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.fractals.LinearTransform;
import me.catmousedog.fractals.main.Settings;
import me.catmousedog.fractals.ui.components.Data;
import me.catmousedog.fractals.ui.components.Item;
import me.catmousedog.fractals.ui.components.concrete.Padding;
import me.catmousedog.fractals.ui.components.concrete.SliderDouble;
import me.catmousedog.fractals.ui.components.concrete.SubTitle;
import me.catmousedog.fractals.ui.components.concrete.TextFieldDouble;

/**
 * Abstract implementation of {@link Fractal} representing a {@link Fractal}
 * generated using 'normalized iteration count'.
 * <p>
 * Any implementation of this class must only override the
 * {@link Fractal#get(double, double)}, all of the {@link String} methods such
 * as {@link Fractal#formalName()} and finally the {@link Fractal#clone()}
 * method and a private constructor to come with the {@link Fractal#clone()}.
 */
public abstract class NormalizedFractal extends Fractal {

	public NormalizedFractal(Settings settings) {
		super(settings);
	}

	protected NormalizedFractal(Settings settings, int iterations, LinearTransform transform, Location[] locations,
			double K) {
		super(settings, iterations, transform, locations);
		this.K = K;
	}

	protected double ra = 1;
	protected double ga = 1;
	protected double ba = 1.1;

	protected double rd = -0.5;
	protected double gd = 0;
	protected double bd = 0.5;

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

	protected double K = 1;

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
