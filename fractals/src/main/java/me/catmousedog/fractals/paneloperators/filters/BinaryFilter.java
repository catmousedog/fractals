package me.catmousedog.fractals.paneloperators.filters;

import java.awt.Color;

import me.catmousedog.fractals.paneloperators.fractals.Fractal;
import me.catmousedog.fractals.ui.components.Item;
import me.catmousedog.fractals.ui.components.concrete.Button;
import me.catmousedog.fractals.ui.components.concrete.Padding;
import me.catmousedog.fractals.ui.components.concrete.SliderDouble;
import me.catmousedog.fractals.ui.components.concrete.TextFieldDouble;

public class BinaryFilter extends Filter {

	private boolean inverted;

	private double r0, r1;
	private double g0, g1;
	private double b0, b1;

	private Button invertjb;

	private TextFieldDouble r0jtf;
	private SliderDouble r0js;

	private TextFieldDouble g0jtf;
	private SliderDouble g0js;

	private TextFieldDouble b0jtf;
	private SliderDouble b0js;

	private TextFieldDouble r1jtf;
	private SliderDouble r1js;

	private TextFieldDouble g1jtf;
	private SliderDouble g1js;

	private TextFieldDouble b1jtf;
	private SliderDouble b1js;

	public BinaryFilter(Fractal fractal) {
		super(fractal);

		Padding p5 = new Padding(5);
		invertjb = new Button.Builder("Invert").setTip("Inverts the current colours.").setAction(a -> actionInvert())
				.build();

		String r0Tip = "<html>The red brightness factor</html>";
		r0jtf = new TextFieldDouble.Builder().setLabel("red factor 0").setTip(r0Tip).build();
		r0js = new SliderDouble.Builder().setTip(r0Tip).setMin(0).setMax(1).setChange(c -> changeR0()).build();
		String g0Tip = "<html>The green brightness factor</html>";
		g0jtf = new TextFieldDouble.Builder().setLabel("green factor 0").setTip(g0Tip).build();
		g0js = new SliderDouble.Builder().setTip(g0Tip).setMin(0).setMax(1).setChange(c -> changeG0()).build();
		String b0Tip = "<html>The blue brightness factor</html>";
		b0jtf = new TextFieldDouble.Builder().setLabel("blue factor 0").setTip(b0Tip).build();
		b0js = new SliderDouble.Builder().setTip(b0Tip).setMin(0).setMax(1).setChange(c -> changeB0()).build();

		String r1Tip = "<html>The red brightness factor</html>";
		r1jtf = new TextFieldDouble.Builder().setLabel("red factor 1").setTip(r1Tip).build();
		r1js = new SliderDouble.Builder().setTip(r1Tip).setMin(0).setMax(1).setChange(c -> changeR1()).build();
		String g1Tip = "<html>The green brightness factor</html>";
		g1jtf = new TextFieldDouble.Builder().setLabel("green factor 1").setTip(g1Tip).build();
		g1js = new SliderDouble.Builder().setTip(g1Tip).setMin(0).setMax(1).setChange(c -> changeG1()).build();
		String b1Tip = "<html>The blue brightness factor</html>";
		b1jtf = new TextFieldDouble.Builder().setLabel("blue factor 1").setTip(b1Tip).build();
		b1js = new SliderDouble.Builder().setTip(b1Tip).setMin(0).setMax(1).setChange(c -> changeB1()).build();
		items = new Item[] { invertjb, p5, r1jtf, r1js, p5, g1jtf, g1js, p5, b1jtf, b1js };

		items = new Item[] { invertjb, p5, r0jtf, r0js, g0jtf, g0js, b0jtf, b0js, p5, r1jtf, r1js, g1jtf, g1js, b1jtf,
				b1js };

		inverted = false;
		r0 = 0;
		b0 = 0;
		g0 = 0;
		r1 = 1;
		b1 = 1;
		g1 = 1;
		setColour();
	}

	/**
	 * Constructor used to clone the {@link Filter}.
	 * 
	 * @param filter
	 */
	private BinaryFilter(BinaryFilter filter) {
		super(filter);

		inverted = filter.inverted;
		r0 = filter.r0;
		g0 = filter.g0;
		b0 = filter.b0;
		r1 = filter.r1;
		g1 = filter.g1;
		b1 = filter.b1;
		setColour();
	}

	@Override
	public int apply(Number V) {
		if (inverted) {
			if (V.byteValue() != 1)
				return c1;
		} else {
			if (V.byteValue() == 1)
				return c1;
		}
		return c0;
	}

	@Override
	public void save() {
		r0 = r0jtf.saveAndGet();
		g0 = g0jtf.saveAndGet();
		b0 = b0jtf.saveAndGet();

		r1 = r1jtf.saveAndGet();
		g1 = g1jtf.saveAndGet();
		b1 = b1jtf.saveAndGet();
	}

	@Override
	public void update() {
		r0js.setDataSafe(r0);
		g0js.setDataSafe(g0);
		b0js.setDataSafe(b0);
		r0jtf.setData(r0);
		g0jtf.setData(g0);
		b0jtf.setData(b0);

		r1js.setDataSafe(r1);
		g1js.setDataSafe(g1);
		b1js.setDataSafe(b1);
		r1jtf.setData(r1);
		g1jtf.setData(g1);
		b1jtf.setData(b1);
	}

	@Override
	public Filter clone() {
		return new BinaryFilter(this);
	}

	private int c0, c1;

	private void setColour() {
		c0 = new Color((int) (255 * r0), (int) (255 * g0), (int) (255 * b0)).getRGB();
		c1 = new Color((int) (255 * r1), (int) (255 * g1), (int) (255 * b1)).getRGB();
	}

	private void actionInvert() {
		inverted = !inverted;
		setColour();
		fractal.saveAndColour();
	}

	private void changeR0() {
		r0jtf.setData(r0js.saveAndGet());
		setColour();
		fractal.saveAndColour();
	}

	private void changeG0() {
		g0jtf.setData(g0js.saveAndGet());
		setColour();
		fractal.saveAndColour();
	}

	private void changeB0() {
		b0jtf.setData(b0js.saveAndGet());
		setColour();
		fractal.saveAndColour();
	}

	private void changeR1() {
		r1jtf.setData(r1js.saveAndGet());
		setColour();
		fractal.saveAndColour();
	}

	private void changeG1() {
		g1jtf.setData(g1js.saveAndGet());
		setColour();
		fractal.saveAndColour();
	}

	private void changeB1() {
		b1jtf.setData(b1js.saveAndGet());
		setColour();
		fractal.saveAndColour();
	}

	@Override
	public String informalName() {
		return "Binary";
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
