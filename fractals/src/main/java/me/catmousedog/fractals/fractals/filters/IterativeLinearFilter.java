package me.catmousedog.fractals.fractals.filters;

import java.awt.Color;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.ui.components.ActiveData;
import me.catmousedog.fractals.ui.components.Data;
import me.catmousedog.fractals.ui.components.Item;
import me.catmousedog.fractals.ui.components.concrete.Button;
import me.catmousedog.fractals.ui.components.concrete.Padding;
import me.catmousedog.fractals.ui.components.concrete.SliderDouble;
import me.catmousedog.fractals.ui.components.concrete.TextFieldDouble;

/**
 * A {@link Filter} designed to be used with iterative {@link Fractal}s. The
 * {@link Filter#get(Number)} takes integers ranging from 0 to 255.<br>
 * The colour pattern is linear and known for its simplicity.
 */
public class IterativeLinearFilter extends Filter {

	private boolean inverted;

	private double r;
	private double g;
	private double b;

	public IterativeLinearFilter(Fractal fractal) {
		super(fractal);
		inverted = false;
		r = 1;
		b = 1;
		g = 1;
	}

	/**
	 * Constructor used to clone the {@link Filter}.
	 * 
	 * @param filter
	 */
	private IterativeLinearFilter(Filter filter) {
		super(filter);
	}

	@Override
	public int get(Number V) {
		int v = (int) V;
		if (inverted)
			v = 255 - v;
		return new Color((int) (v * r), (int) (v * g), (int) (v * b)).getRGB();
	}

	@Override
	public void setFilter(Filter filter) {
		if (filter instanceof IterativeLinearFilter) {
			IterativeLinearFilter nfilter = (IterativeLinearFilter) filter;
			inverted = nfilter.inverted;
			r = nfilter.r;
			g = nfilter.g;
			b = nfilter.b;
		} else {
			System.err.println("setFilter not the same instance");
		}
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
	public Filter clone() {
		return new IterativeLinearFilter(this);
	}
	
	@Override
	public String toString() {
		return "Linear Filter";
	}

	/*
	 * Panel
	 */

	private Button invertjb;

	public Data<Boolean> getInvertjb() {
		return invertjb;
	}

	private TextFieldDouble rjtf;

	private SliderDouble rjs;

	private ActiveData<Double> getRjs() {
		return rjs;
	}

	private TextFieldDouble gjtf;

	private SliderDouble gjs;

	public ActiveData<Double> getGjs() {
		return gjs;
	}

	private TextFieldDouble bjtf;

	private SliderDouble bjs;

	public ActiveData<Double> getBjs() {
		return bjs;
	}

	@Override
	protected void initPanel() {

		Padding p5 = new Padding(5);

		invertjb = new Button.Builder("Invert").setTip("Inverts the current colours.").setAction(a -> actionInvert())
				.build();

		rjtf = new TextFieldDouble.Builder().setLabel("red").setTip("red").build();

		rjs = new SliderDouble.Builder().setTip("red").setChange(c -> changeR()).build();

		gjtf = new TextFieldDouble.Builder().setLabel("green").setTip("green").build();

		gjs = new SliderDouble.Builder().setTip("green").setChange(c -> changeG()).build();

		bjtf = new TextFieldDouble.Builder().setLabel("blue").setTip("blue").build();

		bjs = new SliderDouble.Builder().setTip("blue").setChange(c -> changeB()).build();

		items = new Item[] { invertjb, p5, rjtf, rjs, p5, gjtf, gjs, p5, bjtf, bjs };
	}

	private void actionInvert() {
		inverted = !inverted;
		fractal.saveAndColour();
	}

	private void changeR() {
		rjtf.setData(getRjs().saveAndGet());
		fractal.saveAndColour();
	}

	private void changeG() {
		gjtf.setData(getGjs().saveAndGet());
		fractal.saveAndColour();
	}

	private void changeB() {
		bjtf.setData(getBjs().saveAndGet());
		fractal.saveAndColour();
	}

}