package com.donlian.jdk;

import java.util.HashMap;
/**
 * 测试字符串的hashcode重复几率
 * @author donlianli@126.com
 * 求长度为2的hashcode重复的字符串
 */
public class PrintStringHashCode {
	
	static HashMap<Integer,Object> map = new HashMap<Integer,Object>(); 
	/**
	 * 第一个可见字符
	 */
    private static char startChar = ' '; 
    /**
     * 最后一个可见字符
     */
    private static char endChar = 'z'; 
    private static int offset = endChar - startChar + 1; 
    /**
     * 重复次数
     */
    private static int dupCount = 0; 
    
    public static void main(String[] args) { 
    	int len =2;
		 char[] chars = new char[len]; 
	     tryBit(chars, len); 
	     int total=(int)Math.pow(offset, len);
	     System.out.println(len+":"+total + ":" + dupCount+":"+map.size()+":"+(float)dupCount/total);
    } 
 
    private static void tryBit(char[] chars, int i) { 
        for (char j = startChar; j <= endChar; j++) { 
            chars[i - 1] = j; 
            if (i > 1) 
                tryBit(chars, i - 1); 
            else 
                test(chars); 
        } 
    } 
 
    private static void test(char[] chars) { 
    	String s = new String(chars);
    	Integer key = s.hashCode();
        if (map.containsKey(key)) { 
            dupCount++; 
            System.out.println(map.get(key)+" same :"+s+" hashcode:"+key);
        } else { 
            map.put(key, s); 
        } 
    } 
}
