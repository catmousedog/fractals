package me.catmousedog.fractals.ui.components.concrete;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import me.catmousedog.fractals.ui.components.Data;

/**
 * Active {@link Data} storing a {@link Boolean}. <br>
 * The {@link Data#data} is the {@link JButton#isEnabled()}.
 */
public class Button extends Data<Boolean> {

	private JButton jb;
	private JLabel jl;

	public static class Builder {

		private String text, lbl, tip;
		private boolean b = true;
		private ActionListener e;

		public Builder(String text) {
			this.text = text;
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

		public Builder setDefault(Boolean b) {
			this.b = b;
			return this;
		}
		
		public Button build() {
			Button button = new Button(text, lbl, tip, e);
			button.setData(b);
			return button;
		}

	}

	public Button(String text, String lbl, String tip, ActionListener e) {
		jb = new JButton(text);
		jb.setMaximumSize(new Dimension(Integer.MAX_VALUE, jb.getPreferredSize().height));
		jb.addActionListener(e);
		jb.setAlignmentX(JButton.LEFT_ALIGNMENT);
		jb.setToolTipText(tip);

		jl = new JLabel(lbl);
		jl.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		jl.setFont(new Font(null, Font.PLAIN, 12));
		jl.setToolTipText(tip);
	}

	@Override
	public Component panel() {
		JPanel jp = new JPanel();
		jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
		jp.setAlignmentX(JPanel.LEFT_ALIGNMENT);

		jp.add(jl);
		jp.add(jb);

		return jp;
	}

	@Override
	public void save() {
		data = jb.isEnabled();
	}

	@Override
	public void update() {
		jb.setEnabled(data);
	}
}
