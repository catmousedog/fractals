package me.catmousedog.fractals.fractals;

/**
 * represents a pixel on the screen that points to a transformed coordinate and
 * an output value
 */
public class Pixel {

	public Pixel(int x, int y) {
		this.x = x;
		this.y = y;
	}

	// pixel
	public int x, y;

	// transformed pixel
	public double tx, ty;

	// output
	public double v;

}
