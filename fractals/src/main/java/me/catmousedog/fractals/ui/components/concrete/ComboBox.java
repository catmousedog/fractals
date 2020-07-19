package me.catmousedog.fractals.ui.components.concrete;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import me.catmousedog.fractals.ui.components.Data;

/**
 * An active {@link Data} container. The {@link ComboBox} can be updated using
 * {@link Data#setData(Object[])} but not saved as there is not way for the user
 * to enter anything.
 */
public class ComboBox extends Data<Object[]> {

	private final JLabel jl;
	private final JComboBox<Object> jcb;

	public static class Builder {

		private Object[] initial;
		private String lbl, tip;
		private ActionListener e;

		public Builder(Object[] initial) {
			this.initial = initial;
		}

		public Builder setLabel(String lbl) {
			this.lbl = lbl;
			return this;
		}

		public Builder setTip(String tip) {
			this.tip = tip;
			return this;
		}

		public Builder setAction(ActionListener e) {
			this.e = e;
			return this;
		}

		public ComboBox build() {
			return new ComboBox(initial, lbl, tip, e);
		}

	}

	private ComboBox(Object[] initial, String lbl, String tip, ActionListener e) {
		jcb = new JComboBox<Object>(initial);
		jcb.addActionListener(e);
		jcb.setMaximumSize(new Dimension(Integer.MAX_VALUE, jcb.getPreferredSize().height));
		jcb.setToolTipText(tip);
		jcb.setAlignmentX(JComboBox.LEFT_ALIGNMENT);

		jl = new JLabel(lbl);
		jl.setFont(new Font(null, Font.PLAIN, 12));
		jl.setToolTipText(tip);
		jl.setAlignmentX(JLabel.LEFT_ALIGNMENT);
	}

	@Override
	public void save() {
	}

	@Override
	public void update() {
		jcb.removeAllItems();
		for (Object o : data)
			jcb.addItem(o);
	}

	@Override
	public Component panel() {
		JPanel jp = new JPanel();
		jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
		jp.setAlignmentX(JPanel.LEFT_ALIGNMENT);
		
		jp.add(jl);
		jp.add(jcb);

		return jp;
	}

}
