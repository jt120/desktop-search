package com.jt.desktop.util;

import java.io.File;

import org.junit.After;
import org.junit.Test;

public class TestIndexUtil {
	
	@Test
    public void test01() throws Exception {
	    IndexUtil.index("D:/develop/hadoop/hadoop-1.1.2/docs");
    }
	
	@Test
    public void test02() throws Exception {
	    String s = IndexUtil.file2String(new File("D:/develop/hadoop/hadoop-1.1.2/docs/capacity_scheduler.html"));
	    System.out.println(s);
	    
    }
	@After
	public void shutdown() {
		IndexUtil.close();
	}

}
