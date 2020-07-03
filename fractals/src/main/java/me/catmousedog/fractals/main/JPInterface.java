package me.catmousedog.fractals.main;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import me.catmousedog.fractals.components.Component;
import me.catmousedog.fractals.components.ComponentFactory;

/**
 * class containing all the data entered by the user through the interface panel
 */
@SuppressWarnings("serial")
public class JPInterface extends JPanel {

	/**
	 * factory to easily create {@link Component}s
	 */
	private final ComponentFactory factory;

	/**
	 * below are all the components that the user can interact with and their
	 * respective data, if any
	 */
	
	private JTextField jtf1;

	private JButton jb1;

	public JPInterface(int iwidth, int vgap, int hgap) {
		factory = new ComponentFactory(this);

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
		add(factory.createTextField("window width", jtf1, a -> System.out.println(jtf1.getText())), 0, 10);
		add(factory.createLabel("text"), 0, 10);
		add(factory.createLabel("text"), 0, 10);

		// calculations
		add(factory.createLabel("Calculations", Font.BOLD, 15), 20, 10);
		add(factory.createLabel("text"), 0, 10);
		add(factory.createLabel("text"), 0, 10);
		add(factory.createLabel("text"), 0, 10);

		// colors
		add(factory.createLabel("Colors", Font.BOLD, 15), 20, 10);
		add(factory.createLabel("text"), 0, 10);
		add(factory.createLabel("text"), 0, 10);
		add(factory.createLabel("text"), 0, 10);

		// picture
		add(factory.createLabel("Picture", Font.BOLD, 15), 20, 10);
		add(factory.createLabel("text"), 0, 10);
		add(factory.createLabel("text"), 0, 10);
		add(factory.createLabel("text"), 0, 10);

		// other
		add(factory.createLabel("Other", Font.BOLD, 15), 20, 10);
		jb1 = new JButton("button");
		add(factory.createButton(jb1, a -> System.out.println("pressed")), 0, 10);
		add(factory.createLabel("text"), 0, 10);
		add(factory.createLabel("text"), 0, 10);
		add(factory.createLabel("text"), 0, 10);
	}

	public void add(Component c, int v1, int v2) {
		if (v1 > 0)
			add(Box.createVerticalStrut(v1));
		add(c);
		if (v2 > 0)
			add(Box.createVerticalStrut(v2));
	}

}
