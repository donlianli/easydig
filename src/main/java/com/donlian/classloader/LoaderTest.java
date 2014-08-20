package com.donlian.classloader;

import java.net.URL;
import java.net.URLClassLoader;

public class LoaderTest {

	public static void main(String[] args) {
		//testBootstrapLoader();
		testExtLoader();
	}
	/**
	 * 6 个jar包
	 *  file:/D:/app/Java/jre7/lib/resources.jar
		file:/D:/app/Java/jre7/lib/rt.jar
		file:/D:/app/Java/jre7/lib/jsse.jar
		file:/D:/app/Java/jre7/lib/jce.jar
		file:/D:/app/Java/jre7/lib/charsets.jar
		file:/D:/app/Java/jre7/lib/jfr.jar
		
	 */
	public static void testBootstrapLoader(){
		URL[] urls = sun.misc.Launcher.getBootstrapClassPath().getURLs();  
		for (int i = 0; i < urls.length; i++) {  
		    System.out.println(urls[i].toExternalForm());  
		}
		System.out.println("System.getProperty(\"sun.boot.class.path\")="
		+System.getProperty("sun.boot.class.path"));  
	}
	
	public static void testExtLoader(){
		ClassLoader loader = LoaderTest.class.getClassLoader();     
	    System.out.println("appLoader="+loader);  
	    URLClassLoader appLoader = (URLClassLoader)loader;
	    URL[] urls = appLoader.getURLs();
		for (int i = 0; i < urls.length; i++) {  
		    System.out.println("appLoaderUrls="+urls[i].toExternalForm());  
		}
		
	    URLClassLoader extLoader = (URLClassLoader)loader.getParent();
	    System.out.println("\n\nextLoader="+extLoader);
	    urls = extLoader.getURLs();
		for (int i = 0; i < urls.length; i++) {  
		    System.out.println("extLoaderUrls="+urls[i].toExternalForm());  
		}
	}
}
