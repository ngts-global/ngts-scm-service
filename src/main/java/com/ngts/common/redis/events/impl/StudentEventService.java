package com.ngts.common.redis.events.impl;

import com.ngts.common.redis.events.StudentEventObj;

public interface StudentEventService {
    public String publishEvent(StudentEventObj eventObj);
}
