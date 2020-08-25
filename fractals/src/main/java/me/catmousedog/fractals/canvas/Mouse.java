package me.catmousedog.fractals.canvas;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingUtilities;

import me.catmousedog.fractals.fractals.LinearTransform;
import me.catmousedog.fractals.ui.JPInterface;

/**
 * the mouse listener for interacting with the canvas
 */
public class Mouse implements MouseListener {

	/**
	 * the instance of the canvas this MouseListener belongs to
	 */
	private final Canvas canvas;

	/**
	 * the user interface, used to {@link JPInterface#render()} and retrieve user
	 * data
	 */
	private JPInterface jpi;

	public Mouse(Canvas canvas) {
		this.canvas = canvas;
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		me = SwingUtilities.convertMouseEvent(me.getComponent(), me, canvas);

		// JPInterface#renderWithout(Runnable r) couldn't be used because of the
		// MouseEvent here which isn't effectively final

		jpi.save();

		LinearTransform transform = canvas.getFractal().getTransform();
		double[] t = transform.apply(me.getX(), me.getY());
		transform.setTranslation(t[0], t[1]);

		// lmb (zoom in)
		if (me.getButton() == MouseEvent.BUTTON1)
			transform.zoom(1 / canvas.getZoomFactor());

		// rmb (zoom out)
		if (me.getButton() == MouseEvent.BUTTON3)
			transform.zoom(canvas.getZoomFactor());

		jpi.update();
		jpi.renderNow();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	public void setJPI(JPInterface jpi) {
		this.jpi = jpi;
	}
}
