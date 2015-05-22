import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

public class Parser {
	
	private static final PrintStream out = System.err;
	
	public static final ArrayList<String> parse(String filename) {
		File file = new File(filename);
        if (!file.canRead() || !file.isFile()) System.exit(0);
        BufferedReader in = null;
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
        return list;
    }
	
	private static final boolean isValid(String s) {
		if (s == null || s.isEmpty()) return false;
		return true;
	}
		
}
