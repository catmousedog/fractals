package me.catmousedog.fractals.paneloperators.functions;

import me.catmousedog.fractals.data.FractalValue;
import me.catmousedog.fractals.paneloperators.filters.Filter;
import me.catmousedog.fractals.paneloperators.filters.LogPeriodicFilter;
import me.catmousedog.fractals.paneloperators.fractals.Fractal;
import me.catmousedog.fractals.ui.components.Item;
import me.catmousedog.fractals.ui.components.concrete.SliderDouble;
import me.catmousedog.fractals.ui.components.concrete.TextFieldDouble;

public class NormalizedFunction extends Function {

	private double offset;

	private TextFieldDouble offsetjtf;
	private SliderDouble offsetjs;

	public NormalizedFunction(Fractal fractal) {
		super(fractal);

		String offsetTip = "TODO";
		offsetjtf = new TextFieldDouble.Builder().setLabel("offset").setTip(offsetTip).setDefault(1).setMin(-10)
				.setMax(5).build();
		offsetjs = new SliderDouble.Builder().setTip(offsetTip).setMin(-10).setMax(10).setChange(c -> offset()).build();

		items = new Item[] { offsetjtf, offsetjs };

		filters = new Filter[] { new LogPeriodicFilter(fractal) };
		filter = filters[0];

		offset = 1;
	}

	@Override
	public void save() {
		super.save();
		offset = offsetjtf.saveAndGet();
	}

	@Override
	public void update() {
		super.update();
		offsetjtf.setData(offset);
		offsetjs.setData(offset);
	}

	private NormalizedFunction(Function function, double offset) {
		super(function);
		this.offset = offset;
	}

	@Override
	public Double apply(FractalValue v) {
		return v.i + offset - Math.log(Math.log(v.x * v.x + v.y * v.y) / 2) / Math.log(2);
	}

	@Override
	public Function clone() {
		return new NormalizedFunction(this, offset);
	}

	private void offset() {
		offsetjtf.setData(offsetjs.saveAndGet());
		fractal.saveAndRender();
	}

	@Override
	public String informalName() {
		return "Normalized";
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
