package fractals;

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
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;

import me.catmousedog.fractals.data.Field;
import me.catmousedog.fractals.data.LinearTransform;
import me.catmousedog.fractals.workers.RenderWorker;

public class Main implements MouseMotionListener, MouseListener, MouseWheelListener, KeyListener {

	private JFrame jf;
	private JPanel jp, jp2;

	private int w = 400, h = 400;
	private double inc = 1;
	private boolean allowRender = false, all = false;
	private double a = 0;

	private final List<Pole> poles = new ArrayList<Pole>(20);

	{

	}

	private final Field field;
	private final RenderWorker renderer;
	private final RationalFractal fractal = new RationalFractal(poles);

	public static void main(String[] args) {
		new Main();
	}

	public Main() {

		jp = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (field != null)
					g.drawImage(field.getImg(), 0, 0, null);

				if (allowRender) {
					allowRender = false;
					renderer.runScheduledGenerator();
					renderer.runScheduledPainter();
				}
			}
		};
		jp.setPreferredSize(new Dimension(w, h));
		jp.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

		jp2 = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				LinearTransform tr = fractal.getTransform();
				double c = Math.cos(tr.getrot());
				double s = Math.sin(tr.getrot());
				for (Pole p : poles) {
					double ty = (c * (p.y - tr.getdy()) + s * (tr.getdx() - p.x)) / (s + c * c);
					int y = (int) (ty / tr.getn() + tr.getOy());
					double tx = (p.x + ty * s - tr.getdx());
					int x = (int) (tx / tr.getm() + tr.getOx());

					g.setColor(Color.getHSBColor((float) (Math.tanh(0.5 * p.m) + 2) / 4f, 1, 1));
					int r = (int) (1 * Math.abs(p.m)) + 2;
					g.fillOval(x - r, y - r, 2 * r, 2 * r);
				}
			}
		};
		jp2.setPreferredSize(new Dimension(w, h));
		jp2.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));

		jf = new JFrame("Fractal Drawer");
		jf.setLayout(new FlowLayout());
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.add(jp);
		jf.add(jp2);
		jf.setVisible(true);
		jf.pack();
		jf.addMouseListener(this);
		jf.addMouseMotionListener(this);
		jf.addMouseWheelListener(this);
		jf.addKeyListener(this);

		field = new Field(w, h);
		renderer = RenderWorker.getInstance();
		renderer.setScheduled_workers(false);
		fractal.getTransform().setOrigin(w / 2, h / 2);
		fractal.getTransform().zoom(1);

		update();
	}

	private void update() {

		renderer.newRender(field, fractal.clone(), () -> {
			allowRender = true;
			jp.repaint();
			jp2.repaint();
		});
	}

	/**
	 * @param x
	 * @param y
	 * @return ref to nearest pole
	 */
	private Pole getNearest(double x, double y) {
		Pole nearest = null;
		double d = Double.MAX_VALUE;
		for (Pole p : poles) {
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
			SwingUtilities.convertPointFromScreen(p, jp2);
			double[] c = fractal.getTransform().apply(p.x, p.y);
			poles.add(new Pole(c[0], c[1], 0));
			update();
		} else if (e.getKeyCode() == KeyEvent.VK_G) {
			update();

		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			System.out.println("save");
			for (Pole l : poles) {
				System.out.printf("poles.add(new Pole(%f, %f, %f));\n", l.x, l.y, l.m);
			}

		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			a += 0.1;
			fractal.setC(Math.exp(a));
			System.out.println(a);
			update();

		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			a -= 0.1;
			fractal.setC(Math.exp(a));
			System.out.println(a);
			update();

		} else if (e.getKeyCode() == KeyEvent.VK_A) {
			all = !all;
			System.out.printf("all:%b\n", all);
		} else if (e.getKeyCode() == KeyEvent.VK_I) {
			for (Pole l : poles) {
				l.x *= 0.9;
				l.y *= 0.9;
				update();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_K) {
			for (Pole l : poles) {
				l.x /= 0.9;
				l.y /= 0.9;
				update();
			}
		}
	}

	// SPACE = place pole on mouse
	// LMB drag = drag pole
	// RMB = remove pole
	// SCROLL = change power of pole

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			e = SwingUtilities.convertMouseEvent(e.getComponent(), e, jp2);

			if (all) {
				double[] c = fractal.getTransform().apply(e.getX(), e.getY());
				Pole n = getNearest(c[0], c[1]);
				double dx = n.x - c[0];
				double dy = n.y - c[1];
				for (Pole l : poles) {
					l.x -= dx;
					l.y -= dy;
				}
			} else {
				double[] c = fractal.getTransform().apply(e.getX(), e.getY());
				Pole nearest = getNearest(c[0], c[1]);
				if (nearest != null) {
					nearest.x = c[0];
					nearest.y = c[1];
				}
			}
			update();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			e = SwingUtilities.convertMouseEvent(e.getComponent(), e, jp2);

			double[] c = fractal.getTransform().apply(e.getX(), e.getY());
			Pole nearest = getNearest(c[0], c[1]);
			if (nearest != null) {
				poles.remove(nearest);
				update();
			}
		}
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		MouseEvent me = SwingUtilities.convertMouseEvent(e.getComponent(), e, jp2);

		double[] c = fractal.getTransform().apply(me.getX(), me.getY());
		Pole nearest = getNearest(c[0], c[1]);
		if (nearest != null) {
			if (e.getWheelRotation() < 0) {
				nearest.m += inc;
			} else {
				nearest.m -= inc;
			}
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
