package com.donlian.redisclient;

import java.io.*;
import java.net.Socket;

public class InfoCmd {
    public static void main(String argv[]) throws IOException {
        String infoCmd="$4\r\ninfo\r\n";
        Socket socket=new Socket("localhost", 7777);
        OutputStream os=socket.getOutputStream();//字节输出流
        PrintWriter pw=new PrintWriter(os);//将输出流包装为打印流
        pw.write(infoCmd);
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
