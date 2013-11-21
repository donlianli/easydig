package com.donlianli.es.test0;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

public class ESUtils {
	public static Client getClient() {
		Settings settings = ImmutableSettings.settingsBuilder()
		// 设置集群名称
		.put("cluster.name", "elasticsearch").build();
		Client client = new TransportClient(settings)
				.addTransportAddress(new InetSocketTransportAddress("10.8.210.165", 9300));
//				.addTransportAddress(new InetSocketTransportAddress("10.8.210.188", 9300));
		return client;
	}

	public static String getIndexName() {
		return "index1";
	}

	public static String getTypeName() {
		return "type1";
	}
}
