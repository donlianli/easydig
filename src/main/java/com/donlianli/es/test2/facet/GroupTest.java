package com.donlianli.es.test2.facet;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.search.facet.FacetBuilders;
import org.elasticsearch.search.facet.Facets;
import org.elasticsearch.search.facet.terms.TermsFacet;
import org.elasticsearch.search.facet.terms.TermsFacetBuilder;

import com.donlianli.es.ESUtils;

public class GroupTest {
	public static void  main(String[] argv){
		Client client = ESUtils.getClient();
		TermsFacetBuilder facetBuilder = FacetBuilders.termsFacet("typeFacetName");
		facetBuilder.field("type").size(Integer.MAX_VALUE);
		facetBuilder.facetFilter(FilterBuilders.matchAllFilter());
		SearchResponse response = client.prepareSearch(BulkIndexTest.INDEX_NAME)
				.setTypes(BulkIndexTest.TYPE_NAME)
				.addFacet(facetBuilder)
		        .setFilter(FilterBuilders.matchAllFilter())
		        .execute()
		        .actionGet();
		Facets f = response.getFacets();
		//跟上面的名称一样
		TermsFacet facet = (TermsFacet)f.getFacets().get("typeFacetName");
		for(TermsFacet.Entry tf :facet.getEntries()){
			System.out.println(tf.getTerm()+"\t:\t" + tf.getCount());
		}
		if(facet== null || facet.getTotalCount()<1){
			System.out.println("No facet");
		}
		client.close();
	}
}
