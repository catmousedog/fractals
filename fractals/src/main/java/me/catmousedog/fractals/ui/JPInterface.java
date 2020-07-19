package me.catmousedog.fractals.ui;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import me.catmousedog.fractals.canvas.Canvas;
import me.catmousedog.fractals.components.Item;
import me.catmousedog.fractals.components.Label;
import me.catmousedog.fractals.components.TextFieldDouble;
import me.catmousedog.fractals.components.AllData;
import me.catmousedog.fractals.components.Button;
import me.catmousedog.fractals.components.Data;
import me.catmousedog.fractals.fractals.LinearTransform;
import me.catmousedog.fractals.main.Fractals;
import me.catmousedog.fractals.main.Settings;

/**
 * class containing all the data entered by the user through the interface panel
 * <p>
 * This class is also used to render the image as it contains all the
 * JComponents it needs to change after rendering.
 */
@SuppressWarnings("serial")
public class JPInterface extends JPanel {

	private final Fractals fractals;

	/**
	 * the Canvas instance used to render the image
	 */
	private final Canvas canvas;

	/**
	 * the logger instance
	 */
	private final Logger logger;

	/**
	 * user settings
	 */
	private final Settings settings;

	/**
	 * true if the components have been created and the class has been fully
	 * initialsed
	 */
	private boolean init = false;

	/*
	 * below are all the components that the user can interact with and their
	 * respective data
	 */

	// window
	private JTextField widthjtf, heightjtf;

	// location
	private JTextField xjtf, yjtf, mjtf, njtf, rotjtf;
	private JButton copyjb, pastejb;
	private JComboBox<Position> jcbpos;

	// calculation
	private JTextField iterationjtf, zoomjtf;
	private JButton zoominjb, zoomoutjb;

	// colour
	private JButton renderjb;

	// picture

	// other

	public JPInterface(int iwidth, int vgap, int hgap, Fractals fractals, Canvas canvas, Logger logger,
			Settings settings) {
		this.fractals = fractals;
		this.canvas = canvas;
		this.logger = logger;
		this.settings = settings;

		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setMaximumSize(new Dimension(iwidth, Integer.MAX_VALUE));
		setBorder(BorderFactory.createEmptyBorder(vgap, hgap, vgap, hgap));
		canvas.addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent e) {
				if (init) {
					widthjtf.setText(Integer.toString(canvas.getWidth()));
					heightjtf.setText(Integer.toString(canvas.getHeight()));
				}
			}

			@Override
			public void componentShown(ComponentEvent e) {
			}

			@Override
			public void componentMoved(ComponentEvent e) {
			}

			@Override
			public void componentHidden(ComponentEvent e) {
			}
		});
	}

	/**
	 * create and add all the JComponents
	 */
	public void addComponents() {
//		/* Window */
//		add((ComponentFactory.title("Window")));
//		add(ComponentFactory.padding(10));
//
//		// width textfield
//		widthjtf = new JTextField(Integer.toString(canvas.getWidth()));
//		add(ComponentFactory.textField(widthjtf, "width", "width of the canvas"));
//		add(ComponentFactory.padding(5));
//		// height textfield
//		heightjtf = new JTextField(Integer.toString(canvas.getWidth()));
//		add(ComponentFactory.textField(heightjtf, "height", "height of the canvas"));
//		add(ComponentFactory.padding(20));
//
//		/* Location */
//		add((ComponentFactory.title("Location")));
//		add(ComponentFactory.padding(10));
//		// position textfield
//		xjtf = new JTextField();
//		add(ComponentFactory.textField(xjtf, "x coordinate"));
//		add(ComponentFactory.padding(5));
//		yjtf = new JTextField();
//		add(ComponentFactory.textField(yjtf, "y coordinate"));
//		add(ComponentFactory.padding(5));
//		mjtf = new JTextField();
//		add(ComponentFactory.textField(mjtf, "x zoom"));
//		add(ComponentFactory.padding(5));
//		njtf = new JTextField();
//		add(ComponentFactory.textField(njtf, "y zoom"));
//		add(ComponentFactory.padding(5));
//		rotjtf = new JTextField();
//		add(ComponentFactory.textField(rotjtf, "rotation"));
//		add(ComponentFactory.padding(5));
//		// copy
//		copyjb = new JButton("Copy");
//		copyjb.addActionListener(a -> copy());
//		add(ComponentFactory.button(copyjb, "copy location to clipboard"));
//		add(ComponentFactory.padding(5));
//		// paste
//		pastejb = new JButton("Paste");
//		pastejb.addActionListener(a -> paste());
//		add(ComponentFactory.button(pastejb, "paste location from clipboard"));
//		add(ComponentFactory.padding(5));
//		// dropdown locations
//		jcbpos = new JComboBox<Position>(Position.preSaved);
//		jcbpos.addActionListener(a -> {
//			Position pos = (Position) (jcbpos.getSelectedItem());
//			LinearTransform transform = pos.getTransform();
//			canvas.getTransform().set(transform);
//			canvas.getFractal().setIterations(pos.getIterations());
//			update();
//			if (settings.isRender_on_changes())
//				renderNow();
//		});
//		add(ComponentFactory.dropDown(jcbpos, "locations", "a set of interesting locations"));
//		add(ComponentFactory.padding(20));
//
//		/* Calculation */
//		add(ComponentFactory.title("Calculation"));
//		add(ComponentFactory.padding(10));
//		// iterations
//		iterationjtf = new JTextField();
//		add(ComponentFactory.textField(iterationjtf, "iterations", "the amount of iterations"));
//		add(ComponentFactory.padding(5));
//		zoomjtf = new JTextField();
//		add(ComponentFactory.textField(zoomjtf, "zoom factor", "the zoom factor increment, when zooming in or out"));
//		add(ComponentFactory.padding(5));
//		// zoom
//		zoominjb = new JButton("Zoom In");
//		zoominjb.addActionListener(a -> {
//			try {
//				canvas.getTransform().zoom(Double.parseDouble(zoomjtf.getText()));
//				renderNow();
//			} catch (NumberFormatException e) {
//				logger.log("zoom factor must be a valid double");
//			}
//		});
//		add(ComponentFactory.button(zoominjb, "zooms in with the current zoom factor"));
//		add(ComponentFactory.padding(5));
//		zoomoutjb = new JButton("Zoom Out");
//		zoomoutjb.addActionListener(a -> {
//			try {
//				canvas.getTransform().zoom(1 / Double.parseDouble(zoomjtf.getText()));
//			} catch (NumberFormatException e) {
//				logger.log("zoom factor must be a valid double");
//			}
//		});
//		add(ComponentFactory.button(zoomoutjb, "zooms out with the current zoom factor"));
//		add(ComponentFactory.padding(10));
//		// render
		renderjb = new JButton("Render");
//		renderjb.addActionListener(a -> render());
//		add(ComponentFactory.button(renderjb, "saves user input and renders the image"));
//		add(ComponentFactory.padding(20));
//
//		/* Colour */
//		add((ComponentFactory.title("Colour")));
//		add(ComponentFactory.padding(20));
//
//		/* Picture */
//		add((ComponentFactory.title("Picture")));
//		add(ComponentFactory.padding(20));
//
//		/* Other */
//		add((ComponentFactory.title("Other")));
//		add(ComponentFactory.padding(20));
//		
//		// set position labels
//		update();
//
//		init = true;

		AllData components = new AllData();

		for (Item c : components.getAll())
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
		// already rendering
		if (!renderjb.isEnabled()) {
			logger.log("already rendering...");
			return;
		}

		// disable render button
		renderjb.setEnabled(false);

		// save & update
		boolean s = save();
		update();

		if (!s) {
			logger.log("Illegal data, not rendering");
			renderjb.setEnabled(true);
		} else {
			// render
			canvas.render(this);
		}
	}

	/**
	 * Renders the image without calling {@link JPInterface#save()} pr
	 * {@link JPInterface#update()}.
	 * <p>
	 * This method still calls the {@link JPInterface#postRender()} method.
	 */
	public void renderNow() {
		// already rendering
		if (!renderjb.isEnabled()) {
			logger.log("already rendering...");
			return;
		}

		// disable render button
		renderjb.setEnabled(false);

		canvas.render(this);
	}

	/**
	 * This method is run after the image is done rendering. It shouldn't be
	 * possible for this to occur concurrently or in the wrong order of calling
	 * {@link JPInterface#render}.
	 * <p>
	 * Must be run on the EDT.
	 */
	public void postRender() {
		renderjb.setEnabled(true);
	}

	/**
	 * Opposite of {@link JPInterface#update()}, will take all values that the user
	 * entered and apply/save it internally. If the entered data is illegal, the
	 * previously saved is used.
	 * <p>
	 * When calling {@linkplain JPInterface#save()}, {@link JPInterface#update()}
	 * should be called right after to make sure any illegal data, the user tried
	 * entering, gets restored.
	 * 
	 * @return true if successful, false otherwise. It is advised to log a message
	 *         if false is returned.
	 */
	public boolean save() {
		boolean b = true;
		/* Window */
		try {
			int w = Integer.parseInt(widthjtf.getText());
			int h = Integer.parseInt(heightjtf.getText());
//			canvas.setPanelSize(w, h);
			fractals.setSize(w, h);

		} catch (NumberFormatException e) {
			logger.log("window width and height must be valid integers");
			b = false;
		}
		/* Location */
		try {
			double dx = Double.parseDouble(xjtf.getText());
			double dy = Double.parseDouble(yjtf.getText());
			canvas.getTransform().setTranslation(dx, dy);
		} catch (NumberFormatException e) {
			logger.log("the x and y coordinate have to be valid doubles");
			b = false;
		}
		try {
			double m = Double.parseDouble(mjtf.getText());
			double n = Double.parseDouble(njtf.getText());
			canvas.getTransform().setScalar(m, n);
		} catch (NumberFormatException e) {
			logger.log("the x and y scalars have to be valid doubles");
			b = false;
		}
		try {
			double rot = Double.parseDouble(rotjtf.getText());
			canvas.getTransform().setRot(rot);
		} catch (NumberFormatException e) {
			logger.log("the rotation has to be a valid double");
			b = false;
		}
		/* Calculation */
		try {
			int i = Integer.parseInt(iterationjtf.getText());
			canvas.getFractal().setIterations(i);
		} catch (NumberFormatException e) {
			logger.log("iterations must be a valid integer");
			b = false;
		}
		// zoom factor is not saved, it is retrieved when clicking
		/* Colour */
		/* Picture */
		/* Other */
		return b;
	}

	/**
	 * Update all components by setting their values to the ones found from the
	 * internal (hidden) data.
	 */
	public void update() {
		/* Window */
		/* Location */
		LinearTransform transform = canvas.getTransform();
		xjtf.setText(Double.toString(transform.getdx()));
		yjtf.setText(Double.toString(transform.getdy()));
		mjtf.setText(Double.toString(transform.getm()));
		njtf.setText(Double.toString(transform.getn()));
		rotjtf.setText(Double.toString(transform.getrot()));
		/* Calculation */
		iterationjtf.setText(Integer.toString(canvas.getFractal().getIterations()));
		/* Colour */
		/* Picture */
		/* Other */
	}

	/**
	 * copy current {@link LinearTransform} to clipboard
	 */
	private void copy() {
		update();

		String clip = new Position(canvas.getTransform(), canvas.getFractal().getIterations()).getID();

		try {
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(clip), null);
			logger.log("successfully copied to clipboard!");
		} catch (Exception e) {
			e.printStackTrace();
			logger.log(e.getMessage());
			logger.log("unable to copy to clipboard");
		}
	}

	/**
	 * paste new {@link LinearTransform} from clipboard
	 */
	private void paste() {
		try {
			try {
				String clip = (String) Toolkit.getDefaultToolkit().getSystemClipboard()
						.getData(DataFlavor.stringFlavor);
				Position pos = Position.fromID(clip);
				canvas.getTransform().set(pos.getTransform());
				canvas.getFractal().setIterations(pos.getIterations());
				update();
				logger.log("successfully pasted from clipboard");
			} catch (NumberFormatException e) {
				logger.log("unable to parse from clipboard");
			}
		} catch (HeadlessException | UnsupportedFlavorException | IOException e) {
			e.printStackTrace();
			logger.exception(e);
		}

		if (settings.isRender_on_changes()) {
			update();
			renderNow();
		}
	}

	public JTextField getZoomJTF() {
		return zoomjtf;
	}
	
	private final AllData data = new AllData();
	
	/**
	 * Concrete class containing all the actual {@link Data} containers displayed in
	 * the user interface. This class can be passed around to retrieve the data
	 * needed.
	 */
	public class AllData {

		private Button jb1 = new Button.ButtonBuilder("jb1").setLabel("button").setTip("tiip").setAction(a -> {
			render();
		}).build();

		private TextFieldDouble x = new TextFieldDouble.TextFieldBuilder().setText("0").setTip("tiptip")
				.setLabel("cord").build();

		public Data<Double> getX() {
			return x;
		}

		private Label test = new Label.LabelBuilder().setText("test").setTip("tip").build();

		public Data<Void> getTest() {
			return test;
		}

		private Item[] all = new Item[] { jb1, x, test };

		public Item[] getAll() {
			return all;
		}

	}

}
