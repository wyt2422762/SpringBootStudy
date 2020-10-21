package com.wyt.study.receiver;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Component
@RabbitListener(queues = "directExchangeBindingQueue1")
public class DirectExchangeBindingReceiver1 {
    private final String QUEUE_NAME = "directExchangeBindingQueue1";

    /**
     * 消息消费
     *
     * @RabbitHandler 代表此方法为接受到消息后的处理方法
     */
    @RabbitHandler
    public void recieve(String msg) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timeStr = dtf.format(LocalDateTime.now());
        System.out.printf("%s - %s - [%s]接收消息: %s\n", this.getClass().getName(), timeStr, QUEUE_NAME, msg);
    }
}
