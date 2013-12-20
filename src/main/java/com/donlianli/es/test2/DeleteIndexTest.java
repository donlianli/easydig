package com.donlianli.es.test2;

import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.client.Client;

import com.donlianli.es.ESUtils;

public class DeleteIndexTest {
	public static void main(String[] args) {
		Client client = ESUtils.getClient();
		DeleteIndexResponse delete = client.admin().indices().
				delete(new DeleteIndexRequest("test")).actionGet();
		if (!delete.isAcknowledged()) {
		   System.out.println("Index wasn't deleted");
		}
	}
}
