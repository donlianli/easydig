package com.donlian.util;


public class IDUtils {
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		System.out.println(new JeeheDES().decrypt("8e5be106ae9a4775"));;
		System.out.println(new JeeheDES().encrypt("673059"));;
	}
}
