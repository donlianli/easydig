package com.donlian.util;


public class IDUtils {
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		System.out.println(new JeeheDES().decrypt("83def97dd1796338"));;
		System.out.println(new JeeheDES().encrypt("673059"));;
	}
}
