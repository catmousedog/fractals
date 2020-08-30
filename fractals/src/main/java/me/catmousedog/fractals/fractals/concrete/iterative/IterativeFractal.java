package me.catmousedog.fractals.fractals.concrete.iterative;

import java.awt.Color;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.fractals.LinearTransform;
import me.catmousedog.fractals.main.Settings;
import me.catmousedog.fractals.ui.components.ActiveData;
import me.catmousedog.fractals.ui.components.Data;
import me.catmousedog.fractals.ui.components.Item;
import me.catmousedog.fractals.ui.components.concrete.Button;
import me.catmousedog.fractals.ui.components.concrete.Padding;
import me.catmousedog.fractals.ui.components.concrete.SliderDouble;
import me.catmousedog.fractals.ui.components.concrete.SubTitle;
import me.catmousedog.fractals.ui.components.concrete.TextFieldDouble;

/**
 * Abstract implementation of {@link Fractal} representing an iterative
 * {@link Fractal}.
 * <p>
 * Any implementation of this class must only override the
 * {@link Fractal#get(double, double)}, all of the {@link String} methods such
 * as {@link Fractal#formalName()} and finally the {@link Fractal#clone()}
 * method and a private constructor to come with the {@link Fractal#clone()}.
 */
public abstract class IterativeFractal extends Fractal {

	public IterativeFractal(Settings settings) {
		super(settings);
	}

	protected IterativeFractal(Settings settings, int iterations, LinearTransform transform, Location[] locations,
			double r, double g, double b, boolean inverted) {
		super(settings, iterations, transform, locations);
		this.r = r;
		this.g = g;
		this.b = b;
		this.inverted = inverted;
	}

	protected double r = 1, g = 1, b = 1;

	protected boolean inverted = false;

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
		rjs.setDataSafe(r);
		gjs.setDataSafe(g);
		bjs.setDataSafe(b);
		rjtf.setData(r);
		gjtf.setData(b);
		bjtf.setData(b);
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

	private final Button invertjb = new Button.Builder("Invert").setTip("Inverts the current colours.").setAction(a -> {
		inverted = !inverted;
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

	private final ActiveData<Double> getRjs() {
		return rjs;
	}

	private final TextFieldDouble gjtf = new TextFieldDouble.Builder().setLabel("green").setTip("green").build();

	private final SliderDouble gjs = new SliderDouble.Builder().setTip("green").setChange(c -> {
		gjtf.setData(getGjs().saveAndGet());
		saveAndColour();
	}).build();

	public ActiveData<Double> getGjs() {
		return gjs;
	}

	private final TextFieldDouble bjtf = new TextFieldDouble.Builder().setLabel("blue").setTip("blue").build();

	private final SliderDouble bjs = new SliderDouble.Builder().setTip("blue").setChange(c -> {
		bjtf.setData(getBjs().saveAndGet());
		saveAndColour();
	}).build();

	public ActiveData<Double> getBjs() {
		return bjs;
	}

	private final Padding p5 = new Padding(5);

	{
		items = new Item[] { colour, p5, invertjb, p5, rjtf, rjs, p5, gjtf, gjs, p5, bjtf, bjs };
	}

}
