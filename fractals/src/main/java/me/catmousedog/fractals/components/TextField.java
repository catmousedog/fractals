package me.catmousedog.fractals.components;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JTextField;

import org.jetbrains.annotations.Nullable;

@SuppressWarnings("serial")
public class TextField extends Component {
	
	private final JLabel jlb;
	
	private final JTextField jtf;
	
	/**
	 * creates a new instance of {@link TextField} and sets the display messages
	 * @param label text to be displayed by the TextField
	 * @param text default text inside the {@link JTextField}
	 */
	public TextField(@Nullable String label, @Nullable String text, int size) {
		jlb = new JLabel(label);
		jlb.setFont(new Font(null, Font.PLAIN, 12));
		jtf = new JTextField(text);
		jtf.setPreferredSize(new Dimension(size, jtf.getPreferredSize().height));
		jtf.setMaximumSize(new Dimension(size, jtf.getPreferredSize().height));
		
		if (label != null) {
			add(Box.createHorizontalGlue());
			add(jlb);
			add(Box.createHorizontalGlue());
		}
		add(jtf);
		
		jtf.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("action");
	}
	
}



