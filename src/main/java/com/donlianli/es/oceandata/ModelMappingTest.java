package com.donlianli.es.oceandata;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.ImmutableSettings.Builder;
import org.elasticsearch.common.xcontent.XContentBuilder;

import com.donlianli.es.ESUtils;
/**
 * 创建code的mapping
 * @author donlianli@126.com
 */
public class ModelMappingTest {
	static final String INDEX_NAME="code";
	static final String TYPE_NAME="oceandata";
	
	public static void  main(String[] argv) throws Exception{
		Client client = ESUtils.getCodeClient();
		Builder settings = ImmutableSettings.settingsBuilder()
                .loadFromSource(getAnalysisSettings());
		//首先创建索引库
		CreateIndexResponse  indexresponse = client.admin().indices()
		//这个索引库的名称还必须不包含大写字母
		.prepareCreate(INDEX_NAME).setSettings(settings)
		//这里直接添加type的mapping
		.addMapping(TYPE_NAME, getMapping())
		.execute().actionGet();
		
		System.out.println("success:"+indexresponse.isAcknowledged());
	}
	private static String getAnalysisSettings() throws Exception {
		XContentBuilder mapping = jsonBuilder()  
			       .startObject()  
			       //主分片数量
			       .field("number_of_shards",5)
			       .field("number_of_replicas",0)
			         .startObject("analysis")  
			         	.startObject("filter")
			         		//创建分词过滤器
			         		.startObject("pynGram")
			         			.field("type","nGram")
			         			//从1开始
			         			.field("min_gram",1)
			         			.field("max_gram",15)
			         		.endObject()
			         	.endObject()	
			         	
			         	.startObject("analyzer")
			         			//拼音analyszer
			         			.startObject("pyAnalyzer")
			         			.field("type","custom")
			         			.field("tokenizer","standard")
			         			.field("filter", new String[]{ "lowercase","pynGram"})
			         			.endObject()
			         	.endObject()	
			        .endObject()  
			      .endObject();  
		System.out.println(mapping.string());
		return mapping.string();
	}
	/**
	 * mapping 一旦定义，之后就不能修改。
	 * @return
	 * @throws Exception
	 */
	private static XContentBuilder getMapping() throws Exception{
		XContentBuilder mapping = jsonBuilder()  
			       .startObject()  
			         .startObject("icd")  
			         //指定分词器
			         .field("index_analyzer","pyAnalyzer")
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
			           		.field("store", "yes")
			           		.field("index", "no")
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
