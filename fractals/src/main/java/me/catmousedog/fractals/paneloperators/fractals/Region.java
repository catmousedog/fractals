package me.catmousedog.fractals.paneloperators.fractals;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import me.catmousedog.fractals.data.Complex;
import me.catmousedog.fractals.data.LinearTransform;

public class Region {

	public final LinkedList<Complex> points = new LinkedList<Complex>();

	public double C = 1;
	
	public double s;

	public Region(double s) {
		this.s = s;
	}

	public void setC() {
		Complex last;
		if (points.size() == 0) {
			return;
		} else {
			last = points.getLast();
		}
		C = Math.exp(-points.size() * s / 2.0);
		double prod = 1;
		int i = 0;
		for (Complex l : points) {
			if (i < points.size() - 1)
				prod *= Math.sqrt(last.subtract(l).mag());
			i++;
		}
		C /= prod;
	}

	// w
	public Complex omega(Complex q) {
		Complex omega = new Complex(1, 0);
		for (Complex r : points) {
			omega = omega.multiply(q.subtract(r));
		}
		return omega.multiply(C);
	}

	// w'
	public Complex domega(Complex q) {
		Complex dOmega = new Complex(0, 0); // w' = 0

		for (int i = 0; i < points.size(); i++) {
			Complex term = new Complex(1, 0); // term = 1
			for (int j = 0; j < points.size(); j++) {
				if (i != j)
					term = term.multiply(q.subtract(points.get(j))); // term *= (z-r)
			}
			dOmega = dOmega.add(term); // w' += term
		}
		dOmega = dOmega.multiply(C); // w' *= C
		return dOmega;
	}

	public void draw(Graphics g, LinearTransform tr) {
		double c = Math.cos(tr.getrot());
		double s = Math.sin(tr.getrot());

		// draw r
		for (Complex p : points) {
			double ty = (c * (p.y - tr.getdy()) + s * (tr.getdx() - p.x)) / (s + c * c);
			int Y = (int) (ty / tr.getn() + tr.getOy());
			double tx = (p.x + ty * s - tr.getdx());
			int X = (int) (tx / tr.getm() + tr.getOx());

			g.setColor(Color.RED);
			g.fillOval(X - 2, Y - 2, 2 * 2, 2 * 2);
		}
	}
}