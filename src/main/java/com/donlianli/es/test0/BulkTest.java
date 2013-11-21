package com.donlianli.es.test0;

import java.security.SecureRandom;

import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequestBuilder;
import org.elasticsearch.client.Client;

import com.donlian.util.RandomData;
import com.donlian.util.Utils;

/**
 * @author lidongliang
 *
 */
public class BulkTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Client client = ESUtils.getClient();
		BulkRequestBuilder bulkRequest = client.prepareBulk();
		User user = new User();
		for(int i=20000;i<40001;i++){
			user.setName(RandomData.randomName("user_"));
			SecureRandom random = new SecureRandom();
			long l = Math.abs(random.nextLong());
			user.setWeight(l);
			user.setMarried(l%1==0?true:false);
			user.setAge(l%2==0?28:82);
			IndexRequestBuilder ir = client.prepareIndex(ESUtils.getIndexName(),
					ESUtils.getTypeName(), String.valueOf(i)).setSource(Utils.toJson(user));
			bulkRequest.add(ir);
		}
		long beginTime = System.currentTimeMillis();
		BulkResponse bulkResponse = bulkRequest.execute().actionGet();
		long useTime = System.currentTimeMillis() - beginTime;
		//1406ms
		System.out.println("useTime:" + useTime);
		if (bulkResponse.hasFailures()) {
		    // process failures by iterating through each bulk response item
		}
	}
}
