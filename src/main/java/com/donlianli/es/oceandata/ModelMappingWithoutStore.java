package com.donlianli.es.oceandata;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;

import com.donlianli.es.ESUtils;
/**
 * 创建code的mapping
 * @author donlianli@126.com
 */
public class ModelMappingWithoutStore {
	static final String INDEX_NAME="code";
	static final String TYPE_NAME="oceandata";
	
	public static void  main(String[] argv) throws Exception{
		Client client = ESUtils.getCodeClient();
		//首先创建索引库
		PutMappingResponse  response = client.admin().indices().preparePutMapping(INDEX_NAME)
				.setType("od")
		//这个索引库的名称还必须不包含大写字母
		.setSource(getMapping())
		.execute().actionGet();
		
		System.out.println("success:"+response.isAcknowledged());
	}
	
	/**
	 * mapping 一旦定义，之后就不能修改。
	 * @return
	 * @throws Exception
	 */
	private static XContentBuilder getMapping() throws Exception{
		XContentBuilder mapping = jsonBuilder()  
			       .startObject()  
			         .startObject("od")  
			         .startObject("properties")         
			           .startObject("oid")
			           		.field("type", "string")
			           		.field("store", "yes")
			           		.field("index", "no")
			           	.endObject()    
			           	
			           .startObject("firstKey")
			           		.field("type", "string")
			           		.field("store", "yes")
			           		.field("index", "analyzed")
			           	.endObject()  
			           	
			           	 .startObject("secondKey")
			           		.field("type", "string")
			           		.field("store", "yes")
			           		.field("index", "analyzed")
			           	.endObject()  
			           	
			           	 .startObject("thirdKey")
			           		.field("type", "string")
			           		.field("store", "yes")
			           		.field("index", "analyzed")
			           	.endObject() 
			           	
			           	.startObject("catIds")
			           		.field("type", "integer")
			           		.field("store", "no")
			           		.field("index", "not_analyzed")
			           	.endObject()  
			           	
			           .startObject("isShow")
			           		.field("type", "integer")
			           		.field("store", "yes")
			           		.field("index", "no")
			           .endObject()  
			           
			           .startObject("isShow")
			           		.field("type", "integer")
			           		.field("store", "yes")
			           		.field("index", "no")
			           .endObject()  
			           
			            .startObject("updateTime")
			           		.field("type", "date")
			           		.field("store", "yes")
			           		.field("format", "YYYY-MM-dd")
			           .endObject() 
			           
			           .startObject("clickCount")
			           		.field("type", "long")
			           		.field("store", "yes")
			           		.field("index", "no")
			           .endObject() 
			           
			            .startObject("tableName")
			           		.field("type", "string")
			           		.field("store", "no")
			           		.field("index", "no")
			           .endObject()  
			           
			             .startObject("title")
			           		.field("type", "string")
			           		.field("store", "no")
			           		.field("index", "no")
			           .endObject()  
			           
			             .startObject("mainDesc")
			           		.field("type", "string")
			           		.field("store", "no")
			           		.field("index", "no")
			           .endObject()  
			           
			             .startObject("titleUrl")
			           		.field("type", "string")
			           		.field("store", "no")
			           		.field("index", "no")
			           .endObject()  
			           
			         .endObject()  
			        .endObject()  
			      .endObject();  
		return mapping;
	}
}
