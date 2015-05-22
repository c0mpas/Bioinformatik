
public class Sequence {

	private String seq;
	
	public Sequence() {
		this.seq = null;
	}
	
	public Sequence(String s) {
		if (s == null || s.isEmpty()) throw new RuntimeException("Sequenz ist leer");
		this.seq = s;
	}
	
	// Erzeugt eine zuf�llige Sequenz der L�nge n
	public void generate(int n) {
		
	}
	
	// Testet, ob die Sequenz ab der Stelle i gleich einer weiteren angegebenen Sequenz s ist
	public boolean compare(Sequence s,int i) {
		return true;
	}
	
	// Berechnet die maximale �berlappung vom Suffix der Sequenz mit dem Pr�fix der �bergebenen Sequenz s
	public int overlap(Sequence s) {
		return 0;
	}
	
	// Testet, ob es sich um eine korrekte DNA-Sequenz handelt
	public boolean isValid() {
		for (int i = 0; i < this.seq.length(); i++) {
			switch (seq.charAt(i)) {
				case 'A': break;
				case 'C': break;
				case 'T': break;
				case 'G': break;
				default : return false;			
			}
		}
		return true;
	}
	
}
