package com.donlian.util;


public class IDUtils {
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		System.out.println(new JeeheDES().decrypt("86df75a33f68b977"));;
		System.out.println(new JeeheDES().encrypt("673059"));;
	}
}
