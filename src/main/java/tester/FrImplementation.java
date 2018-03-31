package tester;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.lucene.queryparser.classic.ParseException;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import constants.FrEnum;
import pojo.Fr;

public class FrImplementation {

	public static void main(String[] args) throws ParseException, IOException, ParserConfigurationException, SAXException, DocumentException {
		File[] files = new File("/Users/sethuram/Desktop/Assignment Two/fr94").listFiles();
		List<Fr> frs = new ArrayList<Fr>();
		for (File file : files) {
			if(file.isDirectory()){
				File[] subNote = file.listFiles();
				for (File temp : subNote) {
					System.out.println("path.........."+temp.getAbsolutePath());
				

					Scanner scanner = new Scanner(temp);
					scanner.useDelimiter(Pattern.compile("</DOC>"));
					while (scanner.hasNext()) {
						StringBuffer sBuffer = new StringBuffer();
						sBuffer.append(scanner.next().replaceAll("&blank;|/&blank;|<\\![^>]*>|&.*;",""));
						if(!sBuffer.toString().trim().isEmpty()) {
							sBuffer.append("</DOC>");
							frs.add(parser(sBuffer));
						}

					}
				}

			}
		}
		System.out.println("frs........"+frs.size());
//		for(Fr a:frs) {
//			System.out.println("inside");
//			if(a.getText() != null) {
//				System.out.println(a.getText());
//			}
//
//		}
	}

	private static Fr parser(StringBuffer sBuffer) throws DocumentException {
		SAXReader reader = new SAXReader();
		org.dom4j.Document document = reader.read(new InputSource(new StringReader(sBuffer.toString().trim())));
		List<Node> nodes = document.selectNodes("/DOC" ); 
		for (Node node : nodes) {
			Fr fr = new Fr();
			for (FrEnum tags : FrEnum.values()) {
				if(node.selectSingleNode(tags.toString()) != null){
					switch(tags.toString()) {
					case "DOCNO":
						fr.setDocno(cleaner(node.selectSingleNode(tags.toString()).getText()));break;
					case "PARENT":
						fr.setParent(cleaner(node.selectSingleNode(tags.toString()).getText()));break;
					case "USDEPT":
						fr.setUsDept(cleaner(node.selectSingleNode(tags.toString()).getText()));break;
					case "AGENCY":
						fr.setAgency(cleaner(node.selectSingleNode(tags.toString()).getText()));break;
					case "USBUREAU":
						fr.setUsBureau(cleaner(node.selectSingleNode(tags.toString()).getText()));break;
					case "DOCTITLE":
						fr.setDocTitle(cleaner(node.selectSingleNode(tags.toString()).getText()));break;
					case "ADDRESS":
						fr.setAddress(cleaner(node.selectSingleNode(tags.toString()).getText()));break;
					case "FURTHER":
						fr.setFurther(cleaner(node.selectSingleNode(tags.toString()).getText()));break;
					case "RINDOCK":
						fr.setRindock(cleaner(node.selectSingleNode(tags.toString()).getText()));break;
					case "CFRNO":
						fr.setCfrNo(cleaner(node.selectSingleNode(tags.toString()).getText()));break;
					case "FRFILING":
						fr.setFrFiling(cleaner(node.selectSingleNode(tags.toString()).getText()));break;
					case "BILLING":
						fr.setBilling(cleaner(node.selectSingleNode(tags.toString()).getText()));break;
					case "SUPPLEM":
						fr.setSupplem(cleaner(node.selectSingleNode(tags.toString()).getText()));break;
					case "SIGNER":
						fr.setSigner(cleaner(node.selectSingleNode(tags.toString()).getText()));break;
					case "SUMMARY":
						fr.setSummary(cleaner(node.selectSingleNode(tags.toString()).getText()));break;
					case "ACTION":
						fr.setAction(cleaner(node.selectSingleNode(tags.toString()).getText()));break;
					case "SIGNJOB":
						fr.setSignjob(cleaner(node.selectSingleNode(tags.toString()).getText()));break;
					case "DATE":
						fr.setDate(cleaner(node.selectSingleNode(tags.toString()).getText()));break;
					case "TEXT":
						fr.setText(cleaner(node.selectSingleNode(tags.toString()).getText()));break;
					default:
						fr = null;
					}
				}

			}
			return fr;
		}
		return null;
	}

	private static String cleaner(String text) {
		return text.replaceAll("\\n", "");
	}
}
