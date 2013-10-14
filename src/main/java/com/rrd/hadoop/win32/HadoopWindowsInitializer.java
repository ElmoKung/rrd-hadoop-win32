package com.rrd.hadoop.win32;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.SystemUtils;

/**
 * A class used to initialize hadoop for 32 bit windows use.  Useful only
 * for Local purposes as it overrides hadoop home AND java library path.  
 * 
 * Creates a temporary directory that is used as the hadoop home directory,
 * extracts pre-compiled win32 binaries into this home directory, loads the
 * binaries and overrides hadoop home.
 * @author erachitskiy
 *
 */
public class HadoopWindowsInitializer {
	/**
	 * Initialize hadoop native libraries to allow operation on windows.  This overrides
	 * java library home and hadoop home and thus has profound consequences.  Only useful
	 * for local testing.
	 * 
	 * If the Operating System detected is not windows, this method returns without doing
	 * anything.
	 */
	public static void initialize(){		
		/* return if not running on windows */
		if(!SystemUtils.IS_OS_WINDOWS){
			return;
		}
		/* create a temporary hadoop home directory */
		File tmp;
		File bin;
		try{
			tmp = File.createTempFile("hadoop", "home");
			tmp.delete();
			tmp.mkdirs();
			bin = new File(tmp,"bin");
			bin.mkdirs();
		}catch(Exception e){
			throw new RuntimeException(
					String.format("Could not create temporary directory for hadoop home:%s",e.getMessage()),e);
		}			
		/* copy binaries to home directory */
		for(String file:new String[]{
				"hadoop.dll",
				"winutils.exe",
				"msvcp100.dll",
				"msvcp100d.dll",
				"msvcp50.dll",
				"msvcp60.dll",
				"msvcr100.dll",
				"msvcr100d.dll",
				"msvcr100_clr0400.dll",
				"msvcrt.dll",
				"msvcrt20.dll",
				"msvcrt40.dll"}){
			try{
				FileOutputStream out = new FileOutputStream(new File(bin,file));
				IOUtils.copy(HadoopWindowsInitializer.class.getClassLoader().getResourceAsStream(
					String.format("org/apache/hadoop/win32/bin/%s",file)), out);
				out.close();
			}catch(Exception e){
				throw new RuntimeException(String.format(
						"Could not write %s to %s:%s",file,bin,e.getMessage()),e);
			}
		}
		/* override java library path */		
		System.setProperty("java.library.path",bin.getAbsolutePath());
		/* load hadoop win32 binary compiled using VS'10 from 
		 * http://svn.apache.org/repos/asf/hadoop/common/branches/branch-1-win */
		System.load(bin.getAbsolutePath()+File.separator+"hadoop.dll");
		/* override hadoop home directory */
		System.setProperty("hadoop.home.dir",tmp.getAbsolutePath());
	}
}
