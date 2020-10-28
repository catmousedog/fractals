package me.catmousedog.fractals.main;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.Set;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.data.LinearTransform;
import me.catmousedog.fractals.paneloperators.fractals.BurningShip;
import me.catmousedog.fractals.paneloperators.fractals.Fractal;
import me.catmousedog.fractals.paneloperators.fractals.Fractal.Location;
import me.catmousedog.fractals.ui.GUI;
import me.catmousedog.fractals.paneloperators.fractals.InverseMandelbrot;
import me.catmousedog.fractals.paneloperators.fractals.JuliaSet;
import me.catmousedog.fractals.paneloperators.fractals.JuliaShip;
import me.catmousedog.fractals.paneloperators.fractals.Mandelbrot;
import me.catmousedog.fractals.utils.OrderedProperties;

/**
 * Class for managing the {@code .properties} files within the resources.
 */
public class Settings {

	private static final Settings SETTINGS = new Settings();

	public static Settings getInstance() {
		return SETTINGS;
	}

	/**
	 * The interface width, this remains constant unless the frame is smaller than
	 * this width.<br>
	 * Note that this is not the width of the {@linkplain Main#jpi}, as this is
	 * usually smaller.
	 */
	private final int iwidth = 200;

	/**
	 * height of the feedback box
	 */
	private final int feedbackheight = 150;

	/**
	 * vertical gap for the jpi border
	 */
	private final int vgap = 10;

	/**
	 * horizontal gap for the jpi border
	 */
	private final int hgap = 4;

	/* Settings */

	private String artifact_id;

	private String version;

	private int width = 700;

	private int height = 700;

	private boolean render_on_changes = true;

	private boolean scheduled_workers = true;

	private boolean logger_enabled = true;

	private final Logger logger = Logger.getLogger("fractals");

	/**
	 * An array of all the fractals, even if disabled in the 'settings.properties'.
	 * <p>
	 * Will become null to free memory.
	 */
	private Fractal[] allFractals;

	/**
	 * Array of all the enabled <code>Fractals</code>.
	 */
	private Fractal[] fractals;

	/**
	 * property in the settings.properties, if not enabled this is the first fractal
	 * that is enabled
	 */
	private Fractal defaultFractal;

	private Settings() {
		logger.log(Level.FINER, "Settings init");

		// create directories
		try {
			File images = new File("./images");
			if (!images.exists())
				images.mkdir();
			File logs = new File("./logs");
			if (!logs.exists())
				logs.mkdirs();
		} catch (Exception e) {
			logger.log(Level.SEVERE, "Settings failed to create images or logs dir", e);
		}

		// pom.xml
		Properties project = new Properties();
		try {
			project.load(getClass().getClassLoader().getResourceAsStream("project.properties"));
		} catch (IOException e) {
			logger.log(Level.WARNING, "Settings project.properties IOException", e);
		}
		artifact_id = project.getProperty("artifactId");
		version = project.getProperty("version");

		try {
			initSettings();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Settings.iniSettings IOException", e);
		}

		try {
			initFractals();
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Settings.initFractals IOException", e);
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
		logger.log(Level.FINER, "Settings.initSettings");

		// create file and load defaults
		File f = new File("./settings.properties");
		InputStream settingsStream = getClass().getClassLoader().getResourceAsStream("settings.properties");
		if (!f.exists()) {
			Files.copy(settingsStream, Paths.get("./settings.properties"), StandardCopyOption.REPLACE_EXISTING);
		}

		// load settings from file
		Properties defaultSettings = new Properties();
		defaultSettings.load(settingsStream);
		settings = new Properties(defaultSettings);
		settings.load(new FileInputStream(f));

		// booleans
		render_on_changes = Boolean.parseBoolean(settings.getProperty("render_on_change"));
		scheduled_workers = Boolean.parseBoolean(settings.getProperty("scheduled_workers"));
		logger_enabled = Boolean.parseBoolean(settings.getProperty("logger"));
		// intial size
		try {
			width = Integer.parseInt(settings.getProperty("width"));
			height = Integer.parseInt(settings.getProperty("height"));
		} catch (NumberFormatException e) {
			logger.log(Level.WARNING, "Settings settings.properties width or height parse exception", e);
		}

		// remove FileHandler
		if (!logger_enabled) {
			Handler[] handlers = logger.getHandlers();
			if (handlers.length > 0) {
				logger.log(Level.CONFIG, "disabled logger");
				logger.removeHandler(handlers[0]);
			}
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
		logger.log(Level.FINER, "Settings.initFractals");

		allFractals = new Fractal[] { new Mandelbrot(), new JuliaSet(), new BurningShip(), new JuliaShip(),
				new InverseMandelbrot() };

		// scan and copy resources inside 'conrete_fractals' resource

		for (Fractal fractal : allFractals) {
			// filename
			String fileName = fractal.fileName();

			// "true" if this fractal should be used
			String enabled = settings.getProperty(fileName);

			if (enabled.equalsIgnoreCase("true")) {
				// the path to the fractal folder in the resources
				String resource = "fractals/" + fileName;

				// path to fractal resource
				String settingsResourcePath = resource + "/settings.properties";

				// path to location resource
				String locationsResourcePath = resource + "/locations.properties";

				// path to fractal file
				String settingsFilePath = "./" + settingsResourcePath;

				// path to location file
				String locationsFilePath = "./" + locationsResourcePath;

				// make directory
				File fractalDirectory = new File("./" + resource);
				if (!fractalDirectory.exists()) {
					fractalDirectory.mkdirs();
					logger.log(Level.FINER, "Settings.initFractals created dir " + resource);
				}

				// settings.properties resource for this fractal
				InputStream settingsStream = getClass().getClassLoader().getResourceAsStream(settingsResourcePath);
				// settings.properties file for this fractal
				File settingsFile = new File(settingsFilePath);
				// if 'properties' resource exists and file doesn't copy over
				if (settingsStream == null) {
					throw new MissingResourceException("missing settings resource for " + fileName,
							"settings.properties", fileName);
				} else if (!settingsFile.exists()) {
					Files.copy(settingsStream, Paths.get(settingsFilePath), StandardCopyOption.REPLACE_EXISTING);
				}
				// create properties
				Properties defaultP = new Properties();
				if (settingsStream != null)
					defaultP.load(settingsStream);
				Properties p = new Properties(defaultP);
				if (settingsFile.exists()) {
					p.load(new FileInputStream(settingsFilePath));
				}

				// locations.properties resource for this fractal
				InputStream locationsStream = getClass().getClassLoader().getResourceAsStream(locationsResourcePath);
				// locations.properties file for this fractal
				File locationsFile = new File(locationsFilePath);
				// if 'location' resource exists and file doesn't copy over
				if (locationsStream == null) {
					throw new MissingResourceException("missing settings resource for " + fileName,
							"settings.properties", fileName);
				} else if (!locationsFile.exists()) {
					Files.copy(locationsStream, Paths.get(locationsFilePath), StandardCopyOption.REPLACE_EXISTING);
				}
				// create locations
				OrderedProperties defaultL = new OrderedProperties();
				if (locationsStream != null)
					defaultL.load(locationsStream);
				OrderedProperties l = new OrderedProperties(defaultL);
				if (locationsFile.exists()) {
					l.load(new FileInputStream(locationsFilePath));
				}

				setProperties(fractal, p, l);
				fractalList.add(fractal);
			}
		}

		// defaultFractal
		String d = settings.getProperty("defaultFractal");
		if (fractalList.size() == 0) {
			logger.log(Level.SEVERE, "Settings.initFractals fractalList.size() == 0, no active fractals!");
		} else {
			defaultFractal = fractalList.get(0);
			for (Fractal f : fractalList) {
				if (f.fileName().equals(d)) {
					defaultFractal = f;
					break;
				}
			}
		}
		allFractals = null; // free memory

		fractals = new Fractal[fractalList.size()];
		for (int i = 0; i < fractalList.size(); i++)
			fractals[i] = fractalList.get(i);
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

			// need to pass 'this' instead of using 'getInstance' from within the fractal as
			// this entire process is still during the initialisation of the Settings class
			// and 'getInstance' will return null.
			fractal.setProperties(this, properties);
		} catch (NumberFormatException e) {
			logger.log(Level.WARNING, "Settings " + fractal.fileName() + " default settings parsing exception", e);
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
					logger.log(Level.WARNING, "Settings " + fractal.fileName() + " location file exception", e);
				}
			}
		}

		Location[] l = new Location[temp.size()];
		temp.toArray(l);

		fractal.setLocations(l);
	}

	/**
	 * <code>Map</code> of key combination to an action.
	 */
	private final Map<Set<Integer>, Runnable> keyMap = new HashMap<Set<Integer>, Runnable>();

	/**
	 * Dynamic <code>Set</code> of pressed keys.
	 */
	private final Set<Integer> activeKeys = new HashSet<Integer>();

	/**
	 * Initialises the keybinds from the <code>keybinds.properties</code> file.
	 * 
	 * @param gui the <code>GUI</code> instance used for the defined actions.
	 * @throws IOException if the file failed to be copied or loaded.
	 */
	public void initKeybinds(GUI gui) throws IOException {
		logger.log(Level.FINER, "Settings.initKeybinds");

		// create file and load defaults
		File f = new File("./keybinds.properties");
		InputStream keybindsStream = getClass().getClassLoader().getResourceAsStream("keybinds.properties");
		if (!f.exists()) {
			Files.copy(keybindsStream, Paths.get("./keybinds.properties"), StandardCopyOption.REPLACE_EXISTING);
		}

		// load settings from file
		Properties defaultKeybinds = new Properties();
		defaultKeybinds.load(keybindsStream);
		Properties keybinds = new Properties(defaultKeybinds);
		keybinds.load(new FileInputStream(f));

		// set the keyMap
		Enumeration<Object> keys = keybinds.keys();

		while (keys.hasMoreElements()) {
			String key = (String) keys.nextElement();
			key.replaceAll(" ", "");

			// array of VK_KEYS
			String[] splitKeys = key.split("\\+");

			// key combination
			Set<Integer> codes = new HashSet<Integer>(splitKeys.length);

			// true if the key combination is valid
			boolean keyValid = true;

			// set key combination
			for (String VK : splitKeys) {
				VK = "VK_" + VK.toUpperCase();
				try {
					int i = KeyEvent.class.getField(VK).getInt(null);
					if (!codes.add(i) || i == 0) {
						keyValid = false;
						logger.log(Level.WARNING, "Settings.initKeybinds " + VK + " is not a valid key");
					}
				} catch (NoSuchFieldException e) {
					keyValid = false;
					logger.log(Level.WARNING, "Settings.initKeybinds: " + VK + " is not a valid key", e);
				} catch (IllegalAccessException | SecurityException | IllegalArgumentException e) {
					keyValid = false;
					logger.log(Level.SEVERE, "Settings.initKeybinds", e);
				}
			}

			// if valid continue
			if (keyValid) {
				// action runnable
				Runnable r;

				// action string
				String action = keybinds.getProperty(key);

				switch (action.toLowerCase()) {
				case "render":
					r = () -> gui.render();
					break;
				case "cancel":
					r = () -> gui.cancel();
					break;
				case "repaint":
					r = () -> gui.repaint();
					break;
				case "copy":
					r = () -> gui.copy();
					break;
				case "paste":
					r = () -> gui.paste();
					break;
				case "undo":
					r = () -> gui.undo();
					break;
				case "zoomin":
					r = () -> gui.zoomIn();
					break;
				case "zoomout":
					r = () -> gui.zoomOut();
					break;
				case "terminate":
					r = () -> System.exit(0);
					break;
				default:
					logger.log(Level.WARNING, "Settings.initKeybinds: " + action + " is not a defined action");
					r = null;
				}

				// if valid continue
				if (r != null)
					keyMap.put(codes, r);
			}

		}

		// add keylistener
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
			@Override
			public synchronized boolean dispatchKeyEvent(KeyEvent e) {
				// to avoid holding down the key
				if (e.getID() == KeyEvent.KEY_TYPED)
					return false;

				if (e.getID() == KeyEvent.KEY_PRESSED) {
					// if key was already pressed return
					// to avoid holding down the key after letting go
					if (!activeKeys.add(e.getKeyCode()))
						return false;
				}

				// to avoid letting go of a key and this triggering an event
				// a key combination should be intended by the user
				if (e.getID() == KeyEvent.KEY_RELEASED) {
					activeKeys.remove((Integer) e.getKeyCode());
					return false;
				}

				runAction();

				return false;
			}
		});
	}

	/**
	 * Runs the <code>Runnable</code> from the <code>keyMap</code>'s key combination
	 * that matches the <code>activeKeys</code>.
	 */
	private void runAction() {
		for (Set<Integer> key : keyMap.keySet()) {
			if (key.equals(activeKeys)) {
				logger.log(Level.FINEST, "Settings.runAction");
				keyMap.get(key).run();
				return;
			}
		}
	}

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
		logger.log(Level.FINER, "Settings.addImage");

		String dirPath = "./images/" + fractal.fileName();
		File dir = new File(dirPath);
		if (!dir.exists())
			dir.mkdir();

		String imageName = fractal.getFunction().fileName() + "_" + fractal.getTransform().toString();
		File imageFile; // actual imageFile with edited path

		for (int i = 1;; i++) {
			imageFile = new File(String.format("%s/%d_%s.%s", dirPath, i, imageName, ext));
			if (!imageFile.exists())
				break;
		}

		try {
			ImageIO.write(img, ext, imageFile);
			logger.log(Level.FINER, "Settings created image at " + imageFile.getPath());
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Settings failed to write image at " + imageFile.getPath(), e);
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

	public boolean isScheduled_workers() {
		return scheduled_workers;
	}

	public boolean isLogger_enabled() {
		return logger_enabled;
	}

	/**
	 * @return the iwidth
	 */
	public int getIwidth() {
		return iwidth;
	}

	/**
	 * @return the feedbackheight
	 */
	public int getFeedbackheight() {
		return feedbackheight;
	}

	/**
	 * @return the vgap
	 */
	public int getVgap() {
		return vgap;
	}

	/**
	 * @return the hgap
	 */
	public int getHgap() {
		return hgap;
	}

	public int getJPIWidth() {
		return iwidth + 2 * hgap;
	}

	private List<Fractal> fractalList = new ArrayList<Fractal>();

	/**
	 * @return An array of the active <code>Fractals</code>.
	 */
	public Fractal[] getFractals() {
		return fractals;
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
