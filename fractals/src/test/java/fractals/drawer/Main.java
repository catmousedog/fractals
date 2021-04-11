package fractals.drawer;

import java.awt.FlowLayout;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import fractals.rational.Complex;
import me.catmousedog.fractals.workers.RenderWorker;

public class Main implements MouseMotionListener, MouseListener, KeyListener {

	private JFrame jf;

	private Background background = new Background();
	private DrawerPanel drawerPanel;
	private OriginPanel originPanel;

	private final FractalDrawer fractal = new FractalDrawer();

	public static void main(String[] args) {
		RenderWorker.getInstance().setScheduled_workers(false);
		new Main();
	}

	public Main() {
		fractal.getTransform().setOrigin(background.w / 2, background.h / 2);
		fractal.getTransform().zoom(1);

		drawerPanel = new DrawerPanel(fractal, background);
		originPanel = new OriginPanel(fractal, background);

		jf = new JFrame("fractal drawer");
		jf.setLayout(new FlowLayout());
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.add(drawerPanel);
		jf.add(originPanel);
		jf.setVisible(true);
		jf.addMouseMotionListener(this);
		jf.addMouseListener(this);
		jf.addKeyListener(this);
		jf.pack();

		fractal.regions.add(new Region());
		fractal.regions.get(0).add(0, -2);
		fractal.regions.add(new Region());
		fractal.regions.get(1).add(1, -2);
		fractal.regions.add(new Region());
		fractal.regions.get(2).add(-1, -2);
		fractal.next(1);

		fractal.origin.add(1, 1);

		update();
	}

	private void update() {
		fractal.update();

		drawerPanel.draw();
		originPanel.draw();
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		if (SwingUtilities.isLeftMouseButton(e)) {
			Complex nearest = null;
			double[] c = null;
			Point p = e.getPoint();
			SwingUtilities.convertPointToScreen(p, jf);
			if (Util.inside(drawerPanel, p) && fractal.selected != null) {
				SwingUtilities.convertPointFromScreen(p, drawerPanel);
				c = drawerPanel.transform.apply(p.x, p.y);
				if (drawerPanel.all) {
					nearest = drawerPanel.getNearestRegions(c[0], c[1]);
				} else {
					nearest = drawerPanel.getNearestSelected(c[0], c[1]);
				}
			} else if (Util.inside(originPanel, p) && fractal.origin != null) {
				SwingUtilities.convertPointFromScreen(p, originPanel);
				c = originPanel.transform.apply(p.x, p.y);
				nearest = drawerPanel.getNearestOrigin(c[0], c[1]);
			}
			if (nearest != null) {
				nearest.x = c[0];
				nearest.y = c[1];
				update();
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (SwingUtilities.isRightMouseButton(e)) {
			if (e.getSource() instanceof DrawerPanel && fractal.selected != null) {
				e = SwingUtilities.convertMouseEvent(e.getComponent(), e, drawerPanel);
				double[] c = fractal.getTransform().apply(e.getX(), e.getY());
				Complex nearest = drawerPanel.getNearestSelected(c[0], c[1]);
				if (nearest != null) {
					for (Region region : fractal.regions) {
						if (region.coefficients.remove(nearest)) {
							update();
							return;
						}
					}
				}
			}

		} else if (e.getSource() instanceof OriginPanel && fractal.origin != null) {
			e = SwingUtilities.convertMouseEvent(e.getComponent(), e, originPanel);
			double[] c = fractal.getTransform().apply(e.getX(), e.getY());
			Complex nearest = drawerPanel.getNearestOrigin(c[0], c[1]);
			if (nearest != null) {
				if (fractal.origin.coefficients.remove(nearest)) {
					update();
					return;
				}
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_D) {
			drawerPanel.toggleShowImage();
			drawerPanel.repaint();
		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			fractal.next(1);
			drawerPanel.draw();
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			fractal.next(-1);
			drawerPanel.draw();
		}
		if (e.getKeyCode() == KeyEvent.VK_A) {
			drawerPanel.all = !drawerPanel.all;
			drawerPanel.draw();
		}
		if (e.getKeyCode() == KeyEvent.VK_N) {
			Point p = MouseInfo.getPointerInfo().getLocation();
			if (Util.inside(drawerPanel, p)) {
				SwingUtilities.convertPointFromScreen(p, drawerPanel);
				double[] c = drawerPanel.transform.apply(p.x, p.y);
				fractal.regions.add(new Region());
				fractal.regions.get(fractal.regions.size() - 1).add(c[0], c[1]);
				update();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			Point p = MouseInfo.getPointerInfo().getLocation();
			if (Util.inside(drawerPanel, p)) {
				SwingUtilities.convertPointFromScreen(p, drawerPanel);
				double[] c = drawerPanel.transform.apply(p.x, p.y);
				if (fractal.selected != null) {
					fractal.selected.add(c[0], c[1]);
				}
			} else if (Util.inside(originPanel, p)) {
				SwingUtilities.convertPointFromScreen(p, originPanel);
				double[] c = originPanel.transform.apply(p.x, p.y);
				if (fractal.origin != null) {
					fractal.origin.add(c[0], c[1]);
				}
			}
			update();
		}
		if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {

		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
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

}
