package me.catmousedog.fractals.components;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ComponentFactory {

	private final JPanel jpi;

	public ComponentFactory(JPanel jpi) {
		this.jpi = jpi;
	}
	
	/**
	 * creates a label
	 * 
	 * @param text the label
	 * @param tip  tooltip if you hover over the label
	 * @return
	 */
	public JLabel label(@NotNull String text, @Nullable String tip) {
		JLabel lbl = new JLabel(text);
		lbl.setFont(new Font(null, Font.PLAIN, 12));
		lbl.setToolTipText(tip);
		return lbl;
	}

	/**
	 * creates a label
	 * 
	 * @param text the label
	 * @return
	 */
	public JLabel label(@NotNull String text) {
		JLabel lbl = new JLabel(text);
		lbl.setFont(new Font(null, Font.PLAIN, 12));
		return lbl;
	}

	/**
	 * creates a title
	 * 
	 * @param text the title
	 * @param tip  tooltip if you hover over the title
	 * @return
	 */
	public JLabel title(@NotNull String text, @Nullable String tip) {
		JLabel lbl = new JLabel(text);
		lbl.setFont(new Font(null, Font.BOLD, 15));
		lbl.setToolTipText(tip);
		return lbl;
	}

	/**
	 * creates a title
	 * 
	 * @param text the title
	 * @return
	 */
	public JLabel title(@NotNull String text) {
		JLabel lbl = new JLabel(text);
		lbl.setFont(new Font(null, Font.BOLD, 15));
		return lbl;
	}

	/**
	 * creates a horizontal space
	 * 
	 * @param w width in pixels
	 * @return
	 */
	public Component space(int w) {
		return Box.createRigidArea(new Dimension(w, 0));
	}

	/**
	 * creates horizontal glue
	 * 
	 * @return
	 */
	public Component glue() {
		return Box.createHorizontalGlue();
	}

	/**
	 * creates a vertical rigid area that acts as padding between components
	 * 
	 * @param h height in pixels
	 * @return
	 */
	public Component padding(int h) {
		return Box.createRigidArea(new Dimension(0, h));
	}
	
	/**
	 * returns {@link JTextField} with added tooltip
	 * 
	 * @param jtf
	 * @param tip the tooltip to be added to <b>jtf</b>
	 * @return
	 */
	public JTextField textField(@NotNull JTextField jtf, @Nullable String tip) {
		jtf.setToolTipText(tip);
		return jtf;
	}

	/**
	 * this method is just for clean code, it just returns the argument
	 * 
	 * @param jtf
	 * @return jtf
	 */
	public JTextField textField(@NotNull JTextField jtf) {
		return jtf;
	}

	public JButton button(@NotNull JButton jb, @Nullable ActionListener e, @Nullable String tip) {
		jb.addActionListener(e);
		jb.setToolTipText(tip);
		return jb;
	}

	public Component button(@NotNull JButton jb, @Nullable ActionListener e) {
		jb.addActionListener(e);
		return jb;
	}

	/**
	 * creates a JPanel that can be added to the user interface. The given
	 * Components will distribute the horizontal space equally.
	 * <p>
	 * This method should always be used when adding components to the user
	 * interface as it resizes them correctly.
	 * 
	 * @param components that need to be added to the returned JPanel
	 * @return the JPanel with the resized components
	 */
	public JPanel create(Component... components) {
		JPanel jp = new JPanel();
		jp.setLayout(new BoxLayout(jp, BoxLayout.X_AXIS));

		int w = jpi.getWidth() / components.length;
		for (Component c : components) {
			c.setMaximumSize(new Dimension(w, c.getPreferredSize().height));
			jp.add(c);
		}

		return jp;
	}

}
