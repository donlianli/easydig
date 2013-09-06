package com.donlianli.es.admin;

import org.elasticsearch.action.admin.indices.settings.UpdateSettingsResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;

import com.donlianli.es.ESUtils;
/**
 * 更新副本个数
 * @author donlianli@126.com
 */
public class UpdateReplicaTest {
	public static void  main(String[] argv) throws Exception{
		Client client = ESUtils.getClient();
		Settings settings =  ImmutableSettings.settingsBuilder()
                //可以更新的配置还有很多，见elasticsearch官网
                .put("number_of_replicas", 2).build();
		//首先创建索引库
		UpdateSettingsResponse  updateSettingsResponse = client.admin().indices()
		.prepareUpdateSettings("testindex").setSettings(settings).execute().actionGet();
		System.out.println(updateSettingsResponse);
	}

}
