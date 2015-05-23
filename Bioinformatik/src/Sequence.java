import java.util.Random;


public class Sequence {

	private String seq;
	
	public Sequence() {
		this.seq = null;
	}
	
	public Sequence(String s) {
		if (s == null || s.isEmpty()) throw new RuntimeException("Sequenz ist leer");
		this.seq = s;
	}
	
	public String getSequence() {
		return this.seq;
	}
	
	// Erzeugt eine zufällige Sequenz der Länge n
	public String generate(int n) {
		Random rand = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= n; i++) {
			switch (rand.nextInt(4)) {
				case 0: sb.append("A");
						break;
				case 1: sb.append("C");
						break;
				case 2: sb.append("G");
						break;
				case 3: sb.append("T");
						break;
				default: break;
			}
		}
		return sb.toString();
	}
	
	// Testet, ob die Sequenz ab der Stelle i gleich einer weiteren angegebenen Sequenz s ist
	public boolean compare(Sequence s,int i) {
		if (i < 0) throw new RuntimeException("index must be >= 0");
		if (i > seq.length()-1) return false;
		if (seq.substring(i, seq.length()-1) == s.getSequence()) return true;
		return false;
	}
	
	// Berechnet die maximale Überlappung vom Suffix der Sequenz mit dem Präfix der übergebenen Sequenz s
	public int overlap(Sequence s) {
		int ownLength = this.seq.length();
		int foreignLength = s.getSequence().length();
		int length = (ownLength < foreignLength) ? ownLength : foreignLength;
		int count = 0;
		for (int i = 0; i < length; i++) {
			if (this.suffix(i) == s.prefix(i)) {
				count++;
			} else {
				return count;
			}
		}
		return count;
	}
	
	// returns prefix of length n
	private String prefix(int n) {
		if ((n < 0) || (n > this.seq.length())) throw new RuntimeException("invalid index");
		return this.seq.substring(0, n);
	}
	
	// returns suffix of length n
	private String suffix(int n) {
		if (n < this.seq.length()) throw new RuntimeException("invalid index");
		return this.seq.substring(this.seq.length()-1-n, this.seq.length()-1);
	}
	
	// Testet, ob es sich um eine korrekte DNA-Sequenz handelt
	public boolean isValid() {
		for (int i = 0; i < this.seq.length(); i++) {
			switch (seq.charAt(i)) {
				case 'A': break;
				case 'C': break;
				case 'G': break;
				case 'T': break;
				default : return false;			
			}
		}
		return true;
	}
	
	// merge two sequences
	public String merge(Sequence left, Sequence right) {
		if (left==null || right==null) throw new RuntimeException("sequence is null");
		int overlap = left.overlap(right);
		String lseq = left.getSequence();
		String rseq = right.getSequence();
		return lseq + rseq.substring(overlap-1, rseq.length()-1);
	}
	
}
