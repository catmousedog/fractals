package me.catmousedog.fractals.paneloperators.filters;

import java.awt.Color;

import me.catmousedog.fractals.paneloperators.fractals.Fractal;
import me.catmousedog.fractals.ui.components.Item;
import me.catmousedog.fractals.ui.components.concrete.Button;
import me.catmousedog.fractals.ui.components.concrete.Padding;
import me.catmousedog.fractals.ui.components.concrete.SliderDouble;
import me.catmousedog.fractals.ui.components.concrete.TextFieldDouble;

public class HueFilter extends Filter {

	private boolean inverted;

	private double offset;

	private Button invertjb;

	private TextFieldDouble offsetjtf;
	private SliderDouble offsetjs;

	public HueFilter(Fractal fractal) {
		super(fractal);

		Padding p5 = new Padding(5);
		invertjb = new Button.Builder("Invert").setTip("Inverts the hue direction").setAction(a -> actionInvert()).build();

		String offsetTip = "<html>The hue offset of the colour</html>";
		offsetjtf = new TextFieldDouble.Builder().setLabel("offset").setTip(offsetTip).build();
		offsetjs = new SliderDouble.Builder().setTip(offsetTip).setChange(c -> changeOffset()).build();

		items = new Item[] { invertjb, p5, offsetjtf, offsetjs };

		inverted = false;
		offset = 1;
	}

	/**
	 * Constructor used to clone the {@link Filter}.
	 * 
	 * @param filter
	 */
	private HueFilter(HueFilter filter) {
		super(filter);

		inverted = filter.inverted;
		offset = filter.offset;
	}

	@Override
	public int apply(Number V) {
		float v = V.floatValue();
		v += offset;
		v %= 1f;
		if (inverted)
			v = 1 - v;

		return Color.HSBtoRGB(v, 1f, 1f);
	}

	@Override
	public void save() {
		offset = offsetjtf.saveAndGet();
	}

	@Override
	public void update() {
		offsetjs.setDataSafe(offset);
		offsetjtf.setData(offset);
	}

	@Override
	public Filter clone() {
		return new HueFilter(this);
	}

	private void actionInvert() {
		inverted = !inverted;
		fractal.saveAndColour();
	}

	private void changeOffset() {
		offsetjtf.setData(offsetjs.saveAndGet());
		fractal.saveAndColour();
	}

	@Override
	public String informalName() {
		return "Hue";
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
