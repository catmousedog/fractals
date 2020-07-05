package me.catmousedog.fractals.main;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import me.catmousedog.fractals.components.Component;
import me.catmousedog.fractals.components.ComponentFactory;
import me.catmousedog.fractals.components.Label;
import me.catmousedog.fractals.fractals.LinearTransform;
import me.catmousedog.fractals.fractals.ScalarField;

/**
 * class containing all the data entered by the user through the interface panel
 */
@SuppressWarnings("serial")
public class JPInterface extends JPanel {

	/**
	 * factory to easily create {@link Component}s
	 */
	private final ComponentFactory factory;

	private final ScalarField canvas;

	private final Logger logger;

	/**
	 * below are all the components that the user can interact with and their
	 * respective data, if any
	 */

	private JTextField jtf1;

	private Label pos, zoom;

	private JButton jb1;

	public JPInterface(int iwidth, int vgap, int hgap, Fractals fractals) {
		factory = new ComponentFactory(this);
		canvas = fractals.getCanvas();
		logger = fractals.getLogger();

		canvas.addMouseListener(new Mouse());

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setMaximumSize(new Dimension(iwidth, Integer.MAX_VALUE));
		setBorder(BorderFactory.createEmptyBorder(vgap, hgap, vgap, hgap));
	}

	/**
	 * adds all the necessary JComponents
	 */
	public void addComponents() {
		// window
		add(factory.createLabel("Window", Font.BOLD, 15), 0, 10);
		jtf1 = new JTextField(Integer.toString(this.getWidth()));
		add(factory.createTextField("textfield", jtf1, a -> System.out.println(jtf1.getText())), 0, 10);

		// location
		add(factory.createLabel("Location", Font.BOLD, 15), 20, 10);
		LinearTransform matrix = canvas.getMatrix();
		pos = factory.createLabel(String.format("x:%f    y:%f", matrix.getdx(), matrix.getdy()));
		add(pos, 0, 5);
		zoom = factory.createLabel(String.format("m:%f    n:%f", matrix.getm(), matrix.getn()));
		add(zoom, 0, 10);
		jb1 = new JButton("Repaint");
		add(factory.createButton(jb1, a -> {
			canvas.generate();
			canvas.repaint();
		}), 0, 10);

		// calculations
		add(factory.createLabel("Calculations", Font.BOLD, 15), 20, 10);

		// colors
		add(factory.createLabel("Colors", Font.BOLD, 15), 20, 10);

		// picture
		add(factory.createLabel("Picture", Font.BOLD, 15), 20, 10);

		// other
		add(factory.createLabel("Other", Font.BOLD, 15), 20, 10);
	}

	/**
	 * update all components
	 */
	public void update() {

		// location
		LinearTransform matrix = canvas.getMatrix();
		pos.setText(String.format("x:%f    y:%f", matrix.getdx(), matrix.getdy()));
		zoom.setText(String.format("m:%f    n:%f", matrix.getm(), matrix.getn()));
	}

	/**
	 * for adding components with extra padding
	 * 
	 * @param c  component to be added
	 * @param v1 padding above
	 * @param v2 padding below
	 */
	public void add(Component c, int v1, int v2) {
		if (v1 > 0)
			add(Box.createVerticalStrut(v1));
		add(c);
		if (v2 > 0)
			add(Box.createVerticalStrut(v2));
	}

	private class Mouse implements MouseListener {

		@Override
		public void mousePressed(MouseEvent e) {

		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

	}

}
