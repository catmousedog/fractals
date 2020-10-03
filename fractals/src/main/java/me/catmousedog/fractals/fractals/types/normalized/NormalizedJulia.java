package me.catmousedog.fractals.fractals.types.normalized;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.Properties;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.fractals.filters.Filter;
import me.catmousedog.fractals.fractals.filters.LogPeriodicFilter;
import me.catmousedog.fractals.main.Settings;
import me.catmousedog.fractals.ui.components.Item;
import me.catmousedog.fractals.ui.components.concrete.CheckBox;
import me.catmousedog.fractals.ui.components.concrete.Padding;
import me.catmousedog.fractals.ui.components.concrete.TextFieldDouble;

/**
 * Number = Integer
 */
public final class NormalizedJulia extends Fractal {

	private int offset;
	private double jx, jy;

	public NormalizedJulia(Settings settings) {
		super(settings);
	}

	private NormalizedJulia(Settings settings, Fractal fractal, double jx, double jy, int offset) {
		super(settings, fractal);
		this.jx = jx;
		this.jy = jy;
		this.offset = offset;
	}

	@Override
	public Number get(double cx, double cy) {
		double x = cx, y = cy;
		double tx;
		double t1, t2;
		double p;

		for (int i = 0; i < iterations; i++) {
			tx = x;

			t1 = x * x;
			t2 = y * y;

			p = Math.log(t1 + t2) / 2;

			if (p > bailout) {
				return i + offset - Math.log(p) / Math.log(2);
			}

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
		return "Normalized Julia Set";
	}

	@Override
	public String fileName() {
		return "NormalizedJulia";
	}

	@Override
	public String getTip() {
		return "<html>The second order julia set (<i>z²+c<i/>) generated using an normalized iteration counts."
				+ "<br>This allows for deep zooms but is slower."
				+ "<br>Dragging the mouse will change the fixed point used to generate this set.</html>";
	}

	private TextFieldDouble jxjtf;

	private TextFieldDouble jyjtf;

	private CheckBox mousejcx;

	@Override
	protected void initFractal() {
		filters = new Filter[] { new LogPeriodicFilter(this) };
		filter = filters[0];
		mouse = new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				if (mousejcx.saveAndGet() && canvas.getGenerator().isGenerated()) {
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
	public void setProperties(Properties properties) {
		super.setProperties(properties);
		offset = Integer.parseInt(properties.getProperty("offset"));
	}

	@Override
	public Fractal clone() {
		return new NormalizedJulia(settings, this, jx, jy, offset);
	}
}
