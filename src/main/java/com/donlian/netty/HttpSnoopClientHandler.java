package com.donlian.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundMessageHandlerAdapter;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.LastHttpContent;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.Charset;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpSnoopClientHandler extends ChannelInboundMessageHandlerAdapter<Object> {
	final static Logger logger = LoggerFactory.getLogger(HttpSnoopClientHandler.class);
	final static String basePath = "d:/temp/data";
	final static String CLRF="\r\n";
	private int count = 0;
    @Override
    public void exceptionCaught(
            ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

	public void messageReceived(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		count++;
		logger.info("count:{}",count);
		 int dataId = ctx.channel().attr(HttpSnoopClient.ODID).get();
		 File file = new File(basePath + "/" + dataId + ".html");
		 if(!file.exists()){
			 logger.info("{}.html created!",dataId);
		 }
		 FileWriter writer = new  FileWriter(file,  true );  
         
//		 FileOutputStream outputStream = FileUtils.openOutputStream(new File(basePath + "/" + dataId + ".html"));
		 if (msg instanceof HttpResponse) {
	            HttpResponse response = (HttpResponse) msg;
	            writer.write(response.getStatus().toString());  
	            writer.write(response.getProtocolVersion().toString()+CLRF);  
	            if (!response.headers().isEmpty()) {
	                for (String name: response.headers().names()) {
	                    for (String value: response.headers().getAll(name)) {
	                        writer.write("HEADER:" + name +"="+value+CLRF);  
	                    }
	                }
	            }

	            if (HttpHeaders.isTransferEncodingChunked(response)) {
	                logger.debug("CHUNKED CONTENT {");
	            } else {
	                logger.debug("CONTENT {");
	            }
	        }
	        if (msg instanceof HttpContent) {
	            HttpContent content = (HttpContent) msg;
	            writer.write(content.content().toString(Charset.forName("gb2312"))+CLRF);  

	            if (content instanceof LastHttpContent) {
	            	logger.info("{}.html finished",dataId);
//	            	 int i=744142;
//	 	            String p = "/" ;
//	 	        	String a =		".html";
//	 	            ctx.attr(HttpSnoopClient.ODID).set(i);
//	 	        	String path = p + i +a;
//	 	            // Prepare the HTTP request.
//	 	            HttpRequest request = new DefaultHttpRequest(
//	 	                    HttpVersion.HTTP_1_1, HttpMethod.GET, path);
//	 	            String host = "item.jd.com";
//	 	            request.headers().set(HttpHeaders.Names.HOST, host);
//	 	            request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
//	 	            request.headers().set(HttpHeaders.Names.ACCEPT_ENCODING, HttpHeaders.Values.GZIP);
//	 	            // Send the HTTP request.
////	 	            ctx.nextOutboundMessageBuffer().add(request);
//	 	           ctx.channel().write(request).sync();
	            }
	        }
	        writer.close();
	}
}
