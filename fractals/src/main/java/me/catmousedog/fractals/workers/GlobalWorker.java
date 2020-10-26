package me.catmousedog.fractals.workers;

import java.beans.PropertyChangeListener;
import java.util.logging.Logger;

import javax.swing.SwingWorker;

import org.jetbrains.annotations.Nullable;

public abstract class GlobalWorker extends SwingWorker<Void, Void> implements PropertyChangeListener {

	protected final Logger logger = Logger.getLogger("fractals");

	/**
	 * The <code>Runnable</code> that is run after the <code>SwingWorker</code> is
	 * done.
	 */
	@Nullable
	protected Runnable runnable;

	protected GlobalWorker(@Nullable Runnable runnable) {
		this();
		this.runnable = runnable;
	}

	protected GlobalWorker() {
		addPropertyChangeListener(this);
	}
}
