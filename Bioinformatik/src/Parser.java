/**
 * 
 * @author Sebastian Schultheiﬂ und Christoph Geidt
 *
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

public class Parser {
	
	private static final PrintStream out = System.err;
	
	public static final String parse(String filename) {
		if (filename == null) throw new IllegalArgumentException("filename empty");
		File file = new File(filename);
        if (!file.canRead() || !file.isFile()) throw new RuntimeException("invalid file");
        BufferedReader in = null;

        StringBuilder sb = new StringBuilder();
        
        try {
            in = new BufferedReader(new FileReader(filename));
            String zeile = null;
            while ((zeile = in.readLine()) != null) sb.append(zeile);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {}
        }
        return sb.toString();
    }
	
	public static final Parameters parseParameters(String input) {
		if (input == null || input.isEmpty()) throw new IllegalArgumentException();
		Parameters params = new Parameters();
		String[] parts = input.split("\\s");

		params.setStateOne(parts[0]);
		for (int i = 1; i <= 6; i++) params.setpOne(i, parseDouble(parts[i]));
		params.setStateTwo(parts[7]);
		for (int i = 1; i <= 6; i++) params.setpTwo(i, parseDouble(parts[i+7]));
		params.setpSwitch(parseDouble(parts[14]));
		return params;
	}
	
	private static final double parseDouble(String s) {
		double d = 0.0;
		String[] parts = s.split("/");
		d = Double.valueOf(parts[0]) / Double.valueOf(parts[1]);
		return d;
	};
}
