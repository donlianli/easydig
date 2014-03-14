package com.donlianli.es.test0;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.AndFilterBuilder;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
/**
 * 没有字段更新
 * @author lidongliang
 */
public class MissingSearchTest {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Settings settings = ImmutableSettings.settingsBuilder()
				//指定集群名称
                .put("cluster.name", "wowogoods")
                //探测集群中机器状态
                .put("client.transport.sniff", true).build();
		Client client = new TransportClient(settings)
		.addTransportAddress(new InetSocketTransportAddress("goodses2.55tuan.me", 9350))
		.addTransportAddress(new InetSocketTransportAddress("goodses2.55tuan.me", 9351));
		long curTime = System.currentTimeMillis()/1000-28800;
		
		FilterBuilder f1=FilterBuilders.missingFilter("soldOut")
				.existence(true).nullValue(true);
		FilterBuilder f2=FilterBuilders.rangeFilter("actEndTime").gte((int)curTime);
		FilterBuilder f3=FilterBuilders.termFilter("isDelete", 0);
		FilterBuilder f4=FilterBuilders.termFilter("isFinish",0);
		AndFilterBuilder filter = FilterBuilders.andFilter(f1,f2,f3,f4);
		System.out.println(filter);
		long beginTime = System.currentTimeMillis();
		SearchResponse response = client.prepareSearch("goods_city_1")
        //设置过滤条件
        .setFilter(filter)   // Filter
        .addField("goodsId")
        //设置分页信息
        .setFrom(0).setSize(10)
        .execute()
        .actionGet();
		SearchHits sh = response.getHits();
		System.out.println("总数:"+response.getHits().getTotalHits());
		StringBuilder sb = new StringBuilder("[");
		for(SearchHit shit : sh){
			sb.append(shit.getId()).append(",").append(shit.getType());
		}
		sb.append("]");
//		System.out.println(sb.toString());
		System.out.println("useTime:"+(System.currentTimeMillis()-beginTime));
		client.close();
	}
}
