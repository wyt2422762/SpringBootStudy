package com.wyt.study;


import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NioSocketClientDemo {

    public static void main(String[] args) throws Exception {
        //1. 打开一个网络通道
        SocketChannel socketChannel = SocketChannel.open();
        //2. 设置阻塞方式
        socketChannel.configureBlocking(false);
        //3. 服务端的地址
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 8087);
        //4. 连接服务端
        boolean connected = socketChannel.connect(inetSocketAddress);
        if(!connected){
            //socketChannel.connect(inetSocketAddress)连接失败后在想连接要使用finishConnect()方法
            while(!socketChannel.finishConnect()){
                //连接失败时持续进行连接，这里可以执行一些其他操作
                System.out.println("执行其他操作");
            }
        }
        //5. 连接成功
        //6. 获取一个缓冲区
        ByteBuffer byteBuffer = ByteBuffer.wrap("发给服务端的数据".getBytes());
        //7. 向服务端发送数据
        socketChannel.write(byteBuffer);

        //
        System.in.read();
    }

}
