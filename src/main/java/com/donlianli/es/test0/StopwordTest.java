package com.donlianli.es.test0;

import org.elasticsearch.client.Client;

import com.donlianli.es.test0.ESUtils;


public class StopwordTest {
	public static void main(String[] argv){
		Client esClient = ESUtils.getClient();
		esClient.admin().indices().prepareDelete(ESUtils.getIndexName()).execute().actionGet();
	}
}
