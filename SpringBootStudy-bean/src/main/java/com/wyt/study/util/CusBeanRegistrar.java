package com.wyt.study.util;


import com.wyt.study.bean.CusFactoryBean;
import com.wyt.study.mapper.OrderMapper;
import com.wyt.study.mapper.UserMapper;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CusBeanRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {

        List<Class> list = Arrays.asList(new Class[]{UserMapper.class, OrderMapper.class});

        for (Class clazz : list) {
            //创建 BeanDefinitionBuilder
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition();
            //创建 BeanDefinition
            AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
            //设置 BeanDefinition
            beanDefinition.setBeanClass(CusFactoryBean.class);
            beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(clazz);
            //注册 BeanDefinition
            registry.registerBeanDefinition(clazz.getName(), beanDefinition);
        }
    }

}
