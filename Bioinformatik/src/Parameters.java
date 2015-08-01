
public class Parameters {

	private static final int cap = 6;
	
	private String stateOne;
	private String stateTwo;
	private double[] pOne;
	private double[] pTwo;
	private double pSwitch;
	
	public Parameters() {
		stateOne = null;
		stateTwo = null;
		pOne = new double[cap];
		pTwo = new double[cap];
		pSwitch = 0.0;
	};
	
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
		if (i < 1 || i > 6 || p < 0.0) throw new IllegalArgumentException("i = " + i + " & p = " + p);
		this.pOne[i-1] = p;
	}

	public double getpTwo(int i) {
		if (i < 1 || i > 6) throw new IllegalArgumentException();
		return pTwo[i-1];
	}

	public void setpTwo(int i, double p) {
		if (i < 1 || i > 6 || p < 0.0) throw new IllegalArgumentException();
		this.pTwo[i-1] = p;
	}

	public double getpSwitch() {
		return pSwitch;
	}

	public void setpSwitch(double pSwitch) {
		if (pSwitch < 0.0) throw new IllegalArgumentException();
		this.pSwitch = pSwitch;
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
