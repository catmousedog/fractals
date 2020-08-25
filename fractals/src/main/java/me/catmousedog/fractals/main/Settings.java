package me.catmousedog.fractals.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import me.catmousedog.fractals.fractals.Fractal;
import me.catmousedog.fractals.fractals.concrete.IterativeMandelbrot;
import me.catmousedog.fractals.fractals.concrete.NormalizedMandelbrot;
import me.catmousedog.fractals.fractals.concrete.PotentialMandelbrot;

/**
 * Class for managing the {@code .properties} files within the resources.
 */
public class Settings {

	private final Main main;

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

		initLocations();
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
	 * An array of all the fractals, even if disabled in the 'settings.properties'.
	 */
	private Fractal[] allFractals = new Fractal[] { new IterativeMandelbrot(this, 100),
			new NormalizedMandelbrot(this, 100), new PotentialMandelbrot(this, 100) };

	/**
	 * Initialises the 'concrete_fractals' directory. <br>
	 * This just copies the .properties files from the resources.
	 * 
	 * @throws IOException if 'concrete_fractals' could not be copied or loaded
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
		try (InputStream in = main.getClass().getClassLoader().getResourceAsStream("concrete_fractals");
				BufferedReader br = new BufferedReader(new InputStreamReader(in))) {

			String resource;

			// for all files inside 'concrete_fractals'
			while ((resource = br.readLine()) != null) {
				// filename without .properties at the end
				String filename = resource.substring(0, resource.length() - 11);

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

				if (enabled != null && enabled.equalsIgnoreCase("true")) {

					// copy 'concrete_fractals' resources
					if (!new File(fractalFilePath).exists())
						Files.copy(main.getClass().getClassLoader().getResourceAsStream(fractalResourcePath),
								Paths.get(fractalFilePath), StandardCopyOption.REPLACE_EXISTING);

					// copy 'locations' resources
					if (!new File(locationFilePath).exists())
						Files.copy(main.getClass().getClassLoader().getResourceAsStream(locationResourcePath),
								Paths.get(locationFilePath), StandardCopyOption.REPLACE_EXISTING);

					// find corresponding fractal
					for (Fractal fractal : allFractals) {
						if (fractal.formalName().equals(filename)) {
							// create properties
							Properties p = new Properties();
							p.load(new FileInputStream(fractalFilePath));
							// create locations
							Properties l = new Properties();
							l.load(new FileInputStream("./locations/" + filename + ".properties"));

							fractal.setProperties(p, l);
							fractals.add(fractal);
						}
					}
				}
			}
		}
	}

	/**
	 * Initialises the locations
	 */
	private void initLocations() {

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

	public Fractal[] getFractals() {
		Fractal[] out = new Fractal[fractals.size()];
		for (int i = 0; i < fractals.size(); i++)
			out[i] = fractals.get(i);

		return out;
	}

}
