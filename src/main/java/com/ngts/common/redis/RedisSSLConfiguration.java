package com.ngts.common.redis;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.SslOptions;
import io.lettuce.core.protocol.ProtocolVersion;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class RedisSSLConfiguration {

    @Value("${redis.host}")
    private String host;

    @Value("${redis.port}")
    private int port;

    @Value("${redis.pass}")
    private String password;

    @Value("${spring.redis.ssl:false}")
    private boolean sslEnabled;

    private final ResourceLoader resourceLoader;

    @Bean
    RedisConnectionFactory redisConnectionFactory() throws IOException {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPort(port);
        redisStandaloneConfiguration.setPassword(password);

        LettuceClientConfiguration.LettuceClientConfigurationBuilder lettuceClientConfigurationBuilder =
                LettuceClientConfiguration.builder();

        if (sslEnabled){
            SslOptions sslOptions = SslOptions.builder()
                    .trustManager(resourceLoader.getResource("classpath:redis.pem").getFile())
                    .build();

            ClientOptions clientOptions = ClientOptions
                    .builder()
                    .sslOptions(sslOptions)
                    .protocolVersion(ProtocolVersion.RESP3)
                    .build();

            lettuceClientConfigurationBuilder
                    .clientOptions(clientOptions)
                    .useSsl();
        }

        LettuceClientConfiguration lettuceClientConfiguration = lettuceClientConfigurationBuilder.build();

        return new LettuceConnectionFactory(redisStandaloneConfiguration, lettuceClientConfiguration);
    }

    @Bean(name = "pubsubRedisTemplate")
    RedisTemplate< String, Object> redisTemplate() throws Exception {
        final RedisTemplate< String, Object> template = new RedisTemplate< String, Object>();
        template.setConnectionFactory(redisConnectionFactory());
        template.setKeySerializer(new StringRedisSerializer());

        template.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        template.setValueSerializer(new Jackson2JsonRedisSerializer<>(Object.class));
        return template;
    }

    @Value("${redis.pubsub.topic:scm-events}")
    private String redisPubSubTopic;

    @Bean
    public ChannelTopic topic() {
        return new ChannelTopic(redisPubSubTopic);
    }

}