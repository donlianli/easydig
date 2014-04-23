package com.donlian.jdk;
/**
 * 这里\0，并不是ascii码的控制字符0，而是转换
 * 成了可见字符ascii字符32。跟c语言的\0还不是一样的。
 * @author donlianli
 *
 */
public class BinSafeSting {
	public static void main(String[] args) {
		
		String s="Hello\0World";
		s.length();
		System.out.println(s);
	}
}
