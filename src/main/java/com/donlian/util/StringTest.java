package com.donlian.util;

import java.util.ArrayList;
import java.util.List;

public class StringTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		list.add(new String("1"));
		list.add(new String("3"));
		System.out.println(list.size());;
		list.remove(new String("3"));
		System.out.println(list.size());;
	}

}
