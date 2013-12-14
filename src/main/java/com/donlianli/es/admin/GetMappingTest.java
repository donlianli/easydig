package com.donlianli.es.admin;

import org.elasticsearch.client.Client;
import org.elasticsearch.cluster.ClusterState;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.cluster.metadata.MappingMetaData;

import com.donlianli.es.ESUtils;
public class GetMappingTest {
	public static void  main(String[] argv) throws Exception{
		Client client = ESUtils.getClient();
		
		ClusterState cs = client.admin().cluster().prepareState()
				//索引库名称为testIndex
				.setFilterIndices("test").execute().actionGet().getState();
		
		IndexMetaData imd = cs.getMetaData()
				//这个名称同上面的名称
				.index("test");
		//type的名称
		MappingMetaData mdd = imd.mapping("test");
		System.out.println(mdd.sourceAsMap());
	}
}
