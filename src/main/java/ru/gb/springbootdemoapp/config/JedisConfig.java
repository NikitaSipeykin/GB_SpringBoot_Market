package ru.gb.springbootdemoapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class JedisConfig {

  @Bean
  JedisConnectionFactory jedisConnectionFactory(){
    JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
    jedisConnectionFactory.setHostName("localhost");
    jedisConnectionFactory.setPort(9999);
    return jedisConnectionFactory;
  }

  @Bean
  RedisTemplate<String, Object> redisTemplate(){
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(jedisConnectionFactory());
    return template;
  }
}
