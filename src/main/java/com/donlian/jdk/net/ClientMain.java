package com.donlian.jdk.net;
import java.io.BufferedReader;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.io.PrintStream;  
import java.net.Socket;  
import java.net.UnknownHostException;  
/** 
 * Socket通讯客户端 
 * @author 米强<如转载请保留作者和出处> 
 * @blog http://hi.baidu.com/mq612/blog 
 * @blog http://blog.csdn.net/mq612 
 */  
public class ClientMain {  
    public ClientMain() {  
        try {  
            // 构造与服务器通讯的Socket对象，参数为服务器IP地址（String）和端口号（int），端口号需要和服务器端开放的端口号对应  
            Socket s = new Socket("localhost", 30102);  
            // 启动一个线程与服务器通讯，并把链接服务器的Socket对象传递过去  
            new LinkThread(s).start();  
        } catch (UnknownHostException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
      
      
    public static void main(String[] args) {  
        new ClientMain();  
    }  
      
}  
/** 
 * 与服务器通讯的线程 
 */  
class LinkThread extends Thread {  
    private Socket s = null;  
    // 输出流  
    private PrintStream out = null;  
    // 缓冲输入流  
    private BufferedReader in = null;  
      
    public LinkThread(Socket s) {  
        // 将Socket对象实例保存在全局变量中，因为run方法中我们还要用它断开链接  
        this.s = s;  
        try {  
            // 从Socket中获取输入流和输出流，由于我们只做一个简单的字符串通讯，所以采用BufferedRead和PrintStream来封装输入、输出流  
            out = new PrintStream(s.getOutputStream());  
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));  
        } catch (UnknownHostException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }  
      
    /** 
     * 线程的运行run方法 
     */  
    public void run() {  
        try {  
            // 死循环可以使客户端不断的向服务器发送信息，不用担心循环无法结束，后面的return语句可以结束整个线程。  
            while(true){  
                // 提示用户输入文字  
                // 将用户输入的字符串保存在message变量中  
                // 通过输出流发送字符串  
                out.println("ok");  
                // 清空缓冲，强制输出  
                out.flush(); 
                // 获取服务器返回的字符串  
                String str = in.readLine();  
                // 如果返回的字符串存在  
                if(str != null){  
                    // 显示在控制台  
                    System.out.println(str);  
                }else{  
                    // 提示会话结束，并结束线程  
                    System.out.println("本次会话结束！");  
                    return;  
                }  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            // 在 finally 代码块中无论如何都会执行下面代码：  
            try {  
                // 如果没有关闭Socket  
                if(!s.isClosed()){  
                    // 关闭Socket链接  
                    s.close();  
                }  
            } catch (IOException e1) {  
                e1.printStackTrace();  
            }  
        }  
    }  
      
}  