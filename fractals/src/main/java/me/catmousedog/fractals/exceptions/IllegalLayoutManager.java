package me.catmousedog.fractals.exceptions;

import java.awt.Container;
import java.awt.LayoutManager;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * exception to indicate the {@link Container} has the incorrect {@link LayoutManager}
 */
@SuppressWarnings("serial")
public class IllegalLayoutManager extends Exception {
	
	private final String manager, desired; 
	
	public IllegalLayoutManager(@NotNull Container c, @Nullable String desired) {
		manager = c.getLayout().getClass().getName();
		this.desired = desired;
	}
	
	@Override
	public String getMessage() {
		return String.format("the JPanel has the incorrect LayoutManager, %s must be %s", manager, desired);
	}
	
}
