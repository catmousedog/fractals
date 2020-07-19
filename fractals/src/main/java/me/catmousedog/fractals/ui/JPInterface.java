package me.catmousedog.fractals.ui;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import me.catmousedog.fractals.canvas.Canvas;
import me.catmousedog.fractals.components.Button;
import me.catmousedog.fractals.components.Button2;
import me.catmousedog.fractals.components.ComboBox;
import me.catmousedog.fractals.components.Data;
import me.catmousedog.fractals.components.Item;
import me.catmousedog.fractals.components.Padding;
import me.catmousedog.fractals.components.TextFieldDouble;
import me.catmousedog.fractals.components.TextFieldInteger;
import me.catmousedog.fractals.components.Title;
import me.catmousedog.fractals.fractals.LinearTransform;
import me.catmousedog.fractals.main.Fractals;
import me.catmousedog.fractals.main.Settings;

/**
 * class containing all the data entered by the user through the interface panel
 * <p>
 * This class is also used to render the image as it contains all the
 * JComponents it needs to change after rendering.
 */
@SuppressWarnings("serial")
public class JPInterface extends JPanel {

	/**
	 * the main instance, used for resizing the frame by calling
	 * {@link Fractals#setSize(int, int)}
	 */
	private final Fractals fractals;

	/**
	 * the Canvas instance used to render the image
	 */
	private final Canvas canvas;

	/**
	 * the logger instance
	 */
	private final Logger logger;

	/**
	 * user settings from {@code .properties} files
	 */
	private final Settings settings;

	/**
	 * user data from the user interface
	 */
	private final AllData data;

	public JPInterface(int iwidth, int vgap, int hgap, Fractals fractals, Canvas canvas, Logger logger,
			Settings settings) {
		this.fractals = fractals;
		this.canvas = canvas;
		this.logger = logger;
		this.settings = settings;
		data = new AllData();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setMaximumSize(new Dimension(iwidth, Integer.MAX_VALUE));
		setBorder(BorderFactory.createEmptyBorder(vgap, hgap, vgap, hgap));
		canvas.addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent e) {
				data.getWidthjtf().setData(canvas.getWidth());
				data.getHeightjtf().setData(canvas.getHeight());
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

		// set all values inside the user interface
		update();
	}

	/**
	 * create and add all the JComponents
	 */
	public void addComponents() {
		for (Item c : data.getAll())
			add(c.panel());
	}

	/**
	 * Grabs user input, restores any illegal inputs, generates the image and paints
	 * it. <br>
	 * This is the preferred method to render the fractal.
	 * <p>
	 * Must be run on the EDT
	 */
	public void render() {
		// already rendering
		if (!data.getRenderjb().saveAndGet()) {
			logger.log("already rendering...");
			return;
		}

		preRender();

		// save & update
		boolean s = save();
		update();

		if (!s) {
			logger.log("Illegal data, not rendering");
			postRender();
		} else {
			// render
			canvas.render(this);
		}
	}

	/**
	 * Renders the image without calling {@link JPInterface#save()} pr
	 * {@link JPInterface#update()}.
	 * <p>
	 * This method still calls the {@link JPInterface#postRender()} method.
	 */
	public void renderNow() {
		// already rendering
		if (!data.getRenderjb().saveAndGet()) {
			logger.log("already rendering...");
			return;
		}

		preRender();

		canvas.render(this);
	}

	/**
	 * Just like {@link JPInterface#render()} but allows for changing a few
	 * variables so the {@link JPInterface#save()} doesn't discard them.
	 * <p>
	 * This method will
	 * <ul>
	 * <li>{@link JPInterface#save()}</li> save all the user entered data
	 * <li>{@link Runnable#run()}</li> change data that wasn't entered by the user
	 * <li>{@link JPInterface#update()}</li> update this data to the user interface
	 * <li>{@link JPInterface#renderNow()}</li> render the image without saving or
	 * updating
	 * </ul>
	 * 
	 * @param r the runnable to change any data not entered by the user.
	 */
	public void renderWithout(Runnable r) {
		save();
		r.run();
		update();
		renderNow();
	}

	/**
	 * Called before the image is rendered.
	 */
	public void preRender() {
		// disable render button
		data.getRenderjb().setData(false);

		// disable zoom in / out button
		data.getZoomjb().setData(false);

		// disable copy paste button
		data.getCopypastejb().setData(false);
	}

	/**
	 * This method is run after the image is done rendering. It shouldn't be
	 * possible for this to occur concurrently or in the wrong order of calling
	 * {@link JPInterface#render}.
	 * <p>
	 * Must be run on the EDT.
	 */
	public void postRender() {
		// enable render button
		data.getRenderjb().setData(true);

		// enable zoom in / out button
		data.getZoomjb().setData(true);

		// enable copy paste button
		data.getCopypastejb().setData(true);
	}

	/**
	 * Will apply the data the user entered to anything that requires it. The data
	 * is retrieved using {@link Data#saveAndGet()}.
	 * <p>
	 * Keep in mind that this save is very different from {@link Data#save()}. <br>
	 * This method performs an action upon finding the values the user entered. <br>
	 * {@link Data#save()} Just takes the user entered data and saves it to the
	 * field {@link Data#data}, but doesn't do anything with it.
	 * 
	 * @return true if successful, false otherwise
	 */
	public boolean save() {
		boolean b = true;
		/* Window */
		fractals.setSize(data.getWidthjtf().saveAndGet(), data.getHeightjtf().saveAndGet());

		/* Location */
		canvas.getConfig().getTransform().setTranslation(data.getXjtf().saveAndGet(), data.getYjtf().saveAndGet());
		canvas.getConfig().getTransform().setScalar(data.getMjtf().saveAndGet(), data.getNjtf().saveAndGet());
		canvas.getConfig().getTransform().setRot(data.getRjtf().saveAndGet());

		/* Calculation */
		canvas.getConfig().setIterations(data.getIterjtf().saveAndGet());
		canvas.getConfig().setZoomFactor(data.getZoomjtf().saveAndGet());

		return b;
	}

	/**
	 * Will update all values inside the user interface.<br>
	 * This is done by calling {@link Data#setData(Object)} for each {@link Data}
	 * inside the user interface.
	 */
	public void update() {
		/* Window */
		data.getWidthjtf().setData(canvas.getWidth());
		data.getHeightjtf().setData(canvas.getHeight());

		/* Location */
		data.getXjtf().setData(canvas.getConfig().getTransform().getdx());
		data.getYjtf().setData(canvas.getConfig().getTransform().getdy());
		data.getMjtf().setData(canvas.getConfig().getTransform().getm());
		data.getNjtf().setData(canvas.getConfig().getTransform().getn());
		data.getRjtf().setData(canvas.getConfig().getTransform().getrot());
		// update ComboBox

		/* Calculation */
		data.getIterjtf().setData(canvas.getConfig().getIterations());
		data.getZoomjtf().setData(canvas.getConfig().getZoomFactor());
	}

	/**
	 * Concrete class containing all the actual {@link Data} containers displayed in
	 * the user interface. This class can be passed around to retrieve the data
	 * needed.
	 * <p>
	 * When adding a new {@link Item} to the user interface, make sure to add it to
	 * the {@link AllData#all} array so it gets added to the user interface.
	 * <p>
	 * When adding a new {@link Data}, make sure to add its <b>getter</b> method so
	 * the data can be retrieved from this class.
	 */
	public class AllData {

		private Padding p5 = new Padding(5);

		private Padding p10 = new Padding(10);

		private Padding p20 = new Padding(20);

		/**
		 * Window
		 */
		private Title window = new Title("Window");

		private TextFieldInteger widthjtf = new TextFieldInteger.Builder().setLabel("width")
				.setTip("the width of the canvas").build();

		public Data<Integer> getWidthjtf() {
			return widthjtf;
		}

		private TextFieldInteger heightjtf = new TextFieldInteger.Builder().setLabel("height")
				.setTip("the height of the canvas").build();

		public Data<Integer> getHeightjtf() {
			return heightjtf;
		}

		/**
		 * Location
		 */
		public Title location = new Title("Location");

		private TextFieldDouble xjtf = new TextFieldDouble.Builder().setLabel("x coordinate")
				.setTip("the x coordinate of the center of the screen").build();

		public Data<Double> getXjtf() {
			return xjtf;
		}

		private TextFieldDouble yjtf = new TextFieldDouble.Builder().setLabel("y coordinate")
				.setTip("the y coordinate of the center of the screen").build();

		public Data<Double> getYjtf() {
			return yjtf;
		}

		private TextFieldDouble mjtf = new TextFieldDouble.Builder().setLabel("x zoom").setTip("the x scaling factor")
				.build();

		public Data<Double> getMjtf() {
			return mjtf;
		}

		private TextFieldDouble njtf = new TextFieldDouble.Builder().setLabel("y zoom").setTip("the y scaling factor")
				.build();

		public Data<Double> getNjtf() {
			return njtf;
		}

		public TextFieldDouble rjtf = new TextFieldDouble.Builder().setLabel("rotation")
				.setTip("the rotation in radians").build();

		public Data<Double> getRjtf() {
			return rjtf;
		}

		public Button2 copypastejb = new Button2.Builder("Copy", "Paste").setAction(a -> copy(), a -> paste())
				.setTip("copy location to clipboard", "paste location from clipboard").build();

		public Data<Boolean> getCopypastejb() {
			return copypastejb;
		}

		public ComboBox locations = new ComboBox.Builder(PreSaved.values()).setLabel("locations").setAction(a -> {
			try {
				renderWithout(() -> {
					@SuppressWarnings("unchecked")
					JComboBox<PreSaved> jcb = (JComboBox<PreSaved>) a.getSource();
					canvas.getConfig().getTransform().set(((PreSaved) jcb.getSelectedItem()).getTransform());
					canvas.getConfig().setIterations(((PreSaved) jcb.getSelectedItem()).getIterations());
				});
			} catch (ClassCastException e) {
				e.printStackTrace();
			}

		}).setTip("a set of interesting locations").build();

		public Data<Object[]> getLocations() {
			return locations;
		}

		/**
		 * Calculation
		 */
		public Title calculations = new Title("Calculation");

		public TextFieldInteger iterjtf = new TextFieldInteger.Builder().setLabel("iterations")
				.setTip("the amount of iterations when rednering").build();

		public Data<Integer> getIterjtf() {
			return iterjtf;
		}

		public TextFieldDouble zoomjtf = new TextFieldDouble.Builder().setLabel("zoom factor").setDefault(-1)
				.setTip("the zoom factor to multiply with on click").build();

		public Data<Double> getZoomjtf() {
			return zoomjtf;
		}

		public Button2 zoomjb = new Button2.Builder("Zoom In", "Zoom Out").setAction(a -> {
			renderWithout(() -> {
				canvas.getConfig().getTransform().zoom(1 / canvas.getConfig().getZoomFactor());
			});
		}, a -> {
			renderWithout(() -> {
				canvas.getConfig().getTransform().zoom(canvas.getConfig().getZoomFactor());
			});
		}).setTip("zoom in without moving", "zoom out without moving").build();

		public Data<Boolean> getZoomjb() {
			return zoomjb;
		}

		public Button renderjb = new Button.Builder("Render").setAction(a -> render()).setTip("render the image")
				.build();

		public Data<Boolean> getRenderjb() {
			return renderjb;
		}

		/**
		 * Array of all {@link Item}s in order of addition.
		 */
		private Item[] all = new Item[] { window, p10, widthjtf, p5, heightjtf, p20, location, p10, xjtf, p5, yjtf, p5,
				mjtf, p5, njtf, p5, rjtf, p10, copypastejb, p5, locations, p20, calculations, p10, iterjtf, p5, zoomjtf,
				p10, zoomjb, p10, renderjb, p20 };

		public Item[] getAll() {
			return all;
		}

		private void copy() {
			// update?
			String clip = canvas.getConfig().getID();

			try {
				Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(clip), null);
			} catch (Exception e) {
				e.printStackTrace();
				logger.exception(e);
				logger.log("unable to copy to clipboard");
			}
		}

		private void paste() {
			try {
				try {
					String clip = (String) Toolkit.getDefaultToolkit().getSystemClipboard()
							.getData(DataFlavor.stringFlavor);

					canvas.getConfig().fromID(clip);
				} catch (HeadlessException | UnsupportedFlavorException | IOException e) {
					e.printStackTrace();
					logger.log("unable to paste from clipboard");
					logger.exception(e);
				}
			} catch (NumberFormatException e) {
				logger.log("unable to parse from clipboard");
			}

			if (settings.isRender_on_changes()) {
				update();
				renderNow();
			}
		}

	}

}
