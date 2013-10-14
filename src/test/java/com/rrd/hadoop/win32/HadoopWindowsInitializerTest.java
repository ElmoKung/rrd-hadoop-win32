package com.rrd.hadoop.win32;

import static org.junit.Assert.*;

import org.apache.commons.lang.SystemUtils;
import org.junit.Test;
/**
 * Tests for {@link HadoopWindowsInitializer}
 * @author erachitskiy
 *
 */
public class HadoopWindowsInitializerTest {

	@Test
	public void testInitializer(){
		/* return if not running on windows */
		if(!SystemUtils.IS_OS_WINDOWS){
			System.err.println("MUST BE ON WINDOWS TO RUN TESTS");
			return;
		}
		HadoopWindowsInitializer.initialize();
		String hadoopHome = System.getProperty("hadoop.home.dir");
		assertNotNull(hadoopHome);
	}
}
