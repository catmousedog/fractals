package me.catmousedog.fractals.canvas;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.fractals.LinearTransform;
import me.catmousedog.fractals.fractals.Pixel;
import me.catmousedog.fractals.fractals.filters.Filter;
import me.catmousedog.fractals.main.Logger;
import me.catmousedog.fractals.main.Main.InitialSize;
import me.catmousedog.fractals.ui.JPInterface;
import me.catmousedog.fractals.ui.JPInterface.AllData;

/**
 * Represents a 2D plane of processed values by the function
 * {@link Canvas#fractal}
 */
@SuppressWarnings("serial")
public class Canvas extends JPanel {

	/**
	 * the mouse listener
	 */
	private final Mouse mouse;

	/**
	 * used to log progress to the user
	 */
	private final Logger logger;

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
	 * The latest {@link SwingWorker} responsible for generating the fractal.
	 */
	private SwingWorker<Void, Void> generator;

	/**
	 * The latest {@link SwingWorker} responsible for colouring the fractal.
	 */
	private Painter painter;

	/**
	 * a list of all pixels
	 */
	private List<Pixel> field;

	/**
	 * image displayed on this instance of {@link JPanel}
	 */
	private BufferedImage img;

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
	public Canvas(InitialSize size, Fractal fractal, Logger logger) {
		this.logger = logger;
		this.fractal = fractal;

		setBorder(BorderFactory.createLoweredBevelBorder());
		mouse = new Mouse(this);
		addMouseListener(mouse);

		setPanelSize(size.getWidth(), size.getHeight());
		savePrevConfig();
	}

	/**
	 * Displays the current {@link Canvas#img}
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(img, 0, 0, null);
	}

	/**
	 * generates the image using a {@link SwingWorker} and calls the
	 * {@link Canvas#colourAndPaint()}.
	 * <p>
	 * This method does not have a cool down and should only be used by the
	 * {@link JPInterface}.
	 */
	public void render() {
		generator = new Generator(this, jpi, logger);
		generator.execute();
	}

	/**
	 * Colours the image and paints it using a the {@link Painter}.
	 * <p>
	 * This can be called safely whilst the {@link Painter} is still colouring. This
	 * method will check if {@link Painter#isRepainted()} returns true before
	 * starting a new {@link Painter}.
	 * 
	 * @return true if a new {@link Painter} was successfully executed.
	 */
	public boolean colourAndPaint() {
		// prePaint
		if (painter == null || painter.isRepainted()) {
			painter = new Painter(this, jpi, logger);
			painter.execute();
			return true;
		}
		return false;
	}

	/**
	 * Sets the size of the canvas and all of its components reliant on that
	 * size.<br>
	 * Components reliant on the size of the {@link Canvas} are:
	 * <ul>
	 * <li>{@link Canvas#field} the list of {@link Pixel}.
	 * <li>{@link Canvas#img} the {@link BufferedImage}.
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

		// set field size
		field = new ArrayList<Pixel>(width * height);
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				field.add(new Pixel(x, y));
			}
		}

		// set image size
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

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
		Fractal fractal = prevConfig.getFractal();
		fractal.pickFilter(prevConfig.getFilter());
		if (fractal.getFilter().getClass().equals(prevConfig.getFilter().getClass())) {
			fractal.getFilter().setFilter(prevConfig.getFilter());
		}
		fractal.getTransform().set(prevConfig.getTransform());
		fractal.setIterations(prevConfig.getIterations());
		zoomFactor = prevConfig.getZoomFactor();
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
		AllData data = jpi.getData();
		addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent e) {
				data.getWidthjtf().setData(getWidth());
				data.getHeightjtf().setData(getHeight());
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

	public void setImg(BufferedImage img) {
		this.img = img;
	}

	/**
	 * Changes the {@link Fractal} of the {@link Canvas}.
	 * 
	 * @param fractal
	 */
	public void setFractal(@NotNull Fractal fractal) {
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
	 * @return the current {@link Generator}.
	 */
	public SwingWorker<Void, Void> getGenerator() {
		return generator;
	}

	/**
	 * @return the current {@link Painter}.
	 */
	public SwingWorker<Void, Void> getPainter() {
		return painter;
	}

	/**
	 * @return the List of all {@link Pixel}s.
	 */
	public List<Pixel> getField() {
		return field;
	}

	/**
	 * A class to store the configuration of the canvas, such as the position and
	 * the iteration count.<br>
	 * Used for saving previous {@link Configuration}s and reverting.
	 * <p>
	 * This class holds clones except for {@link Configuration#fractal} which is a
	 * reference to the original {@link Fractal}.
	 */
	private class Configuration {

		/**
		 * A reference to the {@link Fractal} of this {@link Configuration}. The fields
		 * inside this fractal don't matter for this class since the
		 * {@link LinearTransform} and iterations are stored in this class.
		 * <p>
		 * This field is only used to reference which {@link Fractal} was used in
		 * {@link Canvas#undo()}.
		 */
		private final Fractal fractal;

		/**
		 * A clone of the current {@link Filter}.
		 */
		private final Filter filter;

		/**
		 * A clone of the current {@link LinearTransform}.
		 */
		private final LinearTransform transform;

		private final int iterations;

		private final double zoomFactor;

		public Configuration(Fractal fractal, double zoomFactor) {
			this.fractal = fractal;
			filter = fractal.getFilter().clone();
			transform = fractal.getTransform().clone();
			iterations = fractal.getIterations();
			this.zoomFactor = zoomFactor;
		}

		public Fractal getFractal() {
			return fractal;
		}

		public Filter getFilter() {
			return filter;
		}

		public LinearTransform getTransform() {
			return transform;
		}

		public int getIterations() {
			return iterations;
		}

		public double getZoomFactor() {
			return zoomFactor;
		}
	}

}
