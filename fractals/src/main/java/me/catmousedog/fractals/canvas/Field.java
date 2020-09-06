package me.catmousedog.fractals.canvas;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.fractals.Pixel;

public class Field {
	
	private BufferedImage img;
	
	private List<Pixel> field;
	
	public Field(int width, int height) {
		setSize(width, height);
	}
	
	public void setSize(int width, int height) {
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		// set field size
		field = new ArrayList<Pixel>(width * height);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				field.add(new Pixel(x, y));
			}
		}
	}
	
	@NotNull
	public BufferedImage getImg() {
		return img;
	}
	
	@NotNull
	public List<Pixel> getPixels() {
		return field;
	}
	
}
