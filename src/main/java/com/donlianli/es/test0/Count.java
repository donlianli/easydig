package com.donlianli.es.test0;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import org.elasticsearch.action.count.CountRequestBuilder;
import org.elasticsearch.action.count.CountResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;
/**
 * 测试count
 * @author lidongliang
 */
public class Count {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Node node = nodeBuilder().clusterName("lidl").client(true).node();
		Client client = node.client();
		/**
		 * count时候只能使用query模式
		 */
		BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
		queryBuilder.must(QueryBuilders.termQuery("married", false));	
		CountRequestBuilder countBuilder = client.prepareCount("twitter").setTypes("tweet");
		countBuilder.setQuery(queryBuilder);
		long beginTime = System.currentTimeMillis();
		CountResponse response = countBuilder.execute().actionGet();
		long totalTime = System.currentTimeMillis() - beginTime;
		System.out.println("count:"+response.getCount());
		System.out.println("useTime:"+totalTime);
		node.close();
	}
}
