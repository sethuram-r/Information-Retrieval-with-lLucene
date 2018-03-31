package tester;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import constants.LuceneConstants;

public class ReferenceReader {
	Map<Integer,ArrayList<String>> relevanDoc = new HashMap<>();
	List<List<Integer>> temp = new ArrayList<List<Integer>>();
	List<List<String>> modified = new ArrayList<List<String>>();
	public List<List<Integer>> getReference(String location) throws IOException {

		File file =new File(location);
		Scanner scanner = new Scanner(file);
		scanner.useDelimiter("\n");
		int count = 0;
		String line = null;
		while (scanner.hasNext()) {
			count = count +1;
			List<Integer> rel = new ArrayList<Integer>();
			List<String> modify = new ArrayList<String>();
			String[] lines = scanner.next().split(" ");
			rel.add(Integer.parseInt(lines[0]));
			rel.add(Integer.parseInt(lines[1]));
			modify.add(lines[0]);
			modify.add("0");
			modify.add(lines[1]);
			if(!lines[2].isEmpty()) {
				modify.add(String.valueOf(Math.abs(5-Integer.parseInt(lines[2]))));
			}
			modified.add(modify);
			

			if(!lines[2].isEmpty()) {
				if( (Integer.parseInt(lines[2]) > -1 && (Integer.parseInt(lines[2]) < 4) )) {
					rel.add(1);
				}else {
					rel.add(0);
				}
			}
			temp.add(rel);
		}
		TextFileWriter writer = new TextFileWriter(modified);
		writer.writeFile("reference",null,LuceneConstants.CURRENT_PATH);
		return temp;
	}
	
	public Map<Integer, Integer> getTotalRelevantDocuments( ) {
		
		int b = 0;
		int sum=0;
		Map<Integer,Integer> sample = new HashMap<Integer,Integer>();
		
		for(List<Integer> a:temp) {

			if(b!= a.get(0)) {
				if(b != 0) {
					sample.put(b, sum);
				}
				b = a.get(0);
				if((a.get(2) >= 1 )) {
					sum = 1;
				}
				
			}else if(b == a.get(0) && a.size() == 3){
				if((a.get(2) >= 1 )) {
					sum = sum +1;
				}
			}			
		}
		return sample;
	}
}
