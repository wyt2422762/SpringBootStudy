package com.wyt.study.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;

public class CusSimpleHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {


    //用来保存所有连接的通道
    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    //收到数据会执行该方法
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        String text = msg.text(); //收到的消息
        System.out.println("msg = " + text);
        //将消息发送给所有连接的客户端
        for (Channel channel : channels) {
            channel.writeAndFlush(new TextWebSocketFrame(text + " ---- " + LocalDateTime.now().toString()));
        }
    }

    @Override
    //当有客户端连到服务端后会执行该方法
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //将新的通道加入集合
        channels.add(ctx.channel());
    }
}
