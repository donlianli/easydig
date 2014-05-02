package com.donlian.rpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

public class RpcClient {
	public static void main(String argvs[]){
		for(int i=0;i<10;i++){
			new Thread(new Runnable(){
				@Override
				public void run() {
					RpcClient client = new RpcClient();
					HelloService helloService=client.getProxy(HelloService.class,"127.0.0.1",2580);
					System.out.println(helloService.sayHello("hi, RPC "));
				}
				
			}).start();
		}
	}
	@SuppressWarnings("unchecked")
	public <T> T getProxy(Class<T> interfaceClass, final String host,  final int port) {
		return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
				new Class<?>[] { interfaceClass }, new InvocationHandler() {
					public Object invoke(Object proxy, Method method,
							Object[] arguments) throws Throwable {
						Socket socket = new Socket(host, port);
						ObjectOutputStream output = new ObjectOutputStream(
								socket.getOutputStream());
						output.writeUTF(method.getName());
						output.writeObject(method.getParameterTypes());
						output.writeObject(arguments);
						ObjectInputStream input = new ObjectInputStream(socket
								.getInputStream());
						return input.readObject();
					}
				});
	}
}
