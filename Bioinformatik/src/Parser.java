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
		
}
