package com.donlian.jdk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ListToString {

	public static void main(String[] args) {
		List<String> ids = new ArrayList<String>();
		ids.add("1");ids.add("2");
		System.out.println(ids);
		List<String> ids2=null;
		System.out.println(ids2);
		int[] a=new int[]{12,213};
		System.out.println(ids.toArray());
		int[] b=null;
		System.out.println(Arrays.toString(b));
	}

}
