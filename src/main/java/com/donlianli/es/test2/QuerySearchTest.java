package com.donlianli.es.test2;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import com.donlianli.es.ESUtils;

public class QuerySearchTest {
	public static void main(String[] args) {
		Client client = ESUtils.getClient();
		QueryBuilder query = QueryBuilders.fieldQuery("desc", "小儿颗粒");
		SearchResponse response = client.prepareSearch("twitter")
				.setTypes("tweet")
				//设置查询条件,
		        .setQuery(query)
		        .setFrom(0).setSize(60)
		        .execute()
		        .actionGet();
		/**
		 * SearchHits是SearchHit的复数形式，表示这个是一个列表
		 */
		SearchHits shs = response.getHits();
		for(SearchHit hit : shs){
			System.out.println("分数(score):"+hit.getScore()+", 业务描述(desc):"+
					hit.getSource().get("desc"));
		}
		client.close();
	}

}
