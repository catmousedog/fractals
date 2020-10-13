package me.catmousedog.fractals.fractals.filters;

import java.awt.Color;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.ui.components.Item;
import me.catmousedog.fractals.ui.components.concrete.Padding;
import me.catmousedog.fractals.ui.components.concrete.SliderDouble;
import me.catmousedog.fractals.ui.components.concrete.TextFieldDouble;

public class LogPeriodicFilter extends Filter {

	private double r, g, b;

	private double K;

	private double rf, gf, bf;

	private double rd, gd, bd;

	public LogPeriodicFilter(Fractal fractal) {
		super(fractal);
		r = 1;
		g = 1;
		b = 1;
		K = 0.5;
		rf = 1;
		gf = 1;
		bf = 1;
		rd = 0;
		gd = 0;
		bd = 0;
	}

	/**
	 * Constructor used to clone the {@link Filter}.
	 * 
	 * @param filter
	 */
	private LogPeriodicFilter(Filter filter) {
		super(filter);
	}

	@Override
	public int get(Number v) {
		int cr = curve(r, rf, rd, v.doubleValue());
		int cg = curve(g, gf, gd, v.doubleValue());
		int cb = curve(b, bf, bd, v.doubleValue());
		return new Color(cr, cg, cb).getRGB();
	}

	private int curve(double a, double f, double d, double v) {
		return (int) (a * 127.5 * (1.0 - Math.cos(f * Math.log(Math.abs(v)) / K - d)));
	}

	@Override
	public void setFilter(Filter filter) {
		if (filter instanceof LogPeriodicFilter) {
			LogPeriodicFilter nfilter = (LogPeriodicFilter) filter;
			r = nfilter.r;
			g = nfilter.g;
			b = nfilter.b;
			K = nfilter.K;
			rf = nfilter.rf;
			gf = nfilter.gf;
			bf = nfilter.bf;
			rd = nfilter.rd;
			gd = nfilter.gd;
			bd = nfilter.bd;
		}
	}

	@Override
	public void save() {
		r = rjtf.saveAndGet();
		g = gjtf.saveAndGet();
		b = bjtf.saveAndGet();
		K = kjtf.saveAndGet();
		rf = rajtf.saveAndGet();
		gf = gajtf.saveAndGet();
		bf = bajtf.saveAndGet();
		rd = rdjtf.saveAndGet();
		gd = gdjtf.saveAndGet();
		bd = bdjtf.saveAndGet();
	}

	@Override
	public void update() {
		rjtf.setData(r);
		rjs.setDataSafe(r);
		gjtf.setData(b);
		gjs.setDataSafe(g);
		bjtf.setData(b);
		bjs.setDataSafe(b);
		kjtf.setData(K);
		kjs.setDataSafe(K);
		rajtf.setData(rf);
		rajs.setDataSafe(rf);
		gajtf.setData(gf);
		gajs.setDataSafe(gf);
		bajtf.setData(bf);
		bajs.setDataSafe(bf);
		rdjtf.setData(rd);
		rdjs.setDataSafe(rd);
		gdjtf.setData(gd);
		gdjs.setDataSafe(gd);
		bdjtf.setData(bd);
		bdjs.setDataSafe(bd);
	}

	@Override
	public Filter clone() {
		return new LogPeriodicFilter(this);
	}

	@Override
	public String getTip() {
		return "<html>A filter designed to be used with 'smooth' fractals.<br>"
				+ "It uses a periodic function of the form <i>cos(ln(x))</i> for the red, green and blue components.<br>"
				+ "This filter is best used with values close to zero i.e. 'smooth' fractals.<br>"
				+ "Each 'curve' has its own amplitude, frequency and offset which can be changed.</html>";
	}

	@Override
	public String toString() {
		return "Logarithmic Periodic Filter";
	}

	/*
	 * Panel
	 */
	private TextFieldDouble rjtf;
	private SliderDouble rjs;

	private TextFieldDouble gjtf;
	private SliderDouble gjs;

	private TextFieldDouble bjtf;
	private SliderDouble bjs;

	private TextFieldDouble kjtf;
	private SliderDouble kjs;

	private TextFieldDouble rajtf;
	private SliderDouble rajs;

	private TextFieldDouble gajtf;
	private SliderDouble gajs;

	private TextFieldDouble bajtf;
	private SliderDouble bajs;

	private TextFieldDouble rdjtf;
	private SliderDouble rdjs;

	private TextFieldDouble gdjtf;
	private SliderDouble gdjs;

	private TextFieldDouble bdjtf;
	private SliderDouble bdjs;

	@Override
	protected void initPanel() {
		Padding p5 = new Padding(5);

		String rTip = "<html>The amplitude for the red curve</html>";

		rjtf = new TextFieldDouble.Builder().setLabel("red factor").setTip(rTip).build();

		rjs = new SliderDouble.Builder().setTip(rTip).setChange(c -> changeR()).build();

		String gTip = "<html>The amplitude for the green curve</html>";

		gjtf = new TextFieldDouble.Builder().setLabel("green factor").setTip(gTip).build();

		gjs = new SliderDouble.Builder().setTip(gTip).setChange(c -> changeG()).build();

		String bTip = "<html>The amplitude for the blue curve</html>";

		bjtf = new TextFieldDouble.Builder().setLabel("blue factor").setTip(bTip).build();

		bjs = new SliderDouble.Builder().setTip(bTip).setChange(c -> changeB()).build();

		String tipK = "<html>The inverse frequency factor.<br> The close to zero this value is, the higher the frequency of the global colour filter.</html>";

		kjtf = new TextFieldDouble.Builder().setLabel("inverse frequency").setTip(tipK).setDefault(K).build();

		kjs = new SliderDouble.Builder().setTip(tipK).setMin(0.01).setMax(1).setChange(c -> changeK()).build();

		String tipRa = "<html>The frequency factor for the red component</html>";

		rajtf = new TextFieldDouble.Builder().setLabel("red frequency").setTip(tipRa).build();

		rajs = new SliderDouble.Builder().setTip(tipRa).setChange(c -> changeRf()).build();

		String tipGa = "<html>The frequency factor for the green component</html>";

		gajtf = new TextFieldDouble.Builder().setLabel("green frequency").setTip(tipGa).build();

		gajs = new SliderDouble.Builder().setTip(tipGa).setChange(c -> changeGf()).build();

		String tipBa = "<html>The frequency factor for the blue component</html>";

		bajtf = new TextFieldDouble.Builder().setLabel("blue frequency").setTip(tipBa).build();

		bajs = new SliderDouble.Builder().setTip(tipBa).setChange(c -> changeBf()).build();

		String tipRd = "<html>The offset for the red component</html>";

		rdjtf = new TextFieldDouble.Builder().setLabel("red offset").setTip(tipRd).build();

		rdjs = new SliderDouble.Builder().setTip(tipRd).setMax(Math.PI * 2).setChange(c -> changeRd()).build();

		String tipGd = "<html>The offset for the green component</html>";

		gdjtf = new TextFieldDouble.Builder().setLabel("green offset").setTip(tipGd).build();

		gdjs = new SliderDouble.Builder().setTip(tipGd).setMax(Math.PI * 2).setChange(c -> changeGd()).build();

		String tipBd = "<html>The offset for the red component</html>";

		bdjtf = new TextFieldDouble.Builder().setLabel("blue offset").setTip(tipBd).build();

		bdjs = new SliderDouble.Builder().setTip(tipBd).setMax(Math.PI * 2).setChange(c -> changeBd()).build();

		items = new Item[] { rjtf, rjs, p5, gjtf, gjs, p5, bjtf, bjs, p5, kjtf, kjs, p5, rajtf, rajs, p5, gajtf, gajs,
				p5, bajtf, bajs, p5, rdjtf, rdjs, p5, gdjtf, gdjs, p5, bdjtf, bdjs };
	}

	private void changeR() {
		rjtf.setData(rjs.saveAndGet());
		fractal.saveAndColour();
	}

	private void changeG() {
		gjtf.setData(gjs.saveAndGet());
		fractal.saveAndColour();
	}

	private void changeB() {
		bjtf.setData(bjs.saveAndGet());
		fractal.saveAndColour();
	}

	private void changeK() {
		kjtf.setData(kjs.saveAndGet());
		fractal.saveAndColour();
	}

	private void changeRf() {
		rajtf.setData(rajs.saveAndGet());
		fractal.saveAndColour();
	}

	private void changeGf() {
		gajtf.setData(gajs.saveAndGet());
		fractal.saveAndColour();
	}

	private void changeBf() {
		bajtf.setData(bajs.saveAndGet());
		fractal.saveAndColour();
	}

	private void changeRd() {
		rdjtf.setData(rdjs.saveAndGet());
		fractal.saveAndColour();
	}

	private void changeGd() {
		gdjtf.setData(gdjs.saveAndGet());
		fractal.saveAndColour();
	}

	private void changeBd() {
		bdjtf.setData(bdjs.saveAndGet());
		fractal.saveAndColour();
	}

}