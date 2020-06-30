package com.wyt.study.bean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * FanctoryBean
 */
//@Component
public class CusFactoryBean implements FactoryBean {

    private Class clazz;

    public CusFactoryBean(Class clazz) {
        this.clazz = clazz;
    }

    @Override
    public Object getObject() throws Exception {
        System.out.println("FactoryBean getObject");

        Object instance = Proxy.newProxyInstance(CusFactoryBean.class.getClassLoader(), new Class[]{clazz}, (proxy, method, args) -> {
            System.out.println("method = " + method);
            return null;
        });

        return instance;
    }

    @Override
    public Class<?> getObjectType() {
        return clazz;
    }
}
