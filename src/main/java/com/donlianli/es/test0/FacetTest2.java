package com.donlianli.es.test0;

import java.util.Map;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
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
public class FacetTest2 {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Settings settings = ImmutableSettings.settingsBuilder()
		.put("cluster.name", "lidl")
		.put("client.transport.sniff", true).build();
		Client client = new TransportClient(settings)
		.addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
		//请求
		SearchRequestBuilder searchBuilder = client.prepareSearch("twitter").setTypes("tweet");
		//分组条件
		FacetBuilder marriedFacet = FacetBuilders.termsFacet("age").field("age").allTerms(true);;
		searchBuilder.addFacet(marriedFacet);
		long beginTime = System.currentTimeMillis();
		SearchResponse response = searchBuilder
        //设置过滤条件
        .execute()
        .actionGet();
		Facets facets = response.getFacets();
		Map<String, Facet> map = facets.getFacets();
		
		Facet facet = map.get("age");
		TermsFacet mFacet = (TermsFacet)facet;
		for(TermsFacet.Entry entry : mFacet.getEntries()){
    		System.out.println("key:" +entry.getTerm().toString() + " count:" + entry.getCount());
    	}
		long total = System.currentTimeMillis() - beginTime;
		System.out.println("useTime:"+total);
	}
}
