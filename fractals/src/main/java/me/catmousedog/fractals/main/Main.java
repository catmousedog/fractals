package me.catmousedog.fractals.main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * 
 */
public class Main {

	private final JFrame frame;
	
	/**
	 * JPanel where the images are rendered
	 */
	private final JPanel canvas;
	
	/**
	 * JPanel interface for the user
	 */
	private final JPanel jpi;
	
	private final GridBagConstraints gbc;
	
	/**
	 * ScrollPane which contains {@link Main#jpi}
	 */
	private final JScrollPane jsp;
	
	private int width=1000, height=1000;
	
	public static void main(String[] args) { new Main(); }
	
	/**
	 * intialises the frame, panel and JComponents 
	 */
	public Main() {
		frame = new JFrame("");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		canvas = new JPanel();
		canvas.setPreferredSize(new Dimension(width, height));
		canvas.setBorder(BorderFactory.createLoweredBevelBorder());
		frame.getContentPane().add(canvas, BorderLayout.CENTER);
		
		jpi = new JPanel();
		jpi.setPreferredSize(new Dimension(200, height));
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.NONE;
//		gbc.
		gbc.anchor = GridBagConstraints.PAGE_START;
		jpi.setLayout(new GridBagLayout());
		jpi.setBorder(BorderFactory.createRaisedBevelBorder());
		
		jsp = new JScrollPane(jpi, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		frame.getContentPane().add(jsp, BorderLayout.LINE_END);
		
		frame.setVisible(true);
		frame.pack();
		
		addComponents();
	}
	
	/**
	 * adds all the necessary JComponents
	 */
	private void addComponents() {
		add(new JLabel("test"), 0, 0);
		add(new JLabel("test"), 1, 0);
		add(new JLabel("test"), 0, 1);
		add(new JLabel("test"), 1, 1);
		add(new JLabel("test"), 0, 2);
		add(new JLabel("test"), 1, 2);
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.gridy = 3;
		jpi.add(new JLabel("ttt"), gbc);
	}
	
	/**
	 * adds the given {@link JComponent} to the {@link Main#jpi}
	 * @param c Component to add
	 * @param x grid
	 * @param y grid
	 */
	private void add(JComponent c, int x, int y) {
		gbc.gridx = x;
		gbc.gridy = y;
		jpi.add(c, gbc);
	}
	
	private class TestAction extends AbstractAction {

		public TestAction() {
			super("test");
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println(AbstractAction.NAME);
		}
		
	}
	
}
