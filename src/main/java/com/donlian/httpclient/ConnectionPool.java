package com.donlian.httpclient;

import java.io.IOException;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

public class ConnectionPool {
	public static void main(String argv[]){
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		// Increase max total connection to 200
		cm.setMaxTotal(1);
		// Increase default max connection per route to 20
		cm.setDefaultMaxPerRoute(20);
		HttpHost wowotuan = new HttpHost("www.55tuan.com", 80);
		cm.setMaxPerRoute(new HttpRoute(wowotuan), 50);

		/**
		 * keepalive option
		 */
		ConnectionKeepAliveStrategy myStrategy = new ConnectionKeepAliveStrategy() {
		    public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
		        // Honor 'keep-alive' header
		        HeaderElementIterator it = new BasicHeaderElementIterator(
		                response.headerIterator(HTTP.CONN_KEEP_ALIVE));
		        while (it.hasNext()) {
		            HeaderElement he = it.nextElement();
		            String param = he.getName();
		            String value = he.getValue();
		            if (value != null && param.equalsIgnoreCase("timeout")) {
		                try {
		                	//转换成毫秒ms
		                    return Long.parseLong(value) * 1000;
		                } catch(NumberFormatException ignore) {
		                }
		            }
		        }
		        HttpHost target = (HttpHost) context.getAttribute(
		                HttpClientContext.HTTP_TARGET_HOST);
		        if ("www.naughty-server.com".equalsIgnoreCase(target.getHostName())) {
		            // Keep alive for 5 seconds only
		            return 5 * 1000;
		        } else {
		            // otherwise keep alive for 30 seconds
		            return 30 * 1000;
		        }
		    }

		};
		
		CloseableHttpClient httpclient = HttpClients.custom()
		        .setConnectionManager(cm)
		        .setKeepAliveStrategy(myStrategy)
		        .build();
		
		
		HttpGet httpget = new HttpGet("http://beijing.55tuan.com");
		HttpGet httpget2 = new HttpGet("http://beijing.55tuan.com/goods-9466428a9526bbec.html");
		
		  CloseableHttpResponse response=null;
		  try {
			  response = httpclient.execute(httpget);
			  HttpEntity entity = response.getEntity();// 响应实体/内容  
			  String str = EntityUtils.toString(entity,"utf-8"); 
			 System.out.println(str.substring(0, 100));
			 System.out.println("------------------------");
			 response.close();
			 response = httpclient.execute(httpget2);
			  HttpEntity entity2 = response.getEntity();// 响应实体/内容  
			  String str2 = EntityUtils.toString(entity2,"utf-8"); 
			 System.out.println(str2.substring(0, 100));
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
