package cn.business.config;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

/**
 * <p>redis缓存配置</p>
 */
@Configuration
@EnableCaching
public class RedisCachedConfig extends CachingConfigurerSupport {
	//过期时间1天
//	private Duration timeToLive = Duration.ofMinutes(1);
	private Duration timeToLive = Duration.ofHours(1);
	@Bean
	public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
		//默认1
		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
				.entryTtl(this.timeToLive)
				.serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer()))
				.serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer()))
				.disableCachingNullValues();
		RedisCacheManager redisCacheManager = RedisCacheManager.builder(connectionFactory)
				.cacheDefaults(config)
				.transactionAware()
				.build();
		return redisCacheManager;
	}
	@Bean(name = "redisTemplate")
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){
		RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(redisConnectionFactory);
		redisTemplate.setKeySerializer(keySerializer());
		redisTemplate.setHashKeySerializer(keySerializer());
		redisTemplate.setValueSerializer(valueSerializer());
		redisTemplate.setHashValueSerializer(valueSerializer());
		return redisTemplate;
	}
	private RedisSerializer<String> keySerializer() {
		return new StringRedisSerializer();
	}
	private RedisSerializer<Object> valueSerializer() {
		return new GenericJackson2JsonRedisSerializer();
	}
}


