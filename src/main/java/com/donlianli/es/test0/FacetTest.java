package com.donlianli.es.test0;

import java.util.Map;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.BoolFilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.search.facet.Facet;
import org.elasticsearch.search.facet.FacetBuilder;
import org.elasticsearch.search.facet.FacetBuilders;
import org.elasticsearch.search.facet.Facets;
import org.elasticsearch.search.facet.terms.TermsFacet;
/**
 * Facet测试,即聚合测试
 * @author lidongliang
 *
 */
public class FacetTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Settings settings = ImmutableSettings.settingsBuilder()
		.put("cluster.name", "lidl")
		.put("client.transport.sniff", true).build();
		Client client = new TransportClient(settings)
		.addTransportAddress(new InetSocketTransportAddress("10.9.120.68", 9300));

		//过滤器
		BoolFilterBuilder filter = FilterBuilders.boolFilter();
		filter.must(FilterBuilders.rangeFilter("age").from(0).to(200));	
		//请求
		SearchRequestBuilder searchBuilder = client.prepareSearch("twitter").setTypes("tweet");
		//分组条件
		FacetBuilder marriedFacet = FacetBuilders.termsFacet("married").field("married").allTerms(true);;
		marriedFacet.facetFilter(filter);
		searchBuilder.addFacet(marriedFacet);
		long beginTime = System.currentTimeMillis();
		SearchResponse response = searchBuilder
        //设置过滤条件
        .execute()
        .actionGet();
		Facets facets = response.getFacets();
		Map<String, Facet> map = facets.getFacets();
		
		Facet facet = map.get("married");
		TermsFacet mFacet = (TermsFacet)facet;
		for(TermsFacet.Entry entry : mFacet.getEntries()){
    		System.out.println("key:" +entry.getTerm().toString() 
    				+ " count:" + entry.getCount());
    	}
		long total = System.currentTimeMillis() - beginTime;
		System.out.println("useTime:"+total);
	}
}
