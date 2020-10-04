package me.catmousedog.fractals.ui.components.concrete;

import java.awt.Component;

import javax.swing.JCheckBox;

import me.catmousedog.fractals.ui.components.Data;

public class CheckBox extends Data<Boolean> {

	private JCheckBox jcx;

	public static class Builder {

		private String text, tip;
		private boolean b;

		public Builder(String text) {
			this.text = text;
		}

		public Builder setTip(String tip) {
			this.tip = tip;
			return this;
		}

		public Builder setDefault(boolean b) {
			this.b = b;
			return this;
		}

		public CheckBox build() {
			return new CheckBox(text, tip, b);
		}

	}

	public CheckBox(String text, String tip, boolean b) {
		jcx = new JCheckBox(text);
		jcx.setToolTipText(tip);
		jcx.setAlignmentX(JCheckBox.LEFT_ALIGNMENT);
	}

	@Override
	public void preRender() {
		jcx.setEnabled(false);
	}

	@Override
	public void postRender() {
		jcx.setEnabled(true);
	}

	@Override
	public Component panel() {
		return jcx;
	}

	@Override
	public void save() {
		data = jcx.isSelected();
	}

	@Override
	public void update() {
		jcx.setSelected(data);
	}
}
