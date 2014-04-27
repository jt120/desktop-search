package com.jt.desktop.util;

import org.junit.After;
import org.junit.Test;

public class TestSearchUtil {
	
	@Test
    public void test01() throws Exception {
	    SearchUtil.search("*");
    }
	
	@Test
    public void test02() throws Exception {
	    SearchUtil.search("java");
	    
    }
	@After
	public void shutdown() {
		SearchUtil.close();
	}

}
