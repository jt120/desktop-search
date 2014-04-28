package com.jt.desktop.util;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestPropertiesUtil {
	
	@Test
    public void test01() throws Exception {
	    String s = PropertiesUtil.get("index.path");
	    System.out.println(s);
	    assertNotNull(s);
    }
}
