package me.catmousedog.fractals.canvas;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.SwingUtilities;

import me.catmousedog.fractals.data.LinearTransform;
import me.catmousedog.fractals.data.Pixel;
import me.catmousedog.fractals.ui.JPInterface;
import me.catmousedog.fractals.workers.RenderWorker;

/**
 * the mouse listener for interacting with the canvas
 */
public class Mouse implements MouseListener, MouseMotionListener {

	/**
	 * the instance of the canvas this MouseListener belongs to
	 */
	private final Canvas canvas;

	private final Logger logger = Logger.getLogger("fractals");

	/**
	 * the user interface, used to {@link JPInterface#render()} and retrieve user
	 * data
	 */
	private JPInterface jpi;

	private final RenderWorker renderer = RenderWorker.getInstance();

	private boolean isStationary = true;

	public Mouse(Canvas canvas) {
		this.canvas = canvas;
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		if (canvas.getFractal().isMouseEnabled() && !isStationary)
			return;

		me = SwingUtilities.convertMouseEvent(me.getComponent(), me, canvas);

		// JPInterface#renderWithout(Runnable r) couldn't be used because of the
		// MouseEvent here which isn't effectively final

		// lmb (zoom in)
		if (me.getButton() == MouseEvent.BUTTON1 && renderer.isGeneratorReady()) {
			logger.log(Level.FINEST, "Mouse.mouseReleased lmb");
			canvas.savePrevConfig();
			jpi.save();
			LinearTransform transform = canvas.getFractal().getTransform();
			double[] t = transform.apply(me.getX(), me.getY());
			transform.setTranslation(t[0], t[1]);
			transform.zoom(1 / canvas.getZoomFactor());

			jpi.update();
			jpi.renderNow();
		}

		// rmb (zoom out)
		if (me.getButton() == MouseEvent.BUTTON3 && renderer.isGeneratorReady()) {
			logger.log(Level.FINEST, "Mouse.mouseReleased rmb");
			canvas.savePrevConfig();
			jpi.save();
			LinearTransform transform = canvas.getFractal().getTransform();
			double[] t = transform.apply(me.getX(), me.getY());
			transform.setTranslation(t[0], t[1]);
			transform.zoom(canvas.getZoomFactor());

			jpi.update();
			jpi.renderNow();
		}

		// mmb (info)
		if (me.getButton() == MouseEvent.BUTTON2) {
			middleMouse = false;
			tip.hide();
		}

	}

	@Override
	public void mousePressed(MouseEvent me) {
		me = SwingUtilities.convertMouseEvent(me.getComponent(), me, canvas);

		isStationary = true;

		// mmb (info)
		if (me.getButton() == MouseEvent.BUTTON2) {
			middleMouse = true;
			displayTip(me.getX(), me.getY());
		}
	}

	@Override
	public void mouseDragged(MouseEvent me) {
		isStationary = false;

		if (middleMouse) {
			me = SwingUtilities.convertMouseEvent(me.getComponent(), me, canvas);

			displayTip(me.getX(), me.getY());
		}
	}

	@Override
	public void mouseClicked(MouseEvent me) {
	}

	@Override
	public void mouseEntered(MouseEvent me) {
	}

	@Override
	public void mouseExited(MouseEvent me) {
	}

	@Override
	public void mouseMoved(MouseEvent me) {
	}

	private Popup tip;

	private boolean middleMouse = false;

	private void displayTip(int x, int y) {
		if (tip != null)
			tip.hide();

		Pixel pixel = canvas.getField().getPixel(x, y);
		if (pixel != null) {
			PopupFactory popupFactory = PopupFactory.getSharedInstance();
			Point screenLocation = canvas.getLocationOnScreen();
			tip = popupFactory.getPopup(canvas, new JLabel(pixel.toString()), screenLocation.x + x,
					screenLocation.y + y - 20);
			tip.show();
		}

	}

	public void setJPI(JPInterface jpi) {
		this.jpi = jpi;
	}

}
