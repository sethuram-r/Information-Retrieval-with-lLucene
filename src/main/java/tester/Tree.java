package tester;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import pojo.Csv;

public class Tree {
	public static void main(String[] args) throws IOException   {
		BufferedReader reader = Files.newBufferedReader(Paths.get("/Users/sethuram/Desktop/test/output.csv"));
		CSVParser parser = CSVParser.parse( reader, CSVFormat.RFC4180);
		List<Csv> csvlist = new ArrayList<>();
		for (CSVRecord csvRecord : parser) {
			
		}
		
	}
}
