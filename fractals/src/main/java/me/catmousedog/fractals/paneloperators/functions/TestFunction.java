package me.catmousedog.fractals.paneloperators.functions;

import me.catmousedog.fractals.data.FractalValue;
import me.catmousedog.fractals.paneloperators.filters.BrightnessFilter;
import me.catmousedog.fractals.paneloperators.filters.Filter;
import me.catmousedog.fractals.paneloperators.fractals.Fractal;
import me.catmousedog.fractals.ui.components.concrete.SliderDouble;
import me.catmousedog.fractals.ui.components.concrete.TextFieldDouble;

public class TestFunction extends Function {

	{
		usingDerivative = true;
	}

	private TextFieldDouble ajtf;
	private SliderDouble ajs;

	public TestFunction(Fractal fractal) {
		super(fractal);

		String tipA = "TODO";

		ajtf = new TextFieldDouble.Builder().setLabel("angle").setTip(tipA).setMin(0).setMax(2 * Math.PI).build();

		items = null;
		filters = new Filter[] { new BrightnessFilter(fractal) };
		filter = filters[0];

		setA(1);
	}

	private TestFunction(Function function) {
		super(function);

		setA(1);
	}

	double a = 1, h = 1;

	@Override
	public Double apply(FractalValue v) {
		double b = v.dx * v.dx + v.dy * v.dy;
		double ux = (v.x * v.dx + v.y * v.dy) / b;
		double uy = (v.y * v.dx - v.x * v.dy) / b;
		double u = Math.sqrt(ux * ux + uy * uy);
		ux /= u;
		uy /= u;
		double t = (ux * Math.cos(a) + uy * Math.sin(a) + h) / (1 + h);

//		double b = v.dx * v.dx + v.dy * v.dy;
//		double ux = (v.x * v.dx + v.y * v.dy) / b;
//		double uy = (v.y * v.dx - v.x * v.dy) / b;
//		double u = Math.sqrt(ux * ux + uy * uy);
//		ux /= u;
//		uy /= u;
//		double t = (ux * ax + uy * ay + h) / (1 + h);

		if (t > 1)
			t = 1;
		if (t < 0)
			t = 0;
		return t;
	}

	private double ax, ay, az;

	private void setA(double a) {
		ax = Math.cos(a);
		ay = Math.sin(a);
		az = 1;

//		double t = Math.sqrt(ax * ax + ay * ay + az * az);
//		ax /= t;
//		ay /= t;
//		az /= t;
	}

	@Override
	public Function clone() {
		return new TestFunction(this);
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
