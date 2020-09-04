package me.catmousedog.fractals.ui.components.concrete;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.EventObject;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import me.catmousedog.fractals.ui.components.ActiveData;

/**
 * Represents a {@link JSlider} that goes from 0 to 1.
 */
public class SliderDouble extends ActiveData<Double> {

	private final JSlider js;
	private final JLabel jl;
	private final ChangeListener change;

	public static class Builder {

		private String lbl, tip;
		private ChangeListener c;
		private int major = 25, M = 100;

		public Builder setLabel(String lbl) {
			this.lbl = lbl;
			return this;
		}

		public Builder setTip(String tip) {
			this.tip = tip;
			return this;
		}

		public Builder setChange(ChangeListener c) {
			this.c = c;
			return this;
		}

		/**
		 * The major tick spacing for the {@link JSlider}, must be a value from 0 to the
		 * value given to {@link Builder#setMax(int)}.
		 */
		public Builder setMajor(int major) {
			this.major = major;
			return this;
		}

		/**
		 * The maximum of the {@link JSlider}, the default for this is 100.
		 */
		public Builder setMax(int M) {
			this.M = M;
			return this;
		}

		public SliderDouble build() {
			return new SliderDouble(lbl, tip, c, major, M);
		}

	}

	private SliderDouble(String lbl, String tip, ChangeListener c, int m, int M) {
		change = c;
		js = new JSlider();
		js.setMaximum(M);
		js.setMajorTickSpacing(m);
		js.setPreferredSize(new Dimension(100, js.getPreferredSize().height));
		js.addChangeListener(e -> event(e));
		js.setAlignmentX(JSlider.LEFT_ALIGNMENT);
		js.setToolTipText(tip);

		jl = new JLabel(lbl);
		jl.setFont(new Font(null, Font.PLAIN, 12));
		jl.setAlignmentX(JLabel.LEFT_ALIGNMENT);
		jl.setToolTipText(tip);
	}

	@Override
	public Component panel() {
		JPanel jp = new JPanel();
		jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
		jp.setAlignmentX(JPanel.LEFT_ALIGNMENT);

		jp.add(jl);
		jp.add(js);

		return jp;
	}

	@Override
	public void save() {
		data = js.getValue() / (double) js.getMaximum();
	}

	@Override
	public void update() {
		js.setValue((int) (data * js.getMaximum()));
	}

	@Override
	public void preRender() {
		js.setEnabled(false);
	}

	@Override
	public void postRender() {
		js.setEnabled(true);
	}

	@Override
	protected void concreteEvent(EventObject e) {
		change.stateChanged((ChangeEvent) e);
	}
}
