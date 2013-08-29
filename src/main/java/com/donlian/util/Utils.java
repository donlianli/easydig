package com.donlian.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class Utils {
	public static ObjectMapper mapper = new ObjectMapper(); 
	public static ObjectMapper getMapper(){
		return mapper;
	}
	public static String toJson(Object o){
		try {
			ObjectWriter writer = mapper.writerWithDefaultPrettyPrinter();
			return writer.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return "转换JSON时发生异常";
		}
	}
}
