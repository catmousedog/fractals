package me.catmousedog.fractals.fractals;

/**
 * Represents a position on the 2D plane through a coordinate, stretch and
 * rotation.
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
	private double m = 0.01, n = 0.01, c = 1, s = 0;

	/**
	 * translation coordinates from the origin determined by
	 * {@link LinearTransform#ox} and {@link LinearTransform#oy}
	 */
	private double dx = 0, dy = 0;

	/**
	 * rotation angle in radians
	 */
	private double rot = 0;

	/**
	 * Translation coordinates in pixels. <br>
	 * This should be half the width and height of the canvas.
	 */
	private int ox, oy;

	public LinearTransform() {
	}

	public LinearTransform(double dx, double dy, double m, double n, double rot) {
		this.dx = dx;
		this.dy = dy;
		this.m = m;
		this.n = n;
		setRot(rot);
	}

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
	 * @param rot
	 */
	public void setRot(double rot) {
		this.rot = rot;
		c = Math.cos(rot);
		s = Math.sin(rot);
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

	public void set(LinearTransform transform) {
		setTranslation(transform.dx, transform.dy);
		setRot(transform.rot);
		setScalar(transform.m, transform.n);
	}

	@Override
	public LinearTransform clone() {
		LinearTransform transform = new LinearTransform(dx, dy, m, n, rot);
		transform.setOrigin(ox, oy);
		return transform;
	}

	/**
	 * Changes the zoom by a factor <code>f</code>.
	 * 
	 * @param f
	 */
	public void zoom(double f) {
		m *= f;
		n *= f;
	}

	public int getOx() {
		return ox;
	}

	public int getOy() {
		return oy;
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

	public double getrot() {
		return rot;
	}

	/**
	 * @return {@link String} of format: <code>x=%dx y=%dy m=%m n=%n</code>
	 */
	@Override
	public String toString() {
		return String.format("x=%f y=%f m=%f n=%f", dx, dy, m, n);
	}
}
