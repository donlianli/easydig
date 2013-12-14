package com.donlianli.es.code;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;

import com.donlianli.es.ESUtils;

public class PinyinSearchTest {
	public static void main(String[] args) {
		Client client = ESUtils.getCodeClient();
		String keyWord = "高血压";
//		String keyWord = "老年 高血压";
//		String keyWord = "gxy";
		//多个字段匹配
		MultiMatchQueryBuilder query = QueryBuilders.multiMatchQuery(keyWord, "diseaseName","pinyin");
		
		long b = System.currentTimeMillis();
		SearchResponse response = client.prepareSearch("code").setTypes("icd")
				.setQuery(query)
				.setFrom(0)
				//前20个
				.setSize(20)
				.execute().actionGet();
		long useTime = System.currentTimeMillis()-b;
		System.out.println("search use time:" + useTime + " ms");
		
		SearchHits shs = response.getHits();
		for (SearchHit hit : shs) {
			System.out.println("分数:" 
					+ hit.getScore()
					+ ",ID:"
					+ hit.getId()
					+ ", 疾病名称:"
					+ hit.getSource().get("diseaseName")
					+ ",拼音:" + hit.getSource().get("pinyin"));
		}
		client.close();
	}
}
