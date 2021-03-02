package fractals;

public class Complex {

	public double x, y;

	public Complex(double x, double y) {
		this.x = x;
		this.y = y;
	}

	public Complex divide(Complex c) {
		double Y = c.x * c.x + c.y * c.y;
		double tx = (c.x * x + c.y * y) / Y;
		double ty = (c.x * y - c.y * x) / Y;
		return new Complex(tx, ty);
	}

	public void multiply(Complex c) {
		double tx = x * c.x - y * c.y;
		y = y * c.x + x * c.y;
		x = tx;
	}

	public void subtract(Complex c) {
		x -= c.x;
		y -= c.y;
	}

	public void power(double a) {
		double theta = Math.atan2(y, x);
//		double Z = Math.pow(x * x + y * y, a / 2);
		double Z = Math.exp(Math.log(mag()) * a / 2);
		x = Math.cos(a * theta) * Z;
		y = Math.sin(a * theta) * Z;
	}

	public double mag() {
		return x * x + y * y;
	}

	@Override
	public Complex clone() {
		return new Complex(x, y);
	}

}