package com.donlian.jdk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 当List里面是基本数据类型Long,String等对象时，使用
 * toString方法打印的格式就是“[1,2,3]"等
 * 
 * Arrays.toString(a)也类似，可以将数组打印成
 * "[1,2,3]"等格式
 */
public class ListToString {

	public static void main(String[] args) {
		List<String> ids = new ArrayList<String>();
		ids.add("1");ids.add("2");
		//[1, 2]
		System.out.println(ids);
		//[Ljava.lang.Object;@7451b0af
		System.out.println(ids.toArray());
		List<String> ids2=null;
		//null
		System.out.println(ids2);
		int[] b=null;
		//null
		System.out.println(Arrays.toString(b));
		int[] a=new int[]{12,213};
		//[12, 213]
		System.out.println(Arrays.toString(a));
	}

}
