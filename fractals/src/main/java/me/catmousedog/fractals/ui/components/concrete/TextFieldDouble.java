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
 * Passive {@link Data} representing a {@link JTextField} that stores a
 * {@link Double}
 */
public class TextFieldDouble extends Data<Double> {

	private JTextField jtf;
	private JLabel jl;
	private final double m, M;

	public static class Builder {

		private String lbl, tip;
		private double d, m = Double.NEGATIVE_INFINITY, M = Double.POSITIVE_INFINITY;

		public Builder setDefault(double d) {
			this.d = d;
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
		 * not register. The default is -infinity.
		 */
		public Builder setMin(double m) {
			this.m = m;
			return this;
		}

		/**
		 * Sets the maximum of the {@link JTextField}, any value entered above this will
		 * not register. The default is +infinity.
		 */
		public Builder setMax(double M) {
			this.M = M;
			return this;
		}

		public TextFieldDouble build() {
			TextFieldDouble tfd = new TextFieldDouble(lbl, tip, m, M);
			tfd.setData(d);
			return tfd;
		}

	}

	private TextFieldDouble(String lbl, String tip, double m, double M) {
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
			double d = Double.parseDouble(jtf.getText());
			if (d > M)
				d = M;
			if (d < m)
				d = m;
			data = d;
		} catch (Exception e) {
		}
	}

	@Override
	public void update() {
		jtf.setText(Double.toString(data));
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
