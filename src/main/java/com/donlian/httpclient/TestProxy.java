package com.donlian.httpclient;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;

import com.donlian.httpclient.data.Data0;

/**
 * 刷搜房网帖子的点击数
 * @author donlianli@126.com
 * 2014年12月6日
 */
public class TestProxy {
  private static  String url = "http://123.57.75.145/test.php";
  public static void main(String[] args) throws InterruptedException {
	  ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);  
	  for(final Map.Entry<String, String> entry: Data0.PROXY_MAP.entrySet()){
		  fixedThreadPool.submit(new Runnable(){
			@Override
			public void run() {
				 hit(entry.getKey(),Integer.valueOf(entry.getValue()));
			}
		  });
	  }
	  fixedThreadPool.shutdown();
	  fixedThreadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
	  System.out.println("this time finish!");
  }
  public static void hit(String proxyId,int proxyPort){
	//代理服务器
	  HttpHost proxy = new HttpHost(proxyId, proxyPort);
	  DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy);
	  CloseableHttpClient httpclient = HttpClients.custom()
	          .setRoutePlanner(routePlanner)
	          .build();
	  RequestConfig requestConfig = RequestConfig.custom()
			  	//读取超时时间
		        .setSocketTimeout(8000)
		        //连接超时时间
		        .setConnectTimeout(8000)
		        .build();
	  HttpGet httpget = new HttpGet(url);
	  //伪造xxf
	  Header header = new BasicHeader("X-Forwarded-For", "13.25.23.1");
	  httpget.addHeader(header);
	  httpget.setConfig(requestConfig);
	  CloseableHttpResponse response=null;
	  try {
		  
		  response = httpclient.execute(httpget);
		  HttpEntity entity = response.getEntity();// 响应实体/内容  
		  String str = EntityUtils.toString(entity,"utf-8"); 
		  if(str!=null && str.contains("")){
			  System.out.println("单击一次，IP:"+proxyId);
			  System.out.println("response:"+str);
			  
		  }
		  else {
			  System.out.println("没有完成单击，IP:"+proxyId);
		  }
	  } 
	  catch(Exception e){
		 // e.printStackTrace();
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