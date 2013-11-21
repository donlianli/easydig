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
public class MaxFacetTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Client client = ESUtils.getClient();
		//请求
		SearchRequestBuilder searchBuilder = client.prepareSearch(ESUtils.getIndexName()).setTypes(
				ESUtils.getTypeName());
		//分组条件
		FacetBuilder marriedFacet = FacetBuilders.termsFacet("age").field("age").allTerms(true);
		marriedFacet.facetFilter(FilterBuilders.termFilter("married", false));
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
    		System.out.println("key:" +entry.getTerm().toString() 
    				+ " count:" + entry.getCount());
    	}
		long total = System.currentTimeMillis() - beginTime;
		System.out.println("useTime:"+total);
	}
}
