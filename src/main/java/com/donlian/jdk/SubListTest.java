package com.donlian.jdk;

import java.util.Arrays;
import java.util.List;
/**
 * jdk的sublist
 * @author donlianli@126.com
 * 说明sublist是前闭后开区间函数
 */
public class SubListTest {

	public static void main(String[] args) {
		List<Integer> list = Arrays.asList(1,2,3,4,5);
		List<Integer> l1 = list.subList(3, 5);
		//[4, 5]
		System.out.println(l1);
		List<Integer> l2 = list.subList(4, 5);
		//[5]
		System.out.println(l2);
		List<Integer> l3 = list.subList(1, 2);
		//java.lang.IndexOutOfBoundsException: toIndex = 6
		System.out.println(l3);
		List<Integer> l4 = list.subList(4, 6);
		//java.lang.IndexOutOfBoundsException: toIndex = 6
		System.out.println(l4);
	}
}
