package com.wyt.study.netty;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class SimpleNettyServerDemo {

    @PostConstruct
    public void startServer(){

        log.debug("netty服务端启动......");

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
            //等待子线程结束，关闭程序
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            subGroup.shutdownGracefully();
            mainGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        SimpleNettyServerDemo simpleNettyServerDemo = new SimpleNettyServerDemo();
        simpleNettyServerDemo.startServer();
    }

}

