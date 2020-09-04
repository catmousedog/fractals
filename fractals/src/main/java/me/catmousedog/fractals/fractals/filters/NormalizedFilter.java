package me.catmousedog.fractals.fractals.filters;

import java.awt.Color;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.ui.components.Item;
import me.catmousedog.fractals.ui.components.concrete.Padding;
import me.catmousedog.fractals.ui.components.concrete.SliderDouble;
import me.catmousedog.fractals.ui.components.concrete.TextFieldDouble;

public class NormalizedFilter extends Filter {

	private double ra;
	private double ga;
	private double ba;

	private double rd;
	private double gd;
	private double bd;

	private double K;

	public NormalizedFilter(Fractal fractal) {
		super(fractal);
		ra = 1;
		ga = 1;
		ba = 1;
		rd = -0.5;
		gd = 0;
		bd = 0.5;
		K = 0.5;
	}

	/**
	 * Constructor used to clone the {@link Filter}.
	 * 
	 * @param filter
	 */
	private NormalizedFilter(Filter filter) {
		super(filter);
	}

	@Override
	public int get(Number v) {
		int r = curve(ra, rd, v.doubleValue());
		int g = curve(ga, gd, v.doubleValue());
		int b = curve(ba, bd, v.doubleValue());
		return new Color(r, g, b).getRGB();
	}

	private int curve(double a, double d, double v) {
		return (int) (127.5 * (1.0 + (1) * Math.cos(a * Math.log(v) / K - d)));
	}

	@Override
	public void setFilter(Filter filter) {
		if (filter instanceof NormalizedFilter) {
			NormalizedFilter nfilter = (NormalizedFilter) filter;
			ra = nfilter.ra;
			ga = nfilter.ga;
			ba = nfilter.ba;
			rd = nfilter.rd;
			gd = nfilter.gd;
			bd = nfilter.bd;
			K = nfilter.K;
		}
	}

	@Override
	public void save() {
		K = kjtf.saveAndGet();
		ra = rajtf.saveAndGet();
	}

	@Override
	public void update() {
		kjtf.setData(K);
		kjs.setData(K);
		rajtf.setData(ra);
		rajs.setData(ra);
	}

	@Override
	public Filter clone() {
		return new NormalizedFilter(this);
	}
	
	@Override
	public String toString() {
		return "Normalized Filter";
	}

	/*
	 * Panel
	 */
	private TextFieldDouble kjtf;

	private SliderDouble kjs;

	private TextFieldDouble rajtf;

	private SliderDouble rajs;

	@Override
	protected void initPanel() {
		Padding p5 = new Padding(5);

		String tipK = "<html>The inverse frequency factor.<br> The close to zero this value is, the higher the frequency of the colour filter.</html>";

		kjtf = new TextFieldDouble.Builder().setLabel("inverse frequency").setTip(tipK).setDefault(K).build();

		kjs = new SliderDouble.Builder().setTip(tipK).setChange(c -> changeK()).build();

		String tipRa = "<html>The frequence factor for the red component.</html>";

		rajtf = new TextFieldDouble.Builder().setLabel("red frequency").setTip(tipRa).build();

		rajs = new SliderDouble.Builder().setTip(tipRa).setChange(c -> changeRa()).build();

		items = new Item[] { kjtf, kjs, p5, rajtf, rajs };
	}

	private void changeK() {
		kjtf.setData(kjs.saveAndGet());
		fractal.saveAndColour();
	}

	private void changeRa() {
		rajtf.setData(rajs.saveAndGet());
		fractal.saveAndColour();
	}
}