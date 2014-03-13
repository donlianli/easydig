package com.donlian.jdk;

import java.util.Arrays;
import java.util.List;

public class SubListTest {

	public static void main(String[] args) {
		List<Integer> list = Arrays.asList(1,2,3,4,5);
		List<Integer> l1 = list.subList(3, 5);
		System.out.println(l1);
		List<Integer> l2 = list.subList(4, 5);
		System.out.println(l2);
	}

}
