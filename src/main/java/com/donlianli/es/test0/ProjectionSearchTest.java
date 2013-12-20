package com.donlianli.es.test0;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.FieldQueryBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
/**
 * 测试模板
 * @author lidongliang
 *
 */
public class ProjectionSearchTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Settings settings = ImmutableSettings.settingsBuilder()
		.put("cluster.name", "lidl")
		.put("client.transport.sniff", true).build();
		Client client = new TransportClient(settings)
		.addTransportAddress(new InetSocketTransportAddress("10.9.120.68", 9300));

		FieldQueryBuilder fqb = QueryBuilders.fieldQuery("married", true);
		
		SearchResponse response = client.prepareSearch("twitter")
        .setTypes("tweet")
        .setSearchType(SearchType.QUERY_AND_FETCH)
        //设置查询条件
        .setQuery(fqb)             // Query
        .addField("age")
        //设置过滤条件
        .setFilter(FilterBuilders.rangeFilter("weight").from(131834924088936899l).to(2469485369926853902l))   // Filter
        //设置分页信息
        .setFrom(0).setSize(50)
        .setExplain(true)
        .execute()
        .actionGet();
		SearchHits sh = response.getHits();
		for(SearchHit shit : sh){
			System.out.println(shit.getId()+",age:" + shit.field("age").value());
		}
		
		client.close();
	}
}
