//package com.dokebi.dalkom.common.config;
//
//import lombok.RequiredArgsConstructor;
//import lombok.Value;
//import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//@RequiredArgsConstructor
//@Configuration
//@EnableRedisRepositories // Redis를 사용한다고 명시해주는 어노테이션
//public class RedisConfig {
//    // Redis 서버와의 연결 정보를 저장하는 객체
//    private final RedisProperties redisProperties;
//
//    // redisConnectionFactory()
//    // LettuceConnectionFactory 객체를 생성하여 반환하는 메서드. Lettuce를 사용해서 Redis 서버와 연결해 준다.
//    @Bean
//    public RedisConnectionFactory redisConnectionFactory(){
//        // RedisProperties로 yml에 저장한 host, port를 연결
//        return new LettuceConnectionFactory(redisProperties.getHost(), redisProperties.getPort());
//    }
//
//    // redisTemplate()
//    // RedisTemplate 객체를 생성하여 반환한다. RedisTemplate은 Redis 데이터를 저장하고 조회하는 기능을 하는 클래스
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(){
//        // serializer 설정으로 redis-cli를 통해 직접 데이터를 조회할 수 있도록 설정
//        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
//        // Redis Key를 문자열로 반환
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
//        // Redis Value를 문자열로 반환
//        redisTemplate.setValueSerializer(new StringRedisSerializer());
//        redisTemplate.setConnectionFactory(redisConnectionFactory());
//
//        return redisTemplate;
//    }
//}

package com.dokebi.dalkom.common.config;

import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import lombok.Generated;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Configuration
@EnableRedisRepositories // Redis를 사용한다고 명시해주는 어노테이션
@Generated
public class RedisConfig {
	// Redis 서버와의 연결 정보를 저장하는 객체
	private final RedisProperties redisProperties;

	// redisConnectionFactory()
	// LettuceConnectionFactory 객체를 생성하여 반환하는 메서드. Lettuce를 사용해서 Redis 서버와 연결해 준다.
	@Bean
	public RedisConnectionFactory redisConnectionFactory() {
		// RedisProperties로 yml에 저장한 host, port를 연결
		RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
		redisStandaloneConfiguration.setHostName(redisProperties.getHost());
		redisStandaloneConfiguration.setPort(redisProperties.getPort());

		// 패스워드 인증을 위해 RedisConnectionFactory 생성 시 설정
		LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);
		lettuceConnectionFactory.setPassword(redisProperties.getPassword());

		return lettuceConnectionFactory;
	}

	// redisTemplate()
	// RedisTemplate 객체를 생성하여 반환한다. RedisTemplate은 Redis 데이터를 저장하고 조회하는 기능을 하는 클래스
	@Bean
	public RedisTemplate<String, Object> redisTemplate() {
		// serializer 설정으로 redis-cli를 통해 직접 데이터를 조회할 수 있도록 설정
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		// Redis Key를 문자열로 반환
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		// Redis Value를 문자열로 반환
		redisTemplate.setValueSerializer(new StringRedisSerializer());
		// Redis Connection Factory를 설정
		redisTemplate.setConnectionFactory(redisConnectionFactory());

		return redisTemplate;
	}
}
