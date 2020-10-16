package me.catmousedog.fractals.fractals;

/**
 * An <code>Exception</code> to indicate when an action performed was not
 * supported due to the <code>Object</code> being a clone.
 */
@SuppressWarnings("serial")
public class CloneException extends Exception {

	public CloneException() {
		super();
	}

	public CloneException(String s) {
		super(s);
	}

}
