package com.wyt.study;


import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class NioSocketClientDemo {

    private SocketChannel socketChannel;
    private final Integer server_port = 8087;
    private final String server_ip = "127.0.0.1";

    /**
     * 初始化
     * @throws IOException
     */
    public void init() throws IOException {
        //1. 打开一个网络通道
        socketChannel = SocketChannel.open();
        //2. 设置阻塞方式
        socketChannel.configureBlocking(false);
    }

    /**
     * 连接服务端
     * @return
     * @throws Exception
     */
    public boolean connectToServer() throws Exception {
        boolean connected = false;
        //3. 服务端的地址
        InetSocketAddress inetSocketAddress = new InetSocketAddress(server_ip, server_port);
        //4. 连接服务端
        connected = socketChannel.connect(inetSocketAddress);
        if(!connected){
            //socketChannel.connect(inetSocketAddress)连接失败后在想连接要使用finishConnect()方法
            while(!(connected = socketChannel.finishConnect())){
                //连接失败时持续进行连接，这里可以执行一些其他操作
                System.out.println("连接服务端中...，可以执行其他操作");
            }
        }
        return connected;
    }

    /**
     * 发送用户登录消息
     */
    public void sendLoginMsg() throws Exception {
        String loginMsg = "{msgType: 0, user: " + System.currentTimeMillis() + "}";
        //获取一个缓冲区
        ByteBuffer byteBuffer = ByteBuffer.wrap(loginMsg.getBytes());
        //向服务端发送数据
        socketChannel.write(byteBuffer);
    }

    /**
     * 用一个线程来接收消息
     * @throws Exception
     */
    public void receiveMsg() throws Exception {
        /**
         * 自定义线程池
         * 线程池参数
         * int corePoolSize 核心线程数(最小线程数)
         * int maximumPoolSize 最大线程数
         * long keepAliveTime 线程最大空闲时间，
         当线程池中的线程数 > corePoolSize是时，如果线程的空闲时间 >= keepAliveTime,
         多余的线程会被销毁，直到剩下corePoolSize个线程
         * TimeUnit unit 时间单位
         * BlockingQueue<Runnable> workQueue 阻塞队列
         * ThreadFactory threadFactory 生成线程的工厂类，一般用默认即可
         * RejectedExecutionHandler handler 拒绝策略，jdk自带的有如下几种
         1.AbortPolicy(默认) 抛出RejectedExecutionException异常
         2.CallerRunsPolicy 将多余的任务退回给调用者，不会抛出异常
         3.DiscardOldestPolicy 扔掉等待队列等待最久的任务，然后再次尝试提交本次任务，不会抛出异常
         4.DiscardPolicy 直接抛弃本次任务，不会抛出异常
         */
        ThreadPoolExecutor cusSingleThreadExecutor = new ThreadPoolExecutor(1, 1, 2L, TimeUnit.SECONDS, new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());

        cusSingleThreadExecutor.execute(() -> {
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            try {
                while(true) {
                    int read = socketChannel.read(byteBuffer);
                    if(read > 0){
                        String msg = new String(byteBuffer.array());
                        System.out.println("收到消息：" + msg);
                        byteBuffer.clear();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void main(String[] args) throws Exception {
        NioSocketClientDemo nioSocketClientDemo = new NioSocketClientDemo();
        nioSocketClientDemo.init();
        nioSocketClientDemo.connectToServer();
        nioSocketClientDemo.receiveMsg();
        nioSocketClientDemo.sendLoginMsg();

        System.in.read();
    }

    public void simpleExample() throws Exception {
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
