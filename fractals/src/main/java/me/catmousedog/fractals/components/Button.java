package me.catmousedog.fractals.components;

import java.awt.event.ActionListener;

import javax.swing.JButton;

import org.jetbrains.annotations.NotNull;

public class Button extends Component {
	
	public Button(@NotNull JButton jb, @NotNull ActionListener e) {
//		jb.setPreferredSize(new Dimension(width, jtf.getPreferredSize().height));
//		jb.setMaximumSize(new Dimension(width, jtf.getPreferredSize().height));
		
		add(jb);
		
		jb.addActionListener(e);
	}
	
}
