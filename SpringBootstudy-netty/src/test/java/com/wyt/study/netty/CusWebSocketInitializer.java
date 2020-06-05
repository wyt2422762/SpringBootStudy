package com.wyt.study.netty;


import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;


public class CusWebSocketInitializer extends ChannelInitializer<SocketChannel> {
    /**
     *
     * 初始化通道
     * @param ch
     * @throws Exception
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        //获取piepeline
        ChannelPipeline pipeline = ch.pipeline();
        //添加各种处理器
        //添加HTTP编解码器
        pipeline.addLast(new HttpServerCodec());
        //添加大数据支持处理器
        pipeline.addLast(new ChunkedWriteHandler());
        //http聚合器
        pipeline.addLast(new HttpObjectAggregator(1024 * 64));

        //指定路由
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        //添加自定义handler
        pipeline.addLast(new CusSimpleHandler());

    }



}
