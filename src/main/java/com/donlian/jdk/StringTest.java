package com.donlian.jdk;

public class StringTest {

	public static void main(String[] args) {
		String a = "a\nbc\n";
		System.out.println(a);
		System.out.println(a.contains("\n"));
		System.out.println(a.replace("\n",""));
	}

}
