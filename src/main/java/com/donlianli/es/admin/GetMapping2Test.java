package com.donlianli.es.admin;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.client.Client;
import org.elasticsearch.cluster.ClusterState;
import org.elasticsearch.cluster.metadata.IndexMetaData;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.cluster.metadata.MetaData;

import com.donlianli.es.ESUtils;
/**
 * 查询索引下面所有的mapping
 * @author donlianli@126.com
 */
public class GetMapping2Test {

	public static void main(String[] args) {
//		String json = getMapping("goods_city_1");
		getClusterMapping();
	}

	public static String getMapping(String string) {
		String indexName = "goods_city_1";
		Client client = ESUtils.getClient();
		ClusterState cs = client.admin().cluster().prepareState()
				.setFilterIndices(indexName)
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
		return null;
	}
	
	public static String getClusterMapping() {
		Client client = ESUtils.getClient();
		ClusterState cs = client.admin().cluster().prepareState()
				.execute().actionGet().getState();
		MetaData meta = cs.getMetaData();	
		Map<String, IndexMetaData> indexMap = meta.indices();
		for(Map.Entry<String, IndexMetaData> iEntry : indexMap.entrySet()){
			//索引名称
			String indexName = iEntry.getKey();
			System.out.println("indexName:"+indexName);
			IndexMetaData imd = iEntry.getValue();
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
		return null;
	}
	
	public static List<String> getAllIndexes() {
		Client client = ESUtils.getClient();
		ClusterState cs = client.admin().cluster().prepareState()
				.execute().actionGet().getState();
		MetaData meta = cs.getMetaData();	
		Map<String, IndexMetaData> indexMap = meta.indices();
		List<String> indexList = new ArrayList<String>();
		for(Map.Entry<String, IndexMetaData> iEntry : indexMap.entrySet()){
			//索引名称
			String indexName = iEntry.getKey();
			indexList.add(indexName);
		}
		return indexList;
	}
}
