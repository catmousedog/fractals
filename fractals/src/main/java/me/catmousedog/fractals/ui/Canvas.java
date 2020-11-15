package me.catmousedog.fractals.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.data.Field;
import me.catmousedog.fractals.data.LinearTransform;
import me.catmousedog.fractals.main.Settings;
import me.catmousedog.fractals.paneloperators.filters.Filter;
import me.catmousedog.fractals.paneloperators.fractals.Fractal;
import me.catmousedog.fractals.paneloperators.functions.Function;
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

	private final Settings settings = Settings.getInstance();

	private final Logger logger = Logger.getLogger("fractals");

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
	public Canvas(Fractal fractal) {
		logger.log(Level.FINER, "Canvas init");

		this.fractal = fractal;
		addMouseMotionListener(fractal.getMouse());
		field = new Field(settings.getWidth(), settings.getHeight());

		mouse = new Mouse(this);
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		setBorder(BorderFactory.createLoweredBevelBorder());

		setPanelSize(settings.getWidth(), settings.getHeight());
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
		logger.log(Level.FINEST, "canvas.render");

		renderer.newRender(field, fractal.clone(), () -> {
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
		logger.log(Level.FINEST, "canvas.paint");

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
	 * <li>The {@link JPanel} extended by the {@link Canvas}.
	 * </ul>
	 * <p>
	 * If the <code>w</code> and <code>h</code> are the same as {@link Canvas#width}
	 * and {@link Canvas#height} this method will not do anything.
	 * 
	 * @param w new width
	 * @param h new height
	 */
	public void setPanelSize(int w, int h) {
		// do nothing if unchanged
		if (width == w && height == h)
			return;

		logger.log(Level.FINEST, "canvas.setPanelSize");

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
	 * Should only be called through {@link JPInterface#savePrevConfig()} when the
	 * current <code>Fractal</code>, <code>Function</code> or <code>Filter</code>
	 * changes or if the current <code>Fractal</code> is altered such as its
	 * {@link LinearTransform}.
	 */
	void savePrevConfig() {
		logger.log(Level.FINEST, "canvas.savePrevConfig");

		prevConfig = new Configuration();
	}

	/**
	 * Changes the {@link Canvas} to the previous {@link Configuration} through
	 * {@link Canvas#prevConfig}, reverting any changes saved to it after the last
	 * time {@link Canvas#savePrevConfig()} was called.
	 */
	public void undo() {
		logger.log(Level.FINEST, "canvas.undo");

		// get original reference
		Fractal f = pickFractal(prevConfig.fractal.getClass());
		if (f != null) {
			f.getTransform().set(prevConfig.fractal.getTransform());
			f.setIterations(prevConfig.fractal.getIterations());
			// set function
			f.pickFunction(prevConfig.fractal.getFunction().getClass());
			// set filter
			f.getFunction().pickFilter(prevConfig.fractal.getFunction().getFilter().getClass());

			zoomFactor = prevConfig.zoomFactor;

			setFractal(f);
		} else {
			logger.log(Level.WARNING, "canvas.undo no original fractal found of " + prevConfig.fractal.fileName());
		}
	}

	/**
	 * Returns the {@link Canvas#fractal} to the <code>Fractal</code> whose class
	 * equals the given <code>clazz</code>.
	 * <p>
	 * This does not call the {@link Canvas#setFractal(Fractal)}.
	 * 
	 * @param clazz the <code>Class</code> of the <code>Fractal</code>.
	 * 
	 * @return the original reference to the fractal with the same class as
	 *         <code>clazz</code>. <br>
	 *         Null if not found.
	 */
	public Fractal pickFractal(Class<? extends Fractal> clazz) {
		logger.log(Level.FINEST, "Canvas.pickFractal " + clazz.getSimpleName());
		for (Fractal f : settings.getFractals()) {
			if (f.getClass().equals(clazz))
				return f;
		}
		return null;
	}

	/**
	 * Sets the current {@link Canvas#fractal}.<br>
	 * Keep in mind this method must take the original reference to the
	 * <code>Fractal</code> as parameter, not a clone of it.
	 * 
	 * @param fractal the reference to the original <code>Fractal</code>.
	 */
	public void setFractal(@NotNull Fractal fractal) {
		logger.log(Level.FINEST, "canvas.setFractal " + fractal.getName());

		super.removeMouseMotionListener(this.fractal.getMouse());
		super.addMouseMotionListener(fractal.getMouse());
		this.fractal = fractal;
		fractal.getTransform().setOrigin(getWidth() / 2, getHeight() / 2);
		jpi.updateFractal();
	}

	public void setFunction(@NotNull Function function) {
		logger.log(Level.FINEST, "canvas.setFunction " + function.getName());

		fractal.pickFunction(function.getClass());
		jpi.updateFunction();
	}

	public void setFilter(@NotNull Filter filter) {
		logger.log(Level.FINEST, "canvas.setFilter " + filter.getName());

		fractal.getFunction().pickFilter(filter.getClass());
		jpi.updateFilter();
	}

	/**
	 * sets the instance of the {@link JPInterface} so the Canvas can save and
	 * update the user input
	 * 
	 * @param jpi the instance of {@link JPInterface}
	 */
	public void setJPI(@NotNull JPInterface jpi) {
		logger.log(Level.FINER, "canvas.setJPI");

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
		 * A clone of the saved <code>Fractal</code>, this includes a
		 * <code>Function</code> and <code>Filter</code>.
		 */
		@NotNull
		private final Fractal fractal;

		private final double zoomFactor;

		/**
		 * Creates a new <code>Configuration</code> from the current state of the
		 * <code>Canvas</code>.
		 */
		public Configuration() {
			this.fractal = Canvas.this.fractal.clone();
			this.zoomFactor = Canvas.this.zoomFactor;
		}
	}
}
