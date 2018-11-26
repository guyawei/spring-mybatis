package com.boot.test.redis;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by sunyulei on 2018/5/14.
 */

@Component
public class RedisRepository {

    protected final Logger logger = Logger.getLogger(this.getClass());


    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public Long lpush(String key, String value) {
        return redisTemplate.opsForList().leftPush(key, value);
    }

    public String rpop(String key) {
        try {
            return redisTemplate.opsForList().rightPop(key);
        } catch (Exception e) {
            logger.error("===rpop===错误" + e);
            return null;
        }
    }

    public long llen(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            logger.error("===llen===错误" + e);
            return 0;
        }
    }


    //===================================string类型常用方法========================================//

    public String getValue(String key) {
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            logger.error("===getValue===错误" + e);
            return null;
        }
    }

    public boolean del(String key) {
        try {
            redisTemplate.delete(key);
            return true;
        } catch (Exception e) {
            logger.error("===del===错误" + e);
            return false;
        }

    }

    public void setExpire(String key, String value, long timeout, TimeUnit timeUnit) {
        this.set(key, value);
        this.expire(key, timeout, timeUnit);
    }

    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void expire(String key, long timeout, TimeUnit TimeUnit) {
        final long timeoutfi = timeout;
        redisTemplate.expire(key, timeoutfi, TimeUnit);
    }

    private static String mapToString(Map<String, String> map) {
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        map.forEach((k,v) ->
            sb.append("\"").append(k).append("\"").append(":").append("\"").append(v).append("\"").append(",")
        );
        sb.deleteCharAt(sb.length()-1);
        sb.append("}");
        // 遍历jsonObject数据，添加到Map对象
        return sb.toString();
    }
}
