package com.ngts.common.utils;

import java.util.Date;

public class TimerUtils {

    static final long ONE_MINUTE_IN_MILLIS = 60000;

    public Date addMinutesToDate(int minutesToExpire) {
        long curTimeInMs = new Date().getTime();
        Date afterAddingMins = new Date(curTimeInMs + (minutesToExpire * ONE_MINUTE_IN_MILLIS));
        return afterAddingMins;
    }
}
