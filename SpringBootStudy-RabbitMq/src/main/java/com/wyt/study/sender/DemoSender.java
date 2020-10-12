package com.wyt.study.sender;


import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DemoSender {
    private final String QUEUE_NAME = "demoQueue";

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void sendMsg(String msg) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timeStr = dtf.format(LocalDateTime.now());
        System.out.printf("%s - %s - [%s]发送消息: %s\n", this.getClass().getName(), timeStr, QUEUE_NAME, msg);

        /**
         * 参数1 routingKey 这里是队列名称
         * 参数2 消息体
         */
        amqpTemplate.convertAndSend(QUEUE_NAME, msg);
    }
}
