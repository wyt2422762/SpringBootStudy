package com.wyt.study.netty;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class SimpleNettyServerDemo {

    public static void main(String[] args) {
        //创建netty工作线程组
        NioEventLoopGroup mainGroup = new NioEventLoopGroup(); //主线程池
        NioEventLoopGroup subGroup = new NioEventLoopGroup(); //从线程池

        try {
            //创建服务启动器
            ServerBootstrap bootstrap = new ServerBootstrap(); //服务启动器
            //初始化启动器, 给启动器初始化各类参数
            bootstrap
                    //初始化工作线程组
                    .group(mainGroup, subGroup)
                    //初始化通道类型
                    .channel(NioServerSocketChannel.class)
                    //初始化通道初始化器，用来进行业务处理
                    .childHandler(new CusWebSocketInitializer());

            //绑定端口，以同步的方式启动服务
            ChannelFuture channelFuture = bootstrap.bind(9090).sync();
            //这步不太了解
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            subGroup.shutdownGracefully();
            mainGroup.shutdownGracefully();
        }


    }

}

