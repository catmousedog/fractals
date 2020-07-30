package me.catmousedog.fractals.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

/**
 * Class for managing the {@code .properties} files. This class onyl contains
 * getters to retrieve the values from those files.
 */
public class Settings {

	public Settings(Fractals main) {
		// load properties
		Properties project = new Properties();
		try {
			project.load(main.getClass().getClassLoader().getResourceAsStream("project.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		artifact_id = project.getProperty("artifactId");
		version = project.getProperty("version");

		// create settings
		Properties settingsobj = new Properties();
		File f = new File("./settings.properties");
		if (!f.exists()) {
			try {
				Files.copy(main.getClass().getClassLoader().getResourceAsStream("default_settings.properties"),
						Paths.get("./settings.properties"), StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// load settings
		try {
			settingsobj.load(new FileInputStream(f));
		} catch (IOException e) {
			e.printStackTrace();
		}
		// render_on_changes
		render_on_changes = Boolean.parseBoolean(settingsobj.getProperty("render_on_change"));
		// intial size
		try {
			width = Integer.parseInt(settingsobj.getProperty("width"));
			height = Integer.parseInt(settingsobj.getProperty("height"));
		} catch (NumberFormatException e) {
			e.printStackTrace();
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

	private int width = 100;

	public int getWidth() {
		return width;
	}

	private int height = 100;

	public int getHeight() {
		return height;
	}

	private boolean render_on_changes = true;

	public boolean isRender_on_changes() {
		return render_on_changes;
	}

}
