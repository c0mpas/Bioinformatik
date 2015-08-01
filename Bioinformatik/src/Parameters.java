
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
		pSwitch = 0;
	};
	
}
