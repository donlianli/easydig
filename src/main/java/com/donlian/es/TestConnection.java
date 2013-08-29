package com.donlian.es;

import org.elasticsearch.action.admin.cluster.health.ClusterHealthRequestBuilder;
import org.elasticsearch.action.admin.cluster.health.ClusterHealthResponse;
import org.elasticsearch.action.admin.cluster.state.ClusterStateRequestBuilder;
import org.elasticsearch.action.admin.cluster.state.ClusterStateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
/**
 * 测试是否可以连接上ES
 * @author lidongliang
 */
public class TestConnection {
	public static void main(String[] args) {
		Settings settings = ImmutableSettings.settingsBuilder()
		//设置集群名称
        .put("cluster.name", "lidl").build();
		
		Client client = new TransportClient(settings)
        .addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
//		.addTransportAddress(new InetSocketTransportAddress("10.9.210.29", 9301))
//		.addTransportAddress(new InetSocketTransportAddress("10.9.210.29", 9302));
		
	
		ClusterStateRequestBuilder builder = client.admin().cluster().prepareState();
		ClusterStateResponse response = builder.execute().actionGet();
		System.out.println(response.getState());
		
		ClusterHealthRequestBuilder cbuilder = client.admin().cluster().prepareHealth("twister");
		ClusterHealthResponse r = cbuilder.execute().actionGet();
		System.out.println(r.getStatus());
		client.close();
	}
}
