package com.donlian.es;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;

import com.donlian.util.Utils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 测试模板
 * @author lidongliang
 *
 */
public class Put {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Node node = nodeBuilder().clusterName("lidl").node();
		Client client = node.client();
		ObjectMapper mapper = Utils.getMapper();
		try {
			String json = mapper.writeValueAsString(new User());
			IndexResponse response = client.prepareIndex("twitter", "tweet","1")
	        .setSource(json)
	        .execute()
	        .actionGet();
			
			System.out.println(Utils.toJson(response));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		node.close();
	}
}
