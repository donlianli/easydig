package com.donlian.jdk;

public class BinSafeSting {
	public static void main(String[] args) {
		String s="Hello\0World";
		s.length();
		System.out.println(s);
	}
}
