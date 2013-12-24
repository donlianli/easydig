package com.donlian.reflect;

import java.util.ArrayList;
import java.util.List;

import com.donlianli.es.model.LogModel;
import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * 测试结果说明：
 * jackson的使用时间大概是原生拼接的1.5-2倍。
 * 性能率低于原生拼接。
 * @author donlianli@126.com
 */
public class Jackson2Test {
	static ObjectMapper mapper = new ObjectMapper();
	public static void main(String[] args) throws Exception {
		test();
		
	}
	public void testTime() throws Exception {
		int COUNT=100000;
		List<LogModel> list = new ArrayList<LogModel>(COUNT);
		for(int i=0;i<COUNT;i++){
			list.add(new LogModel());
		}
		for(LogModel l:list){
			l.getId();
		}
		long beginTime=System.currentTimeMillis();
		for(LogModel l:list){
			mapper.writeValueAsString(l);
		}
		long useTime= System.currentTimeMillis()-beginTime;
		System.out.println("json use Time:"+useTime);
		
		beginTime=System.currentTimeMillis();
		for(LogModel l:list){
			toString(l);
			
		}
		useTime= System.currentTimeMillis()-beginTime;
		System.out.println("concat use Time:"+useTime);
	}
	public static String toString(LogModel l){
		StringBuilder sb= new StringBuilder("{");
		sb.append("id:").append(l.getId());
		sb.append(",subId:").append(l.getSubId());
		sb.append(",systemName:").append(l.getSystemName());
		sb.append(",host:").append(l.getHost());
		sb.append(",desc:").append(l.getDesc());
		sb.append(",catIds:").append(l.getCatIds());
		sb.append("}");
		return sb.toString();
	}
	
	public static void test() throws Exception{
		LogModel l = new LogModel();
		System.out.println(toString(l));
		System.out.println(mapper.writeValueAsString(l));
	}
}
