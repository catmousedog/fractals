package me.catmousedog.fractals.fractals;

/**
 * represents a 2x2 matrix with a scalar stretch, a rotation and a translation.
 */
public class LinearTransform {

	/**
	 * |m*c, -n*s| |m*s, n*c|
	 */
	private double m, n, c, s;

	/**
	 * translation coordinates from complex origin
	 */
	private double dx, dy;

	/**
	 * Translation coordinates in pixels. <br>
	 * This should be half the width and height of the canvas.
	 */
	private int ox, oy;

	/**
	 * apply the transformation to the given coordinates
	 * 
	 * @param x
	 * @param y
	 * @return array of transformed coordinates (tx, ty)
	 */
	public double[] transform(int x, int y) {

		// affine
		double tx = x - ox;
		double ty = y - oy;

		// matrix
		tx *= m;
		ty *= n;

		// matrix + affine
		return new double[] { tx * c - ty * s - dx, tx * s + ty * c - dy };
	}

	/**
	 * set the scalar component of the transformation
	 * 
	 * @param m the x scalar
	 * @param n the y scalar
	 */
	public void setScalar(double m, double n) {
		this.m = m;
		this.n = n;
	}

	/**
	 * set the angle
	 * 
	 * @param t
	 */
	public void setTheta(double t) {
		c = Math.cos(t);
		s = Math.sin(t);
	}

	/**
	 * set the translation coordinates
	 * 
	 * @param dx
	 * @param dy
	 */
	public void setTranslation(int ox, int oy, double dx, double dy) {
		this.ox = ox;
		this.oy = oy;
		this.dx = dx;
		this.dy = dy;
	}
	
	/**
	 * for lazy people like myself
	 */
	public void setAll(int ox, int oy, double dx, double dy, double m, double n, double t) {
		setTranslation(ox, oy, dx, dy);
		setScalar(m, n);
		setTheta(t);
	}
	
	public double getdx() {
		return dx;
	}
	
	public double getdy() {
		return dy;
	}
	
	public double getm() {
		return m;
	}
	
	public double getn() {
		return n;
	}
	
}
