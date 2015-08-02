
public class Parameters {

	private static final int cap = 6;
	private static final double ZERO = 0.0d;
	
	private String stateOne;
	private String stateTwo;
	private double[] pOne;
	private double[] pTwo;
	private double pSwitch;
	private double pNoSwitch;
	
	public Parameters() {
		stateOne = null;
		stateTwo = null;
		pOne = new double[cap];
		pTwo = new double[cap];
		pSwitch = ZERO;
		pNoSwitch = ZERO;
	};
	
	public double getP(int state, int i) {
		double value = ZERO;
		if (state == HMM.FAIR) {
			value = getpOne(i);
		} else if (state == HMM.UNFAIR) {
			value = getpTwo(i);
		} else {
			throw new IllegalArgumentException();
		}
		return value;
	}
	
	public void logarithmize() {
		pSwitch = Math.log(pSwitch);
		pNoSwitch = Math.log(pNoSwitch);
		for (int i = 0; i < cap; i++) {
			pOne[i] = Math.log(pOne[i]);
			pTwo[i] = Math.log(pTwo[i]);
		}
	}
	
	public String getStateOne() {
		return stateOne;
	}

	public void setStateOne(String stateOne) {
		if (stateOne == null || stateOne.isEmpty()) throw new IllegalArgumentException();
		this.stateOne = stateOne;
	}

	public String getStateTwo() {
		return stateTwo;
	}

	public void setStateTwo(String stateTwo) {
		if (stateTwo == null || stateTwo.isEmpty()) throw new IllegalArgumentException();
		this.stateTwo = stateTwo;
	}

	public double getpOne(int i) {
		if (i < 1 || i > 6) throw new IllegalArgumentException();
		return pOne[i-1];
	}

	public void setpOne(int i, double p) {
		if (i < 1 || i > 6 || p < ZERO) throw new IllegalArgumentException("i = " + i + " & p = " + p);
		this.pOne[i-1] = p;
	}

	public double getpTwo(int i) {
		if (i < 1 || i > 6) throw new IllegalArgumentException();
		return pTwo[i-1];
	}

	public void setpTwo(int i, double p) {
		if (i < 1 || i > 6 || p < ZERO) throw new IllegalArgumentException();
		this.pTwo[i-1] = p;
	}

	public double getpSwitch() {
		return pSwitch;
	}

	public double getpNoSwitch() {
		return pNoSwitch;
	}

	public void setpSwitch(double pSwitch) {
		if (pSwitch < ZERO) throw new IllegalArgumentException();
		this.pSwitch = pSwitch;
		this.pNoSwitch = 1.0d - pSwitch;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[ Parameters \n");
		sb.append(stateOne).append(" ");
		for (double d : pOne) sb.append(d).append(" ");
		sb.append("\n");
		sb.append(stateTwo).append(" ");
		for (double d : pTwo) sb.append(d).append(" ");
		sb.append("\n").append(pSwitch).append(" ]");
		return sb.toString();
	}

}
