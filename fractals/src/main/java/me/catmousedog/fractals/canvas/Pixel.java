package me.catmousedog.fractals.canvas;

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
	public Number v;

	/**
	 * The hashcode for a <code>Pixel</code> is determined by the
	 * <code>actual</code> and coordinates.<br>
	 * The maximum size for <code>x</code> and <code>y</code> so that the hashcode
	 * returns a different integer for unequal <code>Pixels</code> is 2^16.
	 */
	@Override
	public int hashCode() {
		return x << 16 ^ y;
	}

	@Override
	public String toString() {
		return String.format("%f : %f = %s", tx, ty, Double.toString(v.doubleValue()));
	}
}
