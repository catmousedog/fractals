package me.catmousedog.fractals.fractals;

public abstract class Fractal {
	
	protected int iterations = 100; 
	
	public abstract double get(double x, double y);
	
	public void setIterations(int i) {
		iterations = i;
	}
	
}
