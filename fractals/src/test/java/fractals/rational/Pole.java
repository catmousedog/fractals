package fractals.rational;

public class Pole extends Complex {

	public double m;

	public Pole(double x, double y, double m) {
		super(x, y);
		this.m = m;
	}

	public Complex poly(Complex q) {
		Complex c = q.clone();
		c = c.subtract(this);
		c = c.power(m);
		return c;
	}

	public double getM() {
		return m;
	}

	public void setM(double m) {
		this.m = m;
	}
}
