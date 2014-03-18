package com.donlian.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class HttpSnoopClientInitializer extends ChannelInitializer<SocketChannel> {
	private final static Logger logger = LoggerFactory.getLogger(HttpSnoopClientHandler.class);
	private int count =0;
    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        // Create a default pipeline implementation.
        ChannelPipeline p = ch.pipeline();
        p.addLast("log", new LoggingHandler(LogLevel.INFO));
        p.addLast("codec", new HttpClientCodec());
        // Remove the following line if you don't want automatic content decompression.
        p.addLast("inflater", new HttpContentDecompressor());

        // Uncomment the following line if you don't want to handle HttpChunks.
        //p.addLast("aggregator", new HttpObjectAggregator(1048576));

        p.addLast("handler", new HttpSnoopClientHandler());
        logger.info("HttpSnoopClientInitializer count:{}",count);
    }
}
