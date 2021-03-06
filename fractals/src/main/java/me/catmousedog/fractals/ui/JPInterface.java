package me.catmousedog.fractals.ui;

import java.awt.Dimension;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.main.Main;
import me.catmousedog.fractals.main.Settings;
import me.catmousedog.fractals.paneloperators.filters.Filter;
import me.catmousedog.fractals.paneloperators.fractals.Fractal;
import me.catmousedog.fractals.paneloperators.functions.Function;
import me.catmousedog.fractals.ui.components.Data;
import me.catmousedog.fractals.ui.components.Item;
import me.catmousedog.fractals.ui.components.Savable;

/**
 * Class used to interact the user entered data via the {@link GUI} class and
 * the underlying components that need that data such as {@link Canvas}. <br>
 * All of the methods in {@link JPInterface} must be called on the EDT.
 */
@SuppressWarnings("serial")
public class JPInterface extends JPanel implements Savable {

	/**
	 * the main instance, used for resizing the frame by calling
	 * {@link Main#setSize(int, int)}
	 */
	private final Main main;

	/**
	 * The {@link Canvas} instance.
	 */
	private final Canvas canvas;

	/**
	 * user data from the user interface
	 */
	private final GUI gui;

	private final Settings settings = Settings.getInstance();

	private final Logger logger = Logger.getLogger("fractals");

	public JPInterface(Main main, Canvas canvas) {
		logger.log(Level.FINER, "JPInterface init");

		this.main = main;
		this.canvas = canvas;
		gui = new GUI(canvas, this);

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setMaximumSize(new Dimension(settings.getIwidth(), Integer.MAX_VALUE));
		setBorder(BorderFactory.createEmptyBorder(settings.getVgap(), settings.getHgap(), settings.getVgap(),
				settings.getHgap()));
		updateFractal(); // add colour panel, etc.
	}

	/**
	 * create and add all the JComponents
	 */
	public void addComponents() {
		for (Item c : gui.getAll())
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
		logger.log(Level.FINEST, "JPInterface.render");

		preRender();

		savePrevConfig();
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
		logger.log(Level.FINEST, "JPInterface.renderNow");

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
		logger.log(Level.FINEST, "JPInterface.renderWithout");

		save();
		r.run();
		update();
		if (render)
			renderNow();
	}

	/**
	 * Calls anything that needs to be called pre-render.<br>
	 * This includes disabling buttons, inputs, etc.
	 * <p>
	 * Should be run on the EDT.
	 */
	@Override
	public void preRender() {
		/* Fractal */
		gui.getFractaljcb().preRender();
		gui.getFunctionjcb().preRender();
		gui.getFilterjcb().preRender();
		gui.getRepaintjb().preRender();
		canvas.getFractal().preRender();

		/* Canvas */
		gui.getZoomjtf().preRender();
		gui.getZoomjb().preRender();
		gui.getRenderjb().preRender();
		gui.getCanceljb().setData(true);

		/* Location */
		gui.getXjtf().preRender();
		gui.getYjtf().preRender();
		gui.getMjtf().preRender();
		gui.getNjtf().preRender();
		gui.getRjtf().preRender();
		gui.getCopypastejb().preRender();
		gui.getLocationjcb().preRender();
		gui.getUndojb().preRender();

		/* Picture */
		gui.getPicturewjtf().preRender();
		gui.getPicturehjtf().preRender();
		gui.getPicturejb().preRender();
		gui.getPicturejcb().preRender();

		/* Window */
		gui.getWidthjtf().preRender();
		gui.getHeightjtf().preRender();

	}

	/**
	 * True if the undo button should be enabled in
	 * {@link JPInterface#postRender()}.
	 */
	private boolean allowUndo = false;

	/**
	 * Calls anything to that needs to be called post-render.<br>
	 * This includes enabling buttons, inputs, etc.
	 * <p>
	 * Should be run on the EDT.
	 */
	@Override
	public void postRender() {
		/* Fractal */
		gui.getFractaljcb().postRender();
		gui.getFunctionjcb().postRender();
		gui.getFilterjcb().postRender();
		gui.getRepaintjb().postRender();
		canvas.getFractal().postRender();

		/* Canvas */
		gui.getZoomjtf().postRender();
		gui.getZoomjb().postRender();
		gui.getRenderjb().postRender();
		gui.getCanceljb().setData(false);

		/* Location */
		gui.getXjtf().postRender();
		gui.getYjtf().postRender();
		gui.getMjtf().postRender();
		gui.getNjtf().postRender();
		gui.getRjtf().postRender();
		gui.getCopypastejb().postRender();
		gui.getLocationjcb().postRender();
		if (allowUndo) {
			gui.getUndojb().setData(true);
		}

		/* Picture */
		gui.getPicturewjtf().postRender();
		gui.getPicturehjtf().postRender();
		gui.getPicturejb().postRender();
		gui.getPicturejcb().postRender();

		/* Window */
		gui.getWidthjtf().postRender();
		gui.getHeightjtf().postRender();
	}

	/**
	 * Will apply the data the user entered to anything that requires it. The data
	 * is retrieved using {@link Data#saveAndGet()}.
	 * <p>
	 * Keep in mind that this save is very different from {@link Data#save()}. <br>
	 * This method performs an action upon finding the values the user entered. <br>
	 * {@link Data#save()} Just takes the user entered data and saves it to the
	 * field {@link Data#gui}, but doesn't do anything with it.
	 */
	@Override
	public void save() {
		logger.log(Level.FINEST, "JPInterface.save");

		/* Fractal */
		canvas.getFractal().save();

		/* Canvas */
		canvas.setZoomFactor(gui.getZoomjtf().saveAndGet());

		/* Location */
		canvas.getFractal().getTransform().setTranslation(gui.getXjtf().saveAndGet(), gui.getYjtf().saveAndGet());
		canvas.getFractal().getTransform().setScalar(gui.getMjtf().saveAndGet(), gui.getNjtf().saveAndGet());
		canvas.getFractal().getTransform().setRot(gui.getRjtf().saveAndGet());

		/* Window */
		main.setSize(gui.getWidthjtf().saveAndGet(), gui.getHeightjtf().saveAndGet());
	}

	/**
	 * Will update all values inside the user interface.<br>
	 * This is done by calling {@link Data#setData(Object)} for each {@link Data}
	 * inside the user interface.
	 */
	@Override
	public void update() {
		logger.log(Level.FINEST, "JPInterface.update");

		/* Fractal */
		gui.getFractaljcb().setDataSafe(canvas.getFractal());
		gui.getFilterjcb().setDataSafe(canvas.getFractal().getFunction().getFilter());
		canvas.getFractal().safeUpdate();

		/* Canvas */
		gui.getZoomjtf().setData(canvas.getZoomFactor());

		/* Location */
		gui.getXjtf().setData(canvas.getFractal().getTransform().getdx());
		gui.getYjtf().setData(canvas.getFractal().getTransform().getdy());
		gui.getMjtf().setData(canvas.getFractal().getTransform().getm());
		gui.getNjtf().setData(canvas.getFractal().getTransform().getn());
		gui.getRjtf().setData(canvas.getFractal().getTransform().getrot());

		/* Window */
		gui.getWidthjtf().setData(canvas.getWidth());
		gui.getHeightjtf().setData(canvas.getHeight());
	}

	/**
	 * Updates everything that needs to be updated if the <code>Fractal</code>
	 * changes. <br>
	 * This includes calling {@link JPInterface#updateFunction()} which intern also
	 * calls {@link JPInterface#updateFilter()}.
	 */
	public void updateFractal() {
		logger.log(Level.FINEST, "JPInterface.updateFractal");

		Fractal fractal = canvas.getFractal();

		gui.getLocationjcb().setDataSafe(fractal.getLocations());

		// fractal
		gui.getFractaljcb().getComponent().setToolTipText(fractal.getTip());
		gui.getFractaljp().getPanel().removeAll();
		fractal.addPanel(gui.getFractaljp().getPanel());

		// function & filter
		gui.getFunctionjcb().setItems(fractal.getFunctions());
		gui.getFunctionjcb().setDataSafe(fractal.getFunction());
		updateFunction();
	}

	/**
	 * Updates everything that needs to be updated if the <code>Function</code>
	 * changes. <br>
	 * This includes calling {@link JPInterface#updateFilter()}.
	 */
	public void updateFunction() {
		logger.log(Level.FINEST, "JPInterface.updateFunction");

		Fractal fractal = canvas.getFractal();
		Function function = fractal.getFunction();

		// fractal
		fractal.usingDerivative = function.isUsingDerivative();

		// function
		gui.getFunctionjcb().getComponent().setToolTipText(function.getTip());
		gui.getFunctionjp().getPanel().removeAll();
		function.addPanel(gui.getFunctionjp().getPanel());

		// filter
		gui.getFilterjcb().setItems(function.getFilters());
		gui.getFilterjcb().setDataSafe(function.getFilter());
		updateFilter();
	}

	/**
	 * Updates everything that needs to be updated if the <code>Filter</code>
	 * changes.
	 */
	public void updateFilter() {
		logger.log(Level.FINEST, "JPInterface.updateFilter");

		// filter
		Filter filter = canvas.getFractal().getFunction().getFilter();
		gui.getFilterjcb().getComponent().setToolTipText(filter.getTip());
		gui.getFilterjp().getPanel().removeAll();
		filter.addPanel(gui.getFilterjp().getPanel());
	}

	/**
	 * Calls {@link Canvas#savePrevConfig()} and sets the
	 * {@link JPInterface#allowUndo} to true.
	 */
	public void savePrevConfig() {
		allowUndo = true;
		canvas.savePrevConfig();
	}

	/**
	 * Calls {@link Canvas#undo()} and sets the {@link JPInterface#allowUndo} to
	 * false.
	 * 
	 * @return false if the undo could not be done if {@link JPInterface#allowUndo}
	 *         was already false.
	 */
	public boolean undo() {
		if (!allowUndo)
			return false;

		allowUndo = false;
		gui.getUndojb().setData(false);
		canvas.undo();
		return true;
	}

	/**
	 * @return the {@link GUI} instance
	 */
	public GUI getGUI() {
		return gui;
	}
}
