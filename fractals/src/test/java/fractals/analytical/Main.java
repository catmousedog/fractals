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
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
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

	private int w = 600, h = 600;
	private boolean allowRender = false;

	private Field field;
	private final RenderWorker renderer = RenderWorker.getInstance();
	private final AnalFractal fractal = new AnalFractal();

	public static void main(String[] args) {
		new Main();
	}

	private int r = 3;

	public Main() {

		File f = new File("C:\\Users\\Gebruiker\\Desktop\\CPP\\images\\name.png");
		if (!f.exists())
			return;

		BufferedImage img;
		try {
			img = ImageIO.read(f);
			System.out.println(img.getWidth());
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		jp = new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (field != null) {
					BufferedImage t = field.getImg();

					for (int x = 0; x < img.getWidth(); x++) {
						for (int y = 0; y < img.getHeight(); y++) {
							if (img.getRGB(x, y) == 0xff000000) {
								t.setRGB(x, y, 0x80000000);
							}
						}
					}

					g.drawImage(t, 0, 0, null);
				}

				// coefficients
				for (int l = 0; l < fractal.regions.size(); l++) {
					LinearTransform tr = fractal.getTransform();
					double c = Math.cos(tr.getrot());
					double s = Math.sin(tr.getrot());
					for (int i = 0; i < fractal.regions.get(l).size(); i++) {
						Complex p = fractal.regions.get(l).get(i);
						double ty = (c * (p.y - tr.getdy()) + s * (tr.getdx() - p.x)) / (s + c * c);
						int y = (int) (ty / tr.getn() + tr.getOy());
						double tx = (p.x + ty * s - tr.getdx());
						int x = (int) (tx / tr.getm() + tr.getOx());

						g.setColor(Color.getHSBColor((float) (Math.tanh(i) / 4.0), 1, 1));
						g.fillOval(x - r, y - r, 2 * r, 2 * r);
					}
					// r
					tr = fractal.getTransform();
					c = Math.cos(tr.getrot());
					s = Math.sin(tr.getrot());
					for (int i = 0; i < fractal.regions.get(l).r.length; i++) {
						Complex p = fractal.regions.get(l).r[i];
						double ty = (c * (p.y - tr.getdy()) + s * (tr.getdx() - p.x)) / (s + c * c);
						int y = (int) (ty / tr.getn() + tr.getOy());
						double tx = (p.x + ty * s - tr.getdx());
						int x = (int) (tx / tr.getm() + tr.getOx());

						g.setColor(Color.BLUE);
						g.drawOval(x - r, y - r, 2 * r, 2 * r);
					}
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
		renderer.setScheduled_workers(true);
		fractal.getTransform().setOrigin(w / 2, h / 2);
		fractal.getTransform().zoom(1);

		jf.addMouseListener(this);
		jf.addMouseMotionListener(this);
		jf.addMouseWheelListener(this);
		jf.addKeyListener(this);

		update();
	}

	private void update() {
		for (Coefficients e : fractal.regions) {
			e.update();
		}
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
		for (int l = 0; l < fractal.regions.size(); l++) {
			for (Complex p : fractal.regions.get(l)) {
				Complex ref = new Complex(x - p.x, y - p.y);
				double a = ref.mag();
				if (a <= d) {
					nearest = p;
					d = a;
				}
			}
		}
		return nearest;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_1) {
			if (fractal.regions.size() >= 1) {
				Point p = MouseInfo.getPointerInfo().getLocation();
				SwingUtilities.convertPointFromScreen(p, jp);
				double[] c = fractal.getTransform().apply(p.x, p.y);
				fractal.regions.get(0).add(c[0], c[1]);
				update();
			}

		} else if (e.getKeyCode() == KeyEvent.VK_2) {
			if (fractal.regions.size() >= 2) {
				Point p = MouseInfo.getPointerInfo().getLocation();
				SwingUtilities.convertPointFromScreen(p, jp);
				double[] c = fractal.getTransform().apply(p.x, p.y);
				fractal.regions.get(1).add(c[0], c[1]);
				update();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_3) {
			if (fractal.regions.size() >= 3) {
				Point p = MouseInfo.getPointerInfo().getLocation();
				SwingUtilities.convertPointFromScreen(p, jp);
				double[] c = fractal.getTransform().apply(p.x, p.y);
				fractal.regions.get(2).add(c[0], c[1]);
				update();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_4) {
			if (fractal.regions.size() >= 4) {
				Point p = MouseInfo.getPointerInfo().getLocation();
				SwingUtilities.convertPointFromScreen(p, jp);
				double[] c = fractal.getTransform().apply(p.x, p.y);
				fractal.regions.get(3).add(c[0], c[1]);
				update();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_5) {
			if (fractal.regions.size() >= 5) {
				Point p = MouseInfo.getPointerInfo().getLocation();
				SwingUtilities.convertPointFromScreen(p, jp);
				double[] c = fractal.getTransform().apply(p.x, p.y);
				fractal.regions.get(4).add(c[0], c[1]);
				update();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_G) {
			update();
		} else if (e.getKeyCode() == KeyEvent.VK_S) {
			System.out.println();
			for (int l = 0; l < fractal.regions.size(); l++) {
				for (Complex p : fractal.regions.get(l)) {
					System.out.printf("add(%d, %f, %f);\n", l, p.x, p.y);
				}
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
				for (int l = 0; l < fractal.regions.size(); l++) {
					if (fractal.regions.get(l).remove(nearest)) {
						update();
						return;
					}
				}
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
