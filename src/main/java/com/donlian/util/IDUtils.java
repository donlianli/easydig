package com.donlian.util;


public class IDUtils {
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		System.out.println(new JeeheDES().decrypt("56fa57c0fb14552e"));;
		System.out.println(new JeeheDES().decrypt("0d37f286f2b92c91"));;
		System.out.println(new JeeheDES().decrypt("5bc489c4f2e26f07"));;
		System.out.println(new JeeheDES().decrypt("1c7d145d348b9fe2"));;
		System.out.println(new JeeheDES().decrypt("effdb4059b777e19"));;
		System.out.println(new JeeheDES().decrypt("7b76d0d9a6b7b813"));;
		System.out.println(new JeeheDES().decrypt("aefbfa381c33d929"));;
		System.out.println(new JeeheDES().encrypt("673059"));;
	}
}
