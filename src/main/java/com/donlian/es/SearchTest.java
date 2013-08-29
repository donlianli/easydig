package com.donlian.es;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.FieldQueryBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
/**
 * 测试模板
 * @author lidongliang
 *
 */
public class SearchTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Node node = nodeBuilder().clusterName("lidl").client(true).node();
		FieldQueryBuilder fqb = QueryBuilders.fieldQuery("married", true);
		
		Client client = node.client();
		SearchResponse response = client.prepareSearch("twitter")
        .setTypes("tweet")
        .setSearchType(SearchType.QUERY_AND_FETCH)
        //设置查询条件
        .setQuery(fqb)             // Query
        //设置过滤条件
        .setFilter(FilterBuilders.rangeFilter("weight").from(131834924088936899l).to(2469485369926853902l))   // Filter
        //设置分页信息
        .setFrom(0).setSize(10)
        .setExplain(true)
        .execute()
        .actionGet();
		SearchHits sh = response.getHits();
		for(SearchHit shit : sh){
			System.out.println(shit.getSourceAsString());
		}
		node.close();
	}
}
