package me.catmousedog.fractals.ui;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JComboBox;

import me.catmousedog.fractals.canvas.Canvas;
import me.catmousedog.fractals.main.Settings;
import me.catmousedog.fractals.paneloperators.filters.Filter;
import me.catmousedog.fractals.paneloperators.fractals.Fractal;
import me.catmousedog.fractals.paneloperators.fractals.Fractal.Location;
import me.catmousedog.fractals.paneloperators.functions.Function;
import me.catmousedog.fractals.ui.components.ActiveData;
import me.catmousedog.fractals.ui.components.Data;
import me.catmousedog.fractals.ui.components.Item;
import me.catmousedog.fractals.ui.components.concrete.Button;
import me.catmousedog.fractals.ui.components.concrete.Button2;
import me.catmousedog.fractals.ui.components.concrete.ComboBoxItem;
import me.catmousedog.fractals.ui.components.concrete.ComboBoxList;
import me.catmousedog.fractals.ui.components.concrete.Label;
import me.catmousedog.fractals.ui.components.concrete.Padding;
import me.catmousedog.fractals.ui.components.concrete.Panel;
import me.catmousedog.fractals.ui.components.concrete.TextFieldDouble;
import me.catmousedog.fractals.ui.components.concrete.TextFieldInteger;
import me.catmousedog.fractals.ui.components.concrete.Title;
import me.catmousedog.fractals.workers.RenderWorker;

/**
 * Concrete class containing all the actual {@link Data} containers displayed in
 * the user interface. This class can be passed around to retrieve the data
 * needed.
 * <p>
 * When adding a new {@link Item} to the user interface, make sure to add it to
 * the {@link GUI#all} array so it gets added to the user interface.
 * <p>
 * When adding a new {@link Data}, make sure to add its <b>getter</b> method so
 * the data can be retrieved from this class.
 */
public class GUI {

	/**
	 * Array of all the {@link Item}s in order of addition.
	 */
	private final Item[] all;

	private final Canvas canvas;

	private final JPInterface jpi;

	private final Logger logger = Logger.getLogger("fractals");

	private final FeedbackPanel feedback = FeedbackPanel.getInstance();

	private final Settings settings = Settings.getInstance();

	/**
	 * Class used for generating images.
	 */
	private final Picture picture;

	private final RenderWorker RENDER = RenderWorker.getInstance();

	public GUI(Canvas canvas, JPInterface jpi) {
		this.canvas = canvas;
		this.jpi = jpi;
		picture = new Picture(canvas, jpi, settings);

		Padding p5 = new Padding(5);

		Padding p10 = new Padding(10);

		Padding p20 = new Padding(20);

		/**
		 * Calculation
		 */
		Title canvasTitle = new Title("Canvas");

		zoomjtf = new TextFieldDouble.Builder().setLabel("zoom factor").setDefault(-1).setMin(1)
				.setTip("<html>The zoom factor to multiply with on click</html>").build();

		zoomjb = new Button2.Builder("Zoom In", "Zoom Out").setAction(a -> zoomIn(), a -> zoomOut())
				.setTip("<html>Zoom in without moving</html>", "<html>Zoom out without moving</html>").build();

		renderjb = new Button.Builder("Render").setAction(a -> jpi.render())
				.setTip("<html>Render the image by generating it and painting it</html>").build();

		canceljb = new Button.Builder("Cancel").setAction(a -> cancel()).setTip(
				"<html>Cancel the current generator or painter.<br>This also includes the generation of images.</html>")
				.build();

		/**
		 * Fractal
		 */
		Title fractal = new Title("Fractal", "<html>Section containing all the settings regarding the fractal</html>");

		Label fractaljl = new Label("fractal", "<html>All the fractal specific settings<html/>");

		fractaljcb = new ComboBoxItem.Builder(settings.getFractals()).setAction(a -> fractal()).build();

		fractaljp = new Panel();

		Label functionjl = new Label("function", "<html>All the function specific settings</html>");

		functionjcb = new ComboBoxItem.Builder(settings.getDefaultFractal().getFunctions()).setAction(a -> function())
				.build();

		functionjp = new Panel();

		Label filterjl = new Label("filter", "<html>All the filter specific settings</html>");

		filterjcb = new ComboBoxItem.Builder(settings.getDefaultFractal().getFunction().getFilters())
				.setAction(a -> filter()).build();

		repaintjb = new Button.Builder("Repaint").setAction(a -> repaint()).setTip(
				"<html>Repaint the image without generating it again. <br>Useful for just changing colour settings</html>")
				.build();

		filterjp = new Panel();

		/**
		 * Location
		 */
		Title location = new Title("Location");

		xjtf = new TextFieldDouble.Builder().setLabel("x coordinate")
				.setTip("<html>The x coordinate of the center of the screen</html>").build();

		yjtf = new TextFieldDouble.Builder().setLabel("y coordinate")
				.setTip("<html>The y coordinate of the center of the screen</html>").build();

		mjtf = new TextFieldDouble.Builder().setLabel("x zoom").setTip("<html>The x scaling factor</html>").setMin(0)
				.build();

		njtf = new TextFieldDouble.Builder().setLabel("y zoom").setTip("<html>The y scaling factor</html>").setMin(0)
				.build();

		rjtf = new TextFieldDouble.Builder().setLabel("rotation").setTip("<html>The rotation in radians</html>")
				.build();

		copypastejb = new Button2.Builder("Copy", "Paste").setAction(a -> copy(), a -> paste())
				.setTip("<html>Copy current location to clipboard</html>",
						"<html>Paste a location from clipboard</html>")
				.build();

		locationjcb = new ComboBoxList.Builder(canvas.getFractal().getLocations()).setLabel("locations")
				.setAction(a -> location(a)).setTip("<html>A set of interesting locations</html>").build();

		undojb = new Button.Builder("Undo").setAction(a -> undo()).setTip(
				"<html>Go back to the previous location, fractal and filter<br>Does not revert any changes made to the fractal or filter.</html>")
				.setDefault(false).build();

		/**
		 * Picture
		 */
		Title picture = new Title("Picture");

		picturesizejcb = new ComboBoxItem.Builder(new String[] { "1280x720 (720p)", "1920x1080 (HD)", "3840x2160 (4K)",
				"7680x4320 (8K)" }).setAction(a -> pictureSize()).setTip(
						"<html>A set of predefined picture resolutions.<br> All of the aspect ratios in this list are 16:9.</html>")
						.setDefault(1).build();

		picturewjtf = new TextFieldInteger.Builder().setLabel("picture width").setTip(
				"<html>The width of the picture in pixels.<br>It is recommended to have the same aspect ratio for the picture and the canvas to avoid cropping.</html>")
				.setMin(100).setDefault(1920).build();

		picturehjtf = new TextFieldInteger.Builder().setLabel("picture height").setTip(
				"<html>The height of the picture in pixels.<br>It is recommended to have the same aspect ratio for the picture and the canvas to avoid cropping.</html>")
				.setMin(100).setDefault(1080).build();

		picturejb = new Button.Builder("Picture").setAction(a -> picture()).setTip(
				"<html>Generate a picture at the current location.<br> When generating large images make sure to allocate enough memory.</html>")
				.build();

		picturejcb = new ComboBoxItem.Builder(new String[] { "png", "jpg" })
				.setTip("<html>The extension of the image when generated.</html>").build();

		/**
		 * Window
		 */
		Title window = new Title("Window");

		widthjtf = new TextFieldInteger.Builder().setLabel("width").setTip("<html>The width of the canvas</html>")
				.setMin(100).build();

		heightjtf = new TextFieldInteger.Builder().setLabel("height").setMin(100)
				.setTip("<html>The height of the canvas</html>").build();

		all = new Item[] { p20, canvasTitle, p5, zoomjtf, p5, zoomjb, p5, renderjb, p5, canceljb, p20, fractal,
				p5, fractaljl, fractaljcb, p5, fractaljp, p5, functionjl, functionjcb, p5, functionjp, p5, filterjl,
				filterjcb, p5, repaintjb, p5, filterjp, p20, location, p5, xjtf, p5, yjtf, p5, mjtf, p5, njtf, p5,
				rjtf, p10, copypastejb, p5, locationjcb, p5, undojb, p20, picture, p5, picturesizejcb, p5, picturewjtf,
				p5, picturehjtf, p5, picturejb, p5, picturejcb, p20, window, p5, widthjtf, p5, heightjtf };
	}

	/**
	 * copy button
	 */
	private void copy() {
		// update?
		String clip = canvas.getFractal().new Location().getID();

		try {
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(clip), null);
		} catch (Exception e) {
			logger.log(Level.WARNING, "unable to copy to clipboard", e);
		}
	}

	/**
	 * paste button
	 */
	private void paste() {
		try {
			String clip = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);

			canvas.savePrevConfig();

			Location l = canvas.getFractal().new Location(clip);
			canvas.getFractal().setLocation(l);

			if (settings.isRender_on_changes()) {
				jpi.update();
				jpi.renderNow();
			}

		} catch (IllegalArgumentException e) {
			logger.log(Level.FINE, "clipboard is not correct format", e);
		} catch (HeadlessException | UnsupportedFlavorException | IOException e) {
			logger.log(Level.SEVERE, "could not paste clipboard", e);
		}
	}

	/**
	 * locationjcb
	 */
	private void location(ActionEvent a) {
		jpi.renderWithout(settings.isRender_on_changes(), () -> {
			@SuppressWarnings("unchecked")
			JComboBox<Object> jcb = (JComboBox<Object>) a.getSource();
			canvas.getFractal().setLocation((Location) jcb.getSelectedItem());
		});
	}

	/**
	 * undo button
	 */
	private void undo() {
		jpi.allowUndo(false);
		canvas.undo();
		jpi.update();
		jpi.renderNow();
	}

	/**
	 * zoom in button
	 */
	private void zoomIn() {
		jpi.renderWithout(true, () -> {
			canvas.getFractal().getTransform().zoom(1 / canvas.getZoomFactor());
		});
	}

	/**
	 * zoom out button
	 */
	private void zoomOut() {
		jpi.renderWithout(true, () -> {
			canvas.getFractal().getTransform().zoom(canvas.getZoomFactor());
		});
	}

	/**
	 * cancel button
	 */
	private void cancel() {
		if (RENDER.cancel()) {
			if (picture.isGenerating()) {
				picture.setGenerating(false);
				feedback.setGeneratorProgress("cancelled generator", 0);
				feedback.setPainterProgress("cancelled painter", 0);
			} else {
				jpi.allowUndo(false);
				canvas.undo();
				feedback.setGeneratorProgress("cancelled generator", 0);
				feedback.setPainterProgress("cancelled painter", 0);
			}
		}
		jpi.update();
		jpi.postRender();
	}

	/**
	 * repaintjb
	 */
	private void repaint() {
		canvas.getFractal().saveAndColour();
		jpi.update();
	}

	/**
	 * fractaljcb
	 */
	private void fractal() {
		Fractal f = (Fractal) getFractaljcb().saveAndGet();
		if (canvas.getFractal().equals(f))
			return;
		jpi.renderWithout(settings.isRender_on_changes(), () -> {
			canvas.setFractal(f);
		});
	}

	/**
	 * functionjcb
	 */
	private void function() {
		Function f = (Function) getFunctionjcb().saveAndGet();
		if (canvas.getFractal().getFunction().equals(f))
			return;
		jpi.renderWithout(settings.isRender_on_changes(), () -> {
			canvas.setFunction(f);
		});

	}

	/**
	 * filterjcb
	 */
	private void filter() {
		Filter f = (Filter) getFilterjcb().saveAndGet();
		if (canvas.getFractal().getFunction().getFilter().equals(f))
			return;
		jpi.renderWithout(settings.isRender_on_changes(), () -> {
			canvas.setFilter(f);
		});
	}

	/**
	 * picturesizejcb
	 */
	private void pictureSize() {
		switch (getPicturesizejcb().getComponent().getSelectedIndex()) {
		case 0:
			picturewjtf.setData(1280);
			picturehjtf.setData(720);
			break;
		case 1:
			picturewjtf.setData(1920);
			picturehjtf.setData(1080);
			break;
		case 2:
			picturewjtf.setData(3840);
			picturehjtf.setData(2160);
			break;
		case 3:
			picturewjtf.setData(7680);
			picturehjtf.setData(4320);
			break;
		}
	}

	/**
	 * picturejb
	 */
	private void picture() {
		int w = picturewjtf.saveAndGet();
		int h = picturehjtf.saveAndGet();
		String ext = (String) picturejcb.saveAndGet();

		jpi.update();

		picture.newPicture(w, h, ext);
	}

	private final TextFieldInteger widthjtf;

	public Data<Integer> getWidthjtf() {
		return widthjtf;
	}

	private final TextFieldInteger heightjtf;

	public Data<Integer> getHeightjtf() {
		return heightjtf;
	}

	private final TextFieldDouble xjtf;

	public Data<Double> getXjtf() {
		return xjtf;
	}

	private final TextFieldDouble yjtf;

	public Data<Double> getYjtf() {
		return yjtf;
	}

	private final TextFieldDouble mjtf;

	public Data<Double> getMjtf() {
		return mjtf;
	}

	private final TextFieldDouble njtf;

	public Data<Double> getNjtf() {
		return njtf;
	}

	private final TextFieldDouble rjtf;

	public Data<Double> getRjtf() {
		return rjtf;
	}

	private final Button2 copypastejb;

	public Data<Boolean> getCopypastejb() {
		return copypastejb;
	}

	public ComboBoxList locationjcb;

	public ActiveData<Object[]> getLocationjcb() {
		return locationjcb;
	}

	private final Button undojb;

	public Data<Boolean> getUndojb() {
		return undojb;
	}

	private final TextFieldDouble zoomjtf;

	public Data<Double> getZoomjtf() {
		return zoomjtf;
	}

	private final Button2 zoomjb;

	public Data<Boolean> getZoomjb() {
		return zoomjb;
	}

	private final Button renderjb;

	public Data<Boolean> getRenderjb() {
		return renderjb;
	}

	private final Button canceljb;

	public Data<Boolean> getCanceljb() {
		return canceljb;
	}

	private final Button repaintjb;

	public Data<Boolean> getRepaintjb() {
		return repaintjb;
	}

	private final ComboBoxItem fractaljcb;

	public ComboBoxItem getFractaljcb() {
		return fractaljcb;
	}

	private final Panel fractaljp;

	public Panel getFractaljp() {
		return fractaljp;
	}

	private final ComboBoxItem functionjcb;

	public ComboBoxItem getFunctionjcb() {
		return functionjcb;
	}

	private final Panel functionjp;

	public Panel getFunctionjp() {
		return functionjp;
	}

	private final ComboBoxItem filterjcb;

	public ComboBoxItem getFilterjcb() {
		return filterjcb;
	}

	private final Panel filterjp;

	public Panel getFilterjp() {
		return filterjp;
	}

	private final ComboBoxItem picturesizejcb;

	public ComboBoxItem getPicturesizejcb() {
		return picturesizejcb;
	}

	private final TextFieldInteger picturewjtf;

	public Data<Integer> getPicturewjtf() {
		return picturewjtf;
	}

	private final TextFieldInteger picturehjtf;

	public Data<Integer> getPicturehjtf() {
		return picturehjtf;
	}

	private final Button picturejb;

	public Data<Boolean> getPicturejb() {
		return picturejb;
	}

	private final ComboBoxItem picturejcb;

	public ComboBoxItem getPicturejcb() {
		return picturejcb;
	}

	public Item[] getAll() {
		return all;
	}

}