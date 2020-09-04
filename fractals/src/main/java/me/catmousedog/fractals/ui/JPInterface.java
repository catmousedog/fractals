package me.catmousedog.fractals.ui;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.canvas.Canvas;
import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.fractals.Fractal.Location;
import me.catmousedog.fractals.fractals.filters.Filter;
import me.catmousedog.fractals.main.Logger;
import me.catmousedog.fractals.main.Main;
import me.catmousedog.fractals.main.Main.InitialSize;
import me.catmousedog.fractals.main.Settings;
import me.catmousedog.fractals.ui.components.ActiveData;
import me.catmousedog.fractals.ui.components.Data;
import me.catmousedog.fractals.ui.components.Item;
import me.catmousedog.fractals.ui.components.concrete.Button;
import me.catmousedog.fractals.ui.components.concrete.Button2;
import me.catmousedog.fractals.ui.components.concrete.ComboBoxItem;
import me.catmousedog.fractals.ui.components.concrete.ComboBoxList;
import me.catmousedog.fractals.ui.components.concrete.Label;
import me.catmousedog.fractals.ui.components.concrete.Padding;
import me.catmousedog.fractals.ui.components.concrete.Panel;
import me.catmousedog.fractals.ui.components.concrete.SubTitle;
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
public class JPInterface extends JPanel implements Savable {

	/**
	 * the main instance, used for resizing the frame by calling
	 * {@link Main#setSize(int, int)}
	 */
	private final Main main;

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

	public JPInterface(InitialSize size, Main main, Canvas canvas, Logger logger, Settings settings) {
		this.main = main;
		this.canvas = canvas;
		this.logger = logger;
		this.settings = settings;
		data = new AllData();

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setMaximumSize(new Dimension(size.getIwidth(), Integer.MAX_VALUE));
		setBorder(BorderFactory.createEmptyBorder(size.getVgap(), size.getHgap(), size.getVgap(), size.getHgap()));
		updateFractal(); // add colour panel, etc.
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

		canvas.render();
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
	 * <li>{@link r#run()}</li> change data that shouldn't be saved
	 * <li>{@link JPInterface#update()}</li> update this data to the user interface
	 * <li>{@link JPInterface#renderNow()}</li> if <i>render</i> is true: render the
	 * image without saving or updating
	 * </ul>
	 * 
	 * @param render if true it will also render the image.
	 * @param r      the runnable to change any data not entered by the user.
	 */
	public void renderWithout(boolean render, @NotNull Runnable r) {
		save();
		r.run();
		update();
		if (render)
			renderNow();
	}

	/**
	 * Called before the image is rendered.
	 */
	@Override
	public void preRender() {
		/* Window */
		data.getWidthjtf().preRender();
		data.getHeightjtf().preRender();

		/* Location */
		data.getXjtf().preRender();
		data.getYjtf().preRender();
		data.getMjtf().preRender();
		data.getNjtf().preRender();
		data.getRjtf().preRender();
		data.getCopypastejb().preRender();
		data.getLocationjcb().preRender();
		data.getUndojb().preRender();

		/* Calculation */
		data.getIterjtf().preRender();
		data.getZoomjtf().preRender();
		data.getZoomjb().preRender();
		data.getRenderjb().preRender();
		data.getCanceljb().setData(true);

		/* Fractal */
		data.getFractaljcb().preRender();
		data.getFilterjcb().preRender();
		data.getRepaintjb().preRender();
		// colour
		canvas.getFractal().getFilter().preRender();
	}

	/**
	 * True if the undo button should be enabled in
	 * {@link JPInterface#postRender()}.
	 */
	private boolean allowUndo = false;

	/**
	 * This method is run after the image is done rendering. It shouldn't be
	 * possible for this to occur concurrently or in the wrong order of calling
	 * {@link JPInterface#render}.
	 * <p>
	 * Will be run on the EDT.
	 */
	@Override
	public void postRender() {
		/* Window */
		data.getWidthjtf().postRender();
		data.getHeightjtf().postRender();

		/* Location */
		data.getXjtf().postRender();
		data.getYjtf().postRender();
		data.getMjtf().postRender();
		data.getNjtf().postRender();
		data.getRjtf().postRender();
		data.getCopypastejb().postRender();
		data.getLocationjcb().postRender();
		if (allowUndo) {
			data.getUndojb().setData(true);
			allowUndo = false;
		}

		/* Calculation */
		data.getIterjtf().postRender();
		data.getZoomjtf().postRender();
		data.getZoomjb().postRender();
		data.getRenderjb().postRender();
		data.getCanceljb().setData(false);

		/* Fractal */
		data.getFractaljcb().postRender();
		data.getFilterjcb().postRender();
		data.getRepaintjb().postRender();
		;
		// colour
		canvas.getFractal().getFilter().postRender();

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
	@Override
	public void save() {
		allowUndo = true;
		
		canvas.savePrevConfig();

		/* Window */
		main.setSize(data.getWidthjtf().saveAndGet(), data.getHeightjtf().saveAndGet());

		/* Location */
		canvas.getFractal().getTransform().setTranslation(data.getXjtf().saveAndGet(), data.getYjtf().saveAndGet());
		canvas.getFractal().getTransform().setScalar(data.getMjtf().saveAndGet(), data.getNjtf().saveAndGet());
		canvas.getFractal().getTransform().setRot(data.getRjtf().saveAndGet());

		/* Calculation */
		canvas.getFractal().setIterations(data.getIterjtf().saveAndGet());
		canvas.setZoomFactor(data.getZoomjtf().saveAndGet());

		/* Fractal */
		// colour
		canvas.getFractal().save();
	}

	/**
	 * Will update all values inside the user interface.<br>
	 * This is done by calling {@link Data#setData(Object)} for each {@link Data}
	 * inside the user interface.
	 */
	@Override
	public void update() {
		/* Window */
		data.getWidthjtf().setData(canvas.getWidth());
		data.getHeightjtf().setData(canvas.getHeight());

		/* Location */
		data.getXjtf().setData(canvas.getFractal().getTransform().getdx());
		data.getYjtf().setData(canvas.getFractal().getTransform().getdy());
		data.getMjtf().setData(canvas.getFractal().getTransform().getm());
		data.getNjtf().setData(canvas.getFractal().getTransform().getn());
		data.getRjtf().setData(canvas.getFractal().getTransform().getrot());
		data.getLocationjcb().setDataSafe(canvas.getFractal().getLocations());

		/* Calculation */
		data.getIterjtf().setData(canvas.getFractal().getIterations());
		data.getZoomjtf().setData(canvas.getZoomFactor());

		/* Fractal */
		data.getFractaljcb().setDataSafe(canvas.getFractal());
		data.getFilterjcb().setDataSafe(canvas.getFractal().getFilter());
		// colour
		canvas.getFractal().safeUpdate();
	}

	/**
	 * Should be called when the {@link Canvas}' {@link Fractal} is changed. <br>
	 * This method does not change the actual {@link Fractal}, but updates any
	 * components reliant on the {@link Fractal}. This method is independent of
	 * {@link Canvas#setFractal(Fractal)} as the latter actually changes the
	 * {@link Fractal}, but does get called inside
	 * {@link Canvas#setFractal(Fractal)}.
	 * <p>
	 * This should only be called after the {@link Fractal} changes, not every
	 * {@link JPInterface#update()} cycle.
	 */
	public void updateFractal() {
		// tooltip for fractal jcombobox
		data.getFractaljcb().getComponent().setToolTipText(canvas.getFractal().getTip());
		// set filters inside jcombobox
		data.getFilterjcb().setItems(canvas.getFractal().getFilters());
		// set colour panel from current filter
		canvas.getFractal().getFilter().setPanel(data.getFractaljp().getPanel());
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

		/**
		 * Array of all the {@link Item}s in order of addition.
		 */
		private final Item[] all;

		private final TextFieldInteger widthjtf;

		public Data<Integer> getWidthjtf() {
			return widthjtf;
		}

		private final TextFieldInteger heightjtf;

		public Data<Integer> getHeightjtf() {
			return heightjtf;
		}

		private final TextFieldDouble xjtf;

		public Data<Double> getXjtf() {
			return xjtf;
		}

		private final TextFieldDouble yjtf;

		public Data<Double> getYjtf() {
			return yjtf;
		}

		private final TextFieldDouble mjtf;

		public Data<Double> getMjtf() {
			return mjtf;
		}

		private final TextFieldDouble njtf;

		public Data<Double> getNjtf() {
			return njtf;
		}

		private final TextFieldDouble rjtf;

		public Data<Double> getRjtf() {
			return rjtf;
		}

		private final Button2 copypastejb;

		public Data<Boolean> getCopypastejb() {
			return copypastejb;
		}

		public ComboBoxList locationjcb;

		public ActiveData<Object[]> getLocationjcb() {
			return locationjcb;
		}

		private final Button undojb;

		public Data<Boolean> getUndojb() {
			return undojb;
		}

		private final TextFieldInteger iterjtf;

		public Data<Integer> getIterjtf() {
			return iterjtf;
		}

		private final TextFieldDouble zoomjtf;

		public Data<Double> getZoomjtf() {
			return zoomjtf;
		}

		private final Button2 zoomjb;

		public Data<Boolean> getZoomjb() {
			return zoomjb;
		}

		private final Button renderjb;

		public Data<Boolean> getRenderjb() {
			return renderjb;
		}

		private final Button canceljb;

		public Data<Boolean> getCanceljb() {
			return canceljb;
		}

		private final ComboBoxItem fractaljcb;

		public ComboBoxItem getFractaljcb() {
			return fractaljcb;
		}

		private final ComboBoxItem filterjcb;

		public ComboBoxItem getFilterjcb() {
			return filterjcb;
		}

		private final Button repaintjb;

		public Data<Boolean> getRepaintjb() {
			return repaintjb;
		}

		private final Panel fractaljp;

		public Panel getFractaljp() {
			return fractaljp;
		}

		public AllData() {

			Padding p5 = new Padding(5);

			Padding p10 = new Padding(10);

			Padding p20 = new Padding(20);

			/**
			 * Window
			 */
			Title window = new Title("Window");

			widthjtf = new TextFieldInteger.Builder().setLabel("width").setTip("<html>The width of the canvas</html>")
					.build();

			heightjtf = new TextFieldInteger.Builder().setLabel("height")
					.setTip("<html>The height of the canvas</html>").build();

			/**
			 * Location
			 */
			Title location = new Title("Location");

			xjtf = new TextFieldDouble.Builder().setLabel("x coordinate")
					.setTip("<html>The x coordinate of the center of the screen</html>").build();

			yjtf = new TextFieldDouble.Builder().setLabel("y coordinate")
					.setTip("<html>The y coordinate of the center of the screen</html>").build();

			mjtf = new TextFieldDouble.Builder().setLabel("x zoom").setTip("<html>The x scaling factor</html>").build();

			njtf = new TextFieldDouble.Builder().setLabel("y zoom").setTip("<html>The y scaling factor</html>").build();

			rjtf = new TextFieldDouble.Builder().setLabel("rotation").setTip("<html>The rotation in radians</html>")
					.build();

			copypastejb = new Button2.Builder("Copy", "Paste").setAction(a -> copy(), a -> paste())
					.setTip("<html>Copy current location to clipboard</html>",
							"<html>Paste a location from clipboard</html>")
					.build();

			locationjcb = new ComboBoxList.Builder(canvas.getFractal().getLocations()).setLabel("locations")
					.setAction(a -> location(a)).setTip("<html>A set of interesting locations</html>").build();

			undojb = new Button.Builder("Undo").setAction(a -> undo())
					.setTip("<html>Go back to the previous location, fractal and filter</html>").setDefault(false)
					.build();

			/**
			 * Calculation
			 */
			Title calculations = new Title("Calculation");

			iterjtf = new TextFieldInteger.Builder().setLabel("iterations")
					.setTip("<html>The iteration count. Each fractal can use this differently."
							+ "<br>Usually a higher iteration count means better quality but longer generating time.</html>")
					.build();

			zoomjtf = new TextFieldDouble.Builder().setLabel("zoom factor").setDefault(-1)
					.setTip("<html>The zoom factor to multiply with on click</html>").build();

			zoomjb = new Button2.Builder("Zoom In", "Zoom Out").setAction(a -> zoomIn(), a -> zoomOut())
					.setTip("<html>Zoom in without moving</html>", "<html>Zoom out without moving</html>").build();

			renderjb = new Button.Builder("Render").setAction(a -> render())
					.setTip("<html>Render the image by generating it and painting it</html>").build();

			canceljb = new Button.Builder("Cancel").setAction(a -> cancel())
					.setTip("<html>Cancel the current generator.<br>This does not stop the painting process</html>")
					.build();

			/**
			 * Fractal
			 */
			Title fractal = new Title("Fractal");

			Label fractaljl = new Label("fractals", "List of fractals that were enabled in the 'settings.properties'");

			fractaljcb = new ComboBoxItem.Builder(main.getFractals()).setAction(a -> fractal()).build();

			Label filterjl = new Label("filters", "List of filters to be used with this fractal.");

			filterjcb = new ComboBoxItem.Builder(canvas.getFractal().getFilters()).setAction(a -> filter()).build();

			repaintjb = new Button.Builder("Repaint").setAction(a -> repaint()).setTip(
					"<html>Repaint the image without generating it again. <br>Useful for just changing colour settings</html>")
					.build();

			SubTitle colour = new SubTitle("Colour",
					"<html>This section contains the fractal specific colour settings.</html>");

			fractaljp = new Panel();

			all = new Item[] { window, p10, widthjtf, p5, heightjtf, p20, location, p10, xjtf, p5, yjtf, p5, mjtf, p5,
					njtf, p5, rjtf, p10, copypastejb, p5, locationjcb, p5, undojb, p20, calculations, p10, iterjtf, p5,
					zoomjtf, p10, zoomjb, p10, renderjb, p5, canceljb, p20, fractal, p10, fractaljl, fractaljcb, p5,
					filterjl, filterjcb, p5, repaintjb, p5, colour, p5, fractaljp, p5 };
		}

		/**
		 * copy button
		 */
		private void copy() {
			// update?
			String clip = canvas.getFractal().getID();

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
				try {
					canvas.getFractal().fromID(clip);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					logger.exception(e);
				}

				if (settings.isRender_on_changes()) {
					update();
					renderNow();
				}

			} catch (HeadlessException | UnsupportedFlavorException | IOException e) {
				logger.log("unable to paste from clipboard");
				logger.exception(e);
				e.printStackTrace();
			}
		}

		/**
		 * locationjcb
		 */
		private void location(ActionEvent a) {
			renderWithout(settings.isRender_on_changes(), () -> {
				@SuppressWarnings("unchecked")
				JComboBox<Object> jcb = (JComboBox<Object>) a.getSource();
				Location l = (Location) jcb.getSelectedItem();
				System.out.println(l);
				canvas.getFractal().getTransform().set(l.getTransform());
				canvas.getFractal().setIterations(l.getIterations());
			});
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
			renderWithout(true, () -> {
				canvas.getFractal().getTransform().zoom(1 / canvas.getZoomFactor());
			});
		}

		/**
		 * zoom out button
		 */
		private void zoomOut() {
			renderWithout(true, () -> {
				canvas.getFractal().getTransform().zoom(canvas.getZoomFactor());
			});
		}

		/**
		 * cancel button
		 */
		private void cancel() {
			allowUndo = false;
			if (canvas.getGenerator().cancel(true)) {
				canvas.undo();
				update();
				logger.log("successfully stopped current render");
			} else {
				logger.log("could not stop current render");
			}
		}

		/**
		 * fractaljcb
		 */
		private void fractal() {
			Fractal f = (Fractal) getFractaljcb().saveAndGet();
			if (canvas.getFractal().equals(f))
				return;
			renderWithout(settings.isRender_on_changes(), () -> {
				canvas.setFractal(f);
				updateFractal();
			});
		}

		/**
		 * filterjcb
		 */
		private void filter() {
			Filter f = (Filter) getFilterjcb().saveAndGet();
			if (f.getClass().equals(canvas.getFractal().getFilter().getClass()))
				return;
			renderWithout(settings.isRender_on_changes(), () -> {
				canvas.getFractal().pickFilter(f);
				updateFractal();
			});
		}

		/**
		 * repaintjb
		 */
		private void repaint() {
			canvas.getFractal().saveAndColour();
			update();
		}

		public Item[] getAll() {
			return all;
		}
	}

	/**
	 * @return the {@link AllData} instance
	 */
	public AllData getData() {
		return data;
	}
}
