package com.wyt.study;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisSimpleTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    RedisUtil redisUtil;

    @Test
    public void test(){
        /*Object test = redisTemplate.opsForValue().get("test");
        System.out.println("redis查询结果：" + test);*/

        String o = redisUtil.rpoplpushB("list", "list2", 10).toString();

        System.out.println(o);
    }
}
