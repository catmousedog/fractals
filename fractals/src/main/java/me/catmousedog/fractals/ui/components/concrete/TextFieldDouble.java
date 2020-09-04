package me.catmousedog.fractals.ui.components.concrete;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import me.catmousedog.fractals.ui.components.Data;

/**
 * Passive {@link Data} representing a {@link JTextField} that stores a
 * {@link Double}
 */
public class TextFieldDouble extends Data<Double> implements DocumentListener {

	private JTextField jtf;
	private JLabel jl;

	public static class Builder {

		private String lbl, tip;
		private double d;

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

		public TextFieldDouble build() {
			TextFieldDouble tfd = new TextFieldDouble(lbl, tip);
			tfd.setData(d);
			return tfd;
		}

	}

	private TextFieldDouble(String lbl, String tip) {
		jtf = new JTextField();
		jtf.setMaximumSize(new Dimension(Integer.MAX_VALUE, jtf.getPreferredSize().height));
		jtf.getDocument().addDocumentListener(this);
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
			data = Double.parseDouble(jtf.getText());
		} catch (Exception e) {
		}
	}

	@Override
	public void update() {
		jtf.setText(Double.toString(data));
	}

	@Override
	public void insertUpdate(DocumentEvent de) {
		save();
	}

	@Override
	public void removeUpdate(DocumentEvent de) {
		save();
	}

	@Override
	public void changedUpdate(DocumentEvent de) {
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
