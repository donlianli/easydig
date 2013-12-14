package com.donlianli.es.test0;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.node.Node;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
/**
 * 测试模板
 * @author lidongliang
 *
 */
public class FilterSearchTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Node node = nodeBuilder().clusterName("lidl").client(true).node();
		BoolFilterBuilder filter = FilterBuilders.boolFilter();
		filter.must(FilterBuilders.termFilter("married", true));	
		filter.must(FilterBuilders.rangeFilter("weight").from(131834924088936899l).to(2469485369926853902l));
		
		Client client = node.client();
		long beginTime = System.currentTimeMillis();
		SearchRequestBuilder searchBuilder = client.prepareSearch("twitter").setTypes("tweet");
		SearchResponse response = searchBuilder.setSearchType(SearchType.QUERY_AND_FETCH)
        //设置过滤条件
        .setFilter(filter)   // Filter
        //设置分页信息
        .setFrom(0).setSize(20)
        .setExplain(true)
        .execute()
        .actionGet();
		SearchHits sh = response.getHits();
		long total = System.currentTimeMillis() - beginTime;
		int i=0;
		for(SearchHit shit : sh){
			i++;
			System.out.println(i+":"+shit.getSourceAsString());
		}
		node.close();
		System.out.println(total);
	}
}
