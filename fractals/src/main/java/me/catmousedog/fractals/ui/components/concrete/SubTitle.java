package me.catmousedog.fractals.ui.components.concrete;

import java.awt.Component;
import java.awt.Font;

import javax.swing.JLabel;

import me.catmousedog.fractals.ui.components.Item;

public class SubTitle extends Item {

	private final JLabel jl;

	public SubTitle(String text) {
		jl = new JLabel(text);
		jl.setFont(new Font(null, Font.BOLD, 13));
		jl.setAlignmentX(0);
	}

	public SubTitle(String text, String tip) {
		this(text);
		jl.setToolTipText(tip);
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
