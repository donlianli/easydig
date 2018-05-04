package com.donlian.net;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
/**
 * redis协议 网络通信demo 
 * @author donlianli
 * 2018年2月24日
 * 向redis设置一个mykey值为myvalue的动作
 */
public class RedisSet {
	
	public static void main(String[] args) {
		//注意，是回车换行，不要再转义
		String setCmd = 
				  "*3\r\n" //参数的个数 3，说明后面有3个参数
				+ "$3\r\nSET\r\n" //第一个参数的长度及对应的值SET
				+ "$5\r\nmykey\r\n"//第二个参数的长度及对应的值mykey
				+ "$7\r\nmyvalue\r\n";//第三个参数的长度及对应的值myvalue
		try {
            //redis地址
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
            System.out.println("开始接收响应"); 
            /**
             * 因为提前知道，返回内容只有一行，所以直接readLine
             * 响应内容为 "+OK\r\n"
             */
            String s = br.readLine();
            //应该用  判断是否结束
            System.out.println(s);    
            System.out.println("接收完毕");
            
            //关闭资源 
            write.close(); 
            socket.close(); 
        } catch (Exception e) {
            e.printStackTrace();// 出错，打印出错信息
        }
	}
}
