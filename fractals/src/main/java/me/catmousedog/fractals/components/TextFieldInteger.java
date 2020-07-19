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
 * Passive {@link Data} representing a {@link JTextField} that stores an
 * {@link Integer}
 */
public class TextFieldInteger extends Data<Integer> implements DocumentListener {

	private final JTextField jtf;
	private final JLabel jl;
	
	public static class Builder {

		private String lbl, tip;
		private int i;

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

		public TextFieldInteger build() {
			return new TextFieldInteger(i, lbl, tip);
		}

	}

	private TextFieldInteger(int i, String lbl, String tip) {
		jtf = new JTextField(Integer.toString(i));
		jtf.setMaximumSize(new Dimension(Integer.MAX_VALUE, jtf.getPreferredSize().height));
		jtf.getDocument().addDocumentListener(this);
		jtf.setAlignmentX(JTextField.LEFT_ALIGNMENT);
		jtf.setToolTipText(tip);

		jl = new JLabel(lbl);
		jl.setFont(new Font(null, Font.PLAIN, 12));
		jl.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		jl.setToolTipText(tip);
		
		data = i;
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
			data = Integer.parseInt(jtf.getText());
		} catch (Exception e) {
		}
	}

	@Override
	public void update() {
		jtf.setText(Integer.toString(data));
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
