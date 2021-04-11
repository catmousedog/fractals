package me.catmousedog.fractals.paneloperators.filters;

import java.awt.Color;

import me.catmousedog.fractals.paneloperators.fractals.Fractal;
import me.catmousedog.fractals.ui.components.Item;
import me.catmousedog.fractals.ui.components.concrete.Button;
import me.catmousedog.fractals.ui.components.concrete.Padding;
import me.catmousedog.fractals.ui.components.concrete.SliderDouble;
import me.catmousedog.fractals.ui.components.concrete.TextFieldDouble;

public class TestFilter extends Filter {

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

	public TestFilter(Fractal fractal) {
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
	private TestFilter(TestFilter filter) {
		super(filter);

		inverted = filter.inverted;
		r = filter.r;
		g = filter.g;
		b = filter.b;
	}

	@Override
	public int apply(Number v) {
		double d = v.doubleValue();
		if (d == 0) {
			return Color.HSBtoRGB(0.15f, 1.0f, 1.0f);
		}
		return Color.HSBtoRGB(0f, 0f, v.floatValue());
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
		return new TestFilter(this);
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
		return "Test Filter";
	}

	@Override
	public String fileName() {
		return "TestFilter";
	}

	@Override
	public String getTip() {
		return "TESTFUNCTION";
	}

}
