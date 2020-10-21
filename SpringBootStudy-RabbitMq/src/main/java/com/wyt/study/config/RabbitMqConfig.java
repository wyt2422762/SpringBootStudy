package com.wyt.study.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Autowired
    private CachingConnectionFactory connectionFactory;

    /**
     * 定义一个简单消息队列 demoQueue
     *
     * @return
     */
    @Bean
    public Queue demoQueue() {
        return new Queue("demoQueue");
    }

    /**
     * 定义一个直连交换机
     * @return
     */
    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("directExchange");
    }

    /**
     * 定义一个和交换机绑定的队列
     * @return
     */
    @Bean
    public Queue directExchangeBindingQueue1(){
        return new Queue("directExchangeBindingQueue1");
    }

    /**
     * 定义一个和交换机绑定的队列
     * @return
     */
    @Bean
    public Queue directExchangeBindingQueue2(){
        return new Queue("directExchangeBindingQueue2");
    }

    @Bean
    public Binding directExchangeBinding1(){
        return BindingBuilder.bind(directExchangeBindingQueue1()).to(directExchange()).with("direct_routingKey");
    }

    @Bean
    public Binding directExchangeBinding2(){
        return BindingBuilder.bind(directExchangeBindingQueue2()).to(directExchange()).with("direct_routingKey");
    }

    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        return rabbitTemplate;
    }


}
