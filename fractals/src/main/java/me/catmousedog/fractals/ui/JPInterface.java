package me.catmousedog.fractals.main;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import me.catmousedog.fractals.canvas.Canvas;
//import me.catmousedog.fractals.components.Component;
import me.catmousedog.fractals.components.ComponentFactory;
import me.catmousedog.fractals.fractals.LinearTransform;

/**
 * class containing all the data entered by the user through the interface panel
 * <p>
 * This class is also used to render the image as it contains all the
 * JComponents it needs to change after rendering.
 */
@SuppressWarnings("serial")
public class JPInterface extends JPanel {

	/**
	 * factory to easily create {@link Component}s
	 */
	private final ComponentFactory factory;

	/**
	 * the main class instance, used to set the size of the frame
	 */
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
	private int wjtf, hjtf;

	// location
	private JLabel poslbl, zoomlbl;

	// calculation
	private JTextField iterationjtf, zoomjtf;

	// colour
	private JButton renderjb;

	// picture

	// other

	public JPInterface(int iwidth, int vgap, int hgap, Fractals fractals) {
		factory = new ComponentFactory(this);
		this.fractals = fractals;
		canvas = fractals.getCanvas();
		logger = fractals.getLogger();

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
		/* Window */
		add(factory.create(factory.title("Window")));
		add(factory.padding(10));

		// width textfield
		widthjtf = new JTextField(Integer.toString(canvas.getWidth()));
		add(factory.create(factory.label("width"), factory.textField(widthjtf, "width of the canvas")));
		add(factory.padding(5));
		// height textfield
		heightjtf = new JTextField(Integer.toString(canvas.getWidth()));
		add(factory.create(factory.label("height"), factory.textField(heightjtf, "height of the canvas")));
		add(factory.padding(20));

		/* Location */
		add(factory.create(factory.title("Location")));
		add(factory.padding(10));
		// pos
		poslbl = factory.label("Re: 0.000000    Im: 0.000000",
				"the real and imaginary components of the central point of the canvas");
		add(factory.create(poslbl));
		add(factory.padding(5));
		// zoom
		zoomlbl = factory.label("zoom: 0.000000    rot: 0.000000",
				"the zoom factor as an exponent of 10 and the rotation in radians");
		add(factory.create(zoomlbl));
		add(factory.padding(20));

		/* Calculation */
		add(factory.create(factory.title("Calculation")));
		add(factory.padding(10));
		// iterations
		iterationjtf = new JTextField(Integer.toString(canvas.getFractal().getIterations()));
		add(factory.create(factory.label("iterations"), factory.textField(iterationjtf, "the amount of iterations")));
		add(factory.padding(5));
		zoomjtf = new JTextField(Double.toString(Math.log10(1 / canvas.getTransform().getm())));
		add(factory.create(factory.label("zoom factor"),
				factory.textField(zoomjtf, "the zoom factor increment, when zooming in or out")));
		add(factory.padding(5));
		// render
		renderjb = new JButton("Render");
		add(factory.create(factory.button(renderjb, a -> render(), "saves user input and renders the image")));
		add(factory.padding(20));

		/* Colour */
		add(factory.create(factory.title("Colour")));
		add(factory.padding(20));

		/* Picture */
		add(factory.create(factory.title("Picture")));
		add(factory.padding(20));

		/* Other */
		add(factory.create(factory.title("Other")));
		add(factory.padding(20));

		init = true;
	}

	/**
	 * Grabs user input, restores any illegal inputs, generates the image and paints
	 * it.
	 * <p>
	 * Must be run on the EDT
	 */
	public void render() {
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
	 * This method is run after the image is done rendering. It shouldn't be
	 * possible for this to occur concurrently or in the wrong order of calling
	 * {@link JPInterface#render}.
	 * <p>
	 * Must be run on the EDT.
	 */
	public void afterRender() {
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
			fractals.setSize(w, h);
		} catch (NumberFormatException e) {
			logger.log("window width and height must be valid integers");
			b = false;
		}
		/* Location */
		/* Calculation */
		try {
			int i = Integer.parseInt(iterationjtf.getText());
			canvas.getFractal().setIterations(i);
		} catch (NumberFormatException e) {
			logger.log("iterations must be a valid integer");
			b = false;
		}
		try {
			double z = Double.parseDouble(zoomjtf.getText());
		} catch (NumberFormatException e) {
			logger.log("zoom factor must be a valid double");
			b = false;
		}
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
		widthjtf.setText(Integer.toString(canvas.getWidth()));
		heightjtf.setText(Integer.toString(canvas.getHeight()));
		/* Location */
		LinearTransform transform = canvas.getTransform();
		poslbl.setText(String.format("Re: %f    Im: %f", transform.getdx(), transform.getdy()));
		zoomlbl.setText(String.format("zoom: %f    rot: %f", Math.log10(1 / transform.getm()), transform.gettheta()));
		/* Calculation */
		iterationjtf.setText(Integer.toString(canvas.getFractal().getIterations()));
		/* Colour */
		/* Picture */
		/* Other */
	}
}
