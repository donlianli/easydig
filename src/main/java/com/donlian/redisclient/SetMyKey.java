package com.donlian.redisclient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * 利用jdk自带的api编写一个
 * 实现set mykey myvalue的 demo
 * @author donlianli
 */
public class SetMyKey {
    public static void main(String argv[]) throws IOException {
        String setCmd="*3\r\n$3\r\nSET\r\n$5\r\nmykey\r\n$7\r\nmyvalue\r\n";
        Socket socket=new Socket("localhost", 7777);
        OutputStream os=socket.getOutputStream();//字节输出流
        PrintWriter pw=new PrintWriter(os);//将输出流包装为打印流
        pw.write(setCmd);
        pw.flush();
        InputStream is=socket.getInputStream();
        BufferedReader br=new BufferedReader(new InputStreamReader(is));
        String info=null;
        while((info=br.readLine())!=null){
            System.out.println("server response："+info);
        }
        //4.关闭资源
        br.close();
        is.close();
        pw.close();
        os.close();
        socket.close();
    }
}
