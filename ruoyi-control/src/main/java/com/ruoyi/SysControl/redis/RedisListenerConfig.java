package com.ruoyi.SysControl.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.listener.PatternTopic;

@Configuration
public class RedisListenerConfig {

    @Bean
    public MessageListenerAdapter messageListenerAdapter(RedisMessageListener redisMessageListener) {
        return new MessageListenerAdapter(redisMessageListener);
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory,
                                                                       MessageListenerAdapter messageListenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);

        // 设置监听的频道，这里使用 PatternTopic 来匹配所有键的变化
        container.addMessageListener(messageListenerAdapter, new PatternTopic("__keyspace@0__:servo"));

        return container;
    }
}

