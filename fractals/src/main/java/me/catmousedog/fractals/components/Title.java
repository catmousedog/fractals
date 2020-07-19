package me.catmousedog.fractals.components;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;

/**
 * An immutable JLabel
 */
public class Title extends Item {

	private final JLabel jl;

	public Title(String text) {
		jl = new JLabel(text);
		jl.setFont(new Font(null, Font.BOLD, 15));
		jl.setAlignmentX(0);
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
	}

}
