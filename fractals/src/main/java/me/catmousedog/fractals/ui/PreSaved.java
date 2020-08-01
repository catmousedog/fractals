package me.catmousedog.fractals.ui;

import javax.swing.JComboBox;

import me.catmousedog.fractals.fractals.LinearTransform;

/**
 * PreSaved positions for use in a {@link JComboBox} to select from
 */
public enum PreSaved {

	islands(new LinearTransform(-.743643887037151, .131825904205330, 1.2000000000000002E-13, 1.2000000000000002E-13, 0), 4000),
	stove(new LinearTransform(-1, 0, 0.0005, 0.0005, 4), 100);
	private LinearTransform transform;

	private int iterations;
	
	private PreSaved(LinearTransform transform, int iterations) {
		this.transform = transform;
		this.iterations = iterations;
	}

	public LinearTransform getTransform() {
		return transform;
	}

	public int getIterations() {
		return iterations;
	}

}
