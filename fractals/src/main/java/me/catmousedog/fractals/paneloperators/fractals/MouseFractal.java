package me.catmousedog.fractals.paneloperators.fractals;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.SwingUtilities;

import me.catmousedog.fractals.ui.components.Item;
import me.catmousedog.fractals.ui.components.concrete.CheckBox;
import me.catmousedog.fractals.ui.components.concrete.Padding;
import me.catmousedog.fractals.ui.components.concrete.TextFieldDouble;

public abstract class MouseFractal extends Fractal {

	protected double jx, jy;

	/**
	 * Initialises the <code>MouseFractal</code>.
	 * <p>
	 * Still needs to initialise the {@link Fractal#functions} and
	 * {@link Fractal#function}.
	 */
	protected MouseFractal() {
		super();

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

		mouse = new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent me) {
			}

			@Override
			public void mouseDragged(MouseEvent me) {
				if (SwingUtilities.isLeftMouseButton(me) && mousejcx.saveAndGet()) {
					double[] t = transform.apply(me.getX(), me.getY());
					jpi.renderWithout(render_on_changes, () -> {
						jx = t[0];
						jy = t[1];
					});
				}
			}
		};

	}

	/**
	 * Clones the <code>MouseFracatl</code>
	 * 
	 * @param fractal
	 */
	protected MouseFractal(MouseFractal fractal) {
		super(fractal);
		this.jx = fractal.jx;
		this.jy = fractal.jy;
	}

	private TextFieldDouble jxjtf;

	private TextFieldDouble jyjtf;

	private CheckBox mousejcx;

	@Override
	public boolean isMouseEnabled() {
		return mousejcx.saveAndGet();
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

}
