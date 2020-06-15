package com.wyt.study.nio;


import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioBasicDemo {

    @Test
    //NIO读取文件
    public void readFile() throws Exception {
        //1. 获取文件流
        FileInputStream fis = new FileInputStream("read.txt");
        //2. 利用流获取通道
        FileChannel channel = fis.getChannel();
        //3. 创建一个缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        int read = channel.read(buffer);
        while(read > 0){
            System.out.println("读取到了" + read + "位数据");
            buffer.flip();
            //获取buffer中的内容
            System.out.println(new String(buffer.array()));
            buffer.clear();
            //继续读取
            read = channel.read(buffer);
        }
        fis.close();
    }


    @Test
    //NIO写文件
    public void writeFile() throws Exception {
        //1. 获取文件流
        FileOutputStream fos = new FileOutputStream("write.txt", true);
        //2. 利用流获取通道
        FileChannel channel = fos.getChannel();
        //3. 创建一个缓冲区
        ByteBuffer byteBuffer = ByteBuffer.wrap("利用NIO向文件中写入数据！".getBytes());
        int write = channel.write(byteBuffer);
    }

    @Test
    //NIO拷贝文件内容
    public void copyFile() throws Exception {
        //1. 获取文件流
        FileOutputStream fos = new FileOutputStream("write.txt", true);
        FileInputStream fis = new FileInputStream("read.txt");
        //2. 利用流获取通道
        FileChannel fosChannel = fos.getChannel();
        FileChannel fisChannel = fis.getChannel();

        fosChannel.transferFrom(fisChannel, fosChannel.position(), fisChannel.size());

    }

}
