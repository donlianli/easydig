package com.donlian.log4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class HelloLog4J2 {
	private static Logger logger = LogManager.getLogger(HelloLog4J2.class);
	
	public static void main(String[] args) {
		String s = new String("hello");
		logger.info("Hello, World!参数：{}",s);
	}
}
