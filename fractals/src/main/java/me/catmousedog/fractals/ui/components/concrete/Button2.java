package me.catmousedog.fractals.ui.components.concrete;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import me.catmousedog.fractals.ui.components.Data;
import me.catmousedog.fractals.ui.components.Item;

/**
 * Active {@link Item} representing two buttons side by side.<br>
 * The first button and its properties is on the left.
 */
public class Button2 extends Data<Boolean> {

	private JButton jb1, jb2;

	public static class Builder {

		private String text1, tip1, text2, tip2;
		private ActionListener e1, e2;

		public Builder(String text1, String text2) {
			this.text1 = text1;
			this.text2 = text2;
		}

		public Builder setTip(String tip1, String tip2) {
			this.tip1 = tip1;
			this.tip2 = tip2;
			return this;
		}

		public Builder setAction(ActionListener e1, ActionListener e2) {
			this.e1 = e1;
			this.e2 = e2;
			return this;
		}

		public Button2 build() {
			return new Button2(text1, text2, tip1, tip2, e1, e2);
		}

	}

	private Button2(String text1, String text2, String tip1, String tip2, ActionListener e1, ActionListener e2) {
		jb1 = get(text1, tip1, e1);
		jb2 = get(text2, tip2, e2);
	}

	private JButton get(String text, String tip, ActionListener e) {
		JButton jb = new JButton(text);
		jb.setMaximumSize(new Dimension(Integer.MAX_VALUE, jb.getPreferredSize().height));
		jb.addActionListener(e);
		jb.setAlignmentX(JButton.LEFT_ALIGNMENT);
		jb.setToolTipText(tip);
		return jb;
	}

	@Override
	public Component panel() {
		JPanel jp = new JPanel();
		jp.setLayout(new BoxLayout(jp, BoxLayout.X_AXIS));
		jp.setAlignmentX(JPanel.LEFT_ALIGNMENT);
		
		jp.add(jb1);
		jp.add(Box.createRigidArea(new Dimension(2, 0)));
		jp.add(jb2);

		return jp;
	}

	@Override
	public void save() {
		data = (jb1.isEnabled() && jb2.isEnabled());
	}

	@Override
	public void update() {
		jb1.setEnabled(data);
		jb2.setEnabled(data);
	}

	@Override
	public void preRender() {
		jb1.setEnabled(false);
		jb2.setEnabled(false);
	}

	@Override
	public void postRender() {
		jb1.setEnabled(true);
		jb2.setEnabled(true);
	}
}
