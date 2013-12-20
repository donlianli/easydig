package com.donlianli.es.admin;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.xcontent.XContentBuilder;

import com.donlianli.es.ESUtils;
/**
 * 在原有的type上面增加一个字段
 * @author donlian
 * 2013年8月8日
 */
public class AddFieldTest {
	public static void  main(String[] argv) throws Exception{
		Client client = ESUtils.getClient();
		//如果是在两台机器上，下面直接putMapping可能会报异常
		PutMappingRequestBuilder builder = client.admin().indices().preparePutMapping("testindex");
		//testType就像当于数据的table
		builder.setType("testType");
		XContentBuilder mapping = getMapping();
		builder.setSource(mapping);
		PutMappingResponse  response = builder.execute().actionGet();
		System.out.println(response.isAcknowledged());
	}
	/**
	 * mapping 一旦定义，之后就不能修改。
	 * @return
	 * @throws Exception
	 */
	private static XContentBuilder getMapping() throws Exception{
		XContentBuilder mapping = jsonBuilder()  
			       .startObject()  
			         .startObject("test")  
			         .startObject("properties")         
			           .startObject("id")
			           		.field("type", "long")
			           		.field("store", "yes")
			           	.endObject()    
			           	
			           .startObject("type")
			           		.field("type", "string")
			           		.field("index", "not_analyzed")
			           	.endObject()  
			           	//增加字段
			           	.startObject("firstKeyword")
			           		.field("type", "string")
			           		.field("index", "analyzed")
			           	.endObject()  
			           	
			           .startObject("catIds")
			           		.field("type", "integer")
			           .endObject()  
			         .endObject()  
			        .endObject()  
			      .endObject();  
		return mapping;
	}
}
