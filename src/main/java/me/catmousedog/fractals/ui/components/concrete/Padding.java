package me.catmousedog.fractals.ui.components.concrete;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;

import me.catmousedog.fractals.ui.components.Item;

public class Padding extends Item {

	private int h;
	
	public Padding(int h) {
		this.h = h;
	}
	
	@Override
	public Component panel() {
		return Box.createRigidArea(new Dimension(0, h));
	}

	@Override
	public void save() {
	}

	@Override
	public void update() {
	}

	@Override
	public void preRender() {
	}

	@Override
	public void postRender() {
	}

}
