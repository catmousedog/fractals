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
 * An active {@link Data} container. The {@link ComboBoxList} can be updated,
 * but not saved as there is no way of the user to change the {@link JComboBox}
 * selection.
 * <p>
 * The {@link Data} of this class is the list of {@link Object}s inside the
 * {@link JComboBox}, hence the name <code>List</code>.
 */
public class ComboBoxList extends ActiveData<Object[]> {

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

		public ComboBoxList build() {
			return new ComboBoxList(initial, lbl, tip, e);
		}

	}

	private ComboBoxList(Object[] initial, String lbl, String tip, ActionListener e) {
		this.action = e;
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

	@Override
	public void preRender() {
		jcb.setEnabled(false);
	}

	@Override
	public void postRender() {
		jcb.setEnabled(true);
	}

	@Override
	protected void concreteEvent(EventObject e) {
		action.actionPerformed((ActionEvent) e);
	}
}
