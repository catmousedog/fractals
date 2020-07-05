package me.catmousedog.fractals.components;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class ComponentFactory {
	
	private final JPanel jpi;
	
	public ComponentFactory(JPanel jpi) {
		this.jpi = jpi;
	}
	
	public Label createLabel(@NotNull String lbl, int style, int size) {
		return new Label(lbl, style, size);
	}
	
	public Label createLabel(@NotNull String lbl, int style) {
		return new Label(lbl, style);
	}
	
	public Label createLabel(@NotNull String lbl) {
		return new Label(lbl);
	}
	
	/**
	 * creates a TextField with an optional label
	 * @param lbl optional label in front of the JTextField
	 * @param jtf the JTextField
	 * @param e the ActionListener attached to the jtf
	 * @return the TextField {@link Component}
	 */
	public TextField createTextField(@Nullable String lbl, @NotNull JTextField jtf, @NotNull ActionListener e) {
		return new TextField(lbl, jtf, jpi.getWidth() / 2, e);
	}
	
	public Button createButton(JButton jb, ActionListener e) {
		return new Button(jb, e);
	}
	
}
