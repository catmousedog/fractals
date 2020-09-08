package me.catmousedog.fractals.canvas;

import java.awt.image.BufferedImage;

import org.jetbrains.annotations.NotNull;

public class Field {

	private BufferedImage img;

	private Pixel[] pixels;

	public Field(int width, int height) {
		setSize(width, height);
	}

	public void setSize(int width, int height) {
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		// set field size
		pixels = new Pixel[width * height];
		int i = 0;
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				pixels[i] = new Pixel(x, y);
				i++;
			}
		}
	}

	/**
	 * Finds the <code>Pixel</code> with the same <code>x</code> and <code>y</code>
	 * coordinates.<br>
	 * This is done by comparing the hash code of each <code>Pixel</code> with the
	 * <code>Pixel</code> at <code>x:y</code>.
	 * <p>
	 * The comparing of hash codes is effective up to an <code>x</code> and
	 * <code>y</code> of 2^16 or 65536.
	 * 
	 * @param x the actual <code>x</code> coordinate
	 * @param y the actual <code>y</code> coordinate
	 * @return the <code>Pixel</code> with the same <code>x</code> and
	 *         <code>y</code> coordinates.
	 */
	public Pixel getPixel(int x, int y) {
		int h = new Pixel(x, y).hashCode();
		for (Pixel p : pixels) {
			if (p.hashCode() == h)
				return p;
		}
		return null;
	}

	@NotNull
	public BufferedImage getImg() {
		return img;
	}

	@NotNull
	public Pixel[] getPixels() {
		return pixels;
	}

}
