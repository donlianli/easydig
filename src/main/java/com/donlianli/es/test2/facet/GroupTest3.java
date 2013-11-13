package com.donlianli.es.test2.facet;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.search.facet.FacetBuilders;
import org.elasticsearch.search.facet.Facets;
import org.elasticsearch.search.facet.terms.TermsFacet;
import org.elasticsearch.search.facet.terms.TermsFacetBuilder;

import com.donlianli.es.ESUtils;

public class GroupTest3 {
	public static void  main(String[] argv){
		Client client = ESUtils.getClient();
		TermsFacetBuilder facetBuilder = FacetBuilders.termsFacet("catIdName");
		facetBuilder.field("catIds").size(Integer.MAX_VALUE);
		facetBuilder.facetFilter(FilterBuilders.matchAllFilter());
		SearchResponse response = client.prepareSearch(BulkIndexTest.INDEX_NAME)
				.setTypes(BulkIndexTest.TYPE_NAME)
				.addFacet(facetBuilder)
		        .setFilter(FilterBuilders.matchAllFilter())
		        .execute()
		        .actionGet();
		Facets f = response.getFacets();
		//跟上面的名称一样
		TermsFacet facet = (TermsFacet)f.getFacets().get("catIdName");
		for(TermsFacet.Entry tf :facet.getEntries()){
			System.out.println("键:"+tf.getTerm()+"\t;数量:\t" + tf.getCount());
		}
		client.close();
	}
}
