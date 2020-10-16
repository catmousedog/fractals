package me.catmousedog.fractals.utils;

/**
 * A <code>Nameable</code> has an <code>informalName</code>,
 * <code>fileName</code> and a <code>ToolTip</code>.
 */
public interface Nameable {

	/**
	 * @return the <code>String</code> for the user to read. Beware that too long
	 *         <code>Strings</code> can cause issues.
	 */
	public String informalName();

	/**
	 * @return the file name, usually the <code>informalName</code> without spaces.
	 */
	public String fileName();

	/**
	 * @return a description for the user to read.
	 */
	public String getTip();

}