package com.donlianli.es.test;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;

import com.donlianli.es.ESUtils;
import com.donlianli.es.model.LogModel;

public class BulkIndexTest {
	public static void main(String[] args) {
		Client client = ESUtils.getCodeClient();
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		for(int i=1;i<100;i++){
			//业务对象
			String json = ESUtils.toJson(new LogModel());
			IndexRequestBuilder indexRequest = client.prepareIndex("twitter", "tweet")
			//指定不重复的ID		
	        .setSource(json).setId(String.valueOf(i));
			//添加到builder中
			bulkRequest.add(indexRequest);
		}
		
		BulkResponse bulkResponse = bulkRequest.execute().actionGet();
		if (bulkResponse.hasFailures()) {
		    // process failures by iterating through each bulk response item
			System.out.println(bulkResponse.buildFailureMessage());
		}
	}
}
