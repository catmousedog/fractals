package me.catmousedog.fractals.fractals;

import java.util.Properties;

import javax.swing.JPanel;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.catmousedog.fractals.canvas.Canvas;
import me.catmousedog.fractals.fractals.filters.Filter;
import me.catmousedog.fractals.main.Settings;
import me.catmousedog.fractals.ui.SafeSavable;
import me.catmousedog.fractals.ui.Savable;
import me.catmousedog.fractals.ui.components.Data;

/**
 * Represents a fractal including its fractal function
 * {@link Fractal#get(double, double)} and an array of {@link Filter}s to use.
 * <p>
 * An implementation of this class must define:
 * <ul>
 * <li>A constructor for creating the {@link Fractal}.
 * <li>A constructor for cloning the {@link Fractal}, taking the {@link Fractal}
 * itself as a parameter.
 * <li>The {@link Fractal#get(double, double)} function.
 * <li>The name methods: {@link Fractal#informalName()},
 * {@link Fractal#fileName()} and {@link Fractal#getTip()}.
 * <li>{@link Fractal#initFilters()} method.
 * <li>{@link Fractal#clone()} method with the cloning constructor.
 * </ul>
 */
public abstract class Fractal implements SafeSavable {

	/**
	 * Settings object to access user settings
	 */
	protected final Settings settings;

	/**
	 * The instance of the canvas, used to call {@link Canvas#colourAndPaint()} when
	 * {@link Data} changes.
	 * <p>
	 * It is effectively final after being assigned its value through
	 * {@link Fractal#setCanvas(Canvas)}.
	 */
	protected Canvas canvas;

	/**
	 * Array of all locations used by this {@link Fractal}.<br>
	 * This array is created at
	 * {@link Fractal#setProperties(Properties, Properties)} or when the
	 * {@link Fractal} is cloned.
	 * <p>
	 * It is effectively final after being assigned its value through
	 * {@link Fractal#setProperties(Properties, Properties)}.
	 */
	@NotNull
	protected Location[] locations;

	/**
	 * The array of {@link Filter}s, only not null for the original {@link Fractal}
	 * created in the {@link Settings}s.
	 * <p>
	 * It is effectively final after being assigned its value through
	 * {@link Fractal#initFilters()} and it is null for all clones.
	 */
	@Nullable
	protected Filter[] filters;

	/**
	 * The {@link LinearTransform} used to represent the location
	 */
	protected final LinearTransform transform;

	/**
	 * The current {@link Filter}, an element from {@link Fractal#filters}.
	 */
	@NotNull
	protected Filter filter;

	/**
	 * The amount of iterations. Each {@link Fractal} might use this differently.
	 */
	protected int iterations = 100;

	/**
	 * The bailout radius, if the fractal value goes beyond this value the iteration
	 * is stopped. Each {@link Fractal} might use this differently.
	 */
	protected double bailout = 100;

	/**
	 * Constructor used to initialise the {@link Fractal}.<br>
	 * Only used once for each {@link Fractal} in the {@link Settings}.
	 * <p>
	 * This constructor also calls {@link Fractal#initFilters()}.
	 * 
	 * @param settings
	 */
	public Fractal(Settings settings) {
		this.settings = settings;
		transform = new LinearTransform();
		initFilters();
	}

	/**
	 * Creates a new {@link Fractal} without calling {@link Fractal#initFilters()}
	 * that is an exact copy but has no reference (except for
	 * {@link Fractal#settings}) to the original {@link Fractal}.
	 * <code>filter</code>.
	 * 
	 * @param filter
	 */
	protected Fractal(Settings settings, Fractal fractal) {
		this.settings = settings;
		transform = fractal.getTransform().clone();
		filter = fractal.getFilter().clone();
		iterations = fractal.getIterations();
		bailout = fractal.getBailout();
	}

	/**
	 * calculates the fractal value for a given point in space
	 * 
	 * @param x the x coordinate of the point
	 * @param y the y coordinate of the point
	 * @return the value the fractal function returns
	 */
	public abstract Number get(double x, double y);

	/**
	 * Initialses the {@link Fractal#filters} array and the
	 * {@link Fractal#filter}.<br>
	 * Only done once when the {@link Fractal} is created.
	 */
	protected abstract void initFilters();

	/**
	 * Whether or not the listeners in the {@link Fractal#setPanel(JPanel)}
	 * {@link JPanel} are allowed to fire.
	 */
	private boolean allowListener = false;

	/**
	 * Calls {@link Filter#save()}.
	 */
	@Override
	public void save() {
		filter.save();
	}

	/**
	 * Calls {@link Filter#update()}.
	 */
	@Override
	public void update() {
		filter.update();
	}

	/**
	 * Calls {@link Filter#preRender()}.
	 */
	@Override
	public void preRender() {
		filter.preRender();
	}

	/**
	 * Calls {@link Filter#preRender()}.
	 */
	@Override
	public void postRender() {
		filter.postRender();
	}

	/**
	 * Saves all data through {@link Savable#save()} and colours the image through
	 * {@link Canvas#colourAndPaint()} if {@link Settings#isRender_on_changes()}.
	 * 
	 * @return true if the canvas successfully called
	 *         {@link Canvas#colourAndPaint()}, false otherwise
	 */
	public boolean saveAndColour() {
		if (allowListener) {
			save();
			if (settings.isRender_on_changes())
				return canvas.colourAndPaint();
		}
		return false;
	}

	/**
	 * Updates the panel using {@link Fractal#update()} but doesn't allow the
	 * listeners to trigger when changing the data inside them. This method should
	 * always be used when updating the data the user can interact with.
	 * <p>
	 * This only works if the listeners contain {@link Fractal#saveAndColour()}.
	 */
	@Override
	public void safeUpdate() {
		allowListener = false;
		update();
		allowListener = true;
	}

	/**
	 * True if the <code>fractal</code> is the same concrete {@link Fractal}.<br>
	 * The is achieved by checking if the {@link Fractal#fileName()}s are equal.
	 * <p>
	 * Keep in mind that this means two {@link Fractal}s of the same type will
	 * return true even if they represent different locations or iterations.
	 */
	@Override
	public boolean equals(Object fractal) {
		if (fractal instanceof Fractal)
			return ((Fractal) fractal).fileName().equals(fileName());
		return false;
	}

	/**
	 * Just returns {@link Fractal#informalName()}
	 */
	@Override
	public String toString() {
		return informalName();
	}

	/**
	 * @return The informal name, for the user to read.
	 */
	@NotNull
	public abstract String informalName();

	/**
	 * @return The file name, usually the informal name without spaces.
	 */
	@NotNull
	public abstract String fileName();

	/**
	 * A description of the fractal to be displayed as a Tooltip.
	 * 
	 * @return The tip as a {@link String}
	 */
	@NotNull
	public abstract String getTip();

	/**
	 * Return a copy of this {@link Fractal}.
	 */
	@Override
	@NotNull
	public abstract Fractal clone();

	/**
	 * Changes this {@link Fractal} to match the given <code>id</code>.<br>
	 * 
	 * @param id {@link String} of format: <code>dx:dy:m:n:rot:iter</code>
	 * 
	 * @throws IllegalArgumentException if the <code>id</code> is not of the correct
	 *                                  format.
	 */
	public void fromID(@NotNull String id) throws IllegalArgumentException {
		String[] args = id.split(":");

		if (args.length < 4)
			throw new IllegalArgumentException("String not of format 'dx:dy:m:n:rot'");

		try {
			transform.setTranslation(Double.parseDouble(args[0]), Double.parseDouble(args[1]));
			transform.setScalar(Double.parseDouble(args[2]), Double.parseDouble(args[3]));
			if (args.length > 4)
				transform.setRot(Double.parseDouble(args[4]));
			if (args.length > 5)
				iterations = Integer.parseInt(args[5]);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Creates an <code>id</code> to represent the location of this {@link Fractal}.
	 * 
	 * @return String of format: "dx:dy:m:n:rot:iter"
	 */
	public String getID() {
		return Double.toString(transform.getdx()) + ":" + Double.toString(transform.getdy()) + ":"
				+ Double.toString(transform.getm()) + ":" + Double.toString(transform.getn()) + ":"
				+ Double.toString(transform.getrot()) + ":" + Integer.toString(iterations);
	}

	/**
	 * @return Get the final {@link LinearTransform} of this {@link Fractal}
	 */
	@NotNull
	public LinearTransform getTransform() {
		return transform;
	}

	public void setCanvas(@NotNull Canvas canvas) {
		this.canvas = canvas;
	}

	public void setIterations(int i) {
		iterations = i;
	}

	public int getIterations() {
		return iterations;
	}

	public void setBailout(double bailout) {
		this.bailout = bailout;
	}

	public double getBailout() {
		return bailout;
	}

	public void setLocations(Location[] locations) {
		this.locations = locations;
	}

	/**
	 * @return the array of {@link Location}s saved by this {@link Fractal}.
	 */
	public Location[] getLocations() {
		return locations;
	}

	public Filter[] getFilters() {
		return filters;
	}

	/**
	 * Sets the {@link Fractal#filter} to the given if and only if the given
	 * {@link Filter#getClass()} equals a {@link Filter#getClass()} inside
	 * {@link Fractal#filters}.
	 * 
	 * @param filter
	 */
	public void pickFilter(Filter filter) {
		for (Filter f : filters) {
			if (f.getClass().equals(filter.getClass())) {
				this.filter = f;
				return;
			}
		}
	}

	public Filter getFilter() {
		return filter;
	}

	/**
	 * Class used to store an immutable location.
	 */
	public class Location {

		private final String name;

		private final LinearTransform transform;

		private final int iterations;

		public Location(String name, double dx, double dy, double m, double n, double rot, int iterations) {
			this.name = name;
			transform = new LinearTransform(dx, dy, m, n, rot);
			this.iterations = iterations;
		}

		public LinearTransform getTransform() {
			return transform;
		}

		public int getIterations() {
			return iterations;
		}

		@Override
		public String toString() {
			return name;
		}

	}
}
