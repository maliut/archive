
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReader {
	
	public static List<String> readLines(String path) throws IOException {
		File f = new File(path);
		BufferedReader reader = null;
		final ArrayList<String> ret = new ArrayList<String>();
		reader = new BufferedReader(new java.io.FileReader(f));
		reader.lines().forEach(e -> ret.add(e));
		reader.close();
		return ret;
	}
}
