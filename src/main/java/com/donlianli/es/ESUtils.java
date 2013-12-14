package com.donlianli.es;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ESUtils {
	private static ObjectMapper objectMapper = new ObjectMapper();
	public static String toJson(Object o){
		try {
			return objectMapper.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "";
	}
	public static Client getClient(){
		Settings settings = ImmutableSettings.settingsBuilder()
				//指定集群名称
                .put("cluster.name", "wowogoods")
                //探测集群中机器状态
                .put("client.transport.sniff", true).build();
		/*
		 * 创建客户端，所有的操作都由客户端开始，这个就好像是JDBC的Connection对象
		 * 用完记得要关闭
		 */
		Client client = new TransportClient(settings)
		.addTransportAddress(new InetSocketTransportAddress("10.8.210.192", 9350));
//		.addTransportAddress(new InetSocketTransportAddress("192.168.1.107", 9300));
		return client;
	}
	
	public static Client getCodeClient(){
		Settings settings = ImmutableSettings.settingsBuilder()
				//指定集群名称
                .put("cluster.name", "code")
                //探测集群中机器状态
                .put("client.transport.sniff", false).build();
		/*
		 * 创建客户端，所有的操作都由客户端开始，这个就好像是JDBC的Connection对象
		 * 用完记得要关闭
		 */
		Client client = new TransportClient(settings)
		.addTransportAddress(new InetSocketTransportAddress("192.168.1.106", 9300))
		.addTransportAddress(new InetSocketTransportAddress("192.168.1.107", 9300));
		return client;
	}
}
