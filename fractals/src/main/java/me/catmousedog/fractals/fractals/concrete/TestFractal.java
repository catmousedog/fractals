package me.catmousedog.fractals.fractals.concrete;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.main.Settings;
import me.catmousedog.fractals.ui.Savable;
import me.catmousedog.fractals.ui.components.Item;
import me.catmousedog.fractals.ui.components.concrete.SliderInteger;

public class TestFractal extends Fractal implements Savable {

	public TestFractal(Settings settings) {
		super(settings);
	}
	
	private TestFractal(Settings settings, int colour) {
		this(settings);
		this.colour = colour;
	}
	
	@Override
	public Number get(double x, double y) {
		return 0;
	}

	private int colour;

	@Override
	public int filter(Number v) {
		return colour;
	}

	@Override
	public void save() {
		colour = slider.saveAndGet();
	}

	@Override
	public void update() {
		slider.setData(colour);
	}

	@Override
	public void preRender() {
	}

	@Override
	public void postRender() {
	}

	@Override
	public String toString() {
		return "Test Fractal";
	}

	@Override
	public Fractal clone() {
		return new TestFractal(settings, colour);
	}

	private final SliderInteger slider = new SliderInteger.Builder().setLabel("colour").setChange(c -> {
		saveAndColour();
	}).setMin(0).setMax(Integer.MAX_VALUE).build();

	{
		items = new Item[] { slider };
	}

}
