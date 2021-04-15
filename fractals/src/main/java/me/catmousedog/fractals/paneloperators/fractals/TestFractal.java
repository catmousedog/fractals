package me.catmousedog.fractals.paneloperators.fractals;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

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

	private static final List<Region> regions = new ArrayList<Region>();

	static {
		// origin
		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.559324, 149.789442));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.105000, 0.225000));
		regions.get(regions.size() - 1).coefficients.add(new Complex(1.067158, -94.182838));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.017163, 35.935711));

		// inside
//		regions.add(new Region());
//		regions.get(regions.size() - 1).coefficients.add(new Complex(-4.473519, -6.462078));
//		regions.get(regions.size() - 1).coefficients.add(new Complex(0, -16.138242));
//		regions.get(regions.size() - 1).coefficients.add(new Complex(0, 2.422910));
//		regions.get(regions.size() - 1).coefficients.add(new Complex(0, 0.336259));
//		regions.get(regions.size() - 1).coefficients.add(new Complex(0, 0.945526));

		// other
//		regions.add(new Region());
//		regions.get(regions.size() - 1).coefficients.add(new Complex(5.994425, 5.208949));
//		regions.get(regions.size() - 1).coefficients.add(new Complex(7.205093, -13.774308));
//		regions.get(regions.size() - 1).coefficients.add(new Complex(3.596655, 1.529612));
//		regions.get(regions.size() - 1).coefficients.add(new Complex(1.074862, 0.950840));
//		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.744136, 0.620113));

		// ----------------------------------- Name -----------------------------------
		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.336380, -0.124403));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-4.217361, -41.302144));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.191170, 0.038689));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.088757, -0.061447));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.002276, -0.004552));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.286560, 0.177526));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-4.215850, -41.029263));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.151072, -0.147687));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.035624, 0.018935));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.169308, -0.202338));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-3.954182, -40.915049));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.142716, 0.093786));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.073157, 0.004352));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.064993, -0.285415));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-3.596704, -40.764859));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.038191, 0.154069));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.019312, 0.068621));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.010433, 0.037545));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.122321, 0.331862));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-3.051963, -40.740573));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.064818, -0.230293));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.035084, 0.036187));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.292727, 0.304074));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-2.427528, -41.110506));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.176604, -0.127886));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.045674, -0.012180));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.086592, -0.166547));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-2.740038, -40.919014));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.032175, 0.053229));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.062130, 0.016614));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.407069, 0.072961));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-2.031280, -41.669563));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.182961, -0.053474));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.025952, -0.028520));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.292549, -0.115171));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-2.194257, -41.598482));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.119791, 0.047792));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.065171, 0.034137));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.237039, -0.125926));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-1.919256, -42.186717));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.106667, 0.050001));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.007037, -0.045555));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.003334, 0.011482));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.254001, -0.049000));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-2.103331, -42.204272));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.070000, -0.042000));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.056000, -0.056000));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.016333, -0.018667));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.050106, -0.253660));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-2.253498, -42.540740));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.056700, 0.159601));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.010500, -0.016800));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.071876, 0.194073));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-2.334360, -42.450689));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.008765, -0.053111));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.015778, -0.047333));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.005259, -0.035580));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.000000, -0.019284));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.005259, -0.021555));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.280239, -0.141387));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-2.859880, -42.310126));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.078230, 0.116993));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.020162, -0.008014));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.113469, 0.139347));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-2.550564, -42.438815));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.056425, -0.073213));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.022154, -0.032154));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.411307, 0.247006));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-3.213441, -41.744414));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.249473, -0.098779));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.004214, -0.020096));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.578868, 0.141285));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-3.461401, -41.131016));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.307603, -0.156396));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.000000, 0.000000));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.017635, -0.018752));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.613229, 0.291492));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-3.894525, -39.883058));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.406022, -0.096510));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.000000, 0.000000));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.036098, -0.003282));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.277952, -0.263119));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-4.312626, -38.813070));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.156396, 0.057206));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.005056, -0.015167));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.022751, -0.022751));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.237073, 0.167018));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-4.716945, -38.236966));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.103874, -0.074886));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.033819, -0.019325));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.089380, -0.270217));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-5.088621, -38.000231));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.021741, 0.159771));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.004831, 0.016910));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.080900, -0.097107));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-4.924771, -38.184952));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.041086, 0.055891));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.026831, 0.024007));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.070338, -0.189242));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-5.572240, -38.037613));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.061964, 0.087085));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.003349, 0.015072));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.009767, -0.105461));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-5.367518, -38.037599));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.006104, 0.036626));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.003663, 0.037381));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.203783, 0.008961));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-5.789104, -38.389909));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.091638, -0.005098));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.030685, -0.012963));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.007783, -0.003940));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.113781, -0.087077));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-5.698199, -38.211567));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.054568, 0.033670));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.031348, 0.023221));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.008127, 0.006966));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.014983, -0.204010));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-5.489734, -38.616086));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.001363, 0.095419));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.012257, -0.003631));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.067842, 0.145366));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-5.643205, -38.488897));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.029811, -0.046705));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.034969, -0.039496));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.006956, -0.014906));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.082625, -0.338346));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-5.121768, -38.566620));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.011974, 0.215021));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.007227, -0.010118));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.001210, 0.014218));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.103646, 0.239741));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-5.035557, -38.472260));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.019147, -0.120126));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.019147, -0.059338));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.163791, -0.374822));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-4.119547, -38.250729));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.061723, 0.183791));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.000000, 0.000000));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.002069, 0.024827));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.140563, -0.460954));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-3.556055, -38.072373));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.054005, 0.216754));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.019675, 0.017489));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.010931, 0.024048));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.005578, -0.373345));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-2.875837, -37.823314));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.014097, 0.198884));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.018032, 0.021805));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.017708, -0.263847));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-2.785596, -38.063063));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.015937, 0.118643));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.028333, 0.058436));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.130767, 0.184348));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-2.299776, -38.060771));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.067290, -0.068175));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.012666, 0.031353));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.532058, 0.017471));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-1.493634, -38.948549));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.281401, -0.107923));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.054180, -0.011112));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.312778, 0.089962));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-1.123946, -39.685953));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.066556, -0.095493));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.017362, -0.005787));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.183609, 0.079830));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-1.231057, -39.242808));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.083821, -0.041911));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.049894, -0.015966));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.233839, 0.219442));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.825936, -40.040138));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.086996, -0.127956));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.018668, 0.016254));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.250592, 0.133450));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.750297, -39.950476));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.091933, -0.117362));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.003912, -0.050857));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.011956, -0.017165));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.035049, -0.280729));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.203221, -40.283491));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.022887, 0.160473));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.015698, -0.021125));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.069889, 0.146820));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.347721, -40.182021));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.033448, -0.061615));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.008802, -0.045771));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.260874, -0.040027));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.083684, -40.035305));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.112251, 0.026180));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.016044, -0.020055));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.193638, -0.029046));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.016663, -40.098816));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.048410, -0.012448));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.088520, -0.017981));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.271255, 0.197656));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.057197, -39.657989));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.141024, -0.097857));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.017219, 0.008909));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.004053, 0.012159));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.219237, 0.259442));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.371745, -39.317375));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.113690, -0.152474));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.023278, -0.003320));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.007897, -0.007773));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.140011, 0.290852));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.815861, -39.127135));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.077784, -0.185850));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.029169, 0.067224));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.074692, 0.277811));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-1.144166, -38.952685));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.028909, -0.171126));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.001308, 0.013081));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.004186, -0.017164));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.245459, -0.277789));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-1.406752, -38.269537));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.124198, 0.106880));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.054770, 0.005552));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.017242, 0.003676));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.232486, -0.067925));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-1.389445, -38.466397));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.048698, 0.017190));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.104934, 0.039988));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.001474, -0.331289));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.851100, -38.050262));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.059555, 0.211462));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.017427, 0.017193));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.049933, -0.141914));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-1.056310, -38.156987));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.027594, 0.078841));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.011826, 0.036792));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.200966, 0.233540));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.250739, -38.233416));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.086344, -0.183747));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.017061, 0.006230));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.038340, -0.146259));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.467295, -38.212626));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.046860, 0.083780));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.014200, 0.024140));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.224676, 0.127756));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.203660, -38.612678));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.109543, -0.126463));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.001980, 0.020472));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.092465, -0.114834));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.085972, -38.580486));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.078764, 0.074703));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.014772, 0.013295));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.484795, 0.152727));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.807091, -39.217209));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.201232, -0.022768));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.001598, -0.013242));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.040795, 0.018829));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.411608, 0.214267));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.589398, -38.696953));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.194803, -0.037736));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.000000, 0.000000));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.048533, 0.002130));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.176894, 0.119654));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.754193, -38.502120));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.093102, -0.049654));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.023792, -0.018965));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.303940, 0.263310));
		regions.get(regions.size() - 1).coefficients.add(new Complex(1.383503, -39.337905));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.181421, -0.219119));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.004712, -0.023561));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.234090, 0.114469));
		regions.get(regions.size() - 1).coefficients.add(new Complex(1.024390, -38.936758));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.079010, -0.063552));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.020611, -0.056681));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.126928, -0.350393));
		regions.get(regions.size() - 1).coefficients.add(new Complex(2.065686, -39.729461));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.006601, 0.170252));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.025233, -0.024074));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.057948, 0.107647));
		regions.get(regions.size() - 1).coefficients.add(new Complex(1.735983, -39.518878));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.022898, -0.057346));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.006399, -0.031248));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.271857, 0.087903));
		regions.get(regions.size() - 1).coefficients.add(new Complex(2.271960, -39.396754));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.070789, -0.039208));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.060792, -0.027633));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.049736, -0.047630));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.359060, -0.090972));
		regions.get(regions.size() - 1).coefficients.add(new Complex(2.509251, -39.451852));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.156692, 0.043489));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.035454, -0.012639));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.461250, 0.075188));
		regions.get(regions.size() - 1).coefficients.add(new Complex(2.384208, -38.871582));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.225562, -0.121425));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.023971, 0.017946));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.222472, -0.017919));
		regions.get(regions.size() - 1).coefficients.add(new Complex(2.288197, -39.124414));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.071677, 0.013439));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.064235, 0.004480));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.127570, 0.255584));
		regions.get(regions.size() - 1).coefficients.add(new Complex(2.442800, -38.232387));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.016775, -0.127331));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.014808, 0.024660));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.142109, -0.054028));
		regions.get(regions.size() - 1).coefficients.add(new Complex(2.493588, -38.417660));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.067229, 0.005846));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.051152, 0.008769));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.010230, 0.001461));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.160266, 0.230885));
		regions.get(regions.size() - 1).coefficients.add(new Complex(3.005256, -38.352102));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.137622, -0.140441));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.010462, 0.017516));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.063500, -0.159014));
		regions.get(regions.size() - 1).coefficients.add(new Complex(2.828629, -38.295494));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.053506, 0.087006));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.013506, 0.035250));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.154766, -0.134755));
		regions.get(regions.size() - 1).coefficients.add(new Complex(3.189415, -38.578773));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.099257, 0.082262));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.015751, 0.015751));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.532058, 0.017471));
		regions.get(regions.size() - 1).coefficients.add(new Complex(3.616366, -38.948549));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.281401, -0.107923));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.054180, -0.011112));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.312778, 0.089962));
		regions.get(regions.size() - 1).coefficients.add(new Complex(3.986054, -39.685953));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.066556, -0.095493));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.017362, -0.005787));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.183609, 0.079830));
		regions.get(regions.size() - 1).coefficients.add(new Complex(3.878943, -39.242808));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.083821, -0.041911));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.049894, -0.015966));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.233839, 0.219442));
		regions.get(regions.size() - 1).coefficients.add(new Complex(4.284064, -40.040138));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.086996, -0.127956));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.018668, 0.016254));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.250592, 0.133450));
		regions.get(regions.size() - 1).coefficients.add(new Complex(4.359703, -39.950476));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.091933, -0.117362));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.003912, -0.050857));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.011956, -0.017165));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.035049, -0.280729));
		regions.get(regions.size() - 1).coefficients.add(new Complex(4.906779, -40.283491));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.022887, 0.160473));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.015698, -0.021125));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.069889, 0.146820));
		regions.get(regions.size() - 1).coefficients.add(new Complex(4.762279, -40.182021));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.033448, -0.061615));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.008802, -0.045771));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.260874, -0.040027));
		regions.get(regions.size() - 1).coefficients.add(new Complex(5.193684, -40.035305));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.112251, 0.026180));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.016044, -0.020055));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.193638, -0.029046));
		regions.get(regions.size() - 1).coefficients.add(new Complex(5.093337, -40.098816));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.048410, -0.012448));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.088520, -0.017981));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.271255, 0.197656));
		regions.get(regions.size() - 1).coefficients.add(new Complex(5.052803, -39.657989));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.141024, -0.097857));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.017219, 0.008909));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.004053, 0.012159));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.219237, 0.259442));
		regions.get(regions.size() - 1).coefficients.add(new Complex(4.738255, -39.317375));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.113690, -0.152474));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.023278, -0.003320));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.007897, -0.007773));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.140011, 0.290852));
		regions.get(regions.size() - 1).coefficients.add(new Complex(4.294139, -39.127135));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.077784, -0.185850));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.029169, 0.067224));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.074692, 0.277811));
		regions.get(regions.size() - 1).coefficients.add(new Complex(3.965834, -38.952685));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.028909, -0.171126));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.001308, 0.013081));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.004186, -0.017164));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.245459, -0.277789));
		regions.get(regions.size() - 1).coefficients.add(new Complex(3.703248, -38.269537));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.124198, 0.106880));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.054770, 0.005552));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.017242, 0.003676));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.232486, -0.067925));
		regions.get(regions.size() - 1).coefficients.add(new Complex(3.720555, -38.466397));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.048698, 0.017190));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.104934, 0.039988));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.001474, -0.331289));
		regions.get(regions.size() - 1).coefficients.add(new Complex(4.258900, -38.050262));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.059555, 0.211462));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.017427, 0.017193));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.049933, -0.141914));
		regions.get(regions.size() - 1).coefficients.add(new Complex(4.053690, -38.156987));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.027594, 0.078841));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.011826, 0.036792));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.200966, 0.233540));
		regions.get(regions.size() - 1).coefficients.add(new Complex(4.859261, -38.233416));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.086344, -0.183747));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.017061, 0.006230));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.038340, -0.146259));
		regions.get(regions.size() - 1).coefficients.add(new Complex(4.642705, -38.212626));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.046860, 0.083780));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.014200, 0.024140));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.224676, 0.127756));
		regions.get(regions.size() - 1).coefficients.add(new Complex(5.313660, -38.612678));
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.109543, -0.126463));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.001980, 0.020472));

		regions.add(new Region());
		regions.get(regions.size() - 1).coefficients.add(new Complex(-0.092465, -0.114834));
		regions.get(regions.size() - 1).coefficients.add(new Complex(5.195972, -38.580486));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.078764, 0.074703));
		regions.get(regions.size() - 1).coefficients.add(new Complex(0.014772, 0.013295));

		for (Region r : regions) {
			r.shiftAllCoefficients();
			r.update();
		}
	}

	public TestFractal() {
		super();

		degree = Region.N + 1;

		functions = new Function[] { new IterativeFunction(this), new NormalizedFunction(this),
				new PotentialFunction(this), new EscapeAngleFunction(this), new BinaryFunction(this),
				new LambertFunction(this), new TestFunction(this), new DistanceEstimator(this) };
		function = functions[6];

	}

	private TestFractal(TestFractal fractal) {
		super(fractal);
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
			Complex dOmega = new Complex(0, 0); // W' = 0
			for (Region region : regions) {
				Complex iomega1 = region.omega(q).inverse(); // (w+1)^-1
				S = S.add(iomega1); // S += (w+1)^-1
				if (usingDerivative) {
					dOmega = dOmega.add(region.dOmega(q).multiply(iomega1.square())); // W' += (w+1)' / (w+1)^2
				}
			}
			Complex Omega = S.inverse(); // W = S^-1

			if (usingDerivative) {
				dq = dq.multiply(Omega.add(q.multiply(Omega.square()).multiply(dOmega))); // z' = z'(W + z * W^2 * W')
			}

			q = q.multiply(Omega); // z = z * W
			//

			// test
//			Region region = regions.get(0);
//			Complex CA = region.realCoefficient(0).power(-region.r.size());
//			Complex CA = new Complex(1, 0);
//			Complex d = new Complex(0, 0);
//			for (int k = 0; k < region.r.size(); k++) {
//				Complex prod = new Complex(1, 0);
//				for (int j = 0; j < region.r.size(); j++) {
//					if (j != k)
//						prod = prod.multiply(q.subtract(region.r.get(j)));
//				}
//				d = d.add(prod);
//			}
//			dq = dq.multiply(d.multiply(CA));
//
//			Complex prod = new Complex(1, 0);
//			for (Complex r : region.r) {
//				prod = prod.multiply(q.subtract(r));
//			}
//			q = prod.multiply(CA);
			//

			// test2
//			dq = dq.multiply(regions.get(0).dOmega(q));
//			q = regions.get(0).omega(q);
			//

		}
		return new FractalValue(q.x, q.y, dq.x, dq.y, iterations, iterations);
	}

	@Override
	public void save() {
		super.save();
		for (Region region : regions)
			region.update();
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

	private static class Complex {

		public double x, y;

		public Complex(double x, double y) {
			this.x = x;
			this.y = y;
		}

		public Complex(double a) {
			this.x = Math.cos(a);
			this.y = Math.sin(a);
		}

		public Complex divide(Complex c) {
			double Y = c.x * c.x + c.y * c.y;
			double tx = (c.x * x + c.y * y) / Y;
			double ty = (c.x * y - c.y * x) / Y;
			return new Complex(tx, ty);
		}

		public Complex multiply(Complex c) {
			return new Complex(x * c.x - y * c.y, y * c.x + x * c.y);
		}

		public Complex add(Complex c) {
			return new Complex(x + c.x, y + c.y);
		}

		public Complex subtract(Complex c) {
			return new Complex(x - c.x, y - c.y);
		}

		public Complex power(double a) {
			double theta = Math.atan2(y, x);
			double Z = Math.exp(Math.log(mag()) * a / 2);
//			double Z = Math.pow(mag(), a / 2);
			return new Complex(Math.cos(a * theta) * Z, Math.sin(a * theta) * Z);
		}

		public Complex inverse() {
			return new Complex(1, 0).divide(this);
		}

		public Complex square() {
			return this.multiply(this);
		}

		public double mag() {
			return x * x + y * y;
		}

		@Override
		public Complex clone() {
			return new Complex(x, y);
		}

		@Override
		public String toString() {
			return String.format("%f\t%f", x, y);
		}
	}

	private static class Region {

		private static int N = 20;

		public final List<Complex> r = new ArrayList<Complex>(N);

		public final List<Complex> coefficients = new ArrayList<Complex>();

		public void update() {
			r.clear();
			for (int i = 0; i < N; i++)
				r.add(f(new Complex(2 * Math.PI * i / (double) N)));
		}

		private Complex f(Complex z) {
			Complex sum = new Complex(0, 0);

			for (int i = 0; i < coefficients.size(); i++) {
				sum = sum.add(z.power(1 - i).multiply(realCoefficient(i)));
			}
			return sum;
		}

		public Complex realCoefficient(int i) {
			Complex out = coefficients.get(i);
			if (i != 1 && coefficients.size() > 1)
				out = out.subtract(coefficients.get(1));
			return out;
		}

		// w+1
		private Complex omega(Complex q) {
			Complex omega = new Complex(1, 0);
			for (Complex r : this.r) {
				omega = omega.multiply(q.subtract(r));
			}
			if (coefficients.size() > 0)
				omega = omega.multiply(realCoefficient(0).power(-r.size()));
			return omega.add(new Complex(1, 0));
		}

		// (w+1)' = w'
		private Complex dOmega(Complex q) {
			Complex dOmega = new Complex(0, 0); // w' = 0

			for (int i = 0; i < r.size(); i++) {
				Complex term = new Complex(1, 0); // term = 1
				for (int j = 0; j < r.size(); j++) {
					if (i != j)
						term = term.multiply(q.subtract(r.get(j))); // term *= (z-r)
				}
				dOmega = dOmega.add(term); // w' += term
			}

			if (coefficients.size() > 0)
				dOmega = dOmega.multiply(realCoefficient(0).power(-r.size())); // w' *= CA^-n
			return dOmega;
		}

		public void shiftAllCoefficients() {
			if (coefficients.size() > 1) {
				Complex shift = coefficients.get(1);
				for (int i = 0; i < coefficients.size(); i++) {
					if (i != 1) {
						Complex p = coefficients.get(i);
						coefficients.set(i, p.add(shift));
					}
				}
			}
		}
	}
}
