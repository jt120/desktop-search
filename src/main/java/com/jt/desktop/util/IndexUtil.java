package com.jt.desktop.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;

public class IndexUtil {

	private static final Logger log = Logger.getLogger(IndexUtil.class);

	private static Version version = Version.LUCENE_47;
	private static String path = PropertiesUtil.get("index.path");

	private static Directory directory;
	private static IndexWriter indexWriter;
	private static Analyzer analyzer;
	private static IndexWriterConfig conf;

	static {
		try {
			directory = FSDirectory.open(new File(path));
			analyzer = new StandardAnalyzer(version);
			conf = new IndexWriterConfig(version, analyzer);
			indexWriter = new IndexWriter(directory, conf);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void add(File file) {
		Document doc = new Document();
		doc.add(new StringField("path", file.getAbsolutePath(), Store.YES));
		doc.add(new StringField("name", file.getName(), Store.YES));
		doc.add(new TextField("content", file2String(file), Store.NO));
		doc.add(new LongField("size", file.getTotalSpace(), Store.YES));
		try {
			log.info("add a document" + file.getAbsolutePath());
			indexWriter.addDocument(doc);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String file2String(File file) {
		Tika tika = new Tika();
		String s = null;
		try {
			s = tika.parseToString(file);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TikaException e) {
			e.printStackTrace();
		}
		return s;
	}

	public static void index(String filePath) {
		File file = new File(filePath);
		add(file);
	}

	public static void commit() {
		try {
			indexWriter.commit();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void close() {
		try {
			indexWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			directory.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void delete() {
		try {
			indexWriter.deleteAll();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static IndexWriter getIndexWriter() {
		return indexWriter;
	}

	public static void setIndexWriter(IndexWriter indexWriter) {
		IndexUtil.indexWriter = indexWriter;
	}

}
