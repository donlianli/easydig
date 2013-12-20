package com.donlianli.es.admin;

import org.elasticsearch.client.Client;

import com.donlianli.es.ESUtils;

public class DelIndexTest {
	public static void  main(String[] argv) throws Exception{
		Client client = ESUtils.getClient();
		client.admin().indices()
		//这个索引库的名称还必须不包含大写字母
		.prepareDelete("testindex").execute().actionGet();
	}
}
