package com.donlian.redisclient;

import java.io.*;
import java.net.Socket;

public class InfoCmd {
    public static void main(String argv[]) throws IOException {
        String infoCmd="$4\r\ninfo\r\n";
        Socket socket=new Socket("localhost", 7777);
        OutputStream os=socket.getOutputStream();//�ֽ������
        PrintWriter pw=new PrintWriter(os);//���������װΪ��ӡ��
        pw.write(infoCmd);
        pw.flush();
        InputStream is=socket.getInputStream();
        BufferedReader br=new BufferedReader(new InputStreamReader(is));
        String info=null;
        while((info=br.readLine())!=null){
            System.out.println("server response��"+info);
        }
        //4.�ر���Դ
        br.close();
        is.close();
        pw.close();
        os.close();
        socket.close();
    }
}
