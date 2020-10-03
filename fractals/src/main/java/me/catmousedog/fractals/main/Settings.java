package me.catmousedog.fractals.main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.fractals.Fractal.Location;
import me.catmousedog.fractals.fractals.LinearTransform;
import me.catmousedog.fractals.fractals.types.iterative.IterativeInverseMandelbrot;
import me.catmousedog.fractals.fractals.types.iterative.IterativeJulia;
import me.catmousedog.fractals.fractals.types.iterative.IterativeMandelbrot;
import me.catmousedog.fractals.fractals.types.iterative.IterativeShip;
import me.catmousedog.fractals.fractals.types.iterative.TestFractal;
import me.catmousedog.fractals.fractals.types.normalized.NormalizedInverseMandelbrot;
import me.catmousedog.fractals.fractals.types.normalized.NormalizedJulia;
import me.catmousedog.fractals.fractals.types.normalized.NormalizedMandelbrot;
import me.catmousedog.fractals.fractals.types.normalized.NormalizedShip;
import me.catmousedog.fractals.fractals.types.potential.PotentialInverseMandelbrot;
import me.catmousedog.fractals.fractals.types.potential.PotentialMandelbrot;
import me.catmousedog.fractals.fractals.types.potential.PotentialShip;
import me.catmousedog.fractals.utils.OrderedProperties;

/**
 * Class for managing the {@code .properties} files within the resources.
 */
public class Settings {

	private final Main main;

	private String artifact_id;

	private String version;

	private int width = 800;

	private int height = 800;

	private boolean render_on_changes = true;

	/**
	 * An array of all the fractals, even if disabled in the 'settings.properties'.
	 */
	private final Fractal[] allFractals = new Fractal[] { new IterativeMandelbrot(this), new NormalizedMandelbrot(this),
			new PotentialMandelbrot(this), new IterativeJulia(this), new NormalizedJulia(this), new IterativeShip(this),
			new NormalizedShip(this), new PotentialShip(this), new IterativeInverseMandelbrot(this),
			new NormalizedInverseMandelbrot(this), new PotentialInverseMandelbrot(this), new TestFractal(this) };

	/**
	 * property in the settings.properties, if not enabled this is the first fractal
	 * that is enabled
	 */
	private Fractal defaultFractal;

	public Settings(Main main) {
		this.main = main;

		// pom.xml
		Properties project = new Properties();
		try {
			project.load(main.getClass().getClassLoader().getResourceAsStream("project.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		artifact_id = project.getProperty("artifactId");
		version = project.getProperty("version");

		try {
			initSettings();
		} catch (IOException e) {
			System.err.println("Settings.initSettings IOException");
			e.printStackTrace();
		}

		try {
			initFractals();
		} catch (IOException e) {
			System.err.println("Settings.initFractals IOException");
			e.printStackTrace();
		}

		// create images folder
		if (!images.exists())
			images.mkdir();

		File logs = new File("./logs");
		if (!logs.exists())
			logs.mkdirs();
	}

	/**
	 * settings.properties
	 */
	private Properties settings;

	/**
	 * Initialises the 'settings.properties'
	 * 
	 * @throws IOException if files failed to copy or load
	 */
	private void initSettings() throws IOException {
		// create file and load defaults
		File f = new File("./settings.properties");
		InputStream settingsStream = main.getClass().getClassLoader()
				.getResourceAsStream("default_settings.properties");
		if (!f.exists()) {
			Files.copy(settingsStream, Paths.get("./settings.properties"), StandardCopyOption.REPLACE_EXISTING);
		}

		// load settings from file
		Properties defaultSettings = new Properties();
		defaultSettings.load(settingsStream);
		settings = new Properties(defaultSettings);
		settings.load(new FileInputStream(f));

		// render_on_changes
		render_on_changes = Boolean.parseBoolean(settings.getProperty("render_on_change"));
		// intial size
		try {
			width = Integer.parseInt(settings.getProperty("width"));
			height = Integer.parseInt(settings.getProperty("height"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Initialises all the fractal related directories.<br>
	 * This includes the <code>concrete_fractals</code> and <code>locations</code>
	 * files.
	 * 
	 * @throws IOException if a directory could not be copied or a
	 *                     {@link Properties} could not be loaded.
	 */
	private void initFractals() throws IOException {
		// create 'concrete_fractals' directory
		File con = new File("./concrete_fractals");
		if (!con.exists())
			con.mkdir();
		// create 'locations' directory
		File loc = new File("./locations");
		if (!loc.exists())
			loc.mkdir();

		// scan and copy resources inside 'conrete_fractals' resource

		for (Fractal fractal : allFractals) {
			// filename
			String filename = fractal.fileName();

			// filename with .properties at the end
			String resource = filename + ".properties";

			// path to fractal resource
			String fractalResourcePath = "concrete_fractals/" + resource;

			// path to location resource
			String locationResourcePath = "locations/" + resource;

			// path to fractal file
			String fractalFilePath = "./" + fractalResourcePath;

			// path to location file
			String locationFilePath = "./" + locationResourcePath;

			// "true" if this fractal should be used
			String enabled = settings.getProperty(filename);

			InputStream s1 = main.getClass().getClassLoader().getResourceAsStream(fractalResourcePath);
			File f1 = new File(fractalFilePath);
			// if 'properties' resource exists and file doesn't copy over
			if (s1 != null && !f1.exists())
				Files.copy(s1, Paths.get(fractalFilePath), StandardCopyOption.REPLACE_EXISTING);

			InputStream s2 = main.getClass().getClassLoader().getResourceAsStream(locationResourcePath);
			File f2 = new File(locationFilePath);
			// if 'location' resource exists and file doesn't copy over
			if (s2 != null && !f2.exists())
				Files.copy(s2, Paths.get(locationFilePath), StandardCopyOption.REPLACE_EXISTING);

			if (enabled.equalsIgnoreCase("true")) {
				// create properties
				Properties defaultP = new Properties();
				defaultP.load(s1);
				Properties p = new Properties(defaultP);
				if (f1.exists()) {
					p.load(new FileInputStream(fractalFilePath));
				}

				// create locations
				OrderedProperties defaultL = new OrderedProperties();
				defaultL.load(s2);
				OrderedProperties l = new OrderedProperties(defaultL);
				if (f2.exists()) {
					l.load(new FileInputStream(locationFilePath));
				}

				setProperties(fractal, p, l);
				fractals.add(fractal);
			}
		}

		// defaultFractal
		String d = settings.getProperty("defaultFractal");
		defaultFractal = fractals.get(0);
		for (Fractal f : fractals) {
			if (f.fileName().equals(d)) {
				defaultFractal = f;
				break;
			}
		}
	}

	/**
	 * Imports all the settings from the properties files belonging to the given
	 * {@link Fractal}.
	 * <p>
	 * Only called once for each {@link Fractal}.
	 * 
	 * @param properties {@link Properties} object for
	 *                   './conrete_fractal/fileName.properties'
	 * @param locations  {@link OrderedProperties} object for
	 *                   './locations/fileName.properties'
	 */
	private void setProperties(Fractal fractal, Properties properties, OrderedProperties locations) {
		List<Location> temp = new ArrayList<Location>();

		LinearTransform transform = fractal.getTransform();

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

			fractal.setProperties(properties);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		// initialise locations
		// for all keys
		Enumeration<Object> keys = locations.keys();

		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
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
					temp.add(fractal.new Location(key, dx, dy, m, n, rot, iter));
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
		}

		Location[] l = new Location[temp.size()];
		temp.toArray(l);

		fractal.setLocations(l);
	}

	private File images = new File("./images");

	/**
	 * Creates a new image in the <code>images</code> folder with a unique name.<br>
	 * The name of the image will be the given <code>name</code> with a unique
	 * number concatenated.
	 * 
	 * @param img     The {@link BufferedImage} to be stored as a file.
	 * @param ext     The image extension, either 'jpg' or 'png'.
	 * @param fractal The {@link Fractal} with which this image was created.
	 */
	public void addImage(@NotNull BufferedImage img, @NotNull String ext, @NotNull Fractal fractal) {
		String imageName = fractal.fileName() + "_" + fractal.getTransform().toString();
		String path = images.getAbsolutePath() + "/" + imageName;
		File f;

		for (int i = 1;; i++) {
			f = new File(String.format("%s_%d.%s", path, i, ext));
			if (!f.exists())
				break;
		}

		try {
			ImageIO.write(img, ext, f);
		} catch (IOException e) {
			System.err.println("Settings.addImage IOException");
			e.printStackTrace();
		}
	}

	public String getArtifact_id() {
		return artifact_id;
	}

	public String getVersion() {
		return version;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public boolean isRender_on_changes() {
		return render_on_changes;
	}

	private List<Fractal> fractals = new ArrayList<Fractal>();

	/**
	 * @return An array of the active {@link Fractal}s
	 */
	public Fractal[] getFractals() {
		Fractal[] out = new Fractal[fractals.size()];
		for (int i = 0; i < fractals.size(); i++)
			out[i] = fractals.get(i);

		return out;
	}

	/**
	 * The <code>defaultFractal</code> will not be null if
	 * {@link Settings#initFractals()} has been called.
	 * 
	 * @return the default {@link Fractal}
	 */
	@NotNull
	public Fractal getDefaultFractal() {
		return defaultFractal;
	}

}
