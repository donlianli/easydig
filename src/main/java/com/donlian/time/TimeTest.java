package com.donlian.time;

import java.util.Date;

public class TimeTest {
	/**
	 * 输出的年到2 9227 7094 年，说明使用long，精度到毫秒足够人们使用2亿多年
	 * @param args
	 */
	public static void main(String[] args) {
		//长整形可表示到2 9227 7094 年
		System.out.println(new Date(Long.MAX_VALUE));
		//32的整形只能表示68年（精确到秒）
		System.out.println(Integer.MAX_VALUE/(60*60*24*365));
		//64位的可表示292471208677年（大约3千亿年）
		System.out.println(Long.MAX_VALUE/(60*60*24*365));
	}
}
