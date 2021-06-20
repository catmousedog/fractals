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
		
		newRegion(4);
		addToLast(new Complex(-15.714279, -45.249261));
		addToLast(new Complex(-29.054232, -46.155392));

		newRegion(3);
		addToLast(new Complex(5.074091, 0.015589));
		addToLast(new Complex(-4.086720, -6.542156));

		// test name
//		newRegion(25);
//		addToLast(new Complex(-1.483122, 3.119670));
//		addToLast(new Complex(-1.763369, 6.173979));
//		addToLast(new Complex(-0.562563, -1.841117));
		
		newRegion(25);
		addToLast(new Complex(0.336380, -0.124403));
		addToLast(new Complex(-8.217361, 5.197856));
		addToLast(new Complex(-0.191170, 0.038689));
		addToLast(new Complex(-0.088757, -0.061447));
		addToLast(new Complex(-0.002276, -0.004552));

		newRegion(25);
		addToLast(new Complex(-0.286560, 0.177526));
		addToLast(new Complex(-8.215850, 5.470737));
		addToLast(new Complex(0.151072, -0.147687));
		addToLast(new Complex(-0.035624, 0.018935));

		newRegion(25);
		addToLast(new Complex(0.169308, -0.202338));
		addToLast(new Complex(-7.954182, 5.584951));
		addToLast(new Complex(-0.142716, 0.093786));
		addToLast(new Complex(-0.073157, 0.004352));

		newRegion(25);
		addToLast(new Complex(0.064993, -0.285415));
		addToLast(new Complex(-7.596704, 5.735141));
		addToLast(new Complex(-0.038191, 0.154069));
		addToLast(new Complex(-0.019312, 0.068621));
		addToLast(new Complex(-0.010433, 0.037545));

		newRegion(25);
		addToLast(new Complex(0.122321, 0.331862));
		addToLast(new Complex(-7.051963, 5.759427));
		addToLast(new Complex(-0.064818, -0.230293));
		addToLast(new Complex(0.035084, 0.036187));

		newRegion(25);
		addToLast(new Complex(0.292727, 0.304074));
		addToLast(new Complex(-6.427528, 5.389494));
		addToLast(new Complex(-0.176604, -0.127886));
		addToLast(new Complex(0.045674, -0.012180));

		newRegion(25);
		addToLast(new Complex(-0.086592, -0.166547));
		addToLast(new Complex(-6.740038, 5.580986));
		addToLast(new Complex(0.032175, 0.053229));
		addToLast(new Complex(0.062130, 0.016614));

		newRegion(25);
		addToLast(new Complex(0.407069, 0.072961));
		addToLast(new Complex(-6.031280, 4.830437));
		addToLast(new Complex(-0.182961, -0.053474));
		addToLast(new Complex(0.025952, -0.028520));

		newRegion(25);
		addToLast(new Complex(-0.292549, -0.115171));
		addToLast(new Complex(-6.194257, 4.901518));
		addToLast(new Complex(0.119791, 0.047792));
		addToLast(new Complex(0.065171, 0.034137));

		newRegion(25);
		addToLast(new Complex(0.237039, -0.125926));
		addToLast(new Complex(-5.919256, 4.313283));
		addToLast(new Complex(-0.106667, 0.050001));
		addToLast(new Complex(-0.007037, -0.045555));
		addToLast(new Complex(0.003334, 0.011482));

		newRegion(25);
		addToLast(new Complex(-0.254001, -0.049000));
		addToLast(new Complex(-6.103331, 4.295728));
		addToLast(new Complex(0.070000, -0.042000));
		addToLast(new Complex(0.056000, -0.056000));
		addToLast(new Complex(0.016333, -0.018667));

		newRegion(25);
		addToLast(new Complex(0.050106, -0.253660));
		addToLast(new Complex(-6.253498, 3.959260));
		addToLast(new Complex(-0.056700, 0.159601));
		addToLast(new Complex(0.010500, -0.016800));

		newRegion(25);
		addToLast(new Complex(-0.071876, 0.194073));
		addToLast(new Complex(-6.334360, 4.049311));
		addToLast(new Complex(0.008765, -0.053111));
		addToLast(new Complex(0.015778, -0.047333));
		addToLast(new Complex(0.005259, -0.035580));
		addToLast(new Complex(0.000000, -0.019284));
		addToLast(new Complex(0.005259, -0.021555));

		newRegion(25);
		addToLast(new Complex(-0.280239, -0.141387));
		addToLast(new Complex(-6.859880, 4.189874));
		addToLast(new Complex(0.078230, 0.116993));
		addToLast(new Complex(-0.020162, -0.008014));

		newRegion(25);
		addToLast(new Complex(0.113469, 0.139347));
		addToLast(new Complex(-6.550564, 4.061185));
		addToLast(new Complex(-0.056425, -0.073213));
		addToLast(new Complex(-0.022154, -0.032154));

		newRegion(25);
		addToLast(new Complex(0.411307, 0.247006));
		addToLast(new Complex(-7.213441, 4.755586));
		addToLast(new Complex(-0.249473, -0.098779));
		addToLast(new Complex(-0.004214, -0.020096));

		newRegion(25);
		addToLast(new Complex(0.578868, 0.141285));
		addToLast(new Complex(-7.461401, 5.368984));
		addToLast(new Complex(-0.307603, -0.156396));
		addToLast(new Complex(0.000000, 0.000000));
		addToLast(new Complex(-0.017635, -0.018752));

		newRegion(25);
		addToLast(new Complex(0.613229, 0.291492));
		addToLast(new Complex(-7.894525, 6.616942));
		addToLast(new Complex(-0.406022, -0.096510));
		addToLast(new Complex(0.000000, 0.000000));
		addToLast(new Complex(-0.036098, -0.003282));

		newRegion(25);
		addToLast(new Complex(-0.277952, -0.263119));
		addToLast(new Complex(-8.312626, 7.686930));
		addToLast(new Complex(0.156396, 0.057206));
		addToLast(new Complex(-0.005056, -0.015167));
		addToLast(new Complex(0.022751, -0.022751));

		newRegion(25);
		addToLast(new Complex(0.237073, 0.167018));
		addToLast(new Complex(-8.716945, 8.263034));
		addToLast(new Complex(-0.103874, -0.074886));
		addToLast(new Complex(0.033819, -0.019325));

		newRegion(25);
		addToLast(new Complex(-0.089380, -0.270217));
		addToLast(new Complex(-9.088621, 8.499769));
		addToLast(new Complex(0.021741, 0.159771));
		addToLast(new Complex(0.004831, 0.016910));

		newRegion(25);
		addToLast(new Complex(-0.080900, -0.097107));
		addToLast(new Complex(-8.924771, 8.315048));
		addToLast(new Complex(0.041086, 0.055891));
		addToLast(new Complex(0.026831, 0.024007));

		newRegion(25);
		addToLast(new Complex(0.070338, -0.189242));
		addToLast(new Complex(-9.572240, 8.462387));
		addToLast(new Complex(-0.061964, 0.087085));
		addToLast(new Complex(0.003349, 0.015072));

		newRegion(25);
		addToLast(new Complex(0.009767, -0.105461));
		addToLast(new Complex(-9.367518, 8.462401));
		addToLast(new Complex(-0.006104, 0.036626));
		addToLast(new Complex(-0.003663, 0.037381));

		newRegion(25);
		addToLast(new Complex(0.203783, 0.008961));
		addToLast(new Complex(-9.789104, 8.110091));
		addToLast(new Complex(-0.091638, -0.005098));
		addToLast(new Complex(-0.030685, -0.012963));
		addToLast(new Complex(-0.007783, -0.003940));

		newRegion(25);
		addToLast(new Complex(0.113781, -0.087077));
		addToLast(new Complex(-9.698199, 8.288433));
		addToLast(new Complex(-0.054568, 0.033670));
		addToLast(new Complex(-0.031348, 0.023221));
		addToLast(new Complex(-0.008127, 0.006966));

		newRegion(25);
		addToLast(new Complex(0.014983, -0.204010));
		addToLast(new Complex(-9.489734, 7.883914));
		addToLast(new Complex(-0.001363, 0.095419));
		addToLast(new Complex(0.012257, -0.003631));

		newRegion(25);
		addToLast(new Complex(0.067842, 0.145366));
		addToLast(new Complex(-9.643205, 8.011103));
		addToLast(new Complex(-0.029811, -0.046705));
		addToLast(new Complex(-0.034969, -0.039496));
		addToLast(new Complex(-0.006956, -0.014906));

		newRegion(25);
		addToLast(new Complex(0.082625, -0.338346));
		addToLast(new Complex(-9.121768, 7.933380));
		addToLast(new Complex(0.011974, 0.215021));
		addToLast(new Complex(-0.007227, -0.010118));
		addToLast(new Complex(-0.001210, 0.014218));

		newRegion(25);
		addToLast(new Complex(-0.103646, 0.239741));
		addToLast(new Complex(-9.035557, 8.027740));
		addToLast(new Complex(0.019147, -0.120126));
		addToLast(new Complex(0.019147, -0.059338));

		newRegion(25);
		addToLast(new Complex(0.163791, -0.374822));
		addToLast(new Complex(-8.119547, 8.249271));
		addToLast(new Complex(-0.061723, 0.183791));
		addToLast(new Complex(0.000000, 0.000000));
		addToLast(new Complex(0.002069, 0.024827));

		newRegion(25);
		addToLast(new Complex(0.140563, -0.460954));
		addToLast(new Complex(-7.556055, 8.427627));
		addToLast(new Complex(-0.054005, 0.216754));
		addToLast(new Complex(0.019675, 0.017489));
		addToLast(new Complex(-0.010931, 0.024048));

		newRegion(25);
		addToLast(new Complex(-0.005578, -0.373345));
		addToLast(new Complex(-6.875837, 8.676686));
		addToLast(new Complex(0.014097, 0.198884));
		addToLast(new Complex(0.018032, 0.021805));

		newRegion(25);
		addToLast(new Complex(-0.017708, -0.263847));
		addToLast(new Complex(-6.785596, 8.436937));
		addToLast(new Complex(-0.015937, 0.118643));
		addToLast(new Complex(-0.028333, 0.058436));

		newRegion(25);
		addToLast(new Complex(0.130767, 0.184348));
		addToLast(new Complex(-6.299776, 8.439229));
		addToLast(new Complex(-0.067290, -0.068175));
		addToLast(new Complex(-0.012666, 0.031353));

		newRegion(25);
		addToLast(new Complex(0.532058, 0.017471));
		addToLast(new Complex(-5.493634, 7.551451));
		addToLast(new Complex(-0.281401, -0.107923));
		addToLast(new Complex(-0.054180, -0.011112));

		newRegion(25);
		addToLast(new Complex(0.312778, 0.089962));
		addToLast(new Complex(-5.123946, 6.814047));
		addToLast(new Complex(-0.066556, -0.095493));
		addToLast(new Complex(-0.017362, -0.005787));

		newRegion(25);
		addToLast(new Complex(0.183609, 0.079830));
		addToLast(new Complex(-5.231057, 7.257192));
		addToLast(new Complex(-0.083821, -0.041911));
		addToLast(new Complex(-0.049894, -0.015966));

		newRegion(25);
		addToLast(new Complex(0.233839, 0.219442));
		addToLast(new Complex(-4.825936, 6.459862));
		addToLast(new Complex(-0.086996, -0.127956));
		addToLast(new Complex(-0.018668, 0.016254));

		newRegion(25);
		addToLast(new Complex(0.250592, 0.133450));
		addToLast(new Complex(-4.750297, 6.549524));
		addToLast(new Complex(-0.091933, -0.117362));
		addToLast(new Complex(0.003912, -0.050857));
		addToLast(new Complex(0.011956, -0.017165));

		newRegion(25);
		addToLast(new Complex(0.035049, -0.280729));
		addToLast(new Complex(-4.203221, 6.216509));
		addToLast(new Complex(-0.022887, 0.160473));
		addToLast(new Complex(0.015698, -0.021125));

		newRegion(25);
		addToLast(new Complex(0.069889, 0.146820));
		addToLast(new Complex(-4.347721, 6.317979));
		addToLast(new Complex(-0.033448, -0.061615));
		addToLast(new Complex(0.008802, -0.045771));

		newRegion(25);
		addToLast(new Complex(0.260874, -0.040027));
		addToLast(new Complex(-3.916316, 6.464695));
		addToLast(new Complex(-0.112251, 0.026180));
		addToLast(new Complex(0.016044, -0.020055));

		newRegion(25);
		addToLast(new Complex(-0.193638, -0.029046));
		addToLast(new Complex(-4.016663, 6.401184));
		addToLast(new Complex(0.048410, -0.012448));
		addToLast(new Complex(0.088520, -0.017981));

		newRegion(25);
		addToLast(new Complex(0.271255, 0.197656));
		addToLast(new Complex(-4.057197, 6.842011));
		addToLast(new Complex(-0.141024, -0.097857));
		addToLast(new Complex(0.017219, 0.008909));
		addToLast(new Complex(0.004053, 0.012159));

		newRegion(25);
		addToLast(new Complex(0.219237, 0.259442));
		addToLast(new Complex(-4.371745, 7.182625));
		addToLast(new Complex(-0.113690, -0.152474));
		addToLast(new Complex(0.023278, -0.003320));
		addToLast(new Complex(-0.007897, -0.007773));

		newRegion(25);
		addToLast(new Complex(0.140011, 0.290852));
		addToLast(new Complex(-4.815861, 7.372865));
		addToLast(new Complex(-0.077784, -0.185850));
		addToLast(new Complex(0.029169, 0.067224));

		newRegion(25);
		addToLast(new Complex(0.074692, 0.277811));
		addToLast(new Complex(-5.144166, 7.547315));
		addToLast(new Complex(-0.028909, -0.171126));
		addToLast(new Complex(0.001308, 0.013081));
		addToLast(new Complex(0.004186, -0.017164));

		newRegion(25);
		addToLast(new Complex(0.245459, -0.277789));
		addToLast(new Complex(-5.406752, 8.230463));
		addToLast(new Complex(-0.124198, 0.106880));
		addToLast(new Complex(-0.054770, 0.005552));
		addToLast(new Complex(-0.017242, 0.003676));

		newRegion(25);
		addToLast(new Complex(0.232486, -0.067925));
		addToLast(new Complex(-5.389445, 8.033603));
		addToLast(new Complex(-0.048698, 0.017190));
		addToLast(new Complex(-0.104934, 0.039988));

		newRegion(25);
		addToLast(new Complex(0.001474, -0.331289));
		addToLast(new Complex(-4.851100, 8.449738));
		addToLast(new Complex(0.059555, 0.211462));
		addToLast(new Complex(0.017427, 0.017193));

		newRegion(25);
		addToLast(new Complex(0.049933, -0.141914));
		addToLast(new Complex(-5.056310, 8.343013));
		addToLast(new Complex(-0.027594, 0.078841));
		addToLast(new Complex(-0.011826, 0.036792));

		newRegion(25);
		addToLast(new Complex(0.200966, 0.233540));
		addToLast(new Complex(-4.250739, 8.266584));
		addToLast(new Complex(-0.086344, -0.183747));
		addToLast(new Complex(0.017061, 0.006230));

		newRegion(25);
		addToLast(new Complex(-0.038340, -0.146259));
		addToLast(new Complex(-4.467295, 8.287374));
		addToLast(new Complex(0.046860, 0.083780));
		addToLast(new Complex(0.014200, 0.024140));

		newRegion(25);
		addToLast(new Complex(0.224676, 0.127756));
		addToLast(new Complex(-3.796340, 7.887322));
		addToLast(new Complex(-0.109543, -0.126463));
		addToLast(new Complex(0.001980, 0.020472));

		newRegion(25);
		addToLast(new Complex(-0.092465, -0.114834));
		addToLast(new Complex(-3.914028, 7.919514));
		addToLast(new Complex(0.078764, 0.074703));
		addToLast(new Complex(0.014772, 0.013295));

		newRegion(25);
		addToLast(new Complex(0.484795, 0.152727));
		addToLast(new Complex(-3.192909, 7.282791));
		addToLast(new Complex(-0.201232, -0.022768));
		addToLast(new Complex(0.001598, -0.013242));
		addToLast(new Complex(-0.040795, 0.018829));

		newRegion(25);
		addToLast(new Complex(0.411608, 0.214267));
		addToLast(new Complex(-3.410602, 7.803047));
		addToLast(new Complex(-0.194803, -0.037736));
		addToLast(new Complex(0.000000, 0.000000));
		addToLast(new Complex(-0.048533, 0.002130));

		newRegion(25);
		addToLast(new Complex(0.176894, 0.119654));
		addToLast(new Complex(-3.245807, 7.997880));
		addToLast(new Complex(-0.093102, -0.049654));
		addToLast(new Complex(-0.023792, -0.018965));

		newRegion(25);
		addToLast(new Complex(0.303940, 0.263310));
		addToLast(new Complex(-2.616497, 7.162095));
		addToLast(new Complex(-0.181421, -0.219119));
		addToLast(new Complex(0.004712, -0.023561));

		newRegion(25);
		addToLast(new Complex(0.234090, 0.114469));
		addToLast(new Complex(-2.975610, 7.563242));
		addToLast(new Complex(-0.079010, -0.063552));
		addToLast(new Complex(-0.020611, -0.056681));

		newRegion(25);
		addToLast(new Complex(-0.126928, -0.350393));
		addToLast(new Complex(-1.934314, 6.770539));
		addToLast(new Complex(-0.006601, 0.170252));
		addToLast(new Complex(0.025233, -0.024074));

		newRegion(25);
		addToLast(new Complex(0.057948, 0.107647));
		addToLast(new Complex(-2.264017, 6.981122));
		addToLast(new Complex(-0.022898, -0.057346));
		addToLast(new Complex(-0.006399, -0.031248));

		newRegion(25);
		addToLast(new Complex(-0.271857, 0.087903));
		addToLast(new Complex(-1.728040, 7.103246));
		addToLast(new Complex(0.070789, -0.039208));
		addToLast(new Complex(0.060792, -0.027633));
		addToLast(new Complex(0.049736, -0.047630));

		newRegion(25);
		addToLast(new Complex(0.359060, -0.090972));
		addToLast(new Complex(-1.490749, 7.048148));
		addToLast(new Complex(-0.156692, 0.043489));
		addToLast(new Complex(0.035454, -0.012639));

		newRegion(25);
		addToLast(new Complex(0.461250, 0.075188));
		addToLast(new Complex(-1.615792, 7.628418));
		addToLast(new Complex(-0.225562, -0.121425));
		addToLast(new Complex(-0.023971, 0.017946));

		newRegion(25);
		addToLast(new Complex(-0.222472, -0.017919));
		addToLast(new Complex(-1.711803, 7.375586));
		addToLast(new Complex(0.071677, 0.013439));
		addToLast(new Complex(0.064235, 0.004480));

		newRegion(25);
		addToLast(new Complex(-0.127570, 0.255584));
		addToLast(new Complex(-1.557200, 8.267613));
		addToLast(new Complex(0.016775, -0.127331));
		addToLast(new Complex(-0.014808, 0.024660));

		newRegion(25);
		addToLast(new Complex(0.142109, -0.054028));
		addToLast(new Complex(-1.506412, 8.082340));
		addToLast(new Complex(-0.067229, 0.005846));
		addToLast(new Complex(-0.051152, 0.008769));
		addToLast(new Complex(-0.010230, 0.001461));

		newRegion(25);
		addToLast(new Complex(0.160266, 0.230885));
		addToLast(new Complex(-0.994744, 8.147898));
		addToLast(new Complex(-0.137622, -0.140441));
		addToLast(new Complex(0.010462, 0.017516));

		newRegion(25);
		addToLast(new Complex(-0.063500, -0.159014));
		addToLast(new Complex(-1.171371, 8.204506));
		addToLast(new Complex(0.053506, 0.087006));
		addToLast(new Complex(0.013506, 0.035250));

		newRegion(25);
		addToLast(new Complex(-0.154766, -0.134755));
		addToLast(new Complex(-0.810585, 7.921227));
		addToLast(new Complex(0.099257, 0.082262));
		addToLast(new Complex(0.015751, 0.015751));

		newRegion(25);
		addToLast(new Complex(0.532058, 0.017471));
		addToLast(new Complex(-0.383634, 7.551451));
		addToLast(new Complex(-0.281401, -0.107923));
		addToLast(new Complex(-0.054180, -0.011112));

		newRegion(25);
		addToLast(new Complex(0.312778, 0.089962));
		addToLast(new Complex(-0.013946, 6.814047));
		addToLast(new Complex(-0.066556, -0.095493));
		addToLast(new Complex(-0.017362, -0.005787));

		newRegion(25);
		addToLast(new Complex(0.183609, 0.079830));
		addToLast(new Complex(-0.121057, 7.257192));
		addToLast(new Complex(-0.083821, -0.041911));
		addToLast(new Complex(-0.049894, -0.015966));

		newRegion(25);
		addToLast(new Complex(0.233839, 0.219442));
		addToLast(new Complex(0.284064, 6.459862));
		addToLast(new Complex(-0.086996, -0.127956));
		addToLast(new Complex(-0.018668, 0.016254));

		newRegion(25);
		addToLast(new Complex(0.250592, 0.133450));
		addToLast(new Complex(0.359703, 6.549524));
		addToLast(new Complex(-0.091933, -0.117362));
		addToLast(new Complex(0.003912, -0.050857));
		addToLast(new Complex(0.011956, -0.017165));

		newRegion(25);
		addToLast(new Complex(0.035049, -0.280729));
		addToLast(new Complex(0.906779, 6.216509));
		addToLast(new Complex(-0.022887, 0.160473));
		addToLast(new Complex(0.015698, -0.021125));

		newRegion(25);
		addToLast(new Complex(0.069889, 0.146820));
		addToLast(new Complex(0.762279, 6.317979));
		addToLast(new Complex(-0.033448, -0.061615));
		addToLast(new Complex(0.008802, -0.045771));

		newRegion(25);
		addToLast(new Complex(0.260874, -0.040027));
		addToLast(new Complex(1.193684, 6.464695));
		addToLast(new Complex(-0.112251, 0.026180));
		addToLast(new Complex(0.016044, -0.020055));

		newRegion(25);
		addToLast(new Complex(-0.193638, -0.029046));
		addToLast(new Complex(1.093337, 6.401184));
		addToLast(new Complex(0.048410, -0.012448));
		addToLast(new Complex(0.088520, -0.017981));

		newRegion(25);
		addToLast(new Complex(0.271255, 0.197656));
		addToLast(new Complex(1.052803, 6.842011));
		addToLast(new Complex(-0.141024, -0.097857));
		addToLast(new Complex(0.017219, 0.008909));
		addToLast(new Complex(0.004053, 0.012159));

		newRegion(25);
		addToLast(new Complex(0.219237, 0.259442));
		addToLast(new Complex(0.738255, 7.182625));
		addToLast(new Complex(-0.113690, -0.152474));
		addToLast(new Complex(0.023278, -0.003320));
		addToLast(new Complex(-0.007897, -0.007773));

		newRegion(25);
		addToLast(new Complex(0.140011, 0.290852));
		addToLast(new Complex(0.294139, 7.372865));
		addToLast(new Complex(-0.077784, -0.185850));
		addToLast(new Complex(0.029169, 0.067224));

		newRegion(25);
		addToLast(new Complex(0.074692, 0.277811));
		addToLast(new Complex(-0.034166, 7.547315));
		addToLast(new Complex(-0.028909, -0.171126));
		addToLast(new Complex(0.001308, 0.013081));
		addToLast(new Complex(0.004186, -0.017164));

		newRegion(25);
		addToLast(new Complex(0.245459, -0.277789));
		addToLast(new Complex(-0.296752, 8.230463));
		addToLast(new Complex(-0.124198, 0.106880));
		addToLast(new Complex(-0.054770, 0.005552));
		addToLast(new Complex(-0.017242, 0.003676));

		newRegion(25);
		addToLast(new Complex(0.232486, -0.067925));
		addToLast(new Complex(-0.279445, 8.033603));
		addToLast(new Complex(-0.048698, 0.017190));
		addToLast(new Complex(-0.104934, 0.039988));

		newRegion(25);
		addToLast(new Complex(0.001474, -0.331289));
		addToLast(new Complex(0.258900, 8.449738));
		addToLast(new Complex(0.059555, 0.211462));
		addToLast(new Complex(0.017427, 0.017193));

		newRegion(25);
		addToLast(new Complex(0.049933, -0.141914));
		addToLast(new Complex(0.053690, 8.343013));
		addToLast(new Complex(-0.027594, 0.078841));
		addToLast(new Complex(-0.011826, 0.036792));

		newRegion(25);
		addToLast(new Complex(0.200966, 0.233540));
		addToLast(new Complex(0.859261, 8.266584));
		addToLast(new Complex(-0.086344, -0.183747));
		addToLast(new Complex(0.017061, 0.006230));

		newRegion(25);
		addToLast(new Complex(-0.038340, -0.146259));
		addToLast(new Complex(0.642705, 8.287374));
		addToLast(new Complex(0.046860, 0.083780));
		addToLast(new Complex(0.014200, 0.024140));

		newRegion(25);
		addToLast(new Complex(0.224676, 0.127756));
		addToLast(new Complex(1.313660, 7.887322));
		addToLast(new Complex(-0.109543, -0.126463));
		addToLast(new Complex(0.001980, 0.020472));

		newRegion(25);
		addToLast(new Complex(-0.092465, -0.114834));
		addToLast(new Complex(1.195972, 7.919514));
		addToLast(new Complex(0.078764, 0.074703));
		addToLast(new Complex(0.014772, 0.013295));

		for (Region r : regions) {
			r.shiftAllCoefficients();
			r.update();
		}
	}

	public TestFractal() {
		super();

		degree = regions.get(regions.size() - 1).N + 2;
//		degree = 26;

		functions = new Function[] { new IterativeFunction(this), new NormalizedFunction(this),
				new PotentialFunction(this), new EscapeAngleFunction(this), new BinaryFunction(this),
				new LambertFunction(this), new TestFunction(this), new DistanceEstimator(this) };
		function = functions[0];

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

		public int N = 25;

		public final List<Complex> r = new ArrayList<Complex>(N);

		public final List<Complex> coefficients = new ArrayList<Complex>();

		public Region() {
		}

		public Region(int N) {
			this.N = N;
		}

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
	
	private static void addToLast(Complex c) {
		regions.get(regions.size()-1).coefficients.add(c);
	}
	
	private static void newRegion(int N) {
		regions.add(new Region(N));
	}
	
}
