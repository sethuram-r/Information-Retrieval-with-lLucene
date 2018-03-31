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

import constants.XmlEnum;
import pojo.LaTimes;

public class LaTimesImplementation {

	public static void main(String[] args) throws ParseException, IOException, ParserConfigurationException, SAXException, DocumentException {

		File[] files = new File("/Users/sethuram/Desktop/Assignment Two/latimes").listFiles();
		List<LaTimes> laTimes = new ArrayList<LaTimes>();
		int count = 0;
		for (File file : files) {
			count = count+1;
			if(!file.getName().startsWith("r")) {
				File fXmlFile = new File(file.getAbsolutePath());
				Scanner scanner = new Scanner(fXmlFile);
				scanner.useDelimiter(Pattern.compile("</DOC>"));
				while (scanner.hasNext()) {
					StringBuffer sBuffer = new StringBuffer();
					sBuffer.append(scanner.next());
					if(!sBuffer.toString().trim().isEmpty()) {
						sBuffer.append("</DOC>");
						laTimes.add(parser(sBuffer));
					}
				}
			}
			System.out.println("parsing done "+file.getName()+" file " +count+ " ..............");
			break;
		}
		System.out.println("laTimes........"+laTimes.size());
	}
	static LaTimes parser(StringBuffer sBuffer) throws DocumentException{


		SAXReader reader = new SAXReader();
		org.dom4j.Document document = reader.read(new InputSource(new StringReader(sBuffer.toString().trim())));
		List<Node> nodes = document.selectNodes(document.getRootElement().getUniquePath() ); 
		for (Node node : nodes) {
			LaTimes latime = new LaTimes();
			latime.setDocNo(node.selectSingleNode("DOCNO").getText());
			latime.setDocId(Integer.parseInt(node.selectSingleNode("DOCID").getText().trim()));

			for (XmlEnum tags : XmlEnum.values()) {
				if(node.selectSingleNode(tags.toString()) != null) {
					switch(tags.toString()) {
					case "DATE":
						latime.setDate(getParagraph(node,tags,document));break;
					case "SECTION":
						latime.setSection(getParagraph(node,tags,document));break;
					case "LENGTH":
						latime.setLength(getParagraph(node,tags,document));break;
					case "HEADLINE":
						latime.setHeadline(getParagraph(node,tags,document));break;
					case "BYLINE":
						latime.setByline(getParagraph(node,tags,document));break;
					case "TEXT":
						latime.setText(getParagraph(node,tags,document));break;
					case "GRAPHIC":
						latime.setGraphic(getParagraph(node,tags,document));break;
					case "TYPE":
						latime.setType(getParagraph(node,tags,document));break;
					default:
						latime = null;
					}
				}
			}
			return latime;
		}
		return null;
	}
	public static StringBuilder getParagraph(Node node ,XmlEnum tags, org.dom4j.Document document) { 
		if(node.selectSingleNode(tags.toString()).hasContent()) {
			List<Node> modes = document.selectNodes(node.selectSingleNode(tags.toString()).getUniquePath());
			StringBuilder temp = new StringBuilder();
			for (Node nod : modes) {
				if(nod.selectSingleNode("P") != null) {
					temp.append(nod.selectSingleNode("P").getText());
				}
			}
			return temp;
		}
		return null;
	}
}

