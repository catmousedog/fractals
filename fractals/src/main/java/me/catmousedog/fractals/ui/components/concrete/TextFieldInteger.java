package me.catmousedog.fractals.ui.components.concrete;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import me.catmousedog.fractals.ui.components.Data;

/**
 * Passive {@link Data} representing a {@link JTextField} that stores an
 * {@link Integer}
 */
public class TextFieldInteger extends Data<Integer> {

	private final JTextField jtf;
	private final JLabel jl;
	private final int m, M;

	public static class Builder {

		private String lbl, tip;
		private int i, m = Integer.MIN_VALUE, M = Integer.MAX_VALUE;

		public Builder setDefault(int i) {
			this.i = i;
			return this;
		}

		public Builder setLabel(String lbl) {
			this.lbl = lbl;
			return this;
		}

		public Builder setTip(String tip) {
			this.tip = tip;
			return this;
		}

		/**
		 * Sets the minimum of the {@link JTextField}, any value entered below this will
		 * not register. The default is {@link Integer#MIN_VALUE}.
		 */
		public Builder setMin(int m) {
			this.m = m;
			return this;
		}

		/**
		 * Sets the maximum of the {@link JTextField}, any value entered above this will
		 * not register. The default is {@link Integer#MAX_VALUE}.
		 */
		public Builder setMax(int M) {
			this.M = M;
			return this;
		}

		public TextFieldInteger build() {
			TextFieldInteger tfi = new TextFieldInteger(lbl, tip, m, M);
			tfi.setData(i);
			return tfi;
		}

	}

	private TextFieldInteger(String lbl, String tip, int m, int M) {
		this.m = m;
		this.M = M;
		jtf = new JTextField();
		jtf.setMaximumSize(new Dimension(Integer.MAX_VALUE, jtf.getPreferredSize().height));
		jtf.setAlignmentX(JTextField.LEFT_ALIGNMENT);
		jtf.setToolTipText(tip);

		jl = new JLabel(lbl);
		jl.setFont(new Font(null, Font.PLAIN, 12));
		jl.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		jl.setToolTipText(tip);
	}

	@Override
	public Component panel() {
		JPanel jp = new JPanel();
		jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
		jp.setAlignmentX(JPanel.LEFT_ALIGNMENT);

		jp.add(jl);
		jp.add(jtf);

		return jp;
	}

	@Override
	public void save() {
		try {
			int i = Integer.parseInt(jtf.getText());
			if (i <= M && i >= m)
				data = i;
		} catch (Exception e) {
		}
	}

	@Override
	public void update() {
		jtf.setText(Integer.toString(data));
	}

	@Override
	public void preRender() {
		jtf.setEnabled(false);
	}

	@Override
	public void postRender() {
		jtf.setEnabled(true);
	}

}
