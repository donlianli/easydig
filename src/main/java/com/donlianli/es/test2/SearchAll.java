package com.donlianli.es.test2;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import com.donlianli.es.ESUtils;

public class SearchAll {
	public static void main(String[] args) {
		Client client = ESUtils.getClient();
		SearchResponse response = client.prepareSearch("test")
				.setTypes("test")
				.setFilter(FilterBuilders.matchAllFilter())
				//这个size还不能设置的太大
		        .setFrom(0).setSize(1000)
		        .execute()
		        .actionGet();
		SearchHits shs = response.getHits();
		for(SearchHit hit : shs){
			System.out.println(hit.getSource());
		}
		client.close();
	}

}
