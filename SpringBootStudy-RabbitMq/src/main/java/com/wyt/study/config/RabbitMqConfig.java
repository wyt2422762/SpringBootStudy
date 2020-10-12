package com.wyt.study.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    /**
     * 定义一个简单消息队列 demoQueue
     *
     * @return
     */
    @Bean
    public Queue demoQueue() {
        return new Queue("demoQueue");
    }

}
