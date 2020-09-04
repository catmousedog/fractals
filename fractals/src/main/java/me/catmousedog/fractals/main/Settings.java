package me.catmousedog.fractals.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.fractals.types.iterative.IterativeMandelbrot;
import me.catmousedog.fractals.fractals.types.iterative.IterativeShip;
import me.catmousedog.fractals.fractals.types.normalized.NormalizedMandelbrot;
import me.catmousedog.fractals.fractals.types.normalized.NormalizedShip;
import me.catmousedog.fractals.fractals.types.potential.PotentialMandelbrot;
import me.catmousedog.fractals.fractals.types.potential.PotentialShip;

/**
 * Class for managing the {@code .properties} files within the resources.
 */
public class Settings {

	private final Main main;

	/**
	 * An array of all the fractals, even if disabled in the 'settings.properties'.
	 */
	private final Fractal[] allFractals = new Fractal[] { new IterativeMandelbrot(this), new NormalizedMandelbrot(this),
			new PotentialMandelbrot(this), new IterativeShip(this), new NormalizedShip(this), new PotentialShip(this) };

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
		if (!f.exists()) {
			Files.copy(main.getClass().getClassLoader().getResourceAsStream("default_settings.properties"),
					Paths.get("./settings.properties"), StandardCopyOption.REPLACE_EXISTING);
		}

		// load settings from file
		settings = new Properties();
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
				Properties p = new Properties();
				if (f1.exists())
					p.load(new FileInputStream(fractalFilePath));
				// create locations
				Properties l = new Properties();
				if (f2.exists())
					l.load(new FileInputStream(locationFilePath));

				fractal.setProperties(p, l);
				fractals.add(fractal);
			}
		}
	}

	private String artifact_id;

	public String getArtifact_id() {
		return artifact_id;
	}

	private String version;

	public String getVersion() {
		return version;
	}

	private int width = 800;

	public int getWidth() {
		return width;
	}

	private int height = 800;

	public int getHeight() {
		return height;
	}

	private boolean render_on_changes = true;

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

}
