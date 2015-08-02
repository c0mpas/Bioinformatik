
public class HMM {

	public static final int FAIR = 0;
	public static final int UNFAIR = 1;
	
	private double[][] chances;
	private Parameters parameters;
	private String input;
	
	
	public HMM(String input, Parameters params) {
		// TODO error handling
		this.input = input;
		this.parameters = params;
	}
	
	
	/**
	 * 
	 * @param position index of input string
	 * @param state FAIR | UNFAIR
	 * @return chance of state at position
	 */
	private double computeChance(int position, int state) {
		double chanceOne = chances[position-1][state] * parameters.getpNoSwitch();
		double chanceTwo = chances[position-1][state] * parameters.getpSwitch();
		double factor1 = (chanceOne>chanceTwo)? chanceOne : chanceTwo;
		double factor2 = parameters.getP(state, Integer.valueOf(input.substring(position,position+1)));
		return factor1*factor2;
	}
	
	private String getPath(double[][] chances) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < chances.length; i++) {
			if (chances[i][FAIR] > chances[i][UNFAIR]) {
				sb.append("F");
			} else if (chances[i][FAIR] < chances[i][UNFAIR]) {
				sb.append("U");
			} else {
				// both are equal
				sb.append("E");
			}
		}
		return sb.toString();
	}
	
	public String viterbi() {
		log(input, parameters);
		
		chances = new double[input.length()][2];
		
		// compute first values for step 1 and q0
		chances[0][FAIR] = parameters.getpOne(Integer.valueOf(input.substring(0,1))) * 0.5d;
		chances[0][UNFAIR] = parameters.getpTwo(Integer.valueOf(input.substring(0,1))) * 0.5d;
		
		for (int i = 1; i < input.length(); i++) {
			// compute chance for both states at current position
			chances[i][FAIR] = computeChance(i, FAIR);
			chances[i][UNFAIR] = computeChance(i, UNFAIR);
		}
		
		return getPath(chances);
	}
	
	public String rsviterbi() {
		log(input, parameters);
		return null;
	}

	public String forward() {
		log(input, parameters);
		return null;
	}
	
	private static final void log(String input, Parameters params) {
		GUI.log("\ninput = " + input);
		GUI.log("params = " + params.toString() + "\n");
	};

	public String logChances() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < chances.length; i++) {
			sb.append("[");
			sb.append(i);
			sb.append(":F=");
			sb.append(chances[i][FAIR]);
			sb.append(":U=");
			sb.append(chances[i][UNFAIR]);
			sb.append("]\n");
		}
		return sb.toString();
	}
}
