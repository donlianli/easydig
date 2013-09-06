package com.donlianli.es.test2;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.search.facet.FacetBuilders;
import org.elasticsearch.search.facet.Facets;
import org.elasticsearch.search.facet.range.RangeFacet;
import org.elasticsearch.search.facet.range.RangeFacetBuilder;

import com.donlianli.es.ESUtils;

public class GroupTest2 {
	public static void  main(String[] argv){
		Client client = ESUtils.getClient();
		RangeFacetBuilder facetBuilder = FacetBuilders.rangeFacet("idName");
		facetBuilder.field("id")
		.addUnboundedTo(1)
		.addUnboundedFrom(1);
		facetBuilder.facetFilter(FilterBuilders.matchAllFilter());
		SearchResponse response = client.prepareSearch("test")
				.setTypes("test")
				.addFacet(facetBuilder)
		        .setFilter(FilterBuilders.matchAllFilter())
		        .execute()
		        .actionGet();
		Facets f = response.getFacets();
		//跟上面的名称一样
		RangeFacet facet = (RangeFacet)f.getFacets().get("idName");
		for(RangeFacet.Entry tf :facet.getEntries()){
			System.out.println("from:"+tf.getFromAsString()+ " to:"+ tf.getToAsString()+
					"\t;数量:\t" + tf.getCount());
		}
		client.close();
	}
}
