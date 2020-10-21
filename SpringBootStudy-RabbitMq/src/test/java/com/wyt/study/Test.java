package com.wyt.study;

import com.wyt.study.sender.DemoSender;
import com.wyt.study.sender.DirectExchangeBindingSender;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test {

    @Autowired
    private DemoSender demoSender;
    @Autowired
    private DirectExchangeBindingSender directExchangeBindingSender;

    @org.junit.Test
    public void test(){
        //demoSender.sendMsg("Hello World");
        directExchangeBindingSender.sendMsg("directExchangeBinding");
    }

}
