package me.catmousedog.fractals.paneloperators.functions;

import me.catmousedog.fractals.data.FractalValue;
import me.catmousedog.fractals.paneloperators.filters.BinaryFilter;
import me.catmousedog.fractals.paneloperators.filters.Filter;
import me.catmousedog.fractals.paneloperators.fractals.Fractal;
import me.catmousedog.fractals.ui.components.concrete.SliderDouble;
import me.catmousedog.fractals.ui.components.concrete.TextFieldDouble;

public class TestFunction extends Function {

	{
		usingDerivative = true;
	}

	private double a = 1;

	private TextFieldDouble ajtf;
	private SliderDouble ajs;

	public TestFunction(Fractal fractal) {
		super(fractal);

		String tipA = "TODO";

		ajtf = new TextFieldDouble.Builder().setLabel("angle").setTip(tipA).setMin(0).setMax(2 * Math.PI).build();

		items = null;
		filters = new Filter[] { new BinaryFilter(fractal) };
		filter = filters[0];
		a = 1;
	}

	private TestFunction(TestFunction function) {
		super(function);
		a = function.a;
	}

	@Override
	public Integer apply(FractalValue v) {
		if (v.dx * v.dx + v.dy * v.dy < 0.001) {
			return 0;
		}
		return 1;
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
