package com.donlian.jdk;

import java.util.HashMap;
/**
 * 测试字符串的hashcode重复几率
 * @author donlianli@126.com
 * 128-33=95个可见字符
 * 长度：			组合总数：		重复次数：		不重复的数量：	重复率
 * 1:			95:			0:			95:			0.0
 * 2:			9025:		6016:		3009:		0.6665928
 * 3:			857375:		764032:		93343:		0.8911293
 * 4:			81450625:	79326976:	2990144:	0.9739272
 * 从数字0开始到小写字母z
 * 1:			75:			0:			75:			0.0
 * 2:			5625:		3256:		2444:		0.5788444
 * 3:			421875:		351648:		75927:		0.833536
 * 
 * 纯数字
 * 不会重复
 * 
 * 结论：其实是只要key的取值字符串的int值跨度大于31，重复的概率就会逐步增大。
 * 也就是说，我们可以使用从A到_字符的组合作为键值，但是用A到`的字符组做键值，概率就会逐步增大。
 * 跨度范围越大，重复概率就会越大，比如使用所有可见字符作为key的时候，概率竟然能够到达89%，
 * 再加上hashmap自己的键值冲突，其概率肯定会大于90%。几乎跟链表差不多了。
 * 
 * 但是，我们使用string作为键值的时候，通常就因为不知道key的取值范围，因此，建议做数据处理时，
 * 不用用string作为hashMap的键值。
 * 但是在一般的小map中，应该不至于产生性能问题。
 */
public class StringHashCode {
	
	static HashMap<Integer,Object> map = new HashMap<Integer,Object>(); 
	/**
	 * 第一个可见字符
	 */
    private static char startChar = ' '; 
    /**
     * 最后一个可见字符
     */
    private static char endChar = '~'; 
    private static int offset = endChar - startChar + 1; 
    /**
     * 重复次数
     */
    private static int dupCount = 0; 
    
    public static void main(String[] args) { 
        for(int len=1;len<5;len++){
        	 char[] chars = new char[len]; 
             tryBit(chars, len); 
             int total=(int)Math.pow(offset, len);
             System.out.println(len+":"+total + ":" + dupCount+":"+map.size()+":"+(float)dupCount/total);
        }
        
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
 
//        Integer key = new String(chars).replaceAll("[^a-zA-Z_]", "").toUpperCase().hashCode();// 195112:0 
        //String str = new String(chars).toLowerCase();//195112:6612 
        //String str = new String(chars).replaceAll("[^a-zA-Z_]","");//195112:122500 
        //String str = new String(chars);//195112:138510 
    	Integer key = new String(chars).hashCode();
        if (map.containsKey(key)) { 
            dupCount++; 
        } else { 
            map.put(key, null); 
        } 
    } 
}
