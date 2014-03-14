package com.donlian.netty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.AttributeKey;
/**
 * A simple HTTP client that prints out the content of the HTTP response to
 * {@link System#out} to test {@link HttpSnoopServer}.
 */
public class HttpSnoopClient {
	final static Logger logger = LoggerFactory.getLogger(HttpSnoopClient.class);
    private String host = "item.jd.com";
    private int port=80;
    public static AttributeKey<Integer> ODID = new AttributeKey<Integer>("odid");
    // Configure the client.
    private EventLoopGroup group = new NioEventLoopGroup();
    private Bootstrap b = new Bootstrap();
    public HttpSnoopClient() {
            b.group(group)
             .channel(NioSocketChannel.class)
             .handler(new HttpSnoopClientInitializer());
    }

    public void run() throws Exception {
    	String p = "/" ;
    	String a =		".html";
    	// Make the connection attempt.
       
        for(int i = 744141;i<754149;i++){
//    		int i=744141;
        	 Channel ch = b.connect(host, port).sync().channel();
        	 logger.info("ch={}",ch);
            ch.attr(ODID).set(i);
        	String path = p + i +a;
            // Prepare the HTTP request.
            HttpRequest request = new DefaultHttpRequest(
                    HttpVersion.HTTP_1_1, HttpMethod.GET, path);
            request.headers().set(HttpHeaders.Names.HOST, host);
            request.headers().set(HttpHeaders.Names.CONNECTION, HttpHeaders.Values.KEEP_ALIVE);
            request.headers().set(HttpHeaders.Names.ACCEPT_ENCODING, HttpHeaders.Values.GZIP);
            // Send the HTTP request.
            ch.write(request);
            // Wait for the server to close the connection.
            ch.closeFuture().sync();
        }
    
    }

    public static void main(String[] args) throws Exception {
    	//http://item.jd.com/380245.html
        new HttpSnoopClient().run();
    }
}
