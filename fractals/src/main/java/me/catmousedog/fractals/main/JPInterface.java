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

//import me.catmousedog.fractals.components.Component;
import me.catmousedog.fractals.components.ComponentFactory;
import me.catmousedog.fractals.fractals.Canvas;
import me.catmousedog.fractals.fractals.LinearTransform;

/**
 * class containing all the data entered by the user through the interface panel
 */
@SuppressWarnings("serial")
public class JPInterface extends JPanel {

	/**
	 * factory to easily create {@link Component}s
	 */
	private final ComponentFactory factory;

	private final Fractals fractals;

	private final Canvas canvas;

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
	private JTextField width, height;

	// location
	private JLabel pos, zoom;

	// calculation
	private JTextField iterations;

	// colour
	private JButton render;

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
		addComponentListener(new ComponentListener() {
			@Override
			public void componentResized(ComponentEvent e) {
				if (init) {
					width.setText(Integer.toString(canvas.getPanel().getWidth()));
					height.setText(Integer.toString(canvas.getPanel().getHeight()));
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
		width = new JTextField(Integer.toString(canvas.getPanel().getWidth()));
		add(factory.create(factory.label("width"), factory.textField(width, "width of the canvas")));
		add(factory.padding(5));
		// height textfield
		height = new JTextField(Integer.toString(canvas.getPanel().getWidth()));
		add(factory.create(factory.label("height"), factory.textField(height, "height of the canvas")));
		add(factory.padding(20));

		/* Location */
		add(factory.create(factory.title("Location")));
		add(factory.padding(10));
		// pos
		pos = factory.label("Re:0.000000    Im:0.000000",
				"the real and imaginary components of the central point of the canvas");
		add(factory.create(pos));
		add(factory.padding(5));
		// zoom
		zoom = factory.label("zoom: 0.000000    rot: 0.000000", "the zoom factor");
		add(factory.create(zoom));
		add(factory.padding(20));

		/* Calculation */
		add(factory.create(factory.title("Calculation")));
		add(factory.padding(10));
		// iterations
		iterations = new JTextField(Integer.toString(canvas.getFractal().getIterations()));
		add(factory.create(factory.label("iterations"), factory.textField(iterations, "the amount of iterations")));
		add(factory.padding(5));
		// render
		render = new JButton("Render");
		add(factory.create(factory.button(render, a -> {
			render();
		}, "saves user input and renders the image")));
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
	 */
	public void render() {
		boolean s = save();
		update();

		if (!s) {
			logger.log("Illegal data, not rendering");
			return;
		}

		long b = System.nanoTime();
		canvas.generate();
		canvas.getPanel().repaint();//
		long e = System.nanoTime();
		logger.log(String.format("Rendered in %d ms!", (e - b) / 1000000));
	}

	/**
	 * Opposite of {@link JPInterface#update()}, will take all values that the user
	 * entered and apply/save it internally.<br>
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
			int w = Integer.parseInt(width.getText());
			int h = Integer.parseInt(height.getText());
			fractals.setSize(w, h);
		} catch (NumberFormatException e) {
			logger.log("window width and height must be integers");
		}
		/* Location */
		/* Calculation */
		try {
			int i = Integer.parseInt(iterations.getText());
			canvas.getFractal().setIterations(i);
		} catch (NumberFormatException e) {
			logger.log("iterations must be an integer");
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
		width.setText(Integer.toString(canvas.getPanel().getWidth()));
		height.setText(Integer.toString(canvas.getPanel().getHeight()));
		/* Location */
		LinearTransform transform = canvas.getMatrix();
		pos.setText(String.format("Re:%f    Im:%f", transform.getdx(), transform.getdy()));
		zoom.setText(String.format("zoom:%f    rot:%f", 1/transform.getm(), transform.gettheta()));
		/* Calculation */
		iterations.setText(Integer.toString(canvas.getFractal().getIterations()));
		/* Colour */
		/* Picture */
		/* Other */
	}
}
