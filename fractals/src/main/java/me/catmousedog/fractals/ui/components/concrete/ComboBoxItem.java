package me.catmousedog.fractals.ui.components.concrete;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventObject;

import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import me.catmousedog.fractals.ui.components.ActiveData;
import me.catmousedog.fractals.ui.components.Data;

/**
 * An active {@link Data} container. The {@link ComboBoxItem} can be updated and
 * saved.
 * <p>
 * The {@link Data} of this class is the selected item in the {@link JComboBox},
 * hence the name <code>item</code>.
 */
public class ComboBoxItem extends ActiveData<Object> {

	private final JLabel jl;
	private final JComboBox<Object> jcb;
	private final ActionListener action;

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

		public ComboBoxItem build() {
			return new ComboBoxItem(initial, lbl, tip, e);
		}

	}

	private ComboBoxItem(Object[] initial, String lbl, String tip, ActionListener e) {
		action = e;
		jcb = new JComboBox<Object>(initial);
		jcb.setSelectedItem(null);
		jcb.addActionListener(a -> event(a));
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
		data = jcb.getSelectedItem();
	}

	@Override
	public void update() {
		jcb.setSelectedItem(data);
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

	public JComboBox<Object> getComponent() {
		return jcb;
	}
	
	@Override
	public void preRender() {
	}

	@Override
	public void postRender() {
	}

	@Override
	protected void concreteEvent(EventObject e) {
		action.actionPerformed((ActionEvent) e);
	}

}
