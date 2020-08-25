package me.catmousedog.fractals.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.JPanel;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.canvas.Canvas;
import me.catmousedog.fractals.main.Settings;
import me.catmousedog.fractals.ui.JPInterface;
import me.catmousedog.fractals.ui.Savable;
import me.catmousedog.fractals.ui.components.Data;
import me.catmousedog.fractals.ui.components.Item;

/**
 * Represents a fractal including its fractal function and colour filter.
 * <p>
 * An implementation of this class must define {@link Fractal#items} and any
 * active {@link Data} (listeners) added to it should only call
 * {@link Fractal#saveAndColour()}.
 */
public abstract class Fractal implements Savable {

	/**
	 * Settings object to access user settings
	 */
	protected final Settings settings;

	/**
	 * The transformation, used to represent the location
	 */
	protected final LinearTransform transform;

	/**
	 * Array of all {@link Item}s in order of addition.
	 * <p>
	 * Can not be null, hence must be assigned a value.
	 */
	@NotNull
	protected Item[] items;

	/**
	 * The instance of the canvas, used to call {@link Canvas#colourAndPaint()} when
	 * {@link Data} changes.
	 */
	protected Canvas canvas;

	protected int iterations = 100;

	/**
	 * Array of all locations used by this {@link Fractal}.<br>
	 * This array is created at
	 * {@link Fractal#setProperties(Properties, Properties)} or when the
	 * {@link Fractal} is cloned.
	 * <p>
	 * It is effectively final after being assigned its value.
	 */
	@NotNull
	protected Location[] locations;

	public Fractal(Settings settings, int iterations) {
		this.settings = settings;
		this.iterations = iterations;
		transform = new LinearTransform();
	}

	protected Fractal(Settings settings, int iterations, LinearTransform transform, Location[] locations) {
		this.settings = settings;
		this.iterations = iterations;
		this.transform = transform;
		this.locations = locations;
	}

	/**
	 * calculates the fractal function for a given point in space
	 * 
	 * @param x the x coordinate of the point
	 * @param y the y coordinate of the point
	 * @return the value the fractal function returns
	 */
	public abstract Number get(double x, double y);

	/**
	 * Applies the calculated value 'v' to a colour filter and returns an integer
	 * rgb value.
	 * 
	 * @param v value calculated by the fractal
	 * @return the rgb value as an integer
	 */
	public abstract int filter(Number v);

	/**
	 * Adds all the necessary components to a given {@link JPanel} on the
	 * {@link JPInterface}.
	 * 
	 * @param jp the JPanel to add the {@link Item}s to
	 */
	public void addFilter(JPanel jp) {
		jp.removeAll();
		for (Item i : items)
			jp.add(i.panel());
	}

	/**
	 * Whether or not the listeners in the {@link Fractal#addFilter(JPanel)}
	 * {@link JPanel} are allowed to fire.
	 */
	private boolean allowListener = false;

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
	 * This only works if the listeners contain {@link Fractal#saveAndColour()}
	 */
	public void safeUpdate() {
		allowListener = false;
		update();
		allowListener = true;
	}

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
	 * True if the <code>fractal</code> is the same concrete {@link Fractal}.<br>
	 * The is achieved by checking if the {@link Fractal#formalName()}s are equal.
	 * <p>
	 * Keep in mind that this means two {@link Fractal}s of the same type will
	 * return true even if they represent different locations or iterations.
	 */
	@Override
	public boolean equals(Object fractal) {
		if (fractal instanceof Fractal)
			return ((Fractal) fractal).formalName().equals(formalName());
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
	public abstract String formalName();

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
	 * Imports all the settings from the properties files belonging to this
	 * {@link Fractal}.
	 * <p>
	 * Only called once for each {@link Fractal} upon instantiating the
	 * {@link Settings} object. Each {@link Fractal} created after that using
	 * {@link Fractal#clone()} just inherits the fields.
	 * 
	 * @param properties {@link Properties} object for
	 *                   './conrete_fractal/formalName.properties'
	 * @param locations  {@link Properties} object for
	 *                   './locations/formalName.properties'
	 */
	public void setProperties(@NotNull Properties properties, @NotNull Properties locations)
			throws IllegalArgumentException {

		List<Location> temp = new ArrayList<Location>();

		// set default settings
		try {
			double dx = Double.parseDouble(properties.getProperty("default_x"));
			double dy = Double.parseDouble(properties.getProperty("default_y"));
			transform.setTranslation(dx, dy);
			double m = Double.parseDouble(properties.getProperty("default_m"));
			double n = Double.parseDouble(properties.getProperty("default_n"));
			transform.setScalar(m, n);
			double rot = Double.parseDouble(properties.getProperty("default_rot"));
			transform.setRot(rot);

			iterations = Integer.parseInt(properties.getProperty("default_iter"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		// initialise locations
		// for all keys
		for (Object o : locations.keySet()) {
			String key = (String) o;
			String id = locations.getProperty(key);
			String[] args = id.split(":");

			// legal format
			if (args.length > 5) {
				try {
					// parse id
					double dx = Double.parseDouble(args[0]);
					double dy = Double.parseDouble(args[1]);
					double m = Double.parseDouble(args[2]);
					double n = Double.parseDouble(args[3]);
					double rot = Double.parseDouble(args[4]);
					int iter = Integer.parseInt(args[5]);

					// add new location
					temp.add(new Location(key, dx, dy, m, n, rot, iter));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
		}

		this.locations = new Location[temp.size()];
		temp.toArray(this.locations);
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

	/**
	 * @return the list of {@link Location}s saved by this {@link Fractal}.
	 */
	public Location[] getLocations() {
		return locations;
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
