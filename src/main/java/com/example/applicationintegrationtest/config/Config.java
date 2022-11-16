package com.example.applicationintegrationtest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisSentinelPool;

import java.util.Set;

@Configuration
public class Config {
    @Value("${spring.redis.sentinel.nodes}")
    private Set<String> nodes;

    @Value("${spring.redis.sentinel.password}")
    private String sentinelPassword;

    @Value("${spring.redis.sentinel.master}")
    private String master;

    @Value("${spring.redis.password}")
    private String masterPassword;

    @Bean
    JedisSentinelPool pool() {
        return new JedisSentinelPool(master, nodes, masterPassword, sentinelPassword);
    }
}
