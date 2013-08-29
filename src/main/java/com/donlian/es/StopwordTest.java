package com.donlian.es;

import org.elasticsearch.client.Client;

import com.donlian.es.ESUtils;


public class StopwordTest {
	public static void main(String[] argv){
		Client esClient = ESUtils.getClient();
		esClient.admin().indices().prepareDelete(ESUtils.getIndexName()).execute().actionGet();
	}
}
