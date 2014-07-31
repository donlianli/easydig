package com.donlian.jdk;

import java.util.HashMap;

public class Int2StringHashcode {
	public static void main(String arg[]) {
		HashMap<Integer, Object> map = new HashMap<Integer, Object>();
		int dupCount =0;
		for(int i=1000;i<10000;i++){
			String sInt = String.valueOf(i);
			Integer hashcode=sInt.hashCode();
			if(map.containsKey(hashcode)){
				dupCount++;
			}
			else {
				map.put(hashcode, null);
			}
		}
		System.out.println("hashcode重复次数："+dupCount+",mapSize:"+map.size());
		System.out.println(Integer.MAX_VALUE);
	}
}
