package com.donlian.util;


public class IDUtils {
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		System.out.println(new JeeheDES().decrypt("4f40665e3f2b4dff"));;
		System.out.println(new JeeheDES().encrypt("673059"));;
	}
}
