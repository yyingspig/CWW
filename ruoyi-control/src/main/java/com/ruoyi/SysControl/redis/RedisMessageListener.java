package com.ruoyi.SysControl.redis;

import com.ruoyi.SysControl.service.ISensorDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class RedisMessageListener implements MessageListener {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private ISensorDataService service;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String key = "servo";
        String status = redisTemplate.opsForValue().get("autoStatus");
        // 获取键对应的值
        String value = redisTemplate.opsForValue().get(key);
        if ("1".equals(status)) {
            if (new BigDecimal(value).compareTo(new BigDecimal(300)) > 0) {
                service.switchLED(1);
            } else {
                service.switchLED(0);
            }
        }
    }
}

