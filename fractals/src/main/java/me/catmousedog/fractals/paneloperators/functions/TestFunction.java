package me.catmousedog.fractals.paneloperators.functions;

import me.catmousedog.fractals.data.FractalValue;
import me.catmousedog.fractals.paneloperators.filters.BinaryFilter;
import me.catmousedog.fractals.paneloperators.filters.Filter;
import me.catmousedog.fractals.paneloperators.fractals.Fractal;
import me.catmousedog.fractals.ui.components.Item;
import me.catmousedog.fractals.ui.components.concrete.SliderDouble;
import me.catmousedog.fractals.ui.components.concrete.TextFieldDouble;

public class TestFunction extends Function {

	{
		usingDerivative = true;
	}

	private double test = 1;

	private TextFieldDouble testjtf;
	private SliderDouble testjs;

	public TestFunction(Fractal fractal) {
		super(fractal);

		testjtf = new TextFieldDouble.Builder().setLabel("test").build();
		testjs = new SliderDouble.Builder().setMax(20).setChange(c -> changeTest()).build();

		items = new Item[] { testjtf, testjs };
		filters = new Filter[] { new BinaryFilter(fractal) };
		filter = filters[0];
		test = 1;
	}

	private TestFunction(TestFunction function) {
		super(function);
		test = function.test;
	}

	@Override
	public Integer apply(FractalValue v) {
		if (v.isConvergent()) {
			return 0;
		}
		return 1;
	}

	@Override
	public void save() {
		super.save();
		test = testjtf.saveAndGet();
	}

	@Override
	public void update() {
		super.update();
		testjtf.setData(test);
		testjs.setDataSafe(test);
	}

	@Override
	public Function clone() {
		return new TestFunction(this);
	}

	private void changeTest() {
		testjtf.setData(testjs.saveAndGet());
		fractal.saveAndRender();
	}

	@Override
	public String informalName() {
		return "Test Function";
	}

	@Override
	public String fileName() {
		return "TestFunction";
	}

	@Override
	public String getTip() {
		return "TEST FUNCTION";
	}
}
