package me.catmousedog.fractals.canvas;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.fractals.LinearTransform;
import me.catmousedog.fractals.fractals.filters.Filter;
import me.catmousedog.fractals.fractals.functions.Function;
import me.catmousedog.fractals.main.Main.InitialSize;
import me.catmousedog.fractals.ui.GUI;
import me.catmousedog.fractals.ui.JPInterface;
import me.catmousedog.fractals.workers.Generator;
import me.catmousedog.fractals.workers.Painter;
import me.catmousedog.fractals.workers.RenderWorker;

/**
 * Represents a 2D plane of processed values by the function
 * {@link Canvas#fractal}
 */
@SuppressWarnings("serial")
public class Canvas extends JPanel {

	private final RenderWorker renderer = RenderWorker.getInstance();

	/**
	 * the mouse listener
	 */
	private final Mouse mouse;

	/**
	 * The effectively final instance of the {@link JPInterface}.
	 */
	private JPInterface jpi;

	/**
	 * The current {@link Fractal} of the {@link Canvas}.
	 */
	private Fractal fractal;

	/**
	 * The previous {@link Configuration} of the {@link Canvas}. This is used in the
	 * {@link Canvas#savePrevConfig()} and {@link Canvas#undo()} methods.
	 */
	private Configuration prevConfig;

	/**
	 * The zoomFactor to use when zooming in or out.
	 */
	private double zoomFactor = 2;

	/**
	 * The <code>Field</code> of <code>Pixels</code>, used to represent the complex
	 * plane.
	 */
	private final Field field;

	/**
	 * the current known width and height<br>
	 * this might be different from the actual JPanel width and height
	 */
	private int width, height;

	/**
	 * creates the Canvas
	 * 
	 * @param width   initial width
	 * @param height  initial height
	 * @param fractal Fractal containing the fractal function
	 * @param jpi     the user interface, used for saving and updating
	 * @param logger  the logger instance
	 */
	public Canvas(InitialSize size, Fractal fractal) {
		this.fractal = fractal;
		addMouseMotionListener(fractal.getMouse());
		field = new Field(size.getWidth(), size.getHeight());

		mouse = new Mouse(this);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		setBorder(BorderFactory.createLoweredBevelBorder());

		setPanelSize(size.getWidth(), size.getHeight());
		savePrevConfig();
	}

	/**
	 * Displays the current {@link Canvas#img}
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(field.getImg(), 0, 0, null);
		if (allowPainter) {
			allowPainter = false;
			renderer.runScheduledPainter();
		}
		if (allowRender) {
			allowRender = false;
			renderer.runScheduledGenerator();
			renderer.runScheduledPainter();
		}
	}

	/**
	 * Generates and paints the image using the <code>generator</code> and
	 * <code>painter</code>.
	 * <p>
	 * This can be called safely whilst the <code>generator</code> is still
	 * generating. This method will check if {@link Generator#isGenerated()} returns
	 * true before starting a new <code>Generator</code>.
	 */
	public void render() {
		renderer.newRender(field, fractal, () -> {
			allowRender = true;
			repaint();
			if (!renderer.isGeneratorScheduled() && !renderer.isPainterScheduled())
				jpi.postRender();
		});
	}

	private boolean allowRender = false;

	private boolean allowPainter = false;

	/**
	 * Colours the current <code>fractal</code> and paints it using the
	 * {@link Painter}.
	 * <p>
	 * This can be called safely whilst the {@link Painter} is still colouring. This
	 * method will check if {@link Painter#isRepainted()} returns true before
	 * starting a new {@link Painter}.
	 * 
	 * @return true if a new {@link Painter} was successfully executed.
	 */
	public void paint() {
		renderer.newPainter(field, fractal.getFunction().getFilter().clone(), () -> {
			allowPainter = true;
			repaint();
			jpi.postRender();
		});
	}

	/**
	 * Sets the size of the canvas and all of its components reliant on that
	 * size.<br>
	 * Components reliant on the size of the {@link Canvas} are:
	 * <ul>
	 * <li>{@link Canvas#field}
	 * <li>The {@link LinearTransform} of the {@link Fractal}. (the origin)
	 * <li>The {@link JPInterface} extended by the {@link Canvas}.
	 * </ul>
	 * <p>
	 * If the <code>w</code> and <code>h</code> are the same as {@link Canvas#width}
	 * and {@link Canvas#height} this method will return.
	 * 
	 * @param w new width
	 * @param h new height
	 */
	public void setPanelSize(int w, int h) {
		// do nothing if unchanged
		if (width == w && height == h)
			return;

		width = w;
		height = h;

		field.setSize(width, height);

		// set config.getTransform()
		fractal.getTransform().setOrigin(width / 2, height / 2);

		// set panel size
		setPreferredSize(new Dimension(width, height));
	}

	/**
	 * Essentially the opposite of {@link Canvas#undo()}. This should be called
	 * right before saving changes to the {@link Canvas}.
	 * <p>
	 * Creates a new {@link Configuration} by cloning everything except for the
	 * {@link Fractal} which is kept as a reference.
	 */
	public void savePrevConfig() {
		prevConfig = new Configuration(fractal, zoomFactor);
	}

	/**
	 * Changes the {@link Canvas} to the previous {@link Configuration} through
	 * {@link Canvas#prevConfig}, reverting any changes saved to it after the last
	 * time {@link Canvas#savePrevConfig()} was called.
	 */
	public void undo() {
		Fractal fractal = prevConfig.fractal;
		fractal.pickFunction(prevConfig.function.getClass());
		fractal.getFunction().pickFilter(prevConfig.filter.getClass());

		fractal.getTransform().set(prevConfig.transform);
		fractal.setIterations(prevConfig.iterations);
		zoomFactor = prevConfig.zoomFactor;
		setFractal(fractal);
	}

	/**
	 * sets the instance of the {@link JPInterface} so the Canvas can save and
	 * update the user input
	 * 
	 * @param jpi the instance of {@link JPInterface}
	 */
	public void setJPI(@NotNull JPInterface jpi) {
		this.jpi = jpi;
		mouse.setJPI(jpi);
		GUI gui = jpi.getGUI();
		addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent e) {
				gui.getWidthjtf().setData(getWidth());
				gui.getHeightjtf().setData(getHeight());
			}

			@Override
			public void componentShown(ComponentEvent e) {
			}

			@Override
			public void componentMoved(ComponentEvent e) {
			}

			@Override
			public void componentHidden(ComponentEvent e) {
			}
		});
	}

	/**
	 * Changes the {@link Fractal} of the {@link Canvas}.
	 * 
	 * @param fractal
	 */
	public void setFractal(@NotNull Fractal fractal) {
		super.removeMouseMotionListener(this.fractal.getMouse());
		super.addMouseMotionListener(fractal.getMouse());
		this.fractal = fractal;
		fractal.getTransform().setOrigin(getWidth() / 2, getHeight() / 2);
		jpi.updateFractal();
	}

	/**
	 * @return The {@link Fractal} currently being used by the {@link Canvas}.
	 */
	@NotNull
	public Fractal getFractal() {
		return fractal;
	}

	/**
	 * @return the {@link Canvas#mouse} <code>MouseListener</code> and
	 *         <code>MouseMotionListener</code>.
	 */
	public Mouse getMouse() {
		return mouse;
	}

	/**
	 * Changes the {@link Canvas#zoomFactor}.
	 * 
	 * @param zoomFactor
	 */
	public void setZoomFactor(double zoomFactor) {
		this.zoomFactor = zoomFactor;
	}

	/**
	 * @return The {@link Canvas#zoomFactor}.
	 */
	public double getZoomFactor() {
		return zoomFactor;
	}

	/**
	 * @return the {@link Field}.
	 */
	public Field getField() {
		return field;
	}

	public int getPanelWidth() {
		return width;
	}

	public int getPanelHeight() {
		return height;
	}

	/**
	 * A class to store the configuration of the canvas, such as the position and
	 * <code>Function</code> used.
	 */
	private class Configuration {

		/**
		 * A reference to the <code>Fractal</code> of this <code>Configuration</code>.
		 * The fields inside this fractal don't matter for this class since the values
		 * of those are stored in this class.
		 */
		@NotNull
		private final Fractal fractal;

		/**
		 * A clone of the current <code>Function</code>.
		 */
		@NotNull
		private final Function function;

		/**
		 * A clone of the current <code>Filter</code>.
		 */
		@NotNull
		private final Filter filter;

		/**
		 * A clone of the current {@link LinearTransform}.
		 */
		private final LinearTransform transform;

		private final int iterations;

		private final double zoomFactor;

		public Configuration(Fractal fractal, double zoomFactor) {
			this.fractal = fractal;
			function = fractal.getFunction().clone();
			filter = fractal.getFunction().getFilter().clone();
			transform = fractal.getTransform().clone();
			iterations = fractal.getIterations();
			this.zoomFactor = zoomFactor;
		}

//		public Fractal getFractal() {
//			return fractal;
//		}
//
//		public Function getFunction() {
//			return function;
//		}
//
//		public Filter getFilter() {
//			return filter;
//		}
//
//		public LinearTransform getTransform() {
//			return transform;
//		}
//
//		public int getIterations() {
//			return iterations;
//		}
//
//		public double getZoomFactor() {
//			return zoomFactor;
//		}
	}

}
