package me.catmousedog.fractals.paneloperators.filters;

import java.awt.Color;

import me.catmousedog.fractals.paneloperators.fractals.Fractal;
import me.catmousedog.fractals.ui.components.Item;
import me.catmousedog.fractals.ui.components.concrete.Button;
import me.catmousedog.fractals.ui.components.concrete.Padding;
import me.catmousedog.fractals.ui.components.concrete.SliderDouble;
import me.catmousedog.fractals.ui.components.concrete.TextFieldDouble;

public class BrightnessFilter extends Filter {

	private boolean inverted;

	private double h;
	private double s;

	private Button invertjb;

	private TextFieldDouble hjtf;
	private SliderDouble hjs;

	private TextFieldDouble sjtf;
	private SliderDouble sjs;

	public BrightnessFilter(Fractal fractal) {
		super(fractal);

		Padding p5 = new Padding(5);
		invertjb = new Button.Builder("Invert").setTip("Inverts the current colours.").setAction(a -> actionInvert())
				.build();
		String hTip = "<html>The hue component of the colour</html>";
		hjtf = new TextFieldDouble.Builder().setLabel("hue").setTip(hTip).build();
		hjs = new SliderDouble.Builder().setTip(hTip).setChange(c -> changeH()).build();
		String sTip = "<html>The saturation of the colour</html>";
		sjtf = new TextFieldDouble.Builder().setLabel("saturation").setTip(sTip).build();
		sjs = new SliderDouble.Builder().setTip(sTip).setChange(c -> changeS()).build();

		items = new Item[] { invertjb, p5, hjtf, hjs, p5, sjtf, sjs };

		inverted = false;
		h = 0;
		s = 0;
	}

	/**
	 * Constructor used to clone the {@link Filter}.
	 * 
	 * @param filter
	 */
	private BrightnessFilter(BrightnessFilter filter) {
		super(filter);

		inverted = filter.inverted;
		h = filter.h;
		s = filter.s;
	}

	@Override
	public int apply(Number V) {
		float v = V.floatValue();
		if (inverted)
			v = 1 - v;
		return Color.HSBtoRGB((float) h, (float) s, v);
	}

	@Override
	public void save() {
		h = hjtf.saveAndGet();
		s = sjtf.saveAndGet();
	}

	@Override
	public void update() {
		hjs.setDataSafe(h);
		sjs.setDataSafe(s);
		hjtf.setData(h);
		sjtf.setData(s);
	}

	@Override
	public Filter clone() {
		return new BrightnessFilter(this);
	}

	private void actionInvert() {
		inverted = !inverted;
		fractal.saveAndColour();
	}

	private void changeH() {
		hjtf.setData(hjs.saveAndGet());
		fractal.saveAndColour();
	}

	private void changeS() {
		sjtf.setData(sjs.saveAndGet());
		fractal.saveAndColour();
	}

	@Override
	public String informalName() {
		return "Brightness";
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
