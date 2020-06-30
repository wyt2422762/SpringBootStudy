package com.wyt.study.service;

import com.wyt.study.mapper.OrderMapper;
import com.wyt.study.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OrderMapper orderMapper;

    public void test(){
        userMapper.selectById();
        System.out.println("------------------------");
        orderMapper.selectById();
    }

}
