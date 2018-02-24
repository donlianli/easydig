package com.donlian.net;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
/**
 * redis 网络通信协议demo 
 * @author donlianli
 * 2018年2月24日
 * 
 */
public class RedisGet {

	public static void main(String[] args) {
		//注意，确实是回车换行，不要再转义
		String setCmd = "*3\r\n$3\r\nSET\r\n$5\r\n"
				+ "mykey\r\n$7\r\nmyvalue\r\n";
		try {
            //192.168.82.18:6384 redis地址
            Socket socket = new Socket("192.168.82.18", 6384);
            System.out.println("连接redis成功");
            
            // 由系统标准输入设备构造BufferedReader对象
            PrintWriter write = new PrintWriter(socket.getOutputStream());
            // 由Socket对象得到输出流，并构造PrintWriter对象
            write.write(setCmd);
            write.flush();
            System.out.println("发送完毕");  
            System.out.println("内容="+setCmd);  
            // 从服务端程序接收数据
            InputStream ips = socket.getInputStream();
            InputStreamReader ipsr = new InputStreamReader(ips);
            BufferedReader br = new BufferedReader(ipsr);
            String s = "";   
            System.out.println("开始接收响应"); 
            while((s = br.readLine()) != null) {
            	System.out.println(s);    
          	}
            //4、关闭资源 
            write.close(); // 关闭Socket输出流
            socket.close(); // 关闭Socket
        } catch (Exception e) {
            e.printStackTrace();// 出错，打印出错信息
        }
	}
}
