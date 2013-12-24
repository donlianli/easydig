package com.donlian.util;


public class IDUtils {
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		System.out.println(new JeeheDES().decrypt("ac35273040a17464"));;
		System.out.println(new JeeheDES().encrypt("673059"));;
	}
}
