package com.donlian.jdk;

import java.util.List;

public class ForEachTest {
	/**
	 * 会报空指针异常
	 * Exception in thread "main" java.lang.NullPointerException
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> list =null;
		for(String a :list){
			System.out.println(a);
		}
	}

}
