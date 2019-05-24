package com.donlian.httpclient.defaultRoute;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

/**
 * Date: 2019/5/22
 * TIME: 21:25
 * HTTPClient
 *   1、共享httpclient
 *   2、增加超时时间
 *   3、增加连接线程池
 * @author donlianli
 */
public class HTTPClientV5 {
    public static void main(String argvs[]){
    	PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
    	// 总连接数
    	cm.setMaxTotal(200);
    	// Increase default max connection per route to 20
    	cm.setDefaultMaxPerRoute(20);
        // 获得Http客户端(可以理解为:你得先有一个浏览器;注意:实际上HttpClient与浏览器是不一样的)
        CloseableHttpClient httpClient = HttpClientBuilder.create()
        		.setConnectionManager(cm).build();
        
        for(int i=0;i<10;i++) {
        	new Thread(new Runnable() {
				@Override
				public void run() {
					GetRequest(httpClient);
				}
        	}).start();
        }
    }

	private static void GetRequest(CloseableHttpClient httpClient) {
        // 创建Get请求
        HttpGet httpGet = new HttpGet("http://localhost:8888");
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(2000)
                .setConnectTimeout(2000)
                .build();
        httpGet.setConfig(requestConfig);
        // 响应模型
        CloseableHttpResponse response = null;
        try {
            // 由客户端执行(发送)Get请求
            response = httpClient.execute(httpGet);
            // 从响应模型中获取响应实体
            HttpEntity responseEntity = response.getEntity();
           
            if (responseEntity != null) {
                System.out.println("响应内容为:" + EntityUtils.toString(responseEntity));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
		
	}
}
