package me.catmousedog.fractals.fractals;

/**
 * represents a 2x2 matrix with a scalar stretch, a rotation and a translation.
 */
public class LinearTransform {

	/**
	 * <b>m</b> the x scaling factor<br>
	 * <b>n</b> the y scaling factor<br>
	 * <b>c</b> cosine <br>
	 * <b>s</b> sine
	 * <p>
	 * the transformation matrix without translation becomes: <br>
	 * |m*c,-n*s| <br>
	 * |m*s, n*c|
	 */
	private double m, n, c, s;

	/**
	 * translation coordinates from the origin determined by
	 * {@link LinearTransform#ox} and {@link LinearTransform#oy}
	 */
	private double dx, dy;

	/**
	 * rotation angle in radians
	 */
	private double theta;

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
	public double[] apply(int x, int y) {

		// affine pixel transformation
		double tx = x - ox;
		double ty = y - oy;

		// scaling
		tx *= m;
		ty *= n;

		// rotation + displacement 
		return new double[] { tx * c - ty * s + dx, tx * s + ty * c + dy };
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
		theta = t;
		c = Math.cos(t);
		s = Math.sin(t);
	}

	/**
	 * set the origin from which the translated coordinates shift
	 * 
	 * @param ox origin x-coordinate in pixels
	 * @param oy origin y-coordinate in pixels
	 */
	public void setOrigin(int ox, int oy) {
		this.ox = ox;
		this.oy = oy;
	}

	/**
	 * set the translation coordinates
	 * 
	 * @param dx
	 * @param dy
	 */
	public void setTranslation(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
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

	public double gettheta() {
		return theta;
	}

}
