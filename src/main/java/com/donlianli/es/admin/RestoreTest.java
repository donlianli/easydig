package com.donlianli.es.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsRequest;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequestBuilder;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

public class RestoreTest {
	private static List<Map<String,String>> dataList = new ArrayList<Map<String,String>>();
	public static void bulkIndex(String indexName, String typeName,
			String docId, String docSource) {
		Map<String,String> map = new HashMap<String,String>();
		map.put("indexName", indexName);
		map.put("typeName", typeName);
		map.put("docId", docId);
		map.put("docSource", docSource);
		dataList.add(map);
	}
	public static void flushData() {
		Client client = getRestoreClient();
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		for(Map<String,String> map : dataList){
			IndexRequestBuilder indexRequest = client.prepareIndex(map.get("indexName"),map.get("typeName"))
					//指定不重复的ID		
			        .setSource(map.get("docSource")).setId(map.get("docId"));
					//添加到builder中
					bulkRequest.add(indexRequest);
		}
		BulkResponse bulkResponse = bulkRequest.execute().actionGet();
		if (bulkResponse.hasFailures()) {
		    // process failures by iterating through each bulk response item
			System.out.println(bulkResponse.buildFailureMessage());
		}
		else {
			System.out.println(dataList.size()+" bulk indexed");
		}
		dataList.clear();
	}
	public static Client getRestoreClient(){
		Settings settings = ImmutableSettings.settingsBuilder()
				//指定集群名称
                .put("cluster.name", "elasticsearch")
                //探测集群中机器状态
                .put("client.transport.sniff", false).build();
		Client client = new TransportClient(settings)
		.addTransportAddress(new InetSocketTransportAddress("10.9.23.121", 9300));
		return client;
	}
	
	public static void restoreMapping(String indexName, String typeName,
			String mapping) {
		Client client = getRestoreClient();
		IndicesExistsResponse respone = client.admin().indices().exists(new IndicesExistsRequest(indexName)).actionGet();
		if(!respone.isExists()){
			CreateIndexResponse  indexresponse = client.admin().indices()
					//这个索引库的名称还必须不包含大写字母
					.prepareCreate(indexName).execute().actionGet();
					System.out.println(indexresponse.isAcknowledged());;
		}
		//如果是在两台机器上，下面直接putMapping可能会报异常
		PutMappingRequestBuilder builder = client.admin().indices().preparePutMapping(indexName);
		//testType就像当于数据的table
		builder.setType(typeName);
		builder.setSource(mapping);
		PutMappingResponse  response = builder.execute().actionGet();
		System.out.println(response.isAcknowledged());
	}
}
