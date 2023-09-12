package com.ngts.common.redis.events.impl;

import com.ngts.common.redis.events.StudentEventObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Component;

@Component
public class StudentEventServiceImpl implements StudentEventService{

    @Autowired
    @Qualifier("customRedisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ChannelTopic channelTopic;

    public String publishEvent(StudentEventObj eventObj){
        System.out.println("publishing events in Redis ...." + eventObj.getEmail());
        return redisTemplate.convertAndSend(channelTopic.getTopic(), eventObj).toString();
    }
}
