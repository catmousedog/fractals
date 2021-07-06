package me.catmousedog.fractals.paneloperators.fractals;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
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

	private List<Region> leja = new LinkedList<Region>();

	public TestFractal() {
		super();

		// N, s, C in file
		int T = 2;
		Integer[] N = new Integer[T];
		Path par = Path.of("C:\\Users\\Gebruiker\\source\\repos\\FractalDrawer\\FractalDrawer\\data\\parameters.txt");
		try (BufferedReader parameters = Files.newBufferedReader(par)) {
			for (int i = 0; i < T; i++) {
				String[] p = parameters.readLine().split(",");
				N[i] = Integer.parseInt(p[0]);
				double s = Double.parseDouble(p[1]);
				Region region = new Region(s);

				Path segment = Path
						.of("C:\\Users\\Gebruiker\\source\\repos\\FractalDrawer\\FractalDrawer\\data\\leja\\segment_"
								+ Integer.toString(i) + ".txt");
				try (BufferedReader reader = Files.newBufferedReader(segment)) {
					String line;
					for (int j = 0; (line = reader.readLine()) != null && j < N[i]; j++) {
						String[] l = line.split(",");
						region.points.add(new Complex(Double.parseDouble(l[0]), -Double.parseDouble(l[1])));
					}
				}
				region.setC();
				leja.add(region);
			}
			// calculate degree
			List<Integer> degreeList = Arrays.asList(N);
			Collections.sort(degreeList);
			degree = 1 + degreeList.get(0); // 1 + d0
		} catch (IOException e) {
			e.printStackTrace();
		}

		functions = new Function[] { new IterativeFunction(this), new NormalizedFunction(this),
				new PotentialFunction(this), new EscapeAngleFunction(this), new BinaryFunction(this),
				new LambertFunction(this), new TestFunction(this), new DistanceEstimator(this) };
		function = functions[0];
	}

	private TestFractal(TestFractal fractal) {
		super(fractal);
		leja = fractal.leja;
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
			Complex O = new Complex(0, 0); // Omega // W = 1/Omega
			Complex dW = new Complex(0, 0); // W' = (1/Omega)'
			for (Region region : leja) {
				Complex iomega = region.omega(q).inverse(); // o^-1
				O = O.add(iomega); // O += o^-1
				if (usingDerivative) {
					dW = dW.add(region.domega(q).multiply(iomega.square())); // W' += o'o^-2
				}
			}
			O = O.inverse(); // O = O^-1

			if (usingDerivative) {
				dq = dq.multiply(O.add(q.multiply(dW).multiply(O.square()))); // z' = z'O+zO' = z'(O + zW'O²)
			}

			q = q.multiply(O); // z = zO
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

}
