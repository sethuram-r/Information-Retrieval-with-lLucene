package tester;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;

import pojo.Query;

public class NewQueryReader {

	public static void main(String[] args) throws FileNotFoundException, DocumentException {
		File file = new File("/Users/sethuram/Desktop/query");
		Scanner scanner = new Scanner(file);
		scanner.useDelimiter(Pattern.compile("<top>"));
		List<String> queries;
		List<Query> queriyObjects = new ArrayList<Query>();
		while (scanner.hasNext()) {
			Query query = new Query();
			String[] lines = scanner.next().split("<num>|<title>|<desc>|<narr>");
			query.setNum(Integer.parseInt(lines[1].replaceAll("Number:", "").trim()));
			query.setTitle(lines[2].trim());
			query.setDescription(lines[3].replaceAll("Description:", "").trim());
			query.setNarrative(lines[4].replaceAll("Narrative:|</top>", "").trim());
			queriyObjects.add(query);
		}
		queries = queryFormer(queriyObjects);

	}
	private static List<String> queryFormer(List<Query> queriyObjects) {
		List<String> queries = new ArrayList<String>();
		for(Query objects:queriyObjects) {
			queries.add(String.valueOf(objects.getNum())+objects.getTitle()+objects.getDescription()+objects.getNarrative());
		}
		return queries;
	}


}
