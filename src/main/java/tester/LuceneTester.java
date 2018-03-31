package tester;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;

import constants.LuceneConstants;
import constants.LuceneConstants.Models;
import indexer.Indexer;
import searcher.Searcher;

public class LuceneTester {
	List<List<Integer>> reference = null;
	Map<Integer, Integer>totalRelevantDocumets = null;
	List<Double> precesionMap = new ArrayList<Double>();
	List<Double> recallMap = new ArrayList<Double>();
	
	private static String indexDir;
	private static String dataDir;
	private static String querylocation;
	private static String relevantDocLocation;
	

	Indexer indexer;
	Searcher searcher;
	
	
	public LuceneTester() {
//		ReferenceReader referenceReader = new ReferenceReader();
//		try {
//			reference = referenceReader.getReference(relevantDocLocation);
//			totalRelevantDocumets = referenceReader.getTotalRelevantDocuments();
//	
//		} catch (IOException e) {	
//			e.printStackTrace();
//		}
	}


	@SuppressWarnings("unlikely-arg-type")
	public static void main(String[] args) throws ParseException, IOException {
		
		
		if (args != null && args.length > 0) {
			if(args[0].equals("-d")) dataDir = args[1];
			if(args[2].equals("-q")) querylocation = args[3];
			//if(args[4].equals("-r"))  relevantDocLocation= args[5];
			indexDir = LuceneConstants.CURRENT_PATH;
			
			
			System.out.println("----------------------------------------------------------------------------------------");
			System.out.println("indexDir----------------->"+indexDir);
			System.out.println("dataDir----------------->"+dataDir);
			System.out.println("querylocation----------------->"+querylocation);
			System.out.println("relevantDocLocation----------------->"+relevantDocLocation);
			System.out.println("----------------------------------------------------------------------------------------");
			
		} else{
			System.out.print("----------------------------------No Arguments passed-------------------------------------");
		}
		
	

		LuceneTester tester;
		try {
			tester = new LuceneTester();
			tester.createIndex(LuceneConstants.VSM);
			tester.createIndex(LuceneConstants.BM_25);
			System.out.println("### Indexing Done ###");
			QueryReader queryReader = new QueryReader();
			List<List<String>> inputToFiles = new ArrayList<List<String>>();
			int count = 0;
			String model;
			for(Models a:LuceneConstants.Models.values()) {
				if(a.toString().toLowerCase().equals(LuceneConstants.BM_25)){
					model=LuceneConstants.BM_25;
				}else {
					model=LuceneConstants.VSM;
				}
				for (Entry<Integer, String> entry : queryReader.getQueries(querylocation).entrySet()) {
					//	System.out.println("Item : " + entry.getKey() + " Count : " + entry.getValue());
					count = count +1;
					for (Entry<Integer, List<ScoreDoc>> score : tester.search(entry.getValue(),String.valueOf(entry.getKey()),model).entrySet()) {
						for(ScoreDoc temp:score.getValue()) {
							List<String> inputToFile = new ArrayList<String>();
							inputToFile.add(String.valueOf(count));
							inputToFile.add("Q0");
							inputToFile.add(String.valueOf(temp.doc + 1));
							inputToFile.add(String.valueOf(temp.score));
							inputToFile.add(" INFO_RET");
							inputToFiles.add(inputToFile);
						}
					}
				}
				TextFileWriter writer = new TextFileWriter(inputToFiles);
				writer.writeFile("result",model,indexDir);
				System.out.println("----------------------------------------------------------------------------------------");
				System.out.println("---------------------------------WRITING IS DONE---------------------------------------> ");
				System.out.println("----------------------------------------------------------------------------------------");
			}
		//	System.out.println("testing............"+inputToFiles);
			
		//	tester.findMeanAveragePrecisionRecall();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createIndex(String model) throws IOException {
		indexer = new Indexer(indexDir,model);
	    indexer.createIndex(dataDir);
	}
	

	private Map<Integer, List<ScoreDoc>> search(String searchQuery, String queryNumber, String model) throws IOException, ParseException {
		double count = 0;
		double recall=0;
		double precision = 0;
		int totalRetrievedDocuments = 0;


		searcher = new Searcher(indexDir,model);
		
		Map<Integer, List<ScoreDoc>> searched = searcher.search(searchQuery,queryNumber);

		//System.out.println("searched...."+searched);

//		for (Entry<Integer, List<ScoreDoc>> entry : searched.entrySet()) {
//			List<Double> precisionRecall = new ArrayList<Double>();
//			totalRetrievedDocuments = entry.getValue().size();
//			for(List<Integer> a:reference) {
//				if(a.get(0) == entry.getKey()) {
//					for(ScoreDoc temp:entry.getValue()) {
//						if(a.size() == 3) {
//							if(temp.doc == (a.get(1) - 1) && (a.get(2)) == 1) {
//								count = count+1.0;
//							}
//						}
//					}
//				}
//			}
//			precision = count / totalRetrievedDocuments;
//			precisionRecall.add(precision);
//			
//			for(Entry<Integer, Integer> a:totalRelevantDocumets.entrySet()) {
//				if(entry.getKey().equals(a.getKey())) {
//					recall = count/a.getValue();
//					recallMap.add(recall);
//					precisionRecall.add(recall);
//				}
//			}
//			System.out.println("----------------------------------------------------------------------------------------");
//			System.out.println("Query Id: "+entry.getKey()+"  "+"Precision: "+precision+"  "+"Recall: "+ recall);
//			System.out.println("----------------------------------------------------------------------------------------");
//			precesionMap.add(precision);
//		}
		return searched;

	}

	private void findMeanAveragePrecisionRecall() {
		double totalCount = precesionMap.size();
		double totalRecallCount = recallMap.size();
		double sum =0;
		double sums=0;
		for(double a:precesionMap) {
			sum = sum+a;
		}
		for(double b:precesionMap) {
			sums = sums+b;
		} 
		
//		System.out.println("----------------------------------------------------------------------------------------");
//		System.out.println("Mean Average Precision------------------> "+ sum/totalCount);
//		System.out.println("Mean Average Recall------------------> "+ sums/totalRecallCount);
//		System.out.println("----------------------------------------------------------------------------------------");
	}

}
