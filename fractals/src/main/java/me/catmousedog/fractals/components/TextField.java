package me.catmousedog.fractals.components;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("serial")
public class TextField extends Component {
	
	/**
	 * creates a {@linkplain JTextField} with an optional label next to it
	 * @param label next to the TextField
	 * @param text default text in the TextField
	 * @param width of the TextField, usually half of the total width
	 * @param e ActionListener added to the {@link JTextField}
	 */
	public TextField(@Nullable String label, @NotNull JTextField jtf, int width, @NotNull ActionListener e) {
		JLabel jlb = new JLabel(label);
		jlb.setFont(new Font(null, Font.PLAIN, 12));
		jtf.setPreferredSize(new Dimension(width, jtf.getPreferredSize().height));
		jtf.setMaximumSize(new Dimension(width, jtf.getPreferredSize().height));
		
		if (label != null) {
			add(Box.createHorizontalGlue());
			add(jlb);
			add(Box.createHorizontalGlue());
		}
		add(jtf);
		
		jtf.addActionListener(e);
	}
	
	
	
}



