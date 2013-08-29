package com.donlian.es;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;

import com.donlian.util.Utils;

/**
 * 测试模板
 * @author lidongliang
 *
 */
public class ESTmpl {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Settings settings = ImmutableSettings.settingsBuilder()
		.put("cluster.name", "lidl")
		.put("client.transport.sniff", true).build();
		Client client = new TransportClient(settings);
		
		String json = Utils.toJson(new User());
		IndexResponse response = client.prepareIndex("twitter", "tweet")
        .setSource(json)
        .execute()
        .actionGet();
		
		System.out.println(Utils.toJson(response));
		client.close();
	}
}
