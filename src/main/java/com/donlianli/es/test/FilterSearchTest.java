package com.donlianli.es.test;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import com.donlianli.es.ESUtils;

public class FilterSearchTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Client client = ESUtils.getClient();
		/**
		 * 创建查询条件，FilterBuilders相当于Hibernate的Restrictions，
		 * 而FilterBuilder则相当于一个Criteria,可以不停的增加本身对象
		 */
		SearchResponse response = client.prepareSearch("twitter")
				//这个在prepareSearch中指定还不行，必须使用setTypes
				.setTypes("tweet")
				//设置查询条件,
		        .setFilter(FilterBuilders.matchAllFilter())
		        .setFrom(0).setSize(1000)
		        .execute()
		        .actionGet();
		/**
		 * SearchHits是SearchHit的复数形式，表示这个是一个列表
		 */
		SearchHits shs = response.getHits();
		for(SearchHit hit : shs){
			System.out.println("id:"+hit.getId()+":"+hit.getSourceAsString());
		}
		client.close();
	}

}
