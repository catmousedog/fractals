package me.catmousedog.fractals.fractals.abstract_fractals;

import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Properties;

import javax.swing.JPanel;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.catmousedog.fractals.canvas.Canvas;
import me.catmousedog.fractals.canvas.Mouse;
import me.catmousedog.fractals.fractals.FractalValue;
import me.catmousedog.fractals.fractals.LinearTransform;
import me.catmousedog.fractals.fractals.filters.Filter;
import me.catmousedog.fractals.fractals.functions.Function;
import me.catmousedog.fractals.main.Settings;
import me.catmousedog.fractals.ui.JPInterface;
import me.catmousedog.fractals.ui.SafeSavable;
import me.catmousedog.fractals.ui.components.Data;
import me.catmousedog.fractals.ui.components.Item;
import me.catmousedog.fractals.ui.components.UI;
import me.catmousedog.fractals.ui.components.concrete.TextFieldInteger;
import me.catmousedog.fractals.utils.Nameable;

public abstract class Fractal extends UI implements SafeSavable, Nameable {

	/**
	 * A <code>MouseMotionListener</code> for <code>Fractals</code> that allow mouse
	 * motion input. <br>
	 * A <code>Fractal</code> can not have a regular {@link MouseListener} as this
	 * is already used by the {@link Mouse}.
	 * <p>
	 * Null for clones or when not used.
	 */
	@Nullable
	protected MouseMotionListener mouse;

	/**
	 * The <code>LinearTransform</code> used to represent the location.
	 */
	@NotNull
	protected final LinearTransform transform;

	/**
	 * All the <code>Functions</code> belonging to this <code>Fractal</code>.
	 * <p>
	 * Null for clones.
	 */
	@Nullable
	protected Function[] functions;

	/**
	 * The current <code>Function</code>.
	 */
	protected Function function;

	/**
	 * Array of <code>Items</code> that are common to all <code>Fractals</code>,
	 * hence not fractal-specific. <br>
	 * Examples include: <br>
	 * <i>iterations, bailout, etc.</i>
	 * <p>
	 * Null for clones.
	 */
	@NotNull
	private final Item[] commonItems;

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
	 * from {@link Settings#isRender_on_changes()}
	 */
	protected boolean render_on_changes = false;

	/**
	 * Array of all locations used by this {@link Fractal}.<br>
	 * This array is created at
	 * {@link Fractal#setProperties(Properties, Properties)} or when the
	 * {@link Fractal} is cloned.
	 * <p>
	 * Null for clones.
	 */
	@NotNull
	protected Location[] locations;

	/**
	 * The instance of the canvas, used to call {@link Canvas#paint()} when
	 * {@link Data} changes.
	 * <p>
	 * Null for clones.
	 */
	@Nullable
	protected Canvas canvas;

	/**
	 * The instance of the <code>JPInterface</code>, used to call
	 * {@link JPInterface#renderNow()}.
	 * <p>
	 * Null for clones.
	 */
	@NotNull
	protected JPInterface jpi;

	/**
	 * Constructor used to initialise the <code>Fractal</code>.<br>
	 * Only used once for each <code>Fractal</code> in the {@link Settings}.
	 * <p>
	 * Any implementation that uses this constructor should initialise:
	 * <ul>
	 * <li>the {@link UI#items}
	 * <li>the {@link Fractal#functions} and the {@link Fractal#function}
	 * <li>the specific fields belonging to this <code>Function</code>
	 * <li>the {@link Fractal#mouse}
	 * </ul>
	 */
	protected Fractal() {
		super();
		transform = new LinearTransform();

		TextFieldInteger iterjtf = new TextFieldInteger.Builder().setLabel("iterations")
				.setTip("<html>The iteration count. Each fractal can use this differently."
						+ "<br>Usually a higher iteration count means better quality but longer generating time.</html>")
				.setMin(0).build();

		commonItems = new Item[] { iterjtf };
	}

	/**
	 * Constructor used to create a clone.<br>
	 * This constructor must be overridden in the child class so it takes itself as
	 * the parameter <code>fractal</code>. This way it can make an exact copy
	 * without any reference to the original <code>Fractal</code>.
	 * 
	 * @param fractal the <code>Fractal</code> it should copy.
	 */
	protected Fractal(@NotNull Fractal fractal) {
		super();
		mouse = null;
		commonItems = null;
		locations = null;
		functions = null;
		function = fractal.function.clone();
		transform = fractal.transform.clone();
		iterations = fractal.iterations;
		bailout = fractal.bailout;
		render_on_changes = fractal.render_on_changes;
	}

	/**
	 * calculates the fractal value for a given point in space
	 * 
	 * @param cx the x coordinate of the point
	 * @param cy the y coordinate of the point
	 * @return the value the fractal function returns
	 */
	public abstract FractalValue get(double cx, double cy);

	/**
	 * Calls {@link Filter#save()}.
	 */
	@Override
	public void save() {
		super.save();
		function.save();
	}

	/**
	 * Calls {@link Filter#update()}.
	 */
	@Override
	public void update() {
		super.update();
		function.update();
	}

	/**
	 * Calls {@link Filter#preRender()}.
	 */
	@Override
	public void preRender() {
		super.preRender();
		function.preRender();
	}

	/**
	 * Calls {@link Filter#preRender()}.
	 */
	@Override
	public void postRender() {
		super.postRender();
		function.postRender();
	}

	/**
	 * Saves all data through {@link Fractal#save()} and colours the image through
	 * {@link Canvas#paint()} if {@link Settings#isRender_on_changes()}.
	 * 
	 * @return true if the canvas successfully called {@link Canvas#paint()}, false
	 *         otherwise
	 */
	public void saveAndColour() {
		if (allowListeners) {
			save();
			if (render_on_changes)
				canvas.paint();
		}
	}

	/**
	 * Adds all the {@link Fractal#commonItems} and the {@link UI#items} to the
	 * <code>jp</code>.
	 */
	public void addPanel(@NotNull JPanel jp) {
		if (commonItems != null) {
			for (Item item : commonItems)
				jp.add(item.panel());
		}
		super.addPanel(jp);
	}

	/**
	 * Called for each <code>fractal</code> once upon creation. Sets all of the
	 * default values from the <code>properties</code>. <br>
	 * Can be overriden to add fractal specific properties.
	 * 
	 * @param properties
	 */
	public void setProperties(@NotNull Settings settings, @NotNull Properties properties) {
		iterations = Integer.parseInt(properties.getProperty("default_iter"));
		bailout = Double.parseDouble(properties.getProperty("bailout"));
		render_on_changes = settings.isRender_on_changes();
	}

	/**
	 * Sets the {@link Fractal#function} to the <code>Function</code> whose class
	 * equals the given <code>clazz</code>.
	 * 
	 * @param clazz the <code>Class</code> of the <code>Function</code>.
	 */
	public void pickFunction(Class<? extends Function> clazz) {
		for (Function f : functions) {
			if (f.getClass().equals(clazz)) {
				function = f;
				return;
			}
		}
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
	 * @return true if the {@link MouseMotionListener} is enabled for this
	 *         <code>Fractal</code>.
	 */
	public boolean isMouseEnabled() {
		return mouse != null;
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

	public void setJPI(@NotNull JPInterface jpi) {
		this.jpi = jpi;
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

	public Function[] getFunctions() {
		return functions;
	}

	public Function getFunction() {
		return function;
	}

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
