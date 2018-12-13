package com.iqb.consumer.mq.common.base;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
 
/**
 * redis dao
 * @author jack
 *
 */
@Component("baseRedisDao")
public class BaseRedisDao {
	
    /**
     * 用户投资反查队列key
     */
    @Value("${biz.redis.queue.asset.allocation.key}")
    protected String BIZ_REDIS_QUEUE_ASSET_ALLOCATION_KEY;
    
	/**
	 * 注入template
	 */
	@Autowired
	@Qualifier("redisTemplate_consumer")  
    private RedisTemplate<String, String> template;
	
	/**
	 * key - value
	 * @param key
	 * @param value
	 */
	public void setKeyAndValue(String key , String value){
		template.opsForValue().set(key, value);
	}
	
	/**
	 * key - value  - timeout(单位：秒)
	 * @param key
	 * @param value
	 */
	public void setKeyAndValueTimeout(String key , String value,long timeout){
		template.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
	}
	
	/**
	 * 根据key查询value
	 * @param key
	 * @return
	 */
	public String getValueByKey(String key){
		return template.opsForValue().get(key);
	}
	
	/**
	 * 根据key删除value
	 * @param key
	 */
	public void removeValueByKey(String key){
		template.delete(key);
	}
	
	
	/**
	 * 推送消息
	 * @param key
	 * @param value
	 */
	public void leftPush(String key,String value){
		template.opsForList().leftPush(key, value);
	}
		
	/**
	 * 消费消息
	 * @param key
	 * @return
	 */
	public String rightPop(String key){		
		String value = template.opsForList().rightPop(key);
		if(null == value || "".equals(value))
			return null;
		return value;
	}
	
	/**
	 * 消费消息并放入备份队列
	 * @param key backupKey
	 * @return
	 */
	public String rightPopAndLeftPush(String key,String backupKey){		
		String value = template.opsForList().rightPopAndLeftPush(key,backupKey);
		if(null == value || "".equals(value))
			return null;
		return value;
	}
}