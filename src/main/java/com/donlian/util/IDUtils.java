package com.donlian.util;


public class IDUtils {
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		System.out.println(new JeeheDES().decrypt("305bff1e2516e212"));;
		System.out.println(new JeeheDES().decrypt("a3901b33b586084b"));;
		System.out.println(new JeeheDES().decrypt("3735685c3b073fe7"));;
		System.out.println(new JeeheDES().encrypt("673059"));;
	}
}
