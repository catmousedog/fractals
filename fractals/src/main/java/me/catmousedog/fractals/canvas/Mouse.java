package me.catmousedog.fractals.canvas;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.SwingUtilities;

import me.catmousedog.fractals.fractals.LinearTransform;

public class Mouse implements MouseListener {

	/**
	 * the instance of the canvas this MouseListener belongs to
	 */
	private final Canvas canvas;

	/**
	 * factor to multiply the zoom with each time the user clicks
	 */
	private double zoomFactor = 2;

	public Mouse(Canvas canvas) {
		this.canvas = canvas;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		e = SwingUtilities.convertMouseEvent(e.getComponent(), e, canvas);

		LinearTransform transform = canvas.getTransform();
		double[] t = transform.apply(e.getX(), e.getY());
		
		// lmb zoom in
		if (e.getButton() == MouseEvent.BUTTON1) {
			transform.setTranslation(t[0], t[1]);
		}

		// rmb zoom out
		if (e.getButton() == MouseEvent.BUTTON3) {
			transform.setTranslation(t[0], t[1]);
		}

		transform.setTranslation(t[0], t[1]);
		double newZoom = transform.getm() / zoomFactor;
		transform.setScalar(newZoom, newZoom);
		canvas.render(null);
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

	/**
	 * sets the zoomFactor
	 * 
	 * @param z the new zoomFactor
	 */
	public void setZoomFactor(double z) {
		zoomFactor = z;
	}
}
