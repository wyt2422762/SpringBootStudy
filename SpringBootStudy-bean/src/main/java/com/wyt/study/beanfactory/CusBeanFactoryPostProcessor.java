package com.wyt.study.beanfactory;

import com.wyt.study.bean.Bean2;
import com.wyt.study.bean.Bean3;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.stereotype.Component;

@Component
public class CusBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        //注册一个bean
        beanFactory.registerSingleton("bean2", new Bean2());
        /*//获取beanDefinition
        GenericBeanDefinition beanDefinition = (GenericBeanDefinition) beanFactory.getBeanDefinition("bean2");
        //修改bean对应的class
        beanDefinition.setBeanClass(Bean3.class);*/
    }
}
