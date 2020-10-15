package me.catmousedog.fractals.fractals.functions;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import me.catmousedog.fractals.fractals.filters.Filter;
import me.catmousedog.fractals.ui.Savable;
import me.catmousedog.fractals.utils.FractalValue;

public abstract class Function<N extends Number> implements Savable {

	@Nullable
	protected Filter[] filters;

	@NotNull
	protected Filter filter;

	public abstract N get(FractalValue v);
	
	@Override
	public void save() {
		filter.save();
	}
	
	@Override
	public void update() {
		filter.update();
	}
	
	public Filter[] getFilters() {
		return filters;
	}
	
	public Filter getFilter() {
		return filter;
	}

}
