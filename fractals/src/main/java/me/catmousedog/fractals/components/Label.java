package me.catmousedog.fractals.components;

import java.awt.Font;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Label extends Component {

	private JLabel lbl;
	
	public Label(String text, int style, int size) {
		lbl = new JLabel(text);
		lbl.setFont(new Font(null, style, size));
		add(lbl);
	}
	
	public Label(String text, int size) {
		lbl = new JLabel(text);
		lbl.setFont(new Font(null, Font.PLAIN, size));
		add(lbl);
	}
	
	public Label(String text) {
		lbl = new JLabel(text);
		add(lbl);
	}
	
	public void setText(String text) {
		lbl.setText(text);
	}
	
}
