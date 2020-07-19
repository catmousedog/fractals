package me.catmousedog.fractals.components;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Active {@link Data} representing a button
 */
public class Button extends Data<Void> {

	private JButton jb;
	private JLabel jl;
	
	public static class ButtonBuilder {
		
		private String text, lbl, tip;
		private ActionListener e;
		
		public ButtonBuilder(String text) {
			this.text = text;
		}
		
		public ButtonBuilder setLabel(String lbl) {
			this.lbl = lbl;
			return this;
		}
		
		public ButtonBuilder setTip(String tip) {
			this.tip = tip;
			return this;
		}
		
		public ButtonBuilder setAction(ActionListener e) {
			this.e = e;
			return this;
		}
		
		public Button build() {
			return new Button(text, lbl, tip, e);
		}
		
	}
	
	public Button(String text, String lbl, String tip, ActionListener e) {
		jb = new JButton(text);
		jb.setMaximumSize(new Dimension(Integer.MAX_VALUE, jb.getPreferredSize().height));
		jb.addActionListener(e);
		jb.setAlignmentX(0);
		jb.setToolTipText(tip);
		
		jl = new JLabel(lbl);
		jl.setAlignmentX(0);
		jl.setFont(new Font(null, Font.PLAIN, 12));
		jl.setToolTipText(tip);
	}

	@Override
	public Component panel() {
		JPanel jp = new JPanel();
		jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
		
		jp.add(jl);
		jp.add(jb);
		
		return jp;
	}

	@Override
	public void save() {

	}

	@Override
	public void update() {

	}
}
