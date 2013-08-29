package com.donlian.util;

import java.util.HashMap;

public class NullTransTest {
	public static void main(String[] argv) {
		Integer o = (Integer) new HashMap<String,Object>().get("hello");
		System.out.println(o);
	}
}
