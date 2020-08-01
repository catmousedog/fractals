package me.catmousedog.fractals.ui.components.concrete;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import me.catmousedog.fractals.ui.components.Item;

/**
 * A passive {@link Item} representing a {@link JPanel}. The {@link JPanel} can
 * be retrieved using {@link Panel#getPanel()} and edited this way.
 */
public class Panel extends Item {

	private final JPanel panel;

	public Panel() {
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setAlignmentX(JPanel.LEFT_ALIGNMENT);
		
		panel.add(new JLabel("test"));
	}
	
	public JPanel getPanel() {
		return panel;
	}

	@Override
	public Component panel() {
		return panel;
	}

	@Override
	public void save() {
	}

	@Override
	public void update() {
	}

}
