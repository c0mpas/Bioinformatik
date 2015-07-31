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
import java.util.ArrayList;

public class Parser {
	
	private static final PrintStream out = System.err;
	
	public static final Parameters parseParameters(String filename) {
		if (filename == null) throw new IllegalArgumentException("filename empty");
		File file = new File(filename);
        if (!file.canRead() || !file.isFile()) throw new RuntimeException("invalid file");
        BufferedReader in = null;
        
        Parameters params = new Parameters();
        ArrayList<String> list = new ArrayList<String>();
        
        try {
            in = new BufferedReader(new FileReader(filename));
            String zeile = null;
            while ((zeile = in.readLine()) != null) {
            	zeile = zeile.trim();
            	if (isValid(zeile)) {
            		list.add(zeile);
            	} else {
            		out.println("invalid line (" + zeile + ")");
            	}
            } 
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {}
        }
        
        // convert list to parameters object
        return params;
    }
	
	public static final Parameters parseInput(String filename) {
		if (filename == null) throw new IllegalArgumentException("filename empty");
		File file = new File(filename);
        if (!file.canRead() || !file.isFile()) throw new RuntimeException("invalid file");
        BufferedReader in = null;
        
        Parameters params = new Parameters();
        ArrayList<String> list = new ArrayList<String>();
        
        try {
            in = new BufferedReader(new FileReader(filename));
            String zeile = null;
            while ((zeile = in.readLine()) != null) {
            	zeile = zeile.trim();
            	if (isValid(zeile)) {
            		list.add(zeile);
            	} else {
            		out.println("invalid line (" + zeile + ")");
            	}
            } 
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {}
        }
        
        // convert list to parameters object
        return params;
    }
	
	private static final boolean isValid(String s) {
		if (s == null || s.isEmpty()) return false;
		return true;
	}
	
	public static final String salvage(String s) {
		if (s == null) {
			return "";
		} else {
			return s.replace(", ", "\n");
		}
	}
		
}
