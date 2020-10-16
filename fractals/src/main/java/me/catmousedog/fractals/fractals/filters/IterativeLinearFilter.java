package me.catmousedog.fractals.fractals.filters;

import java.awt.Color;
import java.util.logging.Level;

import me.catmousedog.fractals.fractals.abstract_fractals.Fractal;
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
public class IterativeLinearFilter extends Filter {

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

	public IterativeLinearFilter(Fractal fractal) {
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
	private IterativeLinearFilter(Filter filter) {
		super(filter);
	}

	@Override
	public int apply(Number V) {
		int v = V.intValue();
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
			logger.log(Level.WARNING, this.toString() + " is not instance of " + filter.toString());
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
		gjtf.setData(g);
		bjtf.setData(b);
	}

	@Override
	public Filter clone() {
		return new IterativeLinearFilter(this);
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
		return "Linear Filter";
	}

	@Override
	public String fileName() {
		return "LinearFilter";
	}

	@Override
	public String getTip() {
		return "<html>A filter designed to be used with iterative fractals."
				+ "<br>This filter uses a linear function for the red, green and blue components.<br>"
				+ "Each colour has its own amplitude which can be changed.</html>";

	}

}