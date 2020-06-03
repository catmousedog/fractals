package me.catmousedog.fractals.main;

import java.awt.image.BufferedImage;

import org.jetbrains.annotations.NotNull;

public class Fractal {
	
	private final int width, height;
	
	private final int type = BufferedImage.TYPE_INT_RGB;
	
	private final boolean parallel;
	
	public Fractal(int w, int h, boolean parallel) {
		width = w;
		height = h;
		this.parallel = parallel;
	}
	
	@NotNull
	public BufferedImage image(int iterations) {
		BufferedImage img = new BufferedImage(width, height, type);
		
		
		
		return img;
	}
	
}
