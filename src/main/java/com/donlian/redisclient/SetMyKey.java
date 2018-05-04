package com.donlian.redisclient;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * �Լ�����jdk�Դ���api��дһ��
 * ʵ��set mykey myvalue�� demo
 * @author donlianli
 */
public class SetMyKey {
    public static void main(String argv[]) throws IOException {
        String setCmd="*3\r\n$3\r\nSET\r\n$5\r\nmykey\r\n$7\r\nmyvalue\r\n";
        //1.�����ͻ���Socket��ָ����������ַ�Ͷ˿�
        Socket socket=new Socket("localhost", 7777);
        //2.��ȡ���������������˷�����Ϣ
        OutputStream os=socket.getOutputStream();//�ֽ������
        PrintWriter pw=new PrintWriter(os);//���������װΪ��ӡ��
        pw.write(setCmd);
        pw.flush();
        //3.��ȡ������������ȡ�������˵���Ӧ��Ϣ
        InputStream is=socket.getInputStream();
        BufferedReader br=new BufferedReader(new InputStreamReader(is));
        String info=null;
        while((info=br.readLine())!=null){
            System.out.println("���ǿͻ��ˣ�������˵��"+info);
        }
        //4.�ر���Դ
        br.close();
        is.close();
        pw.close();
        os.close();
        socket.close();
    }
}
