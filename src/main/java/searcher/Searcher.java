package searcher;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import constants.LuceneConstants;


public class Searcher {

	IndexSearcher indexSearcher;
	//QueryParser queryParser;
	Query query;
	String[] fields = {LuceneConstants.CONTENTS,LuceneConstants.TITLE};
	Path path = null;;
	public Searcher(String indexDirectoryPath,String model) throws IOException {
		if(model.equals(LuceneConstants.VSM)) {
			path = Paths.get(indexDirectoryPath,LuceneConstants.VSM);
		}else {
			path = Paths.get(indexDirectoryPath,LuceneConstants.BM_25);
		}
		Directory indexDirectory = FSDirectory.open(path);
		DirectoryReader dreader =  DirectoryReader.open(indexDirectory);
		indexSearcher = new IndexSearcher(dreader);

	}


	public Map<Integer, List<ScoreDoc>> search( String searchQuery , String queryNumber) throws IOException {
			
		Map<Integer,List<ScoreDoc>> queryDoc = new HashMap<Integer,List<ScoreDoc>>();
		//MultiFieldQueryParser parser = new MultiFieldQueryParser(fields,new EnglishAnalyzer(EnglishAnalyzer.getDefaultStopSet()));
		//MultiFieldQueryParser parser = new MultiFieldQueryParser(fields,new WhitespaceAnalyzer());
		MultiFieldQueryParser parser = new MultiFieldQueryParser(fields,new StandardAnalyzer());
		
		List<ScoreDoc> doc = new ArrayList<ScoreDoc>();
		try {
			query = parser.parse(searchQuery.replaceAll("[\n|:|\\*|\\?]", ""));
		} catch (ParseException e) {
			e.printStackTrace();
			//query =  null;
		}
		if(query !=null) {

			TopDocs result = indexSearcher.search(query, LuceneConstants.MAX_SEARCH);
			for(ScoreDoc scoreDoc : result.scoreDocs) {
				//System.out.println( "Doc Id:"+" "+scoreDoc.doc + " " +scoreDoc.score);
				doc.add(scoreDoc);
			}
			queryDoc.put(Integer.parseInt(queryNumber.trim()), doc);
		}
		return queryDoc; 
	}
}
