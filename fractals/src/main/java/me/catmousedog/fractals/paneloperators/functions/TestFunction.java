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

	

	private double test;

	private TextFieldDouble testjtf;
	private SliderDouble testjs;

	public TestFunction(Fractal fractal) {
		super(fractal);

		this.degree = fractal.getDegree();

		testjtf = new TextFieldDouble.Builder().setLabel("test").build();
		testjs = new SliderDouble.Builder().setMax(10).setChange(c -> changeTest()).build();
		items = new Item[] { testjtf, testjs };

		filters = new Filter[] { new TestFilter(fractal), new LinearFilter(fractal), new PeriodicFilter(fractal),
				new HueFilter(fractal), new BrightnessFilter(fractal), new LogPeriodicFilter(fractal) };
		filter = filters[5];

		setTest(1);
	}

	private TestFunction(TestFunction function) {
		super(function);
		setTest(function.test);
		this.degree = function.degree;
	}

	private double degree;

	{
		usesDerivative = false;
	}
	
	@Override
	public Double apply(FractalValue v) {
		if (v.isConvergent())
			return 0d;

		// REAL ITER
//		double z = v.x * v.x + v.y * v.y;
//		double i = 1 + v.i + (Math.log(Math.log(b)) - Math.log(0.5 * Math.log(z))) / Math.log(degree);
//		return i;

		// POT
//		return 0.5 * Math.log(v.x * v.x + v.y * v.y) * Math.pow(test, -v.i); //bands

		// NORM
		return 1 + v.i * Math.log(test) - Math.log(Math.log(v.x * v.x + v.y * v.y) * 0.5);

		// GRAD POT
//		return Math.sqrt(v.dx * v.dx + v.dy * v.dy) / (Math.sqrt(v.x * v.x + v.y * v.y) * Math.pow(degree, v.i));

		// DE = POT / GRAD POT
//		double z = Math.sqrt(v.x * v.x + v.y * v.y);
//		return 0.5 * Math.log(z) * Math.sqrt(z) / Math.sqrt(v.dx * v.dx + v.dy * v.dy);
	}

	@Override
	public void save() {
		super.save();
		setTest(testjtf.saveAndGet());
		this.degree = fractal.getDegree();
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
