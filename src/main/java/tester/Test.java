package tester;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import constants.TestEnum;
import pojo.Csv;

public class Test {

	private static void print(String str) {
		System.out.println(str);
	}
	public static void main(String[] args) throws IOException   {
		List<String> skills = new ArrayList<String>();
		skills.add("Analytics & Business Intelligence");
		skills.add("Design");
		skills.add("IT Hardware");
		skills.add("IT Software - Application Programming");
		skills.add("IT Software - DBA");
		skills.add("IT Software - eCommerce");
		skills.add("IT Software - Embedded");
		skills.add("IT Software - ERP");
		skills.add("IT Software - Network Administration");
		skills.add("IT Software - Other");
		skills.add("IT Software - QA & Testing");
		skills.add("IT Software - Telecom Software");
		skills.add("ITES");



		BufferedReader reader = Files.newBufferedReader(Paths.get("/Users/sethuram/Desktop/a.csv"));
		CSVParser parser = CSVParser.parse( reader, CSVFormat.RFC4180);
		List<Csv> csvlist = new ArrayList<>();



		for (CSVRecord csvRecord : parser) {

			if(skills.contains(csvRecord.get(7))){


				if(nullCheck(csvRecord).equals(false)) {

					if((csvRecord.get(6).matches("(.)*(\\d)(.)*"))) {

						for (TestEnum cities : TestEnum.values()) {
							if(namechanger(csvRecord.get(3)).contains(cities.toString())) {

								if(!csvRecord.get(3).trim().contains(" ")) {
									Csv csv = new Csv();
									csv.setCompany(csvRecord.get(0));
									csv.setExperience(csvRecord.get(1));
									csv.setIndustry(csvRecord.get(2));
									csv.setJobLocationAddress(namechanger(csvRecord.get(3)));
									csv.setJobTitle(csvRecord.get(4));
									csv.setNumberOfPositions(csvRecord.get(5));
									if(csv.getNumberOfPositions().isEmpty()) {
										csv.setNumberOfPositions("1");
									}
									csv.setPayRate(csvRecord.get(6));
									csv.setSkills(csvRecord.get(7));
									csvlist.add(csv);
								}
								if(csvRecord.get(3).trim().contains(",")) {
									for(String a:csvRecord.get(3).trim().split(",")) {
										Csv csv = new Csv();
										if(!a.trim().contains(" ")) {
											if(a.equalsIgnoreCase(cities.toString())) {
												csv.setCompany(csvRecord.get(0));
												csv.setExperience(csvRecord.get(1));
												csv.setIndustry(csvRecord.get(2));
												csv.setJobLocationAddress(namechanger(a));
												csv.setJobTitle(csvRecord.get(4));
												csv.setNumberOfPositions(csvRecord.get(5));
												if(csv.getNumberOfPositions().isEmpty()) {
													csv.setNumberOfPositions("1");
												}
												csv.setPayRate(csvRecord.get(6));
												csv.setSkills(csvRecord.get(7));
												csvlist.add(csv);
											}
										}

									}		
								}

							}
						}
					}
				}



			}
		}

		//					for(Csv a:csvlist) {
		//					System.out.println("result..."+a.getNumberOfPositions());
		//				}
		BufferedWriter writer = Files.newBufferedWriter(Paths.get("/Users/sethuram/Desktop/test/output.csv"));
		CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT .withHeader("company","experience","industry","joblocation_address",	"jobtitle","	numberofpositions","payrate","skills"));
		for(Csv a:csvlist) {
			csvPrinter.printRecord(a.getCompany(),a.getExperience(),a.getIndustry(),a.getJobLocationAddress(),a.getJobTitle(),a.getNumberOfPositions(),a.getPayRate(),a.getSkills());
			//System.out.println(a.getJobLocationAddress());
			csvPrinter.flush(); 
		}
		System.out.println("Done.....");

	}

	private static Boolean nullCheck(CSVRecord csvRecord) {

		for(int i =0 ;i<8;i++) {
			if(csvRecord.get(i).isEmpty() && i != 5){
				return true;
			}
		}
		return false;
	}

	public static String namechanger(String oldName) { 

		if(oldName.toLowerCase().contains("bengaluru") || oldName.toLowerCase().contains("banglore")|| oldName.toLowerCase().contains("bangalore")) {
			return "Bengaluru/Banglore";
		}
		if((oldName.toLowerCase().contains("hyderabad") || oldName.toLowerCase().contains("secunderabad"))){
			return "Hyderabad/Secunderabad";
		}
		if((oldName.toLowerCase().contains("delhi") || oldName.toLowerCase().contains("ncr"))){
			return "Delhi/NCR";
		}
		if((oldName.toLowerCase().contains("cochin") || oldName.toLowerCase().contains("kochi"))){
			return "Cochin/Kochi";
		}
		if((oldName.toLowerCase().contains("panjim") || oldName.toLowerCase().contains("panaji"))){
			return "Panjim/Panaji";
		}
		if((oldName.toLowerCase().contains("vadodara") || oldName.toLowerCase().contains("baroda"))){
			return "Vadodara/Baroda";
		} 
		if((oldName.toLowerCase().contains("visakhapatnam") || oldName.toLowerCase().contains("vizag"))){
			return "Vishakapatnam/Vizag";
		}
		return oldName;

	}
}
