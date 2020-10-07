package me.catmousedog.fractals.fractals;

import java.awt.Font;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Properties;

import javax.swing.JPanel;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.catmousedog.fractals.canvas.Canvas;
import me.catmousedog.fractals.canvas.Mouse;
import me.catmousedog.fractals.fractals.filters.Filter;
import me.catmousedog.fractals.main.Settings;
import me.catmousedog.fractals.ui.JPInterface;
import me.catmousedog.fractals.ui.SafeSavable;
import me.catmousedog.fractals.ui.Savable;
import me.catmousedog.fractals.ui.components.Data;
import me.catmousedog.fractals.ui.components.Item;
import me.catmousedog.fractals.ui.components.concrete.Label;

/**
 * Represents a fractal including its fractal function
 * {@link Fractal#get(double, double)} and an array of {@link Filter}s to use.
 * <p>
 * An implementation of this class must define:
 * <ul>
 * <li>A constructor taking a {@link Settings} object for creating the
 * <code>Fractal</code>.
 * <li>A <code>private</code> constructor for cloning the <code>Fractal</code>,
 * taking the {@link Settings} object and the <code>Fractal</code> itself as a
 * parameter.
 * <li>The {@link Fractal#get(double, double)} function.
 * <li>The name methods: {@link Fractal#informalName()},
 * {@link Fractal#fileName()} and {@link Fractal#getTip()}.
 * <li>{@link Fractal#initFractal()} method where the {@link Fractal#filter} and
 * the {@link Fractal#filters} array are initialised.
 * <li>{@link Fractal#clone()} method with the cloning constructor.
 * </ul>
 */
public abstract class Fractal implements SafeSavable {

	/**
	 * Settings object to access user settings
	 */
	@NotNull
	protected final Settings settings;

	/**
	 * The {@link LinearTransform} used to represent the location
	 */
	@NotNull
	protected final LinearTransform transform;

	/**
	 * A {@link MouseMotionListener} for <code>Fractals</code> that allow mouse
	 * motion input. <br>
	 * A <code>Fractal</code> can not have a regular {@link MouseListener} as this
	 * is already used by the {@link Mouse}.
	 * <p>
	 * Null if this <code>Fractal</code> does not have a
	 * <code>MouseMotionListener</code>.
	 * <p>
	 * It is effectively final after being assigned its value.
	 */
	@Nullable
	protected MouseMotionListener mouse;

	/**
	 * The instance of the canvas, used to call {@link Canvas#paint()} when
	 * {@link Data} changes.
	 * <p>
	 * It is effectively final after being assigned its value through
	 * {@link Fractal#setCanvas(Canvas)}.
	 */
	@Nullable
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
	 * {@link Fractal#initFractal()} and it is null for all clones.
	 */
	@Nullable
	protected Filter[] filters;

	/**
	 * The current {@link Filter}, an element from {@link Fractal#filters}.
	 */
	@NotNull
	protected Filter filter;

	/**
	 * Array of all {@link Item}s in order of addition.<br>
	 * Only not null for the original <code>Fractal</code> if defined in
	 * {@link Fractal#initFractal()}.
	 */
	@Nullable
	protected Item[] items;

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
	 * This constructor also calls {@link Fractal#initFractal()}.
	 * 
	 * @param settings
	 */
	public Fractal(Settings settings) {
		this.settings = settings;
		mouse = null;
		transform = new LinearTransform();
		initFractal();
	}

	/**
	 * Creates a new {@link Fractal} without calling {@link Fractal#initFractal()}
	 * that is an exact copy but has no reference (except for
	 * {@link Fractal#settings}) to the original {@link Fractal}.
	 * <code>filter</code>.
	 * 
	 * @param filter
	 */
	protected Fractal(Settings settings, Fractal fractal) {
		this.settings = settings;
		mouse = null;
		transform = fractal.getTransform().clone();
		filter = fractal.getFilter().clone();
		iterations = fractal.getIterations();
		bailout = fractal.getBailout();
	}

	/**
	 * calculates the fractal value for a given point in space
	 * 
	 * @param cx the x coordinate of the point
	 * @param cy the y coordinate of the point
	 * @return the value the fractal function returns
	 */
	public abstract Number get(double cx, double cy);

	/**
	 * Initialises the {@link Fractal#filters} array, the {@link Fractal#filter},
	 * the {@link Fractal#mouse} and the {@link Fractal#items} array. The latter two
	 * may be <code>null</code> if not implemented.
	 * <p>
	 * Only done once when the <code>Fractal</code> is created, not for clones of
	 * <code>Fractals</code>.
	 */
	protected abstract void initFractal();

	/**
	 * Whether or not the listeners in the {@link Fractal#setPanel(JPanel)}
	 * {@link JPanel} are allowed to fire.
	 * <p>
	 * Only stops listeners from calling {@link Fractal#saveAndColour()}.
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
		if (items != null) {
			for (Item item : items)
				item.preRender();
		}
	}

	/**
	 * Calls {@link Filter#preRender()}.
	 */
	@Override
	public void postRender() {
		filter.postRender();
		if (items != null) {
			for (Item item : items)
				item.postRender();
		}
	}

	/**
	 * Saves all data through {@link Savable#save()} and colours the image through
	 * {@link Canvas#paint()} if {@link Settings#isRender_on_changes()}.
	 * 
	 * @return true if the canvas successfully called
	 *         {@link Canvas#paint()}, false otherwise
	 */
	public void saveAndColour() {
		if (allowListener) {
			save();
			if (settings.isRender_on_changes())
				canvas.paint();
		}
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

	private Label fr = new Label("Fractal", "Settings for the current fractal.", Font.BOLD, 12);

	/**
	 * Removes all current components and adds all the necessary components to a
	 * given {@link JPanel} on the {@link JPInterface}.
	 * 
	 * @param jp The JPanel to add the {@link Item}s to.
	 */
	public void setPanel(@NotNull JPanel jp) {
		jp.removeAll();

		if (items != null) {
			jp.add(fr.panel());
			for (Item i : items)
				jp.add(i.panel());
		}

		filter.addPanel(jp);
	}

	/**
	 * Called for each <code>fractal</code> once upon creation. Sets all of the
	 * default values from the <code>properties</code>. <br>
	 * Can be overriden to add fractal specific properties.
	 * 
	 * @param properties
	 */
	public void setProperties(Properties properties) {
		iterations = Integer.parseInt(properties.getProperty("default_iter"));
		bailout = Double.parseDouble(properties.getProperty("bailout"));
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
	 * @return The informal name, for the user to read.<br>
	 *         Can not be longer than the {@link JPInterface} width.
	 */
	@NotNull
	public abstract String informalName();

	/**
	 * @return The file name, usually the informal name without spaces.
	 */
	@NotNull
	public abstract String fileName();

	/**
	 * @return The group name for organising the <code>fractals</code>.<br>
	 *         This is just the last name of the package.<br>
	 *         So for example:
	 *         <p>
	 *         <code>me.catmousedog.fractals.fractals.types.iterative.TestFractal</code>
	 *         <p>
	 *         Would have the <code>groupName</code> <code>'iterative'</code>.
	 */
	@NotNull
	public String groupName() {
		String p = getClass().getPackageName();
		return p.substring(p.lastIndexOf(".") + 1);
	}

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
	 * @return The {@link MouseMotionListener} belonging to this
	 *         <code>Fractal</code> or <code>null</code> if absent.
	 */
	@Nullable
	public MouseMotionListener getMouse() {
		return mouse;
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

	/**
	 * Changes the current {@link LinearTransform} and iterations to correspond with
	 * the given <code>location</code>.
	 * 
	 * @param location
	 */
	public void setLocation(Location location) {
		transform.set(location.getTransform());
		iterations = location.getIterations();
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

//	public String

	/**
	 * Class used to store an immutable location. A <code>Location</code> should
	 * never contain any references, only clones.
	 */
	public class Location {

		private final String name;

		private final LinearTransform transform;

		private int iterations;

		/**
		 * Creates a <code>location</code> of the current <code>fractal</code>.
		 */
		public Location() {
			this.name = null;
			transform = Fractal.this.transform.clone();
			iterations = Fractal.this.iterations;
		}

		/**
		 * Creates a new location with a <code>name</code>, {@link LinearTransform} and
		 * <code>iterations</code>.
		 * 
		 * @param name
		 * @param dx
		 * @param dy
		 * @param m
		 * @param n
		 * @param rot
		 * @param iterations
		 */
		public Location(String name, double dx, double dy, double m, double n, double rot, int iterations) {
			this.name = name;
			transform = new LinearTransform(dx, dy, m, n, rot);
			this.iterations = iterations;
		}

		/**
		 * Creates a <code>location</code> that matches the <code>id</code>. The
		 * <code>id</code> is of the same format as the one returned by
		 * {@link Location#getID()}.
		 * 
		 * @param id <code>String</code> of format 'dx:dy:m:n:rot:iter' with the last
		 *           two, optional parameters, by default the values of the current
		 *           <code>fractal</code>.
		 * @throws IllegalArgumentException if the <code>id</code> is not of the correct
		 *                                  'dx:dy:m:n:rot:iter' format or if a number
		 *                                  could not be parsed.
		 */
		public Location(String id) throws IllegalArgumentException {
			name = null;
			transform = Fractal.this.transform.clone();
			iterations = Fractal.this.iterations;

			String[] args = id.split(":|;");

			if (args.length < 4)
				throw new IllegalArgumentException("String not of format 'dx:dy:m:n'");

			transform.setTranslation(Double.parseDouble(args[0]), Double.parseDouble(args[1]));
			transform.setScalar(Double.parseDouble(args[2]), Double.parseDouble(args[3]));
			if (args.length > 4)
				transform.setRot(Double.parseDouble(args[4]));
			if (args.length > 5)
				iterations = Integer.parseInt(args[5]);

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

		/**
		 * @return The location <code>id</code>, a <code>String</code> of the form:
		 *         <code>x:y:m:mn:rot:iter</code>.
		 */
		public String getID() {
			return Double.toString(transform.getdx()) + ":" + Double.toString(transform.getdy()) + ":"
					+ Double.toString(transform.getm()) + ":" + Double.toString(transform.getn()) + ":"
					+ Double.toString(transform.getrot()) + ":" + Integer.toString(iterations);
		}
	}
}
