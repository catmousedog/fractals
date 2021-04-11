package fractals.drawer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.stream.Stream;

import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;

import fractals.rational.Complex;
import me.catmousedog.fractals.data.Field;
import me.catmousedog.fractals.data.LinearTransform;
import me.catmousedog.fractals.paneloperators.filters.Filter;
import me.catmousedog.fractals.paneloperators.fractals.Fractal;
import me.catmousedog.fractals.paneloperators.functions.Function;

public class DrawerPanel extends Panel {

	private final Background background;

	private boolean showImage = true;

	public final Complex offset = new Complex(0, -1);

	public DrawerPanel(FractalDrawer fractal, Background background) {
		super(new Field(background.w, background.h), fractal, fractal.getTransform().clone());
		this.background = background;

		transform.setTranslation(offset.x, offset.y);

		setPreferredSize(new Dimension(background.w, background.h));
		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (field != null)
			g.drawImage(field.getImg(), 0, 0, null);

		// all coefficients (not origin)
		if (all) {
			for (Region region : fractal.regions) {
				region.draw(g, fractal, transform, offset.x, offset.y);
			}
		} else if (fractal.selected != null) {
			fractal.selected.draw(g, fractal, transform, offset.x, offset.y);
		}

		if (showImage)
			g.drawImage(background.getImg(), 0, 0, null);
	}

	public boolean all = false;

	public void draw() {
		Fractal fr = fractal.clone();
		fr.getTransform().set(transform); // change transform of clone
		Function fu = fr.getFunction();
		Filter fi = fu.getFilter();

		BufferedImage img = field.getImg();

		if (all)
			fractal.mode = FractalDrawer.ALL;
		else
			fractal.mode = FractalDrawer.SELECTED;

		Stream.of(field.getPixels()).parallel().forEach(p -> {
			double[] t = transform.apply(p.x, p.y);
			img.setRGB(p.x, p.y, fi.apply(fu.apply(fractal.get(t[0], t[1]))));
		});

		allowRender = true;
		repaint();
	}

	public void toggleShowImage() {
		showImage = !showImage;
	}

	public Complex getNearestSelected(double x, double y) {
		Complex nearest = null;
		double d = Double.MAX_VALUE;
		double dx = transform.getdx();
		double dy = transform.getdy();
		for (int i = 0; i < fractal.selected.coefficients.size(); i++) {
			Complex p = fractal.selected.coefficients.get(i);
			if (fractal.selected.coefficients.size() > 1 && i != 1) {
				
				if (i != 1 && fractal.selected.coefficients.size() > 1) {
					double px = fractal.selected.coefficients.get(1).x;
					double py = fractal.selected.coefficients.get(1).y;
					transform.setTranslation(offset.x - px, offset.y - py);
				}

//				x += fractal.selected.coefficients.get(1).x;
//				y -= fractal.selected.coefficients.get(1).y;
//				System.out.printf("%f\t%f\n", fractal.selected.coefficients.get(1).x, fractal.selected.coefficients.get(1).y);
			}
			Complex ref = new Complex(x - p.x, y - p.y);
			double a = ref.mag();
			if (a <= d) {
				nearest = p;
				d = a;
			}
			transform.setTranslation(dx, dy);
		}
		return nearest;
	}

	public Complex getNearestRegions(double x, double y) {
		Complex nearest = null;
		double d = Double.MAX_VALUE;
		for (Region region : fractal.regions) {
			for (Complex p : region.coefficients) {
				Complex ref = new Complex(x - p.x, y - p.y);
				double a = ref.mag();
				if (a <= d) {
					nearest = p;
					d = a;
				}
			}
		}
		return nearest;
	}

	public Complex getNearestOrigin(double x, double y) {
		Complex nearest = null;
		double d = Double.MAX_VALUE;
		for (Complex p : fractal.origin.coefficients) {
			Complex ref = new Complex(x - p.x, y - p.y);
			double a = ref.mag();
			if (a <= d) {
				nearest = p;
				d = a;
			}
		}
		return nearest;
	}

}
