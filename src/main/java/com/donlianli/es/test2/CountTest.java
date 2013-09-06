package com.donlianli.es.test2;

import org.elasticsearch.action.count.CountRequestBuilder;
import org.elasticsearch.action.count.CountResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import com.donlianli.es.ESUtils;

public class CountTest {
	public static void  main(String[] argv){
		Client client = ESUtils.getClient();
		// 查询systemName=oa的数量
		QueryBuilder query = QueryBuilders.fieldQuery("systemName", "oa");
		CountRequestBuilder count = client.prepareCount("twitter")
		.setTypes("tweet");
		count.setQuery(query);
		
		CountResponse response = count.execute().actionGet();
		
		System.out.println("count="+response.getCount());
	}
}
