package com.donlian.httpclient.defaultRoute;

import java.io.IOException;
import java.io.PrintWriter;
import org.eclipse.jetty.server.Server;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.LogFactory;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.util.log.Log;

/**
 * Date: 2019/5/21 TIME: 22:41 使用jetty启动一个服务；
 * 
 * @author donlianli
 */
public class JettyServerMain {
	public static void main(String[] args) throws Exception {
		Server server = new Server(8888);
		
		server.setHandler(new HelloHandler());

		server.start();
		server.join();
	}
}

class HelloHandler extends AbstractHandler {
	
	/**
	 * 作为测试，在这个方法故意sleep 3秒，然后返回hello;
	 */
	@Override
	public void handle(String target, Request baseRequest, HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		long threadId = Thread.currentThread().getId();
		Log.getLogger(this.getClass()).info("threadId="+threadId+" come in");
		try {
			Thread.sleep(3000);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
		response.setStatus(HttpServletResponse.SC_OK);
		PrintWriter out = response.getWriter();

		out.println("hello+"+threadId);

		baseRequest.setHandled(true);
		Log.getLogger(this.getClass()).info("threadId="+threadId+" finish");
	}
}
