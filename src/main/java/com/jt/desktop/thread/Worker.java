package com.jt.desktop.thread;

import com.jt.desktop.util.FileUtil;
import com.jt.desktop.util.IndexUtil;

public class Worker implements Runnable {

	
	public void run() {
		
		while(true) {
			int size = FileUtil.fileNums();
			if(size == 0) {
				break;
			}
			String file = FileUtil.pop();
			IndexUtil.index(file);
		}
	}

}
