package com.donlian.httpclient;
import java.io.IOException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.util.EntityUtils;


public class Toupiao {
  private static  String url = "http://hi.edu.sina.com.cn/Poll.php?project_id=7723&id=21";
  public static void main(String[] args) {
	  int count=Data.PROXY_MAP.size();
	  Thread[] all= new Thread[count];
	  int i=0;
	  for(final Map.Entry<String, String> entry: Data.PROXY_MAP.entrySet()){
		  all[i]= new Thread(new Runnable(){
			@Override
			public void run() {
				 vote(entry.getKey(),Integer.valueOf(entry.getValue()));
				
			}
		  });
		  all[i].start();
		  i++;
		  
	  }
	  for(int j=0;j<count;j++){
		  try {
			all[j].join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	  }
	  System.out.println("this time finish!");
  }
  public static void vote(String proxyId,int proxyPort){
	//代理服务器
	  HttpHost proxy = new HttpHost(proxyId, proxyPort);
	  DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
	  CloseableHttpClient httpclient = HttpClients.custom()
	          .setRoutePlanner(routePlanner)
	          .build();
	  HttpGet httpget = new HttpGet(url);
//	  httpget.setHeader("X-Forwarded-For","120.84.254.205");
	  httpget.setHeader("Referer", "http://hi.edu.sina.com.cn/edu/2014wxjpcgms/list.php?t_col2=%D1%C5%CB%BC&t_col3=%D4%C4%B6%C1&t_col18=%B1%B1%BE%A9&fuzzy=1&dpc=1");
	  CloseableHttpResponse response=null;
	  try {
		  response = httpclient.execute(httpget);
		  HttpEntity entity = response.getEntity();// 响应实体/内容  
		  String str = EntityUtils.toString(entity,"gb2312"); 
		  if(str!=null && str.contains("")){
			  System.out.println("投票一次，IP:"+proxyId);
		  }
		  else {
			  System.out.println("投票失败，IP:"+proxyId);
		  }
	  } 
	  catch(Exception e){
		  System.out.println("投票失败，IP:"+proxyId);
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