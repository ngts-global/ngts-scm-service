package com.ngts.common.redis;

import com.ngts.common.utils.MapperUtils;
import com.ngts.common.utils.TimerUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Component
@Slf4j
public class RedisCacheUtils {

    private final RedisTemplate<String,Object> redisTemplate;
    static final long ONE_MINUTE_IN_MILLIS = 60000;

    public RedisCacheUtils(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void hSet(String key,Object hashKey,Object value, int minToExpire){
        Map ruleHash= MapperUtils.objectMapper(value,Map.class);
        redisTemplate.opsForHash().put(key, hashKey, ruleHash);
        redisTemplate.expireAt(key, new TimerUtils().addMinutesToDate(minToExpire));
    }

    public List<Object> hValues(String key){
        return  redisTemplate.opsForHash().values(key);
    }

    public Object hGet(String key,Object hashKey){
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    public Date addMinutesToDate(int minutes, Date beforeTime) {
        long curTimeInMs = beforeTime.getTime();
        Date afterAddingMins = new Date(curTimeInMs + (minutes * ONE_MINUTE_IN_MILLIS));
        return afterAddingMins;
    }
}
