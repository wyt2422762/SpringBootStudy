package com.wyt.study;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NioSocketServerDemo {

    private ServerSocketChannel serverSocketChannel;
    private final Integer port = 8087;
    private Selector selector;

    public NioSocketServerDemo() throws Exception {
        //1. 打开网络通道
        serverSocketChannel = ServerSocketChannel.open();
        //2. 绑定端口号
        serverSocketChannel.bind(new InetSocketAddress(port));
        //3. 设置非阻塞
        serverSocketChannel.configureBlocking(false);
        //4. 创建select
        selector = Selector.open();
        //5. 把通道注册到selector, SelectionKey里有注册的事件类型
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void run() throws Exception {
        while (true){
            int selects = selector.select(2000);
            if(selects == 0) {
                System.out.println("等待客户端连接");
                continue;
            }
            Set selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
            while(keyIterator.hasNext()){
                SelectionKey selectedKey = keyIterator.next();
                if(selectedKey.isConnectable()){
                    System.out.println("connect事件");
                } else if(selectedKey.isAcceptable()) { //客户端连接事件
                    System.out.println("客户端连接事件");
                    //接收客户端连接
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    //设置非阻塞
                    socketChannel.configureBlocking(false);
                    //注册到selector
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                } else if(selectedKey.isReadable()){ //读取客户端数据
                    System.out.println("读取客户端数据");
                    //获取网络通道
                    SocketChannel socketChannel = (SocketChannel)selectedKey.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    socketChannel.read(byteBuffer);
                    System.out.println("客户端发来的数据：" + new String(byteBuffer.array()));
                } else if(selectedKey.isWritable()){ //向客户端发送数据
                    System.out.println("向客户端发送数据");
                }
                //移除处理过的SelectionKey
                keyIterator.remove();
            }
        }
    }

    public static void main(String[] args) throws Exception {
    }

}
