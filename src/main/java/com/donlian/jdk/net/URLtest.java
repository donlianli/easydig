package com.donlian.jdk.net;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * 这个例子说明url的openConnection返回的对象，每次调用都是返回一个新的对象
 * 当调用返回对象的connect方法时，建立的是两个不同的连接
 * @author donlianli
 *
 */
public class URLtest {
	public static void main(String[] args) {  
		try {  
			String urlString="http://localhost:30102";
            StringBuffer html = new StringBuffer();  
            URL url = new URL(urlString);  
            HttpURLConnection conn = (HttpURLConnection) url.openConnection(); 
            HttpURLConnection conn2 = (HttpURLConnection) url.openConnection(); 
            //这是两个不同的连接
            conn.connect(); 
            conn2.connect();
            String contentType = conn.getContentType();  
            String characterEncoding = null;  
            int index = contentType.indexOf("charset=");  
            if(index == -1){  
                characterEncoding = "UTF-8";  
            }else{  
                characterEncoding = contentType.substring(index + 8, contentType.length());  
            }  
            InputStreamReader isr = new InputStreamReader(conn.getInputStream(), characterEncoding);  
            BufferedReader br = new BufferedReader(isr);  
            String temp;  
            while ((temp = br.readLine()) != null) {  
                html.append(temp).append("\n");  
            }  
            br.close();  
            isr.close();  
            
            System.out.println(html.toString());
         } catch (Exception e) {  
            e.printStackTrace();  
         }  
    }  
}
