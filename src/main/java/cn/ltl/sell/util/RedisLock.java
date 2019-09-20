package cn.ltl.sell.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class RedisLock {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public boolean lock(String key, String value) {
        //加锁
        if(redisTemplate.opsForValue().setIfAbsent(key, value)) {
            return true;
        }

        //1.上锁后异常没有解锁。出现死锁问题。   设置超时时间
        String currentValue = redisTemplate.opsForValue().get(key);
        if (!StringUtils.isEmpty(currentValue) && Long.parseLong(currentValue) < System.currentTimeMillis()){
            //Q2 在线程超时的时候，多个线程争抢锁的问题
            String oldValue = redisTemplate.opsForValue().getAndSet(key, value);
            if (!StringUtils.isEmpty(oldValue) && oldValue.equals(currentValue)){
                return true;
            }
        }
        return false;
    }

    public void unlock(String key, String value) {
        String currentValue = redisTemplate.opsForValue().get(key);
        if (currentValue != null && currentValue.equals(value)) {
            redisTemplate.opsForValue().getOperations().delete(key);
        }
    }
}
