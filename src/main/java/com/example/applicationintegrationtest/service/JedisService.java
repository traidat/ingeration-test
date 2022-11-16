package com.example.applicationintegrationtest.service;

import com.example.applicationintegrationtest.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisSentinelPool;

@Service
@RequiredArgsConstructor
public class JedisService {
    private final JedisSentinelPool pool;

    public void set(User user) {
        try (Jedis jedis = pool.getResource()) {
            jedis.set(String.valueOf(user.getId()), user.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String get(String id) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.get(id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
