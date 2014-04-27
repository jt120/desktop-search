package com.jt.desktop.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.jt.desktop.model.SearchFile;

public class SearchUtil {
	
	private static final Logger log = Logger.getLogger(SearchUtil.class);

	private static Version version = Version.LUCENE_47;
	private static String path = "d:/test/index";
	
	private static Directory directory;
	private static Analyzer analyzer;
	private static IndexReader indexReader;
	private static IndexSearcher indexSearcher;
	
	static {
		try {
	        directory = FSDirectory.open(new File(path));
	        indexReader = DirectoryReader.open(directory);
        } catch (IOException e) {
	        e.printStackTrace();
        }
		analyzer = new StandardAnalyzer(version);
		indexSearcher = new IndexSearcher(indexReader);
	}
	
	public static List<SearchFile> search(String value) {
		Query q1 = new WildcardQuery(new Term("name",value));
		Query q2 = new WildcardQuery(new Term("content",value));
		BooleanQuery query = new BooleanQuery();
		query.add(q1, Occur.SHOULD);
		query.add(q2, Occur.SHOULD);
		List<SearchFile> list = new ArrayList<SearchFile>();
		try {
	        TopDocs topDocs = indexSearcher.search(query, 100);
	        ScoreDoc[] sds = topDocs.scoreDocs;
	        log.info("find " + sds.length + " results");
	        for(ScoreDoc s:sds) {
	        	Document doc = indexSearcher.doc(s.doc);
	        	String name = doc.get("name");
	        	String path = doc.get("path");
	        	String sizeStr = doc.get("size");
	        	long size = 0L;
	        	if(sizeStr!=null) {
	        		size = Long.parseLong(sizeStr);
	        	}
	        	SearchFile sf = new SearchFile(name,path,size);
	        	list.add(sf);
	        }
        } catch (IOException e) {
	        e.printStackTrace();
        }
		return list;
	}
	
	public static void close() {
		try {
	        directory.close();
        } catch (IOException e) {
	        e.printStackTrace();
        }
		try {
	        indexReader.close();
        } catch (IOException e) {
	        e.printStackTrace();
        }
	}
}
