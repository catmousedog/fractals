package me.catmousedog.fractals.paneloperators.functions;

import me.catmousedog.fractals.data.FractalValue;
import me.catmousedog.fractals.paneloperators.filters.BrightnessFilter;
import me.catmousedog.fractals.paneloperators.filters.Filter;
import me.catmousedog.fractals.paneloperators.filters.HueFilter;
import me.catmousedog.fractals.paneloperators.filters.LinearFilter;
import me.catmousedog.fractals.paneloperators.filters.LogPeriodicFilter;
import me.catmousedog.fractals.paneloperators.filters.PeriodicFilter;
import me.catmousedog.fractals.paneloperators.filters.TestFilter;
import me.catmousedog.fractals.paneloperators.fractals.Fractal;
import me.catmousedog.fractals.ui.components.Item;
import me.catmousedog.fractals.ui.components.concrete.SliderDouble;
import me.catmousedog.fractals.ui.components.concrete.TextFieldDouble;

public class TestFunction extends Function {

	{
		usesDerivative = true;
	}

	private double test;

	private TextFieldDouble testjtf;
	private SliderDouble testjs;

	public TestFunction(Fractal fractal) {
		super(fractal);

		testjtf = new TextFieldDouble.Builder().setLabel("test").build();
		testjs = new SliderDouble.Builder().setMax(10).setChange(c -> changeTest()).build();

		items = new Item[] { testjtf, testjs };
		filters = new Filter[] { new TestFilter(fractal), new LinearFilter(fractal), new PeriodicFilter(fractal), new HueFilter(fractal),
				new BrightnessFilter(fractal), new LogPeriodicFilter(fractal) };
		filter = filters[0];
		setTest(1);
	}

	private TestFunction(TestFunction function) {
		super(function);
		setTest(function.test);
	}

	@Override
	public Double apply(FractalValue v) {
		if (v.isConvergent())
			return 0d;
		return (Math.sinh(2.2) + v.i * Math.log(test) - Math.log(Math.log(v.x * v.x + v.y * v.y) * 0.5));
	}

	@Override
	public void save() {
		super.save();
		setTest(testjtf.saveAndGet());
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

	private void setTest(double test) {
		this.test = test;
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
