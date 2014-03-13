package com.donlian.jdk;

public class BinSafeSting {
	public static void main(String[] args) {
		String s="Hello\0World";
		System.out.println(s);
		String s1="Hello\000World";
		System.out.println(s1);
	}
}
