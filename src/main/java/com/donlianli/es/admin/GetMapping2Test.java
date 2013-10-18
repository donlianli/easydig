package com.donlianli.es.admin;


import java.io.IOException;
import java.util.Map;

import org.elasticsearch.client.Client;
import org.elasticsearch.cluster.ClusterState;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.cluster.metadata.MappingMetaData;

import com.donlianli.es.ESUtils;
/**
 * 查询索引下面所有的mapping
 * @author donlianli@126.com
 */
public class GetMapping2Test {

	public static void main(String[] args) {
		String indexName = "goods_city_1";
		Client client = ESUtils.getClient();
		ClusterState cs = client.admin().cluster().prepareState()
				.setFilterIndices()
				.execute().actionGet().getState();
		IndexMetaData imd = cs.getMetaData().index(indexName);	
		Map<String, MappingMetaData> typeMap = imd.mappings();
		for(Map.Entry<String, MappingMetaData> entry : typeMap.entrySet()){
			//type名称
			String typeName = entry.getKey();
			MappingMetaData typeDesc = entry.getValue();
			try {
				//这个本身就是一个json格式
				System.out.println(typeName+":"+typeDesc.getSourceAsMap().toString());
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
	}
}
