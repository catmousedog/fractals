package fractals.rational;

public class Complex {

	public double x, y;

	public Complex(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Complex(double a) {
		this.x = Math.cos(a);
		this.y = Math.sin(a);
	}

	public Complex divide(Complex c) {
		double Y = c.x * c.x + c.y * c.y;
		double tx = (c.x * x + c.y * y) / Y;
		double ty = (c.x * y - c.y * x) / Y;
		return new Complex(tx, ty);
	}

	public Complex multiply(Complex c) {
		return new Complex(x * c.x - y * c.y, y * c.x + x * c.y);
	}

	public Complex add(Complex c) {
		return new Complex(x + c.x, y + c.y);
	}

	public Complex subtract(Complex c) {
		return new Complex(x - c.x, y - c.y);
	}

	public Complex power(double a) {
		double theta = Math.atan2(y, x);
		double Z = Math.exp(Math.log(mag()) * a / 2);
		return new Complex(Math.cos(a * theta) * Z, Math.sin(a * theta) * Z);
	}

	public double mag() {
		return x * x + y * y;
	}

	@Override
	public Complex clone() {
		return new Complex(x, y);
	}
	
	@Override
	public String toString() {
		return String.format("%f\t%f", x, y);
	}

}