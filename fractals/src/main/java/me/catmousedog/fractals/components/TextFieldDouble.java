package me.catmousedog.fractals.components;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Passive {@link Data} representing a textfield that stores a {@link Double}
 */
public class TextFieldDouble extends Data<Double> implements DocumentListener {

	private JTextField jtf;
	private JLabel jl;

	public static class TextFieldBuilder {

		private String text, lbl, tip;

		public TextFieldBuilder setText(String text) {
			this.text = text;
			return this;
		}

		public TextFieldBuilder setLabel(String lbl) {
			this.lbl = lbl;
			return this;
		}

		public TextFieldBuilder setTip(String tip) {
			this.tip = tip;
			return this;
		}

		public TextFieldDouble build() {
			return new TextFieldDouble(text, lbl, tip);
		}

	}

	private TextFieldDouble(String text, String lbl, String tip) {
		jtf = new JTextField(text);
		jtf.setMaximumSize(new Dimension(Integer.MAX_VALUE, jtf.getPreferredSize().height));
		jtf.getDocument().addDocumentListener(this);
		jtf.setAlignmentX(0);
		jtf.setToolTipText(tip);
		
		jl = new JLabel(lbl);
		jl.setFont(new Font(null, Font.PLAIN, 12));
		jl.setAlignmentX(0);
		jl.setToolTipText(tip);
	}

	@Override
	public Component panel() {
		JPanel jp = new JPanel();
		jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));

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
//		save();
	}

}
