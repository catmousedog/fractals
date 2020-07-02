package me.catmousedog.fractals.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import me.catmousedog.fractals.components.Component;
import me.catmousedog.fractals.components.Label;
import me.catmousedog.fractals.components.TextField;
import me.catmousedog.fractals.listeners.KeyListener;

/**
 * 
 */
public class Main {
	
	private final Properties properties = new Properties();
	
	private final JFrame frame;
	
	/**
	 * JPanel where the images are rendered
	 */
	private final JPanel canvas;
	
	/**
	 * JPanel interface for the user
	 */
	private final JPanel jpi;
	
	/**
	 * ScrollPane which contains {@link Main#jpi}
	 */
	private final JScrollPane jsp;
	
	private int width=1000, height=1000;
	
	/**
	 * the interface width, 
	 * this remains constant unless the frame is smaller than this width
	 */
	private int iwidth=200;
	
	/**
	 * vertical and horizontal gap for the jpi
	 */
	private int vgap=0, hgap=4;
	
	public static void main(String[] args) { new Main(); }
	
	/**
	 * intialises the frame, panel and JComponents 
	 */
	public Main() {
		try {
			properties.load(this.getClass().getClassLoader().getResourceAsStream("project.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		frame = new JFrame(properties.getProperty("artifactId") + " " + properties.getProperty("version"));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		canvas = new JPanel();
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setBorder(BorderFactory.createLoweredBevelBorder());
		canvas.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
//				System.out.println("Resized to " + e.getComponent().getSize());
			}	
		});
		frame.getContentPane().add(canvas, BorderLayout.CENTER);
		
		jpi = new JPanel();
		jpi.setLayout(new BoxLayout(jpi, BoxLayout.Y_AXIS));
		jpi.setMaximumSize(new Dimension(iwidth, Integer.MAX_VALUE));
		jpi.setBorder(BorderFactory.createEmptyBorder(vgap, hgap, vgap, hgap));
		
		jsp = new JScrollPane(jpi, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		jsp.setPreferredSize(new Dimension(iwidth+2*hgap, height));
		jsp.getVerticalScrollBar().setUnitIncrement(16);
		frame.getContentPane().add(jsp, BorderLayout.LINE_END);
		
		frame.setVisible(true);
		frame.pack();
		
		jpi.add(Box.createVerticalStrut(25));
		addComponents();
		jpi.add(Box.createVerticalStrut(25));
		
		frame.validate();
	}
	
	/**
	 * adds all the necessary JComponents
	 */
	private void addComponents() {
		add(new Label("title", Font.BOLD, 12), 0, 20);
		add(new Label("test", 12), 0, 8);
		add(new Label("test", 12), 0, 8);
		add(new Label("test", 12), 0, 8);
		add(new Label("title", Font.BOLD, 12), 30, 20);
		add(new Label("test", 12), 0, 10);
		add(new TextField("label", "text", jpi.getWidth()/2), 0, 8);
	}
	
	/**
	 * TEMP
	 */
	private void add(Component c, int v1, int v2) {
		if (v1 > 0)
			jpi.add(Box.createVerticalStrut(v1));
		jpi.add(c);
		if (v2 > 0)
			jpi.add(Box.createVerticalStrut(v2));
	}
	
}
