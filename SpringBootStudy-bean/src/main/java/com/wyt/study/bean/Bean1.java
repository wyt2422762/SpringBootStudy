package com.wyt.study.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

@Component
public class Bean1 implements BeanNameAware, ApplicationContextAware {

    @Autowired
    private Bean2 bean2;

    private String beanName;

    public Bean2 getBean2() {
        return bean2;
    }

    public void setBean2(Bean2 bean2) {
        this.bean2 = bean2;
    }

    public String getBeanName() {
        return beanName;
    }

    @PostConstruct
    public void init() {
        //对象初始化方法
        System.out.println("---- init ----");
    }

    @Override
    public void setBeanName(String s) {
        this.beanName = s;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
}
