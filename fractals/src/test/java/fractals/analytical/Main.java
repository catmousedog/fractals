package fractals.analytical;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;

import fractals.rational.Complex;
import me.catmousedog.fractals.data.Field;
import me.catmousedog.fractals.data.LinearTransform;
import me.catmousedog.fractals.workers.RenderWorker;

public class Main implements MouseMotionListener, MouseListener, MouseWheelListener, KeyListener {

	private JFrame jf;
	private JPanel jp;

	private int w = 400, h = 400;
	private boolean allowRender = false;

	private final Field field;
	private final RenderWorker renderer;
	private final AnalFractal fractal = new AnalFractal();

	public static void main(String[] args) {
		new Main();
	}

	private int r = 3;

	public Main() {

		jp = new JPanel() {
			private static final long serialVersionUID = 1L;
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (field != null)
					g.drawImage(field.getImg(), 0, 0, null);

				//coefficients
				LinearTransform tr = fractal.getTransform();
				double c = Math.cos(tr.getrot());
				double s = Math.sin(tr.getrot());
				for (int i = 0; i < fractal.coefficients.size(); i++) {
					Complex p = fractal.coefficients.get(i);
					double ty = (c * (p.y - tr.getdy()) + s * (tr.getdx() - p.x)) / (s + c * c);
					int y = (int) (ty / tr.getn() + tr.getOy());
					double tx = (p.x + ty * s - tr.getdx());
					int x = (int) (tx / tr.getm() + tr.getOx());

					g.setColor(Color.getHSBColor((float) (Math.tanh(i) / 4.0), 1, 1));
					g.fillOval(x - r, y - r, 2 * r, 2 * r);
				}
				//r
				tr = fractal.getTransform();
				c = Math.cos(tr.getrot());
				s = Math.sin(tr.getrot());
				for (int i = 0; i < AnalFractal.r.length; i++) {
					Complex p = AnalFractal.r[i];
					double ty = (c * (p.y - tr.getdy()) + s * (tr.getdx() - p.x)) / (s + c * c);
					int y = (int) (ty / tr.getn() + tr.getOy());
					double tx = (p.x + ty * s - tr.getdx());
					int x = (int) (tx / tr.getm() + tr.getOx());

					g.setColor(Color.BLUE);
					g.drawOval(x - r, y - r, 2 * r, 2 * r);
				}

				if (allowRender) {
					allowRender = false;
					renderer.runScheduledGenerator();
					renderer.runScheduledPainter();
				}
			}
		};
		jp.setPreferredSize(new Dimension(w, h));
		jp.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

		jf = new JFrame("analytical test");
		jf.setLayout(new FlowLayout());
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.add(jp);
		jf.setVisible(true);
		jf.pack();

		field = new Field(w, h);
		renderer = RenderWorker.getInstance();
		renderer.setScheduled_workers(false);
		fractal.getTransform().setOrigin(w / 2, h / 2);
		fractal.getTransform().zoom(1);

		jf.addMouseListener(this);
		jf.addMouseMotionListener(this);
		jf.addMouseWheelListener(this);
		jf.addKeyListener(this);

		update();
	}

	private void update() {
		renderer.newRender(field, fractal.clone(), () -> {
			allowRender = true;
			jp.repaint();
		});
	}

	/**
	 * @param x
	 * @param y
	 * @return ref to nearest pole
	 */
	private Complex getNearest(double x, double y) {
		Complex nearest = null;
		double d = Double.MAX_VALUE;
		for (Complex p : fractal.coefficients) {
			Complex ref = new Complex(x - p.x, y - p.y);
			double a = ref.mag();
			if (a <= d) {
				nearest = p;
				d = a;
			}
		}
		return nearest;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			Point p = MouseInfo.getPointerInfo().getLocation();
			SwingUtilities.convertPointFromScreen(p, jp);
			double[] c = fractal.getTransform().apply(p.x, p.y);
			fractal.add(c[0], c[1]);
			update();
		} else if (e.getKeyCode() == KeyEvent.VK_G) {
			update();
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			System.out.println("save");
			for (Complex l : fractal.coefficients) {
				System.out.printf("%f\t%f\n", l.x, l.y);
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			e = SwingUtilities.convertMouseEvent(e.getComponent(), e, jp);

			double[] c = fractal.getTransform().apply(e.getX(), e.getY());
			Complex nearest = getNearest(c[0], c[1]);
			if (nearest != null) {
				nearest.x = c[0];
				nearest.y = c[1];
			}
			update();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			e = SwingUtilities.convertMouseEvent(e.getComponent(), e, jp);

			double[] c = fractal.getTransform().apply(e.getX(), e.getY());
			Complex nearest = getNearest(c[0], c[1]);
			if (nearest != null) {
				fractal.coefficients.remove(nearest);
				update();
			}
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		if (e.getWheelRotation() < 0) {
			fractal.getTransform().zoom(0.9);
			update();
		} else if (e.getWheelRotation() > 0) {
			fractal.getTransform().zoom(1.1);
			update();
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

}
