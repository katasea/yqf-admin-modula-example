package cn.business.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import org.springframework.data.redis.core.RedisTemplate;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Data
@Component
public class RedisLock {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	/**
	 * 重试时间
	 */
	private static final int DEFAULT_ACQUIRY_RETRY_MILLIS = 100;
	/**
	 * 锁的后缀
	 */
	private static final String LOCK_SUFFIX = "_redis_lock";
	/**
	 * 锁的key
	 */
	private String lockKey;

	/**
	 * 锁超时时间，防止线程在入锁以后，防止阻塞后面的线程无法获取锁
	 */
	private int expireMsecs = 2 * 60 * 1000;
	/**
	 * 线程获取锁的等待时间
	 */
	private int timeoutMsecs = 10 * 1000;
	/**
	 * 是否锁定标志
	 */
	private volatile boolean locked = false;


	/**
	 * 封装和jedis方法
	 *
	 * @param key
	 * @return
	 */
	private String get(final String key) {
		Object obj = redisTemplate.opsForValue().get(key);
		return obj != null ? obj.toString() : null;
	}

	/**
	 * 封装和jedis方法
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	private boolean setNX(final String key, final String value) {
		return redisTemplate.opsForValue().setIfAbsent(key, value);
	}

	/**
	 * 封装和jedis方法
	 *
	 * @param key
	 * @param value
	 * @return
	 */
	private String getSet(final String key, final String value) {
		Object obj = redisTemplate.opsForValue().getAndSet(key, value);
		return obj != null ? (String) obj : null;
	}


	//=================================公共方法区============================//

	/**
	 * 初始化参数
	 * 默认锁超时时间 2分钟
	 * 默认获取锁等待时间 10秒
	 *
	 * @param lockKey 锁的key
	 */
	public void init(String lockKey) {
		this.lockKey = lockKey + LOCK_SUFFIX;
	}

	/**
	 * 初始化参数
	 *
	 * @param lockKey      锁的key
	 * @param timeoutMsecs 获取锁的超时时间 单位毫秒 eg：2 * 60 * 1000
	 */
	public void init(String lockKey, int timeoutMsecs) {
		this.lockKey = lockKey + LOCK_SUFFIX;
		this.timeoutMsecs = timeoutMsecs;
	}

	/**
	 * 初始化参数
	 *
	 * @param lockKey      锁的key
	 * @param timeoutMsecs 获取锁的超时时间
	 * @param expireMsecs  锁的有效期 单位毫秒 eg: 10 * 1000
	 */
	public void init(String lockKey, int timeoutMsecs, int expireMsecs) {
		this.lockKey = lockKey + LOCK_SUFFIX;
		this.timeoutMsecs = timeoutMsecs;
		this.expireMsecs = expireMsecs;
	}

	public String getLockKey() {
		return lockKey;
	}

	/**
	 * 获取锁
	 *
	 * @return 获取锁成功返回ture，超时返回false
	 * @throws InterruptedException
	 */
	public synchronized boolean lock() throws InterruptedException {
		int timeout = timeoutMsecs;
		logger.info("准备获取锁{}，获取锁超时时间为{}",this.getLockKey(),timeout);
		while (timeout >= 0) {
			long expires = System.currentTimeMillis() + expireMsecs + 1;
			String expiresStr = String.valueOf(expires); // 锁到期时间
			if (this.setNX(lockKey, expiresStr)) {
				locked = true;
				logger.info("成功获取锁{}，锁超时时间为{}",this.getLockKey(),expires);
				return true;
			}
			// redis里key的时间
			String currentValue = this.get(lockKey);
			// 判断锁是否已经过期，过期则重新设置并获取
			if (currentValue != null && Long.parseLong(currentValue) < System.currentTimeMillis()) {
				// 设置锁并返回旧值
				String oldValue = this.getSet(lockKey, expiresStr);
				// 比较锁的时间，如果不一致则可能是其他锁已经修改了值并获取
				if (oldValue != null && oldValue.equals(currentValue)) {
					logger.info("成功获取锁{}，锁超时时间为{}",this.getLockKey(),expires);
					locked = true;
					return true;
				}
			}
			timeout -= DEFAULT_ACQUIRY_RETRY_MILLIS;
			// 延时
			Thread.sleep(DEFAULT_ACQUIRY_RETRY_MILLIS);
		}
		logger.warn("获取锁{}失败了！",this.getLockKey());
		return false;
	}

	/**
	 * 释放获取到的锁
	 */
	public void unlock() {
		logger.info("准备释放锁{}",this.getLockKey());
		if (locked) {
			redisTemplate.delete(lockKey);
			logger.info("成功释放锁{}",this.getLockKey());
			locked = false;
		}
	}

}