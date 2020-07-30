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
import javax.swing.JSlider;

import me.catmousedog.fractals.canvas.Canvas;
import me.catmousedog.fractals.main.Fractals;
import me.catmousedog.fractals.main.Logger;
import me.catmousedog.fractals.main.Settings;
import me.catmousedog.fractals.ui.components.Data;
import me.catmousedog.fractals.ui.components.Item;
import me.catmousedog.fractals.ui.components.concrete.Button;
import me.catmousedog.fractals.ui.components.concrete.Button2;
import me.catmousedog.fractals.ui.components.concrete.ComboBox;
import me.catmousedog.fractals.ui.components.concrete.Padding;
import me.catmousedog.fractals.ui.components.concrete.SliderInteger;
import me.catmousedog.fractals.ui.components.concrete.TextFieldDouble;
import me.catmousedog.fractals.ui.components.concrete.TextFieldInteger;
import me.catmousedog.fractals.ui.components.concrete.Title;

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
		save();
		update();

//		if (!s) {
//			logger.log("Illegal data, not rendering");
//			postRender();
//		} else {
		// render
		canvas.render();
//		}
	}

	/**
	 * Renders the image without calling {@link JPInterface#save()} or
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

		canvas.render();
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

		// disable undo button
		data.getUndojb().setData(false);

		// enable cancel button
		data.getCanceljb().setData(true);
	}

	private boolean allowUndo = false;

	/**
	 * This method is run after the image is done rendering. It shouldn't be
	 * possible for this to occur concurrently or in the wrong order of calling
	 * {@link JPInterface#render}.
	 * <p>
	 * Will be run on the EDT.
	 */
	public void postRender() {
		// enable render button
		data.getRenderjb().setData(true);

		// enable zoom in / out button
		data.getZoomjb().setData(true);

		// enable copy paste button
		data.getCopypastejb().setData(true);

		// enable undo button
		if (allowUndo)
			data.getUndojb().setData(true);
		allowUndo = true;

		// disable cancel button
		data.getCanceljb().setData(false);
	}

	/**
	 * Will apply the data the user entered to anything that requires it. The data
	 * is retrieved using {@link Data#saveAndGet()}.
	 * <p>
	 * Keep in mind that this save is very different from {@link Data#save()}. <br>
	 * This method performs an action upon finding the values the user entered. <br>
	 * {@link Data#save()} Just takes the user entered data and saves it to the
	 * field {@link Data#data}, but doesn't do anything with it.
	 */
	public void save() {
		canvas.savePrevConfig();

		/* Window */
		fractals.setSize(data.getWidthjtf().saveAndGet(), data.getHeightjtf().saveAndGet());

		/* Location */
		canvas.getConfig().getTransform().setTranslation(data.getXjtf().saveAndGet(), data.getYjtf().saveAndGet());
		canvas.getConfig().getTransform().setScalar(data.getMjtf().saveAndGet(), data.getNjtf().saveAndGet());
		canvas.getConfig().getTransform().setRot(data.getRjtf().saveAndGet());

		/* Calculation */
		canvas.getConfig().setIterations(data.getIterjtf().saveAndGet());
		canvas.getConfig().setZoomFactor(data.getZoomjtf().saveAndGet());
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

		private final Padding p5 = new Padding(5);

		private final Padding p10 = new Padding(10);

		private final Padding p20 = new Padding(20);

		/**
		 * Window
		 */
		private final Title window = new Title("Window");

		private final TextFieldInteger widthjtf = new TextFieldInteger.Builder().setLabel("width")
				.setTip("the width of the canvas").build();

		public Data<Integer> getWidthjtf() {
			return widthjtf;
		}

		private final TextFieldInteger heightjtf = new TextFieldInteger.Builder().setLabel("height")
				.setTip("the height of the canvas").build();

		public Data<Integer> getHeightjtf() {
			return heightjtf;
		}

		/**
		 * Location
		 */
		public Title location = new Title("Location");

		private final TextFieldDouble xjtf = new TextFieldDouble.Builder().setLabel("x coordinate")
				.setTip("the x coordinate of the center of the screen").build();

		public Data<Double> getXjtf() {
			return xjtf;
		}

		private final TextFieldDouble yjtf = new TextFieldDouble.Builder().setLabel("y coordinate")
				.setTip("the y coordinate of the center of the screen").build();

		public Data<Double> getYjtf() {
			return yjtf;
		}

		private final TextFieldDouble mjtf = new TextFieldDouble.Builder().setLabel("x zoom")
				.setTip("the x scaling factor").build();

		public Data<Double> getMjtf() {
			return mjtf;
		}

		private final TextFieldDouble njtf = new TextFieldDouble.Builder().setLabel("y zoom")
				.setTip("the y scaling factor").build();

		public Data<Double> getNjtf() {
			return njtf;
		}

		private final TextFieldDouble rjtf = new TextFieldDouble.Builder().setLabel("rotation")
				.setTip("the rotation in radians").build();

		public Data<Double> getRjtf() {
			return rjtf;
		}

		private final Button2 copypastejb = new Button2.Builder("Copy", "Paste").setAction(a -> copy(), a -> paste())
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

		private final Button undojb = new Button.Builder("Undo").setAction(a -> undo())
				.setTip("go back to the previous location").setDefault(false).build();

		public Data<Boolean> getUndojb() {
			return undojb;
		}

		/**
		 * Calculation
		 */
		public Title calculations = new Title("Calculation");

		private final TextFieldInteger iterjtf = new TextFieldInteger.Builder().setLabel("iterations")
				.setTip("the amount of iterations when rednering").build();

		public Data<Integer> getIterjtf() {
			return iterjtf;
		}

		private final TextFieldDouble zoomjtf = new TextFieldDouble.Builder().setLabel("zoom factor").setDefault(-1)
				.setTip("the zoom factor to multiply with on click").build();

		public Data<Double> getZoomjtf() {
			return zoomjtf;
		}

		private final Button2 zoomjb = new Button2.Builder("Zoom In", "Zoom Out")
				.setAction(a -> zoomIn(), a -> zoomOut()).setTip("zoom in without moving", "zoom out without moving")
				.build();

		public Data<Boolean> getZoomjb() {
			return zoomjb;
		}

		private final Button renderjb = new Button.Builder("Render").setAction(a -> render()).setTip("render the image")
				.build();

		public Data<Boolean> getRenderjb() {
			return renderjb;
		}

		private final Button canceljb = new Button.Builder("Cancel").setAction(a -> cancel())
				.setTip("cancel the current render incase it is taking too long").build();

		public Data<Boolean> getCanceljb() {
			return canceljb;
		}

		/**
		 * Colour
		 */
		private final Title colour = new Title("Colour");

		/**
		 * Array of all {@link Item}s in order of addition.
		 */
		private Item[] all = new Item[] { window, p10, widthjtf, p5, heightjtf, p20, location, p10, xjtf, p5, yjtf, p5,
				mjtf, p5, njtf, p5, rjtf, p10, copypastejb, p5, locations, p5, undojb, p20, calculations, p10, iterjtf,
				p5, zoomjtf, p10, zoomjb, p10, renderjb, p5, canceljb, p20, colour, p10 };

		public Item[] getAll() {
			return all;
		}

		/**
		 * copy button
		 */
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

		/**
		 * paste button
		 */
		private void paste() {
			try {
				String clip = (String) Toolkit.getDefaultToolkit().getSystemClipboard()
						.getData(DataFlavor.stringFlavor);

				canvas.savePrevConfig();
				canvas.getConfig().fromID(clip);

				if (settings.isRender_on_changes()) {
					update();
					renderNow();
				}

			} catch (HeadlessException | UnsupportedFlavorException | IOException e) {
				e.printStackTrace();
				logger.log("unable to paste from clipboard");
				logger.exception(e);
			} catch (NumberFormatException n) {
				logger.log("clipboard does not contain a valid configuration");
			}
		}

		/**
		 * undo button
		 */
		private void undo() {
			allowUndo = false;
			canvas.undo();
			update();
			renderNow();
		}

		/**
		 * zoom in button
		 */
		private void zoomIn() {
			renderWithout(() -> {
				canvas.getConfig().getTransform().zoom(1 / canvas.getConfig().getZoomFactor());
			});
		}

		/**
		 * zoom out button
		 */
		private void zoomOut() {
			renderWithout(() -> {
				canvas.getConfig().getTransform().zoom(canvas.getConfig().getZoomFactor());
			});
		}

		/**
		 * cancel button
		 */
		private void cancel() {
			allowUndo = false;
			if (canvas.getWorker().cancel(true)) {
				canvas.undo();
				update();
				logger.log("successfully stopped current render");
			} else {
				logger.log("could not stop current render");
			}
		}

	}

	/**
	 * 
	 * 
	 * @return
	 */
	public AllData getData() {
		return data;
	}

}
