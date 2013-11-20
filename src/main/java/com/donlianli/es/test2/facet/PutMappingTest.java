package com.donlianli.es.test2.facet;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;

import com.donlianli.es.ESUtils;
/**
 * 创建索引时，指定分片及副本
 * 并且创建type一起执行
 * @author donlianli@126.com
 */
public class PutMappingTest {
	public static void  main(String[] argv) throws Exception{
		Client client = ESUtils.getClient();
		Settings settings = ImmutableSettings.settingsBuilder()
				//5个主分片
                .put("number_of_shards", 2)
                //测试环境，减少副本提高速度
                .put("number_of_replicas", 0).build();
		//首先创建索引库
		CreateIndexResponse  indexresponse = client.admin().indices()
		//这个索引库的名称还必须不包含大写字母
		.prepareCreate("testindex").setSettings(settings)
		//这里直接添加type的mapping
		.addMapping("testType", getMapping())
		.execute().actionGet();
		System.out.println(indexresponse.isAcknowledged());
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
			           		.field("store", false)
			           		.field("index", "not_analyzed")
			           		.field("include_in_all", false)
			           	.endObject()    
			           	
			           .startObject("type")
			           		.field("type", "string")
			           		.field("store", true)
			           		.field("index", "no")
			           		.field("include_in_all",false)
			           	.endObject()  
			           	//新增字段
			           	 .startObject("title")
			           		.field("type", "string")
			           		.field("store", "yes")
			           		.field("index", "analyzed")
			           		.field("include_in_all", false)
			           	.endObject()  
			           	
			           .startObject("catIds")
			           		.field("type", "integer")
			           		.field("store", false)
			           		.field("index", "not_analyzed")
			           		.field("include_in_all", false)
			           .endObject()  
			         .endObject()  
			        .endObject()  
			      .endObject();  
		return mapping;
	}
}
