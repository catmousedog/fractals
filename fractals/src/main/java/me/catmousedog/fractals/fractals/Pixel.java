package me.catmousedog.fractals.fractals;

/**
 * represents a complex number on a 2D plane with a corresponding value
 */
public class Pixel {

	private static int M = (int)Math.sqrt(Integer.MAX_VALUE);
	
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
	
	/**
	 * shouldn't be needed as we don't use HashSet
	 */
	@Override
	public int hashCode() {
		return y*M+x;
	}
	
	
}
