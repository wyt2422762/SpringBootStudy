package com.wyt.study;

import com.alibaba.fastjson.JSON;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class NioSocketServerDemo {

    private ServerSocketChannel serverSocketChannel;
    private final Integer port = 8087;
    private Selector selector;

    //用来存放登录的用户对应的通道
    private Map<String, SocketChannel> users;

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

        users = new HashMap<>();
    }

    public void run() throws Exception {
        while (true){
            int selects = selector.select(2000);
            if(selects == 0) {
                System.out.println("=====等待客户端连接=====");
                continue;
            }
            Set selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();
            while(keyIterator.hasNext()){
                SelectionKey selectedKey = keyIterator.next();
                if(selectedKey.isConnectable()){
                    System.out.println("=====connect事件=====");
                } else if(selectedKey.isAcceptable()) { //客户端连接事件
                    //接收客户端连接
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    //设置非阻塞
                    socketChannel.configureBlocking(false);
                    SocketAddress remoteAddress = socketChannel.getRemoteAddress();
                    System.out.println("=====客户端：" + remoteAddress.toString() + "连接=====");
                    //注册到selector
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                } else if(selectedKey.isReadable()){ //读取客户端数据
                    //获取网络通道
                    SocketChannel socketChannel = (SocketChannel)selectedKey.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    socketChannel.read(byteBuffer);
                    String msg = new String(byteBuffer.array());
                    System.out.println("=====客户端发来的数据：" + msg + "=====");

                    processMsg(socketChannel, msg);

                } else if(selectedKey.isWritable()){ //向客户端发送数据
                    System.out.println("向客户端发送数据");
                }
                //移除处理过的SelectionKey
                keyIterator.remove();
            }
        }
    }

    /**
     * 处理消息
     */
    public void processMsg(SocketChannel fromSocketChannel, String msg) throws Exception {
        //1. 消息转json
        JsonMsg jsonMsg = JSON.parseObject(msg, JsonMsg.class);
        //2. 设置消息时间
        jsonMsg.setTime(LocalDateTime.now().toString());
        //3. 判断消息类型
        Integer msgType = jsonMsg.getMsgType();
        if(msgType == 0){ //登录消息
            // 存登录用户通道
            users.put(jsonMsg.getUser(), fromSocketChannel);
            JsonMsg remsg = new JsonMsg();
            remsg.setMsg("登录成功");
            remsg.setReceiver(jsonMsg.getUser());
            remsg.setUser("Server");
            remsg.setMsgType(0);
            remsg.setTime(LocalDateTime.now().toString());
            fromSocketChannel.write(ByteBuffer.wrap(JSON.toJSONString(remsg).getBytes()));
        } else if(msgType == 1){ //单聊消息
            String receiver = jsonMsg.getReceiver();
            SocketChannel socketChannel = users.get(receiver);
            socketChannel.write(ByteBuffer.wrap(JSON.toJSONString(jsonMsg).getBytes()));
        } else if(msgType == 2){ //多人聊天，发给出自己以外的其他人
            //获取所有的人通道
            for (SelectionKey key : selector.keys()) {
                SelectableChannel channel = key.channel();
                if(channel != null && channel instanceof SocketChannel){ //判断是否是socketChannel
                    SocketChannel socketChannel = (SocketChannel)channel;
                    if(!socketChannel.equals(fromSocketChannel)){ //排除自己
                        socketChannel.write(ByteBuffer.wrap(JSON.toJSONString(jsonMsg).getBytes()));
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        NioSocketServerDemo nioSocketServerDemo = new NioSocketServerDemo();
        nioSocketServerDemo.run();
    }

}
