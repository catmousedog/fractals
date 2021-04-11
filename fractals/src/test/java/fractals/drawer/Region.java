package fractals.drawer;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import fractals.rational.Complex;
import me.catmousedog.fractals.data.LinearTransform;
import me.catmousedog.fractals.paneloperators.fractals.Fractal;

public class Region {

	private int N = 10;

	public final List<Complex> r = new ArrayList<Complex>(N);

	public final List<Complex> coefficients = new ArrayList<Complex>();

	public Region() {
		add(1, 0);
		update();
	}

	public void update() {
		r.clear();
		for (int i = 0; i < N; i++)
			r.add(f(new Complex(2 * Math.PI * i / (double) N)));
	}

	public boolean add(double x, double y) {
		return coefficients.add(new Complex(x, y));
	}

	public void setSize(int N) {
		this.N = N;
		update();
	}

	public Complex front() {
		return coefficients.get(0);
	}

	public Complex omega(Complex q) {
		Complex omega = new Complex(1, 0);
		for (Complex r : r) {
			omega = omega.multiply(q.subtract(r));
		}
		omega = omega.multiply(front().power(-r.size()));
		omega = omega.add(new Complex(1, 0));
		omega = omega.inverse();
		return omega;
	}

	private Complex f(Complex z) {
		Complex sum = new Complex(0, 0);
		for (int i = 0; i < coefficients.size(); i++) {
			sum = sum.add(z.power(1 - i).multiply(coefficients.get(i)));
		}
		return sum;
	}

	private int R = 4;
	
	
	
	public void draw(Graphics g, Fractal fractal, LinearTransform tr, double ox, double oy) {
		double c = Math.cos(tr.getrot());
		double s = Math.sin(tr.getrot());

		for (int i = 0; i < coefficients.size(); i++) {
			Complex p = coefficients.get(i);

			double dx = tr.getdx();
			double dy = tr.getdy();
			if (i != 1 && coefficients.size() > 1) {
				double px = coefficients.get(1).x;
				double py = coefficients.get(1).y;
				tr.setTranslation(ox-px, oy-py);
			}
			int[] o = test(p.x, p.y, tr, c, s);
			tr.setTranslation(dx, dy);

			g.setColor(Color.getHSBColor((float) (Math.tanh(i) / 4.0), 1, 1));
			g.fillOval(o[0] - R, o[1] - R, 2 * R, 2 * R);
		}

		// r
//		c = Math.cos(tr.getrot());
//		s = Math.sin(tr.getrot());
//		for (int i = 0; i < r.size(); i++) {
//			Complex p = r.get(i);
//			double ty = (c * (p.y - tr.getdy()) + s * (tr.getdx() - p.x)) / (s + c * c);
//			int y = (int) (ty / tr.getn() + tr.getOy());
//			double tx = (p.x + ty * s - tr.getdx());
//			int x = (int) (tx / tr.getm() + tr.getOx());
//
//			g.setColor(Color.BLUE);
//			g.drawOval(x - R, y - R, 2 * R, 2 * R);
//		}
	}

	private int[] test(double x, double y, LinearTransform t, double c, double s) {
		double ty = (c * (y - t.getdy()) + s * (t.getdx() - x)) / (s + c * c);
		int Y = (int) (ty / t.getn() + t.getOy());
		double tx = (x + ty * s - t.getdx());
		int X = (int) (tx / t.getm() + t.getOx());
		return new int[] { X, Y };
	}

}
