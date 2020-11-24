package me.catmousedog.fractals.paneloperators.filters;

import me.catmousedog.fractals.paneloperators.fractals.Fractal;
import me.catmousedog.fractals.ui.components.Item;
import me.catmousedog.fractals.ui.components.concrete.Button;
import me.catmousedog.fractals.ui.components.concrete.Padding;
import me.catmousedog.fractals.ui.components.concrete.SliderDouble;
import me.catmousedog.fractals.ui.components.concrete.SubTitle;
import me.catmousedog.fractals.ui.components.concrete.TextFieldDouble;

/**
 * A {@link Filter} designed to be used with iterative {@link Fractal}s. The
 * {@link Filter#apply(Number)} takes integers ranging from 0 to 255.<br>
 * The colour pattern is periodic, using a different frequency for red, green
 * and blue.
 */
public class PeriodicFilter extends Filter {

	private boolean inverted;

	private Button invertjb;

	private double r, g, b;

	private TextFieldDouble rjtf;
	private SliderDouble rjs;

	private TextFieldDouble gjtf;
	private SliderDouble gjs;

	private TextFieldDouble bjtf;
	private SliderDouble bjs;

	private double rf, gf, bf;

	private TextFieldDouble rfjtf;
	private SliderDouble rfjs;

	private TextFieldDouble gfjtf;
	private SliderDouble gfjs;

	private TextFieldDouble bfjtf;
	private SliderDouble bfjs;

	private double ro, bo, go;

	private TextFieldDouble rojtf;
	private SliderDouble rojs;

	private TextFieldDouble gojtf;
	private SliderDouble gojs;

	private TextFieldDouble bojtf;
	private SliderDouble bojs;

	public PeriodicFilter(Fractal fractal) {
		super(fractal);

		Padding p5 = new Padding(5);
		invertjb = new Button.Builder("Invert").setTip("<html>Inverts the current colours</html>")
				.setAction(a -> actionInvert()).build();

		SubTitle amp = new SubTitle("amplitude");
		String rTip = "<html>The amplitude for the red curve</html>";
		rjtf = new TextFieldDouble.Builder().setLabel("red factor").setTip(rTip).build();
		rjs = new SliderDouble.Builder().setTip(rTip).setChange(c -> changeR()).build();
		String gTip = "<html>TThe amplitude for the green curve</html>";
		gjtf = new TextFieldDouble.Builder().setLabel("green factor").setTip(gTip).build();
		gjs = new SliderDouble.Builder().setTip(gTip).setChange(c -> changeG()).build();
		String bTip = "<html>The amplitude for the blue curve</html>";
		bjtf = new TextFieldDouble.Builder().setLabel("blue factor").setTip(bTip).build();
		bjs = new SliderDouble.Builder().setTip(bTip).setChange(c -> changeB()).build();

		SubTitle fre = new SubTitle("frequency");
		String rfTip = "<html>The frequency factor for the red component</html>";
		rfjtf = new TextFieldDouble.Builder().setLabel("red frequency").setTip(rfTip).build();
		rfjs = new SliderDouble.Builder().setTip(rfTip).setChange(c -> changeRf()).setMin(0.5).setMax(10.5).build();
		String gfTip = "<html>The frequency factor for the green component</html>";
		gfjtf = new TextFieldDouble.Builder().setLabel("green frequency").setTip(gfTip).build();
		gfjs = new SliderDouble.Builder().setTip(gfTip).setChange(c -> changeGf()).setMin(0.5).setMax(10.5).build();
		String bfTip = "<html>The frequency factor for the blue component</html>";
		bfjtf = new TextFieldDouble.Builder().setLabel("blue frequency").setTip(bfTip).build();
		bfjs = new SliderDouble.Builder().setTip(bfTip).setChange(c -> changeBf()).setMin(0.5).setMax(10.5).build();

		SubTitle off = new SubTitle("offset");
		String roTip = "<html>The offset for the red component</html>";
		rojtf = new TextFieldDouble.Builder().setLabel("red offset").setTip(roTip).build();
		rojs = new SliderDouble.Builder().setTip(roTip).setChange(c -> changeRo()).setMin(0).setMax(1).build();
		String goTip = "<html>The offset for the green component</html>";
		gojtf = new TextFieldDouble.Builder().setLabel("green offset").setTip(goTip).build();
		gojs = new SliderDouble.Builder().setTip(goTip).setChange(c -> changeGo()).setMin(0).setMax(1).build();
		String boTip = "<html>The offset for the blue component</html>";
		bojtf = new TextFieldDouble.Builder().setLabel("blue offset").setTip(boTip).build();
		bojs = new SliderDouble.Builder().setTip(boTip).setChange(c -> changeBo()).setMin(0).setMax(1).build();

		items = new Item[] { invertjb, p5, amp, rjtf, rjs, gjtf, gjs, bjtf, bjs, p5, fre, rfjtf, rfjs, gfjtf, gfjs,
				bfjtf, bfjs, p5, off, rojtf, rojs, gojtf, gojs, bojtf, bojs };

		inverted = false;
		r = 1;
		b = 1;
		g = 1;
		rf = 0.5;
		gf = 0.5;
		bf = 0.5;
		ro = 0;
		bo = 0;
		go = 0;
	}

	/**
	 * Constructor used to clone the {@link Filter}.
	 * 
	 * @param filter
	 */
	private PeriodicFilter(PeriodicFilter filter) {
		super(filter);

		inverted = filter.inverted;
		r = filter.r;
		g = filter.g;
		b = filter.b;
		rf = filter.rf;
		gf = filter.gf;
		bf = filter.bf;
		ro = filter.ro;
		go = filter.go;
		bo = filter.bo;
	}

	@Override
	public int apply(Number V) {
		double v = V.doubleValue();
		if (inverted)
			v = 1.0 - v;
		return 0xff000000 | curve(r, rf, ro, v) << 16 | curve(g, gf, go, v) << 8 | curve(b, bf, bo, v) << 0;
	}

	private int curve(double a, double f, double o, double v) {
		return (int) (a * 127.5 * (1 - Math.cos((f * v - o) * 2 * Math.PI)));
	}

	@Override
	public void save() {
		r = rjtf.saveAndGet();
		g = gjtf.saveAndGet();
		b = bjtf.saveAndGet();
		rf = rfjtf.saveAndGet();
		gf = gfjtf.saveAndGet();
		bf = bfjtf.saveAndGet();
		ro = rojtf.saveAndGet();
		go = gojtf.saveAndGet();
		bo = bojtf.saveAndGet();
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
		rojtf.setData(ro);
		gojtf.setData(go);
		bojtf.setData(bo);
		rojs.setDataSafe(ro);
		gojs.setDataSafe(go);
		bojs.setDataSafe(bo);
	}

	@Override
	public Filter clone() {
		return new PeriodicFilter(this);
	}

	private void actionInvert() {
		inverted = !inverted;
		fractal.saveAndColour();
	}

	// amplitude
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

	// frequency
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

	// offset
	private void changeRo() {
		rojtf.setData(rojs.saveAndGet());
		fractal.saveAndColour();
	}

	private void changeGo() {
		gojtf.setData(gojs.saveAndGet());
		fractal.saveAndColour();
	}

	private void changeBo() {
		bojtf.setData(bojs.saveAndGet());
		fractal.saveAndColour();
	}

	@Override
	public String informalName() {
		return "Periodic";
	}

	@Override
	public String fileName() {
		return informalName();
	}

	@Override
	public String getTip() {
		return "TODO";
	}
}