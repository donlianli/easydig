package com.donlian.httpclient.defaultRoute;

import java.io.IOException;
import java.io.PrintWriter;
import org.eclipse.jetty.server.Server;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
/**
 * Date: 2019/5/21
 * TIME: 22:41
 * 使用jetty启动一个服务；
 * @author donlianli
 */
public class JettyServerMain {
    public static void main( String[] args ) throws Exception
    {
        Server server = new Server(8888);
        server.setHandler(new HelloHandler());

        server.start();
        server.join();
    }
}

class HelloHandler extends AbstractHandler
{
    final String greeting;
    final String body;

    public HelloHandler()
    {
        this("Hello World");
    }

    public HelloHandler( String greeting )
    {
        this(greeting, null);
    }

    public HelloHandler( String greeting, String body )
    {
        this.greeting = greeting;
        this.body = body;
    }

    @Override
    public void handle( String target,
                        Request baseRequest,
                        HttpServletRequest request,
                        HttpServletResponse response ) throws IOException,
            ServletException
    {
        response.setContentType("text/html; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        PrintWriter out = response.getWriter();

        out.println("<h1>" + greeting + "</h1>");
        if (body != null)
        {
            out.println(body);
        }

        baseRequest.setHandled(true);
    }
}
