package tester;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;



public class QueryReader {

	public Map<Integer, String> getQueries(String location) throws IOException {
		Map<Integer, String> queries = new HashMap<Integer,String>();
		File file =new File(location);
		Scanner scanner = new Scanner(file);
		scanner.useDelimiter(".I");
		while (scanner.hasNext()) {		
			String[] lines = scanner.next().split(".W");
			queries.put(Integer.parseInt(lines[0].trim()), lines[1]);
		}
		return queries;
	}
}
