package com.donlianli.es.oceandata;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import com.donlianli.es.ESUtils;

public class SearchTest {
	public static void main(String[] args) {
		Client client = ESUtils.getCodeClient();
		String keyWord = "阿莫西林";
		//多个字段匹配
		MultiMatchQueryBuilder query = QueryBuilders.multiMatchQuery(keyWord, "firstKey","secondKey","thirdKey");
		
		long b = System.currentTimeMillis();
		SearchResponse response = client.prepareSearch("code").setTypes("oceandata")
				.setQuery(query)
				.setFrom(0)
				//前20个
				.setSize(20)
				.execute().actionGet();
		long useTime = System.currentTimeMillis()-b;
		System.out.println("search use time:" + useTime + " ms");
		
		SearchHits shs = response.getHits();
		for (SearchHit hit : shs) {
			System.out.println("分数:" 
					+ hit.getScore()
					+ ",ID:"
					+ hit.getId()
					+ " obj="+ hit.getSourceAsString());
		}
		client.close();
	}
}
