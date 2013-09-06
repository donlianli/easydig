package com.donlianli.es.test2;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;

import com.donlianli.es.ESUtils;
import com.donlianli.es.model.FacetTestModel;

public class BulkIndexTest {
	
	public static void main(String[] args) {
		Client client = ESUtils.getClient();
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		for(int i=0;i<10;i++){
			String json = ESUtils.toJson(new FacetTestModel());
			IndexRequestBuilder indexRequest = client.prepareIndex("test", "test")
			//指定不重复的ID		
	        .setSource(json).setId(String.valueOf(i));
			//添加到builder中
			bulkRequest.add(indexRequest);
		}
		
		BulkResponse bulkResponse = bulkRequest.execute().actionGet();
		if (bulkResponse.hasFailures()) {
			System.out.println(bulkResponse.buildFailureMessage());
		}
	}
}
