package me.catmousedog.fractals.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ComponentFactory {

	/**
	 * creates a label with a tooltip
	 */
	public static JLabel label(@NotNull String text, @Nullable String tip) {
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
	public static JLabel label(@NotNull String text) {
		JLabel lbl = new JLabel(text);
		lbl.setFont(new Font(null, Font.PLAIN, 12));
		return lbl;
	}

	/**
	 * creates a title with a tooltip
	 */
	public static JLabel title(@NotNull String text, @Nullable String tip) {
		JLabel lbl = new JLabel(text);
		lbl.setFont(new Font(null, Font.BOLD, 15));
		lbl.setToolTipText(tip);
		return lbl;
	}

	/**
	 * creates a title
	 */
	public static JLabel title(@NotNull String text) {
		JLabel lbl = new JLabel(text);
		lbl.setFont(new Font(null, Font.BOLD, 15));
		return lbl;
	}

	/**
	 * creates a vertical rigid area that acts as padding between components
	 */
	public static Component padding(int h) {
		return Box.createRigidArea(new Dimension(0, h));
	}

	/**
	 * creates a JPanel with a textfield inside
	 */
	public static JPanel textField(@NotNull JTextField jtf) {
		JPanel jp = new JPanel();
		jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));

		jtf.setMaximumSize(new Dimension(Integer.MAX_VALUE, jtf.getPreferredSize().height));
		jtf.setAlignmentX(0);

		jp.add(jtf);

		return jp;
	}

	/**
	 * creates a JPanel with a textfield and text inside
	 */
	public static JPanel textField(@NotNull JTextField jtf, @Nullable String text) {
		JPanel jp = new JPanel();
		jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));

		JLabel lbl = new JLabel(text);
		lbl.setFont(new Font(null, Font.PLAIN, 12));
		lbl.setAlignmentX(0);
		jp.add(lbl);

		jtf.setMaximumSize(new Dimension(Integer.MAX_VALUE, jtf.getPreferredSize().height));
		jtf.setAlignmentX(0);
		jp.add(jtf);

		return jp;
	}

	/**
	 * creates a JPanel with a textfield and text with both a tooltip inside
	 */
	public static JPanel textField(@NotNull JTextField jtf, @Nullable String text, @Nullable String tip) {
		JPanel jp = new JPanel();
		jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));

		JLabel lbl = new JLabel(text);
		lbl.setFont(new Font(null, Font.PLAIN, 12));
		lbl.setToolTipText(tip);
		lbl.setAlignmentX(0);
		jp.add(lbl);

		jtf.setMaximumSize(new Dimension(Integer.MAX_VALUE, jtf.getPreferredSize().height));
		jtf.setToolTipText(tip);
		jtf.setAlignmentX(0);
		jp.add(jtf);

		return jp;
	}

	/**
	 * creates a JPanel with a button inside
	 */
	public static JPanel button(@NotNull JButton jb) {
		JPanel jp = new JPanel();
		jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));

		jb.setMaximumSize(new Dimension(Integer.MAX_VALUE, jb.getPreferredSize().height));
		jb.setAlignmentX(0);
		jp.add(jb);

		return jp;
	}

	/**
	 * creates a JPanel with a button with a tip inside
	 */
	public static JPanel button(@NotNull JButton jb, @Nullable String tip) {
		JPanel jp = new JPanel();
		jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));

		jb.setMaximumSize(new Dimension(Integer.MAX_VALUE, jb.getPreferredSize().height));
		jb.setToolTipText(tip);
		jb.setAlignmentX(0);
		jp.add(jb);

		return jp;
	}

	/**
	 * creates a JPanel with a button and text with both a tooltip inside
	 */
	public static JPanel button(@NotNull JButton jb, @Nullable String text, @Nullable String tip) {
		JPanel jp = new JPanel();
		jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));

		JLabel lbl = new JLabel(text);
		lbl.setFont(new Font(null, Font.PLAIN, 12));
		lbl.setToolTipText(tip);
		lbl.setAlignmentX(0);
		jp.add(lbl);

		jb.setMaximumSize(new Dimension(Integer.MAX_VALUE, jb.getPreferredSize().height));
		jb.setToolTipText(tip);
		jb.setAlignmentX(0);
		jp.add(jb);

		return jp;
	}

	/**
	 * creates a JPanel with a JComboBox inside it
	 */
	public static JPanel dropDown(@NotNull JComboBox<String> jcb) {
		JPanel jp = new JPanel();
		jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));

		jcb.setMaximumSize(new Dimension(Integer.MAX_VALUE, jcb.getPreferredSize().height));
		jcb.setAlignmentX(0);
		jp.add(jcb);

		return jp;
	}
	
	/**
	 * creates a JPanel with a JComboBox and a JLabel inside it
	 */
	public static JPanel dropDown(@NotNull JComboBox<String> jcb, @Nullable String text) {
		JPanel jp = new JPanel();
		jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));

		JLabel lbl = new JLabel(text);
		lbl.setFont(new Font(null, Font.PLAIN, 12));
		lbl.setAlignmentX(0);
		jp.add(lbl);
		
		jcb.setMaximumSize(new Dimension(Integer.MAX_VALUE, jcb.getPreferredSize().height));
		jcb.setAlignmentX(0);
		jp.add(jcb);

		return jp;
	}
	
	/**
	 * creates a JPanel with a JComboBox and a JLabel inside it
	 */
	public static <T> JPanel dropDown(@NotNull JComboBox<T> jcb, @Nullable String text, @Nullable String tip) {
		JPanel jp = new JPanel();
		jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));

		JLabel lbl = new JLabel(text);
		lbl.setFont(new Font(null, Font.PLAIN, 12));
		lbl.setToolTipText(tip);
		lbl.setAlignmentX(0);
		jp.add(lbl);
		
		jcb.setMaximumSize(new Dimension(Integer.MAX_VALUE, jcb.getPreferredSize().height));
		jcb.setToolTipText(tip);
		jcb.setAlignmentX(0);
		jp.add(jcb);

		return jp;
	}

}
