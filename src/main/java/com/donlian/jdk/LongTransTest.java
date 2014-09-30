package com.donlian.jdk;

public class LongTransTest {

	public static void main(String[] args) {
		long l1 = 0x7fffffffffffffffl;
		System.out.println(Long.MAX_VALUE);
		System.out.println(l1==Long.MAX_VALUE);
	}

}
