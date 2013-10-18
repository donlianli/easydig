package com.donlianli.es.admin;

import java.util.List;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.SearchHit;

import com.donlianli.es.ESUtils;
/**
 * 单shard，目录文件大小：5.3M	
 * size为5的时候，需要时间74697 ms
 * size为50的时候，需要时间36981  ms
 * size为1000的时候，需要时间35095  ms
 * size为1000的时候，需要时间23178  ms
 * size为10000的时候，需要时间29854  ms
 * size为5000的时候，需要时间24341  ms
 * 2013年10月18日
 * @author donlianli@126.com
 */
public class BackupTest {
	public static final String backupDir = "d:/temp/es";
	public static void main(String[] args) {
//		backupMapping("goods_city_1");
//		backupDoc("goods_city_1");
		backupAll();
	}
	/**
	 * 备份结构定义
	 * @param string
	 */
	public static void backupAll() {
		//备份所有的mapping,并返回index列表
		List<String> indexes = GetMapping2Test.getAllIndexes();
		for(String indexName : indexes){
			backupDoc(indexName);
			System.out.println(indexName + " backup over");
		}
	}
	
	/**
	 * 备份结构定义
	 * @param string
	 */
	public static void backupMapping(String indexName) {
		//获得所有的index下面的mapping
		//备份mapping的名称和结构定义
		String mapping = GetMapping2Test.getMapping(indexName);
	}
	public static void backupDoc(String indexName){
		long startTime = System.currentTimeMillis();
		Client esClient = ESUtils.getClient();
		SearchResponse searchResponse = esClient.prepareSearch(indexName)
				// 加上这个据说可以提高性能，但第一次却不返回结果
				.setSearchType(SearchType.SCAN)
				// 实际返回的数量为5*index的主分片格式
				.setSize(1000)
				// 这个游标维持多长时间
				.setScroll(TimeValue.timeValueMinutes(8))
				.execute().actionGet();
		// 第一次查询，只返回数量和一个scrollId
		long docCount = searchResponse.getHits().getTotalHits();
		long fetchCount = 0;
		System.out.println("total:" + docCount);
		// 第一次运行没有结果
		for (SearchHit hit : searchResponse.getHits()) {
			System.out.println(hit.getSourceAsString());
		}
		System.out.println("------------------------------");
		int failedCount=0;
		while (fetchCount < docCount && failedCount<3) {
			// 只要取不够，一直取
			// 使用上次的scrollId继续访问
			searchResponse = esClient
					.prepareSearchScroll(searchResponse.getScrollId())
					.setScroll(TimeValue.timeValueMinutes(8)).execute()
					.actionGet();
			if(searchResponse.getHits().hits().length==0){
				failedCount++;
			}
			else {
				// System.out.println(searchResponse.getHits().getTotalHits());
				fetchCount += searchResponse.getHits().hits().length;
				if (fetchCount % 1000 == 0) {
					System.out.println(fetchCount + " fetched");
				}
				for (SearchHit hit : searchResponse.getHits()) {
					// 打印数据或输出到文件
//					 System.out.println(hit.getType());
//					 System.out.println(hit.getSourceAsString());
				}
			}
		}
		System.out.println("fetch count:" + fetchCount);
		System.out.println("useTime:"
				+ (System.currentTimeMillis() - startTime));
	}
	
}
