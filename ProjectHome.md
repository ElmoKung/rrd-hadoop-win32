# Description #
Hadoop 3.X does not work incredibly well on Windows.  The hortonworks distribution provides an installer and 64 bit binaries required for MapReduce .  32-bit windows users however are stuck compiling their own `winutils.exe` and `hadoop.dll` files using Visual Studio.

To save folks this headache, this library provides pre-packaged binaries as well as a utility class that can be used to initialize the appropriate system properties to utilize these.

This library has been tested with the `LocalJobRunner` on windows and has so far only been used for local integration testing purposes.

# Usage #
To extract binaries and initialize the appropriate system properties, simply run:
```
HadoopWindowsInitializer.initialize();
```
somewhere in your code.

To use this repository it must be added to pom.xml:
```
<repositories>
	...
	<repository>
		<id>rrd-hadoop-win32</id>
		<url>http://repo.rrd-hadoop-win32.googlecode.com/git/</url>
	</repository>
	...
</repositories>
```

artifacts can then be included as dependencies:
```
<dependencies>
	...
    <dependency>
        <groupId>com.rrd.hadoop.win32</groupId>
        <artifactId>rrd-hadoop-win32</artifactId>
        <version>3.0.1</version>
    </dependency>
	...
</dependencies>
```

The repository is backed by git and is available at:
https://code.google.com/p/rrd-hadoop-win32.repo/