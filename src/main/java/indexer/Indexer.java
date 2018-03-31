package indexer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import constants.LuceneConstants;

public class Indexer {

	private IndexWriterConfig writerConfiguration;
	private IndexWriter indexWriter;
	Similarity object;
	
	
	public Indexer(String indexDirectoryPath, String scoringModel) throws IOException {

		Path path = null; 


		//Analyzer analyser = new EnglishAnalyzer(EnglishAnalyzer.getDefaultStopSet());
		
		//Analyzer analyser = new WhitespaceAnalyzer();
		
		Analyzer analyser = new StandardAnalyzer();
		if(scoringModel.equals(LuceneConstants.BM_25)) {
			object =  new BM25Similarity();
			path = Paths.get(indexDirectoryPath,LuceneConstants.BM_25);
		}else {
			object =  new ClassicSimilarity();
			path = Paths.get(indexDirectoryPath,LuceneConstants.VSM);
		}
		Directory directory = FSDirectory.open(path);
		writerConfiguration = new IndexWriterConfig (analyser);
		writerConfiguration.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
		writerConfiguration.setSimilarity(object);
		indexWriter = new IndexWriter(directory,writerConfiguration);

	}

	public List<Document> getDocument(File file) throws IOException {
		List<Document> documents = new ArrayList<Document>();
		Scanner scanner = new Scanner(file);
		scanner.useDelimiter(".I");
		while (scanner.hasNext()) {
			Document document = new Document();
			String[] lines = scanner.next().split(".T|.A|.W|.B");
			document.add(new org.apache.lucene.document.TextField(LuceneConstants.ID, lines[0].replaceAll("\n ", ""),org.apache.lucene.document.Field.Store.YES));
			document.add(new org.apache.lucene.document.TextField(LuceneConstants.TITLE, lines[1].replaceAll("\n ", ""),org.apache.lucene.document.Field.Store.YES));
			document.add(new org.apache.lucene.document.TextField(LuceneConstants.AUTHOR, lines[2].replaceAll("\n ", ""),org.apache.lucene.document.Field.Store.YES));
			document.add(new org.apache.lucene.document.TextField(LuceneConstants.BIBLIOGRAPHY, lines[3].replaceAll("\n ", ""),org.apache.lucene.document.Field.Store.YES));
			document.add(new org.apache.lucene.document.TextField(LuceneConstants.CONTENTS, lines[4].replaceAll("\n ", ""),org.apache.lucene.document.Field.Store.YES));
			document.add(new org.apache.lucene.document.TextField(LuceneConstants.FILE_PATH, file.getCanonicalPath(),org.apache.lucene.document.Field.Store.YES));
			document.add(new org.apache.lucene.document.TextField(LuceneConstants.FILE_NAME, file.getName(),org.apache.lucene.document.Field.Store.YES));    
			documents.add(document);
		}
		return documents;
	}


	public void createIndex(String dataDirPath) throws IOException {
		File file = new File(dataDirPath);
		System.out.println("Indexing "+file.getCanonicalPath());
		List<Document> document = getDocument(file);
		for(Document doc :document) {
			indexWriter.addDocument(doc);
		}
		indexWriter.close();
	}
}


