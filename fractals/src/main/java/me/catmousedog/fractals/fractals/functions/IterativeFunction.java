package me.catmousedog.fractals.fractals.functions;

import me.catmousedog.fractals.fractals.FractalValue;

public class IterativeFunction extends Function {

	@Override
	public Integer apply(FractalValue v) {
		return 255 * (v.I - v.i) / v.I;
	}

	@Override
	public String getTip() {
		return "TODO";
	}

	@Override
	public Function clone() {
		return new IterativeFunction();
	}

}
