package me.catmousedog.fractals.utils;

import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.Properties;

/**
 * {@link Properties} with ordered keys.
 */
@SuppressWarnings("serial")
public class OrderedProperties extends Properties {

	private final LinkedHashSet<Object> keyOrder = new LinkedHashSet<>();

	public OrderedProperties() {
	}

	public OrderedProperties(OrderedProperties properties) {
		super(properties);
	}

	@Override
	public Enumeration<Object> keys() {
		return Collections.enumeration(keyOrder);
	}

	@Override
	public synchronized Object put(Object key, Object value) {
		keyOrder.add(key);
		return super.put(key, value);
	}

}
