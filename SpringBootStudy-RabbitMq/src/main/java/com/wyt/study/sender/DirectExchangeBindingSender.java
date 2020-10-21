package com.wyt.study.sender;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DirectExchangeBindingSender {

    private final String EXCHANGE = "directExchange";
    private final String ROUTING_KEY = "direct_routingKey";
    private final String QUEUE_NAME = "directExchangeBindingQueue";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsg(String msg) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timeStr = dtf.format(LocalDateTime.now());
        System.out.printf("%s - %s - [%s]发送消息: %s\n", this.getClass().getName(), timeStr, ROUTING_KEY, msg);

        rabbitTemplate.convertAndSend(EXCHANGE, ROUTING_KEY, msg);

    }

}
