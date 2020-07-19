package me.catmousedog.fractals.ui.components.concrete;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;

import me.catmousedog.fractals.ui.components.Data;

/**
 * A passive {@link Data} container with a variable text.
 */
public class Label extends Data<Object> {

	private final JLabel jl;
	private final String text;

	public Label(String text, String tip) {
		this.text = text;
		jl = new JLabel(text);
		jl.setFont(new Font(null, Font.PLAIN, 12));
		jl.setToolTipText(tip);
		jl.setAlignmentX(0);
	}
	
	public Label(String text) {
		this(text, null);
	}

	@Override
	public Component panel() {
		return jl;
	}

	@Override
	public void save() {
	}

	@Override
	public void update() {
		jl.setText(text + data.toString());
	}

}
