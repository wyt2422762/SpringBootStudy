package com.wyt.study;


import com.wyt.study.anno.WytScan;
import com.wyt.study.bean.Bean1;
import com.wyt.study.service.TestService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


/**
 * spring Bean生命周期:
 *  class、xml、注解等 --> BeanDefinition --> BeanFactory --> 一系列 BeanFactoryPostProcessor --> 对象 --> 属性赋值 --> init(before, after) --> aware --> bean --> spring context
 * spring BeanFactory
 *
 */
@WytScan
@SpringBootApplication
public class StartUp {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(StartUp.class, args);

        TestService testService = context.getBean(TestService.class);
        testService.test();


    }
}
