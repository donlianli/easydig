package com.donlian.rpc;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class RpcServer {
	public static final int port = 2580;

	public static void main(String[] args) throws Exception {
		final HelloService service= new HelloServiceImpl();
		ServerSocket server = new ServerSocket(port);
		System.out.println("server listen on "+port);
		for(;;) {
		final Socket socket = server.accept();
		InetAddress addr = socket.getInetAddress();
		int port = socket.getPort();
		System.out.println("client connection:"+addr+":"+port);
		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				ObjectInputStream input;
				try {
					input = new ObjectInputStream(socket.getInputStream());
					String methodName = input.readUTF();
					Class<?>[] parameterTypes = (Class<?>[])input.readObject();
					Object[] arguments = (Object[])input.readObject();
					ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
					Method method = service.getClass().getMethod(methodName, parameterTypes);
					Object result = method.invoke(service, arguments);
					output.writeObject(result);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			} );
		thread.start();
		}
	}
}
