package me.catmousedog.fractals.components;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;

public class Label extends Data<Void> {

	private final JLabel lbl;

	public static class LabelBuilder {

		private String text = " ";

		private String tip;

		public LabelBuilder setText(String text) {
			this.text = text;
			return this;
		}

		public LabelBuilder setTip(String tip) {
			this.tip = tip;
			return this;
		}

		public Label build() {
			return new Label(text, tip);
		}

	}

	public Label(String text, String tip) {
		lbl = new JLabel(text);
		lbl.setFont(new Font(null, Font.PLAIN, 12));
		lbl.setToolTipText(tip);
		lbl.setAlignmentX(0);
	}

	@Override
	public Component panel() {
		return lbl;
	}

	@Override
	public void save() {

	}

	@Override
	public void update() {

	}

}
