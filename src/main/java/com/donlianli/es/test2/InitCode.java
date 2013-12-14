package com.donlianli.es.test2;


import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;

import com.donlianli.es.ESUtils;
import com.donlianli.es.MongoUtil;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class InitCode {
	
	public static void main(String[] args) {
		Client client = ESUtils.getClient();
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		
		DBCollection col = MongoUtil.getCollectionByName("oceanData");
		DBCursor cursor = col.find();
		int n=0;
		long lastTime = System.currentTimeMillis();
		while(cursor.hasNext()){
			n ++;
			DBObject obj = cursor.next();
			String json = ESUtils.toJson(obj);
			IndexRequestBuilder indexRequest = client.prepareIndex("code", "oceanData")
			//指定不重复的ID		
	        .setSource(json).setId((String)obj.get("_id"));
			//添加到builder中
			bulkRequest.add(indexRequest);
			if(n%1000 == 0){
				 bulkRequest.execute().actionGet();
				System.out.println(n+" indexed,use Time:" + (System.currentTimeMillis()-lastTime));
				 lastTime = System.currentTimeMillis();
			}
			
			if(n%10000 == 0){
				break;
			}
		}
	}
}
