package me.catmousedog.fractals.paneloperators.filters;

import me.catmousedog.fractals.paneloperators.fractals.Fractal;
import me.catmousedog.fractals.ui.components.Item;
import me.catmousedog.fractals.ui.components.concrete.Button;
import me.catmousedog.fractals.ui.components.concrete.Padding;
import me.catmousedog.fractals.ui.components.concrete.SliderDouble;
import me.catmousedog.fractals.ui.components.concrete.TextFieldDouble;

public class LinearFilter extends Filter {

	private boolean inverted;

	private double r;
	private double g;
	private double b;

	private Button invertjb;

	private TextFieldDouble rjtf;
	private SliderDouble rjs;

	private TextFieldDouble gjtf;
	private SliderDouble gjs;

	private TextFieldDouble bjtf;
	private SliderDouble bjs;

	private double s, is;

	private TextFieldDouble sjtf;
	private SliderDouble sjs;

	public LinearFilter(Fractal fractal) {
		super(fractal);

		Padding p5 = new Padding(5);
		invertjb = new Button.Builder("Invert").setTip("Inverts the current colours.").setAction(a -> actionInvert())
				.build();
		String rTip = "<html>The red brightness factor</html>";
		rjtf = new TextFieldDouble.Builder().setLabel("red factor").setTip(rTip).build();
		rjs = new SliderDouble.Builder().setTip(rTip).setChange(c -> changeR()).build();
		String gTip = "<html>The green brightness factor</html>";
		gjtf = new TextFieldDouble.Builder().setLabel("green factor").setTip(gTip).build();
		gjs = new SliderDouble.Builder().setTip(gTip).setChange(c -> changeG()).build();
		String bTip = "<html>The blue brightness factor</html>";
		bjtf = new TextFieldDouble.Builder().setLabel("blue factor").setTip(bTip).build();
		bjs = new SliderDouble.Builder().setTip(bTip).setChange(c -> changeB()).build();
		String sTip = "<html>The slope of the linear filter</html>";
		sjtf = new TextFieldDouble.Builder().setLabel("slope").setTip(sTip).build();
		sjs = new SliderDouble.Builder().setTip(sTip).setMin(0.1).setMax(10).setChange(c -> changeS()).build();
		items = new Item[] { invertjb, p5, rjtf, rjs, p5, gjtf, gjs, p5, bjtf, bjs, p5, sjtf, sjs };

		inverted = false;
		r = 1;
		b = 1;
		g = 1;
		s = 1;
		is = 1 / s;
	}

	/**
	 * Constructor used to clone the {@link Filter}.
	 * 
	 * @param filter
	 */
	private LinearFilter(LinearFilter filter) {
		super(filter);

		inverted = filter.inverted;
		r = filter.r;
		g = filter.g;
		b = filter.b;
		s = filter.s;
		is = filter.is;
	}

	@Override
	public int apply(Number V) {
		double v = V.doubleValue();
		if (inverted)
			v = is - v;
		int R = (int) (255 * v * r * s);
		int G = (int) (255 * v * g * s);
		int B = (int) (255 * v * b * s);

		return 0xff000000 | bound(R) << 16 | bound(G) << 8 | bound(B) << 0;
	}

	private int bound(int x) {
		if (x < 0)
			return 0;
		else if (x > 255)
			return 255;
		return x;
	}

	@Override
	public void save() {
		r = rjtf.saveAndGet();
		g = gjtf.saveAndGet();
		b = bjtf.saveAndGet();
		s = sjtf.saveAndGet();
		is = 1 / s;
	}

	@Override
	public void update() {
		rjs.setDataSafe(r);
		gjs.setDataSafe(g);
		bjs.setDataSafe(b);
		sjs.setDataSafe(s);
		rjtf.setData(r);
		gjtf.setData(g);
		bjtf.setData(b);
		sjtf.setData(s);
	}

	@Override
	public Filter clone() {
		return new LinearFilter(this);
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

	private void changeS() {
		sjtf.setData(sjs.saveAndGet());
		fractal.saveAndColour();
	}

	@Override
	public String informalName() {
		return "Linear";
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