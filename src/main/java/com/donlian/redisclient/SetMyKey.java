package com.donlian.redisclient;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * 自己利用jdk自带的api编写一个
 * 实现set mykey myvalue的 demo
 * @author donlianli
 */
public class SetMyKey {
    public static void main(String argv[]) throws IOException {
        String setCmd="*3\r\n$3\r\nSET\r\n$5\r\nmykey\r\n$7\r\nmyvalue\r\n";
        //1.创建客户端Socket，指定服务器地址和端口
        Socket socket=new Socket("localhost", 7777);
        //2.获取输出流，向服务器端发送信息
        OutputStream os=socket.getOutputStream();//字节输出流
        PrintWriter pw=new PrintWriter(os);//将输出流包装为打印流
        pw.write(setCmd);
        pw.flush();
        //3.获取输入流，并读取服务器端的响应信息
        InputStream is=socket.getInputStream();
        BufferedReader br=new BufferedReader(new InputStreamReader(is));
        String info=null;
        while((info=br.readLine())!=null){
            System.out.println("我是客户端，服务器说："+info);
        }
        //4.关闭资源
        br.close();
        is.close();
        pw.close();
        os.close();
        socket.close();
    }
}
