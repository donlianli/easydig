package com.donlianli.es.oceandata;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.client.Client;

import com.donlianli.es.ESUtils;

public class DelType {
	public static void  main(String[] argv) throws Exception{
		Client client = ESUtils.getCodeClient();
		DeleteIndexResponse  repsone =client.admin().indices()
		.prepareDelete("code").execute().actionGet();
		System.out.println(repsone.isAcknowledged());
	}
}
