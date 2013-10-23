package com.donlianli.es.admin;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.search.SearchHit;

import com.donlianli.es.ESUtils;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
/**
 * 单shard，目录文件大小：5.3M	
 * size为5的时候，需要时间74697 ms
 * size为50的时候，需要时间36981  ms
 * size为100的时候，需要时间35095  ms
 * size为1000的时候，需要时间23178  ms
 * size为10000的时候，需要时间29854  ms
 * size为5000的时候，需要时间24341  ms
 * 2013年10月18日
 * @author donlianli@126.com
 */
public class BackupTest {
	public static final String backupDir = "d:/temp/es";
	public static final int BULK_INDEX_SIZE =1000;
	public static void main(String[] args) throws Exception {
//		backupMapping("goods_city_1");
//		backupDoc("goods_city_1");
//		backupAll();
		long beginTime=System.currentTimeMillis();
		restore("goods_city_1");
		long useTime= System.currentTimeMillis()-beginTime;
		System.out.println("restore index use time :"+useTime);
	}
	public static String getBackupDataFile(String indexName){
		return backupDir+"/"+indexName+".docs";
	}
	public static String getBackupMapFile(String indexName){
		return backupDir+"/"+indexName+".map";
	}
	/**
	 * 备份结构定义
	 * @param string
	 */
	public static void backupAll() throws Exception {
		//备份所有的mapping,并返回index列表
		List<String> indexes = GetMapping2Test.getAllIndexes();
		for(String indexName : indexes){
			backupMapping(indexName);
			backupDoc(indexName);
			System.out.println(indexName + " backup over");
		}
	}
	
	/**
	 * 备份结构定义
	 * @param string
	 * @throws IOException 
	 */
	public static void backupMapping(String indexName) throws IOException {
		//获得所有的index下面的mapping
		//备份mapping的名称和结构定义
		String mapping = GetMapping2Test.getMapping(indexName);
		String mapFile = getBackupMapFile(indexName);
		File file = new File(mapFile);
		FileUtils.writeLines(file, "UTF-8",Arrays.asList(mapping));
	}
	public static void backupDoc(String indexName) throws Exception{
		File file = new File(getBackupDataFile(indexName));
		JsonFactory jfactory = new JsonFactory();  
	    /*** write to file ***/  
	    JsonGenerator jGenerator = jfactory.createGenerator(file, JsonEncoding.UTF8);  
	    
		long startTime = System.currentTimeMillis();
		Client esClient = ESUtils.getClient();
		SearchResponse searchResponse = esClient.prepareSearch(indexName)
				// 加上这个据说可以提高性能，但第一次却不返回结果
				.setSearchType(SearchType.SCAN)
				// 实际返回的数量为5*index的主分片格式
				.setSize(1000)
				// 这个游标维持多长时间
				.setScroll(TimeValue.timeValueMinutes(10000))
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
				fetchCount += searchResponse.getHits().hits().length;
				if (fetchCount % 1000 == 0) {
					System.out.println(fetchCount + " fetched");
				}
				for (SearchHit hit : searchResponse.getHits()) {
					jGenerator.writeStartObject();
					
					String indexNm=hit.getIndex();
					jGenerator.writeStringField("i", indexNm); // 
					String typeNm=hit.getType();
					jGenerator.writeStringField("t", typeNm); // 
					String docId = hit.getId();
					jGenerator.writeStringField("id", docId); // 
					String doc = hit.getSourceAsString();
					jGenerator.writeStringField("s", doc); // 
					jGenerator.writeEndObject();
					jGenerator.writeRaw("\n");
				}
			}
		}
		jGenerator.flush();
		jGenerator.close();
		System.out.println("fetch count:" + fetchCount);
		System.out.println("useTime:"
				+ (System.currentTimeMillis() - startTime));
	}
	
	public static void restore(String indexName) throws Exception {
		File file = new File(getBackupDataFile(indexName));
		JsonFactory jfactory = new JsonFactory();
		JsonParser jParser = jfactory.createParser(file);
		int n=0;
		// loop until token equal to "}"
		while (!jParser.isClosed()) {
			jParser.nextToken();
			String fieldname = jParser.getCurrentName();
			if ("i".equals(fieldname)) {
				n++;
				String iName = jParser.getText(),
						typeName="",docId="",docSource="";
				//表示数据段开始
				// 当前结点为indexName
				jParser.nextToken();
				;
//				System.out.println("indexName:"+jParser.getText()); 
				jParser.nextToken();
				fieldname = jParser.getCurrentName();
				if ("t".equals(fieldname)) {
					// 当前结点为type
					jParser.nextToken();
					typeName = jParser.getText();
//					System.out.println("typeName:"+jParser.getText()); 
				}
				else {
					System.err.println("expect typeName,actural name:"+fieldname); 
				}
				jParser.nextToken();
				fieldname = jParser.getCurrentName();
				if ("id".equals(fieldname)) {
					//文档id
					jParser.nextToken();
					docId = jParser.getText();
//					System.out.println("docId:"+jParser.getText());
				}
				else {
					System.err.println("expect docId,actural name:"+fieldname); 
				}
				jParser.nextToken();
				fieldname = jParser.getCurrentName();
				if ("s".equals(fieldname)) {
					//文档对象
					jParser.nextToken();
					docSource = jParser.getText();
//					System.out.println("docSource:"+jParser.getText());
				}
				else {
					System.err.println("expect docSource,actural name:"+fieldname); 
				}
				RestoreTest.bulkIndex(iName,typeName,docId,docSource);
				if(n%BULK_INDEX_SIZE==0){
					RestoreTest.flushData();
				}
			}
		}
		RestoreTest.flushData();
		jParser.close();
	}
	
}
