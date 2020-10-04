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
	private final double M, m;

	public static class Builder {

		private String lbl, tip;
		private ChangeListener c;
		private double m = 0.0, M = 1.0;

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
		 * The minimum of the {@link JSlider}, the default for this is 0.
		 * @param m
		 * @return
		 */
		public Builder setMin(double m) {
			this.m = m;
			return this;
		}

		/**
		 * The maximum of the {@link JSlider}, the default for this is 1.
		 */
		public Builder setMax(double M) {
			this.M = M;
			return this;
		}

		public SliderDouble build() {
			return new SliderDouble(lbl, tip, c, m, M);
		}

	}

	private SliderDouble(String lbl, String tip, ChangeListener c, double m, double M) {
		this.M = M;
		this.m = m;
		change = c;
		js = new JSlider();
		js.setMaximum(100);
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
		data = m + (M - m) * js.getValue() / 100.0;
	}

	@Override
	public void update() {
		js.setValue((int) ((data - m) * 100 / (M - m)));
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
