package me.catmousedog.fractals.ui;

import me.catmousedog.fractals.fractals.LinearTransform;

/**
 * A concrete position described by a {@link LinearTransform} and an iteration
 * count. This class also has a {@link Position#name} to identify it
 */
public class Position {

	private final LinearTransform transform;

	private String name;

	private int iterations;

	/**
	 * an Array of pre-saved positions
	 */
	public static Position[] preSaved = {
			new Position("pos1", new LinearTransform(-0.53001, 0.201, 0.001, 0.001, 0), 100),
			new Position("pos2", new LinearTransform(0.005, 0.01, 0.01, 0.02, 4), 1000) };

	public Position(String name, LinearTransform transform, int iterations) {
		this.name = name;
		this.transform = transform;
		this.iterations = iterations;
	}

	public Position(LinearTransform transform, int iterations) {
		this.transform = transform;
		this.iterations = iterations;
	}

	public LinearTransform getTransform() {
		return transform;
	}

	public int getIterations() {
		return iterations;
	}

	@Override
	public String toString() {
		return name;
	}

	/**
	 * returns the information needed to recreate this {@link Position} as a String
	 * 
	 * @return String of format: "dx:dy:m:n:rot:iter"
	 */
	public String getID() {
		return Double.toString(transform.getdx()) + ":" + Double.toString(transform.getdy()) + ":"
				+ Double.toString(transform.getm()) + ":" + Double.toString(transform.getn()) + ":"
				+ Double.toString(transform.getrot()) + ":" + Integer.toString(iterations);
	}

	/**
	 * Creates a new {@link Position} from the String.
	 * <p>
	 * The String should be the same retrieved from using {@link Position#getID()}.
	 * <br>
	 * Doing <b>position.fromID(transform.getID())</b> should result in the same
	 * {@link Position}, just without the name.
	 * 
	 * @param id String representing this {@link Position} of the form:<br>
	 *           <b>dx:dy:m:n:rot:iter</b>
	 * 
	 * @throws NumberFormatException if any of the values could not be parsed
	 */
	public static Position fromID(String id) throws NumberFormatException {
		String[] args = id.split(":");
		double dx = 0, dy = 0, m = 1, n = 1, rot = 0;
		int iter = 0;

		if (args.length > 0)
			dx = Double.parseDouble(args[0]);
		if (args.length > 1)
			dy = Double.parseDouble(args[1]);
		if (args.length > 2)
			m = Double.parseDouble(args[2]);
		if (args.length > 3)
			n = Double.parseDouble(args[3]);
		if (args.length > 4)
			rot = Double.parseDouble(args[4]);
		if (args.length > 5)
			iter = Integer.parseInt(args[5]);

		return new Position(new LinearTransform(dx, dy, m, n, rot), iter);

	}
}
