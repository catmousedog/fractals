package me.catmousedog.fractals.components;

import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

/**
 * Graphics Component for use with {@link GridBagLayout}
 */
@SuppressWarnings("serial")
public abstract class Component extends JPanel implements ActionListener {

	public Component() {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
	}
}
