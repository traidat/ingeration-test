package com.example.applicationintegrationtest;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import lombok.extern.slf4j.Slf4j;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;
import org.testcontainers.images.PullPolicy;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

import java.util.ArrayList;
import java.util.List;


@Slf4j
public class EnvironmentForTest {
    public static final PostgreSQLContainer<?> postgresql;
    public static final GenericContainer<?> redisMaster;
    public static final GenericContainer<?> redisSentinel;
//    public static final GenericContainer<?> verifyContainer;

    static {
        postgresql = new PostgreSQLContainer<>(DockerImageName.parse("postgres:13.2"))
                .withDatabaseName("user_db")
                .withUsername("test")
                .withPassword("test")
//                .withCreateContainerCmdModifier(cmd -> cmd.getHostConfig()
//                        .withPortBindings(new PortBinding(Ports.Binding.bindPort(5432), new ExposedPort(5432))))
//                .withReuse(true)
                .withInitScript("init.sql");
//
        postgresql.start();

        redisMaster =
                new GenericContainer<>("bitnami/redis:6.2.6-debian-10-r16")
                        .withEnv("REDIS_PASSWORD", "redis_test")
                        .withCreateContainerCmdModifier(cmd -> cmd.getHostConfig()
                                .withPortBindings(new PortBinding(Ports.Binding.bindPort(6378), new ExposedPort(6379))))
                        .withExposedPorts(6379);
        redisMaster.start();

        redisSentinel =
                new GenericContainer<>("bitnami/redis-sentinel:6.2.6-debian-10-r16")
                        .withEnv("REDIS_MASTER_HOST", redisMaster.getHost())
                        .withEnv("REDIS_MASTER_PORT_NUMBER", "6378")
                        .withEnv("REDIS_MASTER_PASSWORD", "redis_test")
                        .withEnv("REDIS_SENTINEL_PASSWORD", "redis_test")
                        .withExposedPorts(26379).dependsOn(redisMaster);
        redisSentinel.start();

//        verifyContainer = new GenericContainer<>("verify-user:1.0.0")
//                .withLogConsumer(new Slf4jLogConsumer(log))
//                .withImagePullPolicy(PullPolicy.defaultPolicy())
//                .dependsOn(List.of(postgresql, redisSentinel))
//                .withEnv("DB_PASSWORD", postgresql.getPassword())
//                .withEnv("DB_URL", postgresql.getJdbcUrl())
//                .withEnv("DB_USERNAME", postgresql.getUsername())
//                .withEnv("REDIS_NODE", redisSentinel.getHost() + ":" + redisSentinel.getMappedPort(26379))
//                .withExposedPorts(8080);
//        verifyContainer.start();
    }

    @DynamicPropertySource
    static void overrideTestProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresql::getJdbcUrl);
        registry.add("spring.datasource.username", postgresql::getUsername);
        registry.add("spring.datasource.password", postgresql::getPassword);
        registry.add("spring.redis.sentinel.master", () -> "mymaster");
        registry.add("spring.redis.sentinel.password", () -> "redis_test");
        registry.add("spring.redis.password", () -> "redis_test");
        registry.add("spring.redis.sentinel.nodes", () -> redisSentinel.getHost() + ":" + redisSentinel.getMappedPort(26379));
    }
}
