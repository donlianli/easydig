package com.donlian.util;


public class IDUtils {
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		System.out.println(new JeeheDES().decrypt("44bc2381d8e6813b"));;
		System.out.println(new JeeheDES().encrypt("673059"));;
	}
}
