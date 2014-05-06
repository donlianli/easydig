package com.donlian.httpclient;
import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.util.EntityUtils;


public class HttpClientTutorial {
  private static String url = "http://www.baidu.cn";

  public static void main(String[] args) {
	  
	  //代理服务器
	  HttpHost proxy = new HttpHost("61.163.231.212", 9999);
	  DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
	  CloseableHttpClient httpclient = HttpClients.custom()
	          .setRoutePlanner(routePlanner)
	          .build();
	  HttpGet httpget = new HttpGet(url);
	  CloseableHttpResponse response=null;
	  try {
		  response = httpclient.execute(httpget);
		  HttpEntity entity = response.getEntity();// 响应实体/内容  
	      System.out.println(EntityUtils.toString(entity)); 
	  } 
	  catch(Exception e){
		  e.printStackTrace();
	  }
	  finally {
	      try {
			if(response !=null){
				response.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	  } 
  }
}