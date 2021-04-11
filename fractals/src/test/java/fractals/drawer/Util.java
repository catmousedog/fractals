package fractals.drawer;

import java.awt.Point;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Util {

	public static boolean inside(JPanel jp, Point a) {
		Point p = new Point(a.x, a.y);
		SwingUtilities.convertPointFromScreen(p, jp);
		return 0 <= p.x && p.x <= jp.getWidth() && 0 <= p.y && p.y <= jp.getHeight();
	}
}
