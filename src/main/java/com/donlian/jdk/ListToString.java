package com.donlian.jdk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ListToString {

	public static void main(String[] args) {
		List<Integer> ids = new ArrayList<Integer>();
		ids.add(1);ids.add(2);
		int[] a=new int[]{12,213};
		System.out.println(Arrays.toString(a));
		int[] b=null;
		System.out.println(Arrays.toString(b));
	}

}
