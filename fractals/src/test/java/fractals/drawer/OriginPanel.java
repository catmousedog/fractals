package fractals.drawer;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.stream.Stream;

import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;

import me.catmousedog.fractals.data.Field;
import me.catmousedog.fractals.paneloperators.filters.Filter;
import me.catmousedog.fractals.paneloperators.fractals.Fractal;
import me.catmousedog.fractals.paneloperators.functions.Function;

public class OriginPanel extends Panel {

	public OriginPanel(FractalDrawer fractal, Background background) {
		super(new Field(background.w, background.h), fractal, fractal.getTransform());
		
		setPreferredSize(new Dimension(background.w, background.h));
		setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (field != null)
			g.drawImage(field.getImg(), 0, 0, null);

		if (fractal.origin != null)
			fractal.origin.draw(g, fractal, transform, 0, 0);
	}

	public void draw() {
		Fractal fr = fractal.clone();
		fr.getTransform().set(transform); // change transform of clone
		Function fu = fr.getFunction();
		Filter fi = fu.getFilter();

		BufferedImage img = field.getImg();
		
		fractal.mode = FractalDrawer.ORIGIN;
		
		Stream.of(field.getPixels()).parallel().forEach(p -> {
			double[] t = transform.apply(p.x, p.y);
			img.setRGB(p.x, p.y, fi.apply(fu.apply(fractal.get(t[0], t[1]))));
		});

		allowRender = true;
		repaint();
	}
	
}
