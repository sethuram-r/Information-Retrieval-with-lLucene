package constants;

import java.nio.file.Paths;

public class LuceneConstants {
	public static final String ID = "id";
	public static final String TITLE = "title";
	public static final String AUTHOR = "author";
	public static final String BIBLIOGRAPHY = "bibliography";
	public static final String CONTENTS = "contents";
	public static final String FILE_NAME = "filename";
	public static final String FILE_PATH = "filepath";
	public static final String REFERENCE = "cranrel";
	public static final int MAX_SEARCH = 1000;
	public static final String BM_25 = "bm25";
	public static final String VSM = "vsm";
	public static final String CURRENT_PATH = Paths.get("").toAbsolutePath().toString();
	public static enum Models {VSM, BM25};
	
	
	
}
