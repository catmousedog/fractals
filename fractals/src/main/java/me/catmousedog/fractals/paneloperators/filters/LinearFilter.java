package me.catmousedog.fractals.paneloperators.filters;

import me.catmousedog.fractals.paneloperators.fractals.Fractal;
import me.catmousedog.fractals.ui.components.Item;
import me.catmousedog.fractals.ui.components.concrete.Button;
import me.catmousedog.fractals.ui.components.concrete.Padding;
import me.catmousedog.fractals.ui.components.concrete.SliderDouble;
import me.catmousedog.fractals.ui.components.concrete.TextFieldDouble;

/**
 * A {@link Filter} designed to be used with iterative {@link Fractal}s. The
 * {@link Filter#apply(Number)} takes integers ranging from 0 to 255.<br>
 * The colour pattern is linear and known for its simplicity.
 */
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
		items = new Item[] { invertjb, p5, rjtf, rjs, p5, gjtf, gjs, p5, bjtf, bjs };

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
	private LinearFilter(LinearFilter filter) {
		super(filter);

		inverted = filter.inverted;
		r = filter.r;
		g = filter.g;
		b = filter.b;
	}

	@Override
	public int apply(Number V) {
		double v = V.doubleValue();
		if (inverted)
			v = 1.0 - v;
		return 0xff000000 | (int) (255 * v * r) << 16 | (int) (255 * v * g) << 8 | (int) (255 * v * b) << 0;
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
		gjtf.setData(g);
		bjtf.setData(b);
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