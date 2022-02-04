package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisClusterConnection;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import java.util.List;

@SpringBootApplication
@EnableRedisRepositories
public class DemoApplication implements CommandLineRunner {


    private final ApplicationContext context;

    public DemoApplication(final ApplicationContext context) {
        this.context = context;
    }


    @Override
    public void run(final String... args) {
        final RedisConnectionFactory bean = context.getBean(RedisConnectionFactory.class);
        final RedisConnection connection = bean.getConnection();

        if (connection instanceof RedisClusterConnection) {
            System.out.println("Cluster connection");
        } else {
            System.out.println("Standalone connection");
        }
        System.out.println(connection.getClass());
        System.out.println("DONE");

    }

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        return new JedisConnectionFactory(new RedisClusterConfiguration(List.of("127.0.0.1:16379")));
    }

    public static void main(final String... args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}
