package com.shortener.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.shortener.model.Url;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Url> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Url> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);

        // String serializer for keys
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        // Configure Jackson ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new JavaTimeModule()) // Support Java 8+ Time API
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) // Use ISO format for dates
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // Avoid issues with unknown fields

        // Jackson JSON serializer
        Jackson2JsonRedisSerializer<Url> jsonSerializer = new Jackson2JsonRedisSerializer<>(objectMapper, Url.class);

        // Set value serializers
        redisTemplate.setValueSerializer(jsonSerializer);
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(jsonSerializer);

        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }
}

