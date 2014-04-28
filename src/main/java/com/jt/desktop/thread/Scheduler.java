package com.jt.desktop.thread;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.jt.desktop.util.ClockUtil;
import com.jt.desktop.util.FileUtil;
import com.jt.desktop.util.IndexUtil;

public class Scheduler {
	
	private static ExecutorService es = Executors.newCachedThreadPool();
	
	public static void run() {
		ClockUtil.start();
		new Thread(new FileUtil("d:")).start();
		
		System.out.println("file nums: " + FileUtil.fileNums());
		IndexUtil.delete();
		for (int i = 0; i < 10; i++) {
	        Worker w = new Worker();
	        es.execute(w);
        }
		ClockUtil.end();
		while(true) {
			Scanner sc = new Scanner(System.in);
			String s = sc.nextLine();
			if(s == "shutdown") {
				System.out.println("quit...");
				shutdown();
			}
		}
		
		
    }
	
	public static void shutdown() {
		es.shutdown();
		IndexUtil.close();
	}

}
