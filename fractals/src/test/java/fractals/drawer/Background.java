package fractals.drawer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Background {

	private BufferedImage img;
	
	public int w, h;

	public Background() {
		try {
			File f = new File("C:\\Users\\Gebruiker\\Desktop\\CPP\\images\\name.png");
			if (!f.exists())
				return;
			BufferedImage t = ImageIO.read(f);
			w = t.getWidth();
			h = t.getHeight();
			img = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);

			for (int x = 0; x < w; x++) {
				for (int y = 0; y < h; y++) {
					int c = t.getRGB(x, y);
					if (c == 0xffffffff)
						img.setRGB(x, y, 0x000000ff);
					else
						img.setRGB(x, y, 0x80ff0000);
				}
			}

		} catch (IOException e) {
			return;
		}
	}

	public BufferedImage getImg() {
		return img;
	}

}
