package me.catmousedog.fractals.workers;

import java.beans.PropertyChangeListener;

import javax.swing.SwingWorker;

import org.jetbrains.annotations.Nullable;

public abstract class GlobalWorker extends SwingWorker<Void, Void> implements PropertyChangeListener {

//	protected boolean ready = false;

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

//	public boolean isReady() {
//		return ready;
//	}

	/**
	 * Cancels the {@link SwingWorker} and sets {@link GlobalWorker#ready} to true.
	 * 
	 * @return {@link SwingWorker#cancel(boolean)}
	 */
//	public boolean cancel() {
//		ready = true;
//		return super.cancel(true);
//	}
}
