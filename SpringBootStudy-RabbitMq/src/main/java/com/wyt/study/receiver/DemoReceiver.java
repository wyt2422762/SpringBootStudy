package com.wyt.study.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@RabbitListener(queues = "demoQueue") //监听消息队列 demoQueue
public class DemoReceiver {

    private final String QUEUE_NAME = "demoQueue";

    /**
     * 消息消费
     *
     * @RabbitHandler 代表此方法为接受到消息后的处理方法
     */
    @RabbitHandler
    public void recieved(String msg) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timeStr = dtf.format(LocalDateTime.now());
        System.out.printf("%s - [%s]接收消息: %s\n", timeStr, QUEUE_NAME, msg);
    }
}