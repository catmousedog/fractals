package me.catmousedog.fractals.fractals.types.iterative;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.fractals.filters.Filter;
import me.catmousedog.fractals.fractals.filters.IterativeLinearFilter;
import me.catmousedog.fractals.fractals.filters.IterativePeriodicFilter;
import me.catmousedog.fractals.main.Settings;
import me.catmousedog.fractals.ui.components.Item;
import me.catmousedog.fractals.ui.components.concrete.CheckBox;
import me.catmousedog.fractals.ui.components.concrete.Padding;
import me.catmousedog.fractals.ui.components.concrete.TextFieldDouble;

public class TestFractal extends Fractal {

	private double jx, jy;

	public TestFractal(Settings settings) {
		super(settings);
	}

	private TestFractal(Settings settings, Fractal fractal, double jx, double jy) {
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
			y = Math.abs(2 * tx * y) + jy;

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
					jpi.renderWithout(settings.isRender_on_changes(), () -> {
						jx = t[0];
						jy = t[1];
					});
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
	public boolean isMouseEnabled() {
		return mousejcx.saveAndGet();
	}

	@Override
	public @NotNull String informalName() {
		return "Test Fractal";
	}

	@Override
	public @NotNull String fileName() {
		return "TestFractal";
	}

	@Override
	public @NotNull String getTip() {
		return "This is a test fractal and should be disabled";
	}

	@Override
	public @NotNull Fractal clone() {
		return new TestFractal(settings, this, jx, jy);
	}

}
