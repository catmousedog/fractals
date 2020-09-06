package me.catmousedog.fractals.fractals.filters;

import java.awt.Color;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.ui.components.Item;
import me.catmousedog.fractals.ui.components.concrete.Button;
import me.catmousedog.fractals.ui.components.concrete.Padding;
import me.catmousedog.fractals.ui.components.concrete.SliderDouble;
import me.catmousedog.fractals.ui.components.concrete.TextFieldDouble;

/**
 * A {@link Filter} designed to be used with iterative {@link Fractal}s. The
 * {@link Filter#get(Number)} takes integers ranging from 0 to 255.<br>
 * The colour pattern is periodic, using a different frequency for red, green
 * and blue.
 */
public class IterativePeriodicFilter extends Filter {

	private boolean inverted;

	private double r;
	private double g;
	private double b;

	private double rf;
	private double gf;
	private double bf;

	public IterativePeriodicFilter(Fractal fractal) {
		super(fractal);
		inverted = false;
		r = 1;
		b = 1;
		g = 1;
		rf = 0.01;
		gf = 0.01;
		bf = 0.01;
	}

	/**
	 * Constructor used to clone the {@link Filter}.
	 * 
	 * @param filter
	 */
	private IterativePeriodicFilter(Filter filter) {
		super(filter);
	}

	@Override
	public int get(Number V) {
		int v = (int) V;
		if (inverted)
			v = 255 - v;
		return new Color(curve(r, rf, v), curve(g, gf, v), curve(b, bf, v)).getRGB();
	}

	private int curve(double a, double f, int v) {
		return (int) (a * 127.5 * (1 - Math.cos(f * v)));
	}

	@Override
	public void setFilter(Filter filter) {
		if (filter instanceof IterativePeriodicFilter) {
			IterativePeriodicFilter nfilter = (IterativePeriodicFilter) filter;
			inverted = nfilter.inverted;
			r = nfilter.r;
			g = nfilter.g;
			b = nfilter.b;
			rf = nfilter.rf;
			gf = nfilter.gf;
			bf = nfilter.bf;
		} else {
			System.err.println("setFilter not the same instance");
		}
	}

	@Override
	public void save() {
		r = rjtf.saveAndGet();
		g = gjtf.saveAndGet();
		b = bjtf.saveAndGet();
		rf = rfjtf.saveAndGet();
		gf = gfjtf.saveAndGet();
		bf = bfjtf.saveAndGet();
	}

	@Override
	public void update() {
		rjtf.setData(r);
		gjtf.setData(b);
		bjtf.setData(b);
		rjs.setDataSafe(r);
		gjs.setDataSafe(g);
		bjs.setDataSafe(b);
		rfjtf.setData(rf);
		gfjtf.setData(gf);
		bfjtf.setData(bf);
		rfjs.setDataSafe(rf);
		gfjs.setDataSafe(gf);
		bfjs.setDataSafe(bf);
	}

	@Override
	public Filter clone() {
		return new IterativePeriodicFilter(this);
	}

	@Override
	public String getTip() {
		return "<html>A filter designed to be used with iterative fractals."
				+ "<br>This filter uses a periodic function for the red, green and blue components.<br>"
				+ "Each 'curve' has its own amplitude and frequency which can be changed.<br>"
				+ "This filter doesn't work well with high frequencies as the aliasing effect<br>"
				+ "of iterative fractals become more nuanced.</html>";
	}

	@Override
	public String toString() {
		return "Periodic Filter";
	}

	/*
	 * Panel
	 */

	private Button invertjb;

	private TextFieldDouble rjtf;
	private SliderDouble rjs;

	private TextFieldDouble gjtf;
	private SliderDouble gjs;

	private TextFieldDouble bjtf;
	private SliderDouble bjs;

	private TextFieldDouble rfjtf;
	private SliderDouble rfjs;

	private TextFieldDouble gfjtf;
	private SliderDouble gfjs;

	private TextFieldDouble bfjtf;
	private SliderDouble bfjs;

	@Override
	protected void initPanel() {

		Padding p5 = new Padding(5);

		invertjb = new Button.Builder("Invert").setTip("Inverts the current colours.").setAction(a -> actionInvert())
				.build();

		String rTip = "<html>The amplitude for the red curve</html>";

		rjtf = new TextFieldDouble.Builder().setLabel("red factor").setTip(rTip).build();

		rjs = new SliderDouble.Builder().setTip(rTip).setChange(c -> changeR()).build();

		String gTip = "<html>TThe amplitude for the green curve</html>";

		gjtf = new TextFieldDouble.Builder().setLabel("green factor").setTip(gTip).build();

		gjs = new SliderDouble.Builder().setTip(gTip).setChange(c -> changeG()).build();

		String bTip = "<html>The amplitude for the blue curve</html>";

		bjtf = new TextFieldDouble.Builder().setLabel("blue factor").setTip(bTip).build();

		bjs = new SliderDouble.Builder().setTip(bTip).setChange(c -> changeB()).build();

		String rfTip = "<html>The frequency factor for the red component</html>";

		rfjtf = new TextFieldDouble.Builder().setLabel("red frequency").setTip(rfTip).build();

		rfjs = new SliderDouble.Builder().setTip(rfTip).setChange(c -> changeRf()).setMax(0.1).build();

		String gfTip = "<html>The frequency factor for the green component</html>";

		gfjtf = new TextFieldDouble.Builder().setLabel("green frequency").setTip(gfTip).build();

		gfjs = new SliderDouble.Builder().setTip(gfTip).setChange(c -> changeGf()).setMax(0.1).build();

		String bfTip = "<html>The frequency factor for the blue component</html>";

		bfjtf = new TextFieldDouble.Builder().setLabel("blue frequency").setTip(bfTip).build();

		bfjs = new SliderDouble.Builder().setTip(bfTip).setChange(c -> changeBf()).setMax(0.1).build();

		items = new Item[] { invertjb, p5, rjtf, rjs, p5, gjtf, gjs, p5, bjtf, bjs, p5, rfjtf, rfjs, p5, gfjtf, gfjs,
				p5, bfjtf, bfjs };
	}

	private void actionInvert() {
		inverted = !inverted;
		fractal.saveAndColour();
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

	private void changeRf() {
		rfjtf.setData(rfjs.saveAndGet());
		fractal.saveAndColour();
	}

	private void changeGf() {
		gfjtf.setData(gfjs.saveAndGet());
		fractal.saveAndColour();
	}

	private void changeBf() {
		bfjtf.setData(bfjs.saveAndGet());
		fractal.saveAndColour();
	}

}