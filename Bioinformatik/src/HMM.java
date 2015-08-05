
public class HMM {

	public static final int FAIR = 0;
	public static final int UNFAIR = 1;
	
	private double[][] chances;
	private Parameters parameters;
	private String input;
	
	
	public HMM(String input, Parameters params) {
		if (input == null || input.isEmpty() || params == null) throw new IllegalArgumentException();
		this.input = input;
		this.parameters = params;
	}
	
	private double computeChanceViterbi(int position, int state) {
		if (!isValidState(state) || position < 0) throw new IllegalArgumentException();
		double chanceOne = chances[position-1][state] * parameters.getpNoSwitch();
		double chanceTwo = chances[position-1][invertState(state)] * parameters.getpSwitch();
		double factor1 = (chanceOne>chanceTwo)? chanceOne : chanceTwo;
		double factor2 = parameters.getP(state, Integer.valueOf(input.substring(position,position+1)));
		return factor1 * factor2;
	}
	
	private double computeChanceViterbiLog(int position, int state) {
		if (!isValidState(state) || position < 0) throw new IllegalArgumentException();
		double chanceOne = chances[position-1][state] + parameters.getpNoSwitch();
		double chanceTwo = chances[position-1][invertState(state)] + parameters.getpSwitch();
		double factor1 = (chanceOne>chanceTwo)? chanceOne : chanceTwo;
		double factor2 = parameters.getP(state, Integer.valueOf(input.substring(position,position+1)));
		return factor1 + factor2;
	}

	private double computeChanceForward(int position, int state) {
		if (!isValidState(state) || position < 0) throw new IllegalArgumentException();
		double chanceOne = chances[position-1][state] * parameters.getpNoSwitch();
		double chanceTwo = chances[position-1][invertState(state)] * parameters.getpSwitch();
		double factor1 = chanceOne + chanceTwo;
		double factor2 = parameters.getP(state, Integer.valueOf(input.substring(position,position+1)));
		return factor1 * factor2;
	}
	
	private int invertState(int state) {
		if (isValidState(state)) {
			return (state == FAIR)? UNFAIR : FAIR;
		} else {
			throw new IllegalArgumentException("invalid state");
		}
	}
	
	private String getPath() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < chances.length; i++) {
			if (chances[i][FAIR] >= chances[i][UNFAIR]) {
				sb.append(parameters.getStateOne());
			} else {
				sb.append(parameters.getStateTwo());
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
			chances[i][FAIR] = computeChanceViterbi(i, FAIR);
			chances[i][UNFAIR] = computeChanceViterbi(i, UNFAIR);
		}
		
		GUI.log(logChances());
		return getPath();
	}

	public String viterbiLog() {
		log(input, parameters);
		
		parameters.logarithmize();
		chances = new double[input.length()][2];
		
		// compute first values for step 1 and q0
		chances[0][FAIR] = parameters.getpOne(Integer.valueOf(input.substring(0,1))) + Math.log(0.5d);
		chances[0][UNFAIR] = parameters.getpTwo(Integer.valueOf(input.substring(0,1))) + Math.log(0.5d);
		
		for (int i = 1; i < input.length(); i++) {
			// compute chance for both states at current position
			chances[i][FAIR] = computeChanceViterbiLog(i, FAIR);
			chances[i][UNFAIR] = computeChanceViterbiLog(i, UNFAIR);
		}
		
		GUI.log(logChances());
		return getPath();
	}
	
	public String rsviterbi() {
		log(input, parameters);
		GUI.log(logChances());
		return null;
	}

	public String forward() {
		log(input, parameters);
		
		chances = new double[input.length()][2];
		
		// compute first values for step 1 and q0
		chances[0][FAIR] = parameters.getpOne(Integer.valueOf(input.substring(0,1))) * 0.5d;
		chances[0][UNFAIR] = parameters.getpTwo(Integer.valueOf(input.substring(0,1))) * 0.5d;
		
		for (int i = 1; i < input.length(); i++) {
			// compute chance for both states at current position
			chances[i][FAIR] = computeChanceForward(i, FAIR);
			chances[i][UNFAIR] = computeChanceForward(i, UNFAIR);
		}
		
		GUI.log(logChances());
		return getPath();
	}
	
	private boolean isValidState(int state) {
		if (state == FAIR || state == UNFAIR) {
			return true;
		} else {
			return false;
		}
	}
	
	private void log(String input, Parameters params) {
		if (input == null || params == null) return;
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
