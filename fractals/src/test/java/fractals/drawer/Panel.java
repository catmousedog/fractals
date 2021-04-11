package fractals.drawer;

import javax.swing.JPanel;

import me.catmousedog.fractals.data.Field;
import me.catmousedog.fractals.data.LinearTransform;
import me.catmousedog.fractals.workers.RenderWorker;

public abstract class Panel extends JPanel {

	protected final RenderWorker renderer = RenderWorker.getInstance();

	protected boolean allowRender = true;
	
	public boolean singleRegion = false;

	protected final LinearTransform transform;

	protected final Field field;

	protected final FractalDrawer fractal;

	public Panel(Field field, FractalDrawer fractal, LinearTransform transform) {
		this.field = field;
		this.fractal = fractal;
		this.transform = transform;
	}

	public void update() {
		if (allowRender) {
			allowRender = false;
			new Thread(this::draw).start();
		}
	}
	
	public abstract void draw();

}
