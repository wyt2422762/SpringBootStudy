package com.wyt.study.nio.threadpool;


import java.net.SocketAddress;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.concurrent.Executor;

public class NioSocketServerDemo_threadPool {



}


class BootStrap {

    private Executor bossPool;
    private Executor workerPool;

    private ServerSocketChannel serverSocketChannel;
    private Selector selector;

    public BootStrap(Executor bossPool, Executor workerPool){
        this.bossPool = bossPool;
        this.workerPool = workerPool;
    }

    public BootStrap init() throws Exception {
        //1. 打开网络通道
        serverSocketChannel = ServerSocketChannel.open();
        //3. 设置非阻塞
        serverSocketChannel.configureBlocking(false);
        //4. 创建select
        selector = Selector.open();
        return this;
    }

    public BootStrap bind(SocketAddress socketAddress) throws Exception {
        //2. 绑定端口号
        serverSocketChannel.bind(socketAddress);
        return this;
    }





}