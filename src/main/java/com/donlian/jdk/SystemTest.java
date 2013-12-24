package com.donlian.jdk;

public class SystemTest {

	public static void main(String[] args) {
		long m = System.currentTimeMillis();
		System.out.println(m);
		System.out.println(Long.MAX_VALUE-m);
	}

}
