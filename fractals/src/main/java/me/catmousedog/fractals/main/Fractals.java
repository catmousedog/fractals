package me.catmousedog.fractals.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import me.catmousedog.fractals.components.Component;
import me.catmousedog.fractals.components.ComponentFactory;

/**
 * 
 */
public class Fractals {

	private final Properties properties = new Properties();

	private final JFrame frame;

	/**
	 * JPanel where the images are rendered
	 */
	private final JPanel canvas;
	
	/**
	 * master panel on the right side containing {@link Fractal#jsp} and {@link Fractals#feedback}
	 */
//	private final JPanel rpanel;
	
	/**
	 * JPanel to display any feedback such as progress bars
	 */
	private final JPanel feedback;
	
	/**
	 * JPanel interface for the user
	 */
	private final JPanel jpi;

	/**
	 * ScrollPane which contains {@link Fractals#jpi}
	 */
	private final JScrollPane jsp;

	/**
	 * default width and height of the {@link Fractals#canvas}
	 */
	private int width = 1000, height = 1000;

	/**
	 * The interface width, this remains constant unless the frame is smaller than
	 * this width.<br>
	 * Note that this is not the width of the {@linkplain Fractals#jpi}, as this is
	 * usually smaller.
	 */
	private int iwidth = 200;

	/**
	 * vertical and horizontal gap for the jpi
	 */
	private int vgap = 0, hgap = 4;

	public static void main(String[] args) {
		new Fractals();
	}

	/**
	 * intialises the frame, panel and JComponents
	 */
	public Fractals() {
		// load properties
		try {
			properties.load(this.getClass().getClassLoader().getResourceAsStream("project.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// create JFrame
		frame = new JFrame(properties.getProperty("artifactId") + " " + properties.getProperty("version"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());

		// create canvas
		canvas = new JPanel();
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setBorder(BorderFactory.createLoweredBevelBorder());
		canvas.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
//				System.out.println("Resized to " + e.getComponent().getSize());
			}
		});
		canvas.setBackground(Color.LIGHT_GRAY);
		frame.getContentPane().add(canvas, BorderLayout.CENTER);
		
		//create right panel
//		rpanel = new JPanel();
//		rpanel.setLayout(new BorderLayout());
		
		// create interface
		jpi = new JPanel();
		jpi.setLayout(new BoxLayout(jpi, BoxLayout.Y_AXIS));
		jpi.setMaximumSize(new Dimension(iwidth, Integer.MAX_VALUE));
		jpi.setBorder(BorderFactory.createEmptyBorder(vgap, hgap, vgap, hgap));

		// create scroll panel
		jsp = new JScrollPane(jpi, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.setPreferredSize(new Dimension(iwidth + 2 * hgap, height));
		jsp.getVerticalScrollBar().setUnitIncrement(16);
		frame.getContentPane().add(jsp, BorderLayout.LINE_END);

		feedback = new JPanel();
		feedback.setPreferredSize(new Dimension(width + iwidth + 2 * hgap, 45));
		feedback.setBackground(Color.gray);
		frame.getContentPane().add(feedback, BorderLayout.PAGE_END);

		// set size
		frame.setVisible(true);
		frame.pack();

		// add components
		jpi.add(Box.createVerticalStrut(25));
		addComponents();
		jpi.add(Box.createVerticalStrut(25));

		frame.validate();
	}

	/**
	 * adds all the necessary JComponents
	 */
	private void addComponents() {
		ComponentFactory factory = new ComponentFactory(jpi);

		// window
		add(factory.createLabel("Window", Font.BOLD, 15), 0, 10);
		JTextField jtf1 = new JTextField(Integer.toString(jpi.getWidth()));
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
		JButton jb = new JButton("button");
		add(factory.createButton(jb, a -> System.out.println("pressed")), 0, 10);
		add(factory.createLabel("text"), 0, 10);
		add(factory.createLabel("text"), 0, 10);
		add(factory.createLabel("text"), 0, 10);

	}

	private void add(Component c, int v1, int v2) {
		if (v1 > 0)
			jpi.add(Box.createVerticalStrut(v1));
		jpi.add(c);
		if (v2 > 0)
			jpi.add(Box.createVerticalStrut(v2));
	}
}
