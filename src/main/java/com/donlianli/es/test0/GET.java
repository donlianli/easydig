package com.donlianli.es.test0;

import static org.elasticsearch.node.NodeBuilder.nodeBuilder;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
/**
 * 测试模板
 * @author lidongliang
 *
 */
public class GET {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Node node = nodeBuilder().clusterName("lidl").node();
		Client client = node.client();
		GetResponse response = client.prepareGet("twitter", "tweet", "1")
		 .setOperationThreaded(false)
        .execute()
        .actionGet();
		System.out.println(response.getId());
		System.out.println(response.getSourceAsString());
		System.out.println(response.getVersion());
		System.out.println(response.getSource());
		node.close();
	}
}
