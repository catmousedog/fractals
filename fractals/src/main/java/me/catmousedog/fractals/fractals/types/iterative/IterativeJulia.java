package me.catmousedog.fractals.fractals.types.iterative;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.fractals.filters.Filter;
import me.catmousedog.fractals.fractals.filters.IterativeLinearFilter;
import me.catmousedog.fractals.fractals.filters.IterativePeriodicFilter;
import me.catmousedog.fractals.main.Settings;
import me.catmousedog.fractals.ui.components.Item;
import me.catmousedog.fractals.ui.components.concrete.CheckBox;
import me.catmousedog.fractals.ui.components.concrete.Padding;
import me.catmousedog.fractals.ui.components.concrete.TextFieldDouble;

/**
 * Number = Integer
 */
public final class IterativeJulia extends Fractal {

	private double jx, jy;

	public IterativeJulia(Settings settings) {
		super(settings);
	}

	private IterativeJulia(Settings settings, Fractal fractal, double jx, double jy) {
		super(settings, fractal);
		this.jx = jx;
		this.jy = jy;
	}

	@Override
	public Number get(double cx, double cy) {
		double x = cx, y = cy;
		double tx;
		double t1, t2;

		for (int i = 0; i < iterations; i++) {
			tx = x;

			t1 = x * x;
			t2 = y * y;

			if (t1 + t2 > bailout)
				return 255 * (iterations - i) / iterations;

			x = t1 - t2 + jx;
			y = 2 * tx * y + jy;

		}
		return 0;
	}

	@Override
	public void save() {
		jx = jxjtf.saveAndGet();
		jy = jyjtf.saveAndGet();
		super.save();
	}

	@Override
	public void update() {
		jxjtf.setData(jx);
		jyjtf.setData(jy);
		super.update();
	}

	@Override
	public String informalName() {
		return "Iterative Julia Set";
	}

	@Override
	public String fileName() {
		return "IterativeJulia";
	}

	@Override
	public String getTip() {
		return "<html>The second order julia set (<i>z²+c<i/>) generated using an escape time algorithm."
				+ "<br>This allows for deep zooms but creates aliasing effects, generally has the shortest generating time."
				+ "<br>Dragging the mouse will change the fixed point used to generate this set.</html>";
	}

	private TextFieldDouble jxjtf;

	private TextFieldDouble jyjtf;

	private CheckBox mousejcx;

	@Override
	protected void initFractal() {
		filters = new Filter[] { new IterativeLinearFilter(this), new IterativePeriodicFilter(this) };
		filter = filters[0];
		mouse = new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				if (mousejcx.saveAndGet()) {
					double[] t = transform.apply(e.getX(), e.getY());
					jx = t[0];
					jy = t[1];
					update();
					if (settings.isRender_on_changes())
						canvas.render();
				}
			}
		};

		Padding p5 = new Padding(5);

		mousejcx = new CheckBox.Builder("mouse listener")
				.setTip("<html>If enabled, the user can click and drag the mouse along the canvas<br>"
						+ " to change the fixed julia point.</html>")
				.build();

		jxjtf = new TextFieldDouble.Builder().setLabel("Jx").setTip("The fixed julia point's x-coordinate")
				.setDefault(jx).build();
		jyjtf = new TextFieldDouble.Builder().setLabel("Jy").setTip("The fixed julia point's y-coordinate")
				.setDefault(jy).build();

		items = new Item[] { jxjtf, p5, jyjtf, p5, mousejcx };
	}

	@Override
	public Fractal clone() {
		return new IterativeJulia(settings, this, jx, jy);
	}
}
