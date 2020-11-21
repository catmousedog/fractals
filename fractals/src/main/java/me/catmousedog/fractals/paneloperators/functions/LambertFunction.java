package me.catmousedog.fractals.paneloperators.functions;

import me.catmousedog.fractals.data.FractalValue;
import me.catmousedog.fractals.paneloperators.filters.BrightnessFilter;
import me.catmousedog.fractals.paneloperators.filters.Filter;
import me.catmousedog.fractals.paneloperators.fractals.Fractal;
import me.catmousedog.fractals.ui.components.Item;
import me.catmousedog.fractals.ui.components.concrete.Padding;
import me.catmousedog.fractals.ui.components.concrete.SliderDouble;
import me.catmousedog.fractals.ui.components.concrete.TextFieldDouble;

public class LambertFunction extends Function {

	{
		usesDerivative = true;
	}

	private double a;
	private double h;

	private TextFieldDouble ajtf;
	private SliderDouble ajs;

	private TextFieldDouble hjtf;
	private SliderDouble hjs;

	public LambertFunction(Fractal fractal) {
		super(fractal);

		Padding p5 = new Padding(5);

		String tipA = "TODO";
		ajtf = new TextFieldDouble.Builder().setLabel("angle").setTip(tipA).setMin(0).setMax(2 * Math.PI).build();
		ajs = new SliderDouble.Builder().setMin(0).setMax(2 * Math.PI).setTip(tipA).setChange(c -> changeA()).build();
		String tipH = "TODO";
		hjtf = new TextFieldDouble.Builder().setLabel("height").setTip(tipH).setMin(0).build();
		hjs = new SliderDouble.Builder().setMin(0).setMax(2).setTip(tipH).setChange(c -> changeH()).build();
		items = new Item[] { ajtf, ajs, p5, hjtf, hjs };

		filters = new Filter[] { new BrightnessFilter(fractal) };
		filter = filters[0];

		setA(Math.PI);
		h = 1;
	}

	private LambertFunction(LambertFunction function) {
		super(function);

		setA(function.a);
		h = function.h;
	}

	@Override
	public Double apply(FractalValue v) {
		if (v.isConvergent())
			return 0d;
		double b = v.dx * v.dx + v.dy * v.dy;
		double ux = (v.x * v.dx + v.y * v.dy) / b;
		double uy = (v.y * v.dx - v.x * v.dy) / b;
		double u = Math.sqrt(ux * ux + uy * uy);
		ux /= u;
		uy /= u;

		double t = (ux * ax + uy * ay + h) / (1 + h);
		if (t < 0)
			t = 0;
		return t;
	}

	private double ax, ay; // az = h

	private void setA(double a) {
		this.a = a;
		ax = Math.cos(a);
		ay = Math.sin(a);

//		double t = Math.sqrt(ax * ax + ay * ay + az * az);
//		ax /= t;
//		ay /= t;
//		az /= t;
	}

	@Override
	public void save() {
		super.save();
		setA(ajtf.saveAndGet());
		h = hjtf.saveAndGet();
	}

	@Override
	public void update() {
		super.update();
		ajtf.setData(a);
		ajs.setDataSafe(a);
		hjtf.setData(h);
		hjs.setDataSafe(h);
	}

	private void changeA() {
		ajtf.setData(ajs.saveAndGet());
		fractal.saveAndRender();
	}

	private void changeH() {
		hjtf.setData(hjs.saveAndGet());
		fractal.saveAndRender();
	}

	@Override
	public Function clone() {
		return new LambertFunction(this);
	}

	@Override
	public String informalName() {
		return "Lambert";
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
