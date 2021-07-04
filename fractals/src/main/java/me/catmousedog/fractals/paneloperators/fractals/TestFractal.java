package me.catmousedog.fractals.paneloperators.fractals;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import me.catmousedog.fractals.data.Complex;
import me.catmousedog.fractals.data.FractalValue;
import me.catmousedog.fractals.paneloperators.functions.BinaryFunction;
import me.catmousedog.fractals.paneloperators.functions.DistanceEstimator;
import me.catmousedog.fractals.paneloperators.functions.EscapeAngleFunction;
import me.catmousedog.fractals.paneloperators.functions.Function;
import me.catmousedog.fractals.paneloperators.functions.IterativeFunction;
import me.catmousedog.fractals.paneloperators.functions.LambertFunction;
import me.catmousedog.fractals.paneloperators.functions.NormalizedFunction;
import me.catmousedog.fractals.paneloperators.functions.PotentialFunction;
import me.catmousedog.fractals.paneloperators.functions.TestFunction;

public class TestFractal extends MouseFractal {

	private final int o = 1;
	
	private List<Region> origins = new LinkedList<Region>();

	private List<Region> regions = new LinkedList<Region>();

	public TestFractal() {
		super();

		// N, s, C in file
		Path par = Path.of("C:\\Users\\Gebruiker\\source\\repos\\FractalDrawer\\FractalDrawer\\data\\parameters.txt");
		try (BufferedReader parameters = Files.newBufferedReader(par)) {
			for (int i = 0; i <= 0; i++) {
				double C = Double.parseDouble(parameters.readLine().split(",")[2]);
				Region region = new Region(C);

				Path segment = Path
						.of("C:\\Users\\Gebruiker\\source\\repos\\FractalDrawer\\FractalDrawer\\data\\leja\\segment_"
								+ Integer.toString(i) + ".txt");
				try (BufferedReader reader = Files.newBufferedReader(segment)) {
					String line;
					while ((line = reader.readLine()) != null) {
						String[] l = line.split(",");
						region.leja.add(new Complex(Double.parseDouble(l[0]), Double.parseDouble(l[1])));
					}
				}
				regions.add(region);
				if (i < o)
					origins.add(region);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		degree = regions.get(regions.size() - 1).N + 2;
//		degree = 26;

		functions = new Function[] { new IterativeFunction(this), new NormalizedFunction(this),
				new PotentialFunction(this), new EscapeAngleFunction(this), new BinaryFunction(this),
				new LambertFunction(this), new TestFunction(this), new DistanceEstimator(this) };
		function = functions[0];
	}

	private TestFractal(TestFractal fractal) {
		super(fractal);
		origins = fractal.origins;
		regions = fractal.regions;
	}

	@Override
	public FractalValue get(double cx, double cy) {
		Complex q = new Complex(cx, cy);
		Complex dq = new Complex(1, 0);

		for (int i = 0; i < iterations; i++) {

			if (q.mag() > bailout) {
				return new FractalValue(q.x, q.y, dq.x, dq.y, i, iterations);
			}

			// f(z)
			Complex S = new Complex(0, 0);
//			Complex dOmega = new Complex(0, 0); // W' = 0
			for (Region region : regions) {
				Complex iomega = region.omega(q).inverse(); // w^-1
				S = S.add(iomega); // S += w^-1
//				if (usingDerivative) {
//					dOmega = dOmega.add(region.dOmega(q).multiply(iomega1.square())); // W' += (w+1)' / (w+1)^2
//				}
			}
			S = S.inverse(); // W = S^-1

//			if (usingDerivative) {
//				dq = dq.multiply(Omega.add(q.multiply(Omega.square()).multiply(dOmega))); // z' = z'(W + z * W^2 * W')
//			}

			q = q.multiply(S); // z = z * W
			//
		}
		return new FractalValue(q.x, q.y, dq.x, dq.y, iterations, iterations);
	}

	@Override
	public @NotNull String informalName() {
		return "Test Fractal";
	}

	@Override
	public @NotNull String fileName() {
		return "TestFractal";
	}

	@Override
	public @NotNull String getTip() {
		return "TODO";
	}

	@Override
	public @NotNull Fractal clone() {
		return new TestFractal(this);
	}

	private static class Region {

		public final List<Complex> leja = new LinkedList<Complex>();

		public final double C;

		public Region(double C) {
			this.C = C;
		}

		// w
		private Complex omega(Complex q) {
			Complex omega = new Complex(1, 0);
			for (Complex r : leja) {
				omega = omega.multiply(q.subtract(r));
			}
			return omega.multiply(C);
		}

		// (w+1)' = w'
		private Complex dOmega(Complex q) {
			Complex dOmega = new Complex(0, 0); // w' = 0

			for (int i = 0; i < leja.size(); i++) {
				Complex term = new Complex(1, 0); // term = 1
				for (int j = 0; j < leja.size(); j++) {
					if (i != j)
						term = term.multiply(q.subtract(leja.get(j))); // term *= (z-r)
				}
				dOmega = dOmega.add(term); // w' += term
			}
			dOmega = dOmega.multiply(C); // w' *= C
			return dOmega;
		}
	}
}
