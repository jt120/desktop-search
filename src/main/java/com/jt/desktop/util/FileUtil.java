package com.jt.desktop.util;

import java.io.File;
import java.io.FileFilter;
import java.util.LinkedList;

import org.apache.log4j.Logger;


public class FileUtil implements Runnable {
	
	private static final Logger log = Logger.getLogger(FileUtil.class);
	
	private String fileName;
	
	public FileUtil(String fileName) {
		this.fileName = fileName;
	}
	
	private static LinkedList<String> fileList = new LinkedList<String>();
	
	public synchronized static void push(String filename) {
		fileList.push(filename);
	}
	
	public synchronized static String pop() {
		return fileList.pop();
	}
	
	public synchronized static int fileNums() {
		return fileList.size();
	}
	
	static class ExtFilter implements FileFilter {

		public boolean accept(File pathname) {
			if (pathname.isDirectory()) {
				return true;
			}
			if (pathname.isHidden()) {
				return false;
			}
			String name = pathname.getName();

			if (name.endsWith("html") || name.endsWith("txt")) {
				return true;
			}
			return false;
		}
	}

	public void run() {
		File file = new File(fileName);
		LinkedList<File> list = new LinkedList<File>();
		File[] files = file.listFiles(new ExtFilter());
		
		for(File f:files) {
			if(f.isHidden()) continue;
			if(f.isDirectory()) {
				list.add(f);
			} else {
				push(f.getAbsolutePath());
			}
		}
		
		while(!list.isEmpty()) {
			File one = list.removeFirst();
			File[] tmps = one.listFiles(new ExtFilter());
			for(File f:tmps) {
				if(f.isHidden()) continue;
				if(f.isDirectory()) {
					list.add(f);
				} else {
					push(f.getAbsolutePath());
				}
			}
		}
    }
	

}
