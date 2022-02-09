
package com.zzj.rule.engine.server.common.util;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;

import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

/**
 * <p>控制频率的日志工具类</p>
 *
 * @date 07/30/2021
 */
@Slf4j
public class RateLimitLogUtil {
    public static TreeMap<String, Long> eventName2startTime = Maps.newTreeMap();
    public static TreeMap<String, Integer> eventName2count = Maps.newTreeMap();

    // todo zzj 定期清理已过期的eventName, 怕会比较多

    /**
     * @param throwable  异常
     * @param eventName  事件名. 同样的事件名, 如果log次数在loopInMs时间内, 超过limitTimes次, 就会被忽略
     * @param msg        log打出来的真正的信息
     * @param duration   与 timeUnit 组合表示
     * @param timeUnit   与 duration 组合表示
     * @param limitTimes 在loopInMs毫秒内只能执行limitTimes次
     */
    public static void error(String eventName, String msg, int duration, TimeUnit timeUnit, int limitTimes, Throwable throwable) {
        try {
            if (thisTimeCanLog(eventName, duration, timeUnit, limitTimes)) {
                log.error(msg, throwable);
            }
        } catch (Exception e) {
            log.error("RateLimitLogUtil.error", e);
        }
    }

    public static void warn(String eventName, String msg, int duration, TimeUnit timeUnit, int limitTimes) {
        try {
            if (thisTimeCanLog(eventName, duration, timeUnit, limitTimes)) {
                log.warn(msg);
            }
        } catch (Exception e) {
            log.error("RateLimitLogUtil.warn", e);
        }
    }

    private static boolean thisTimeCanLog(String eventName, int duration, TimeUnit timeUnit, int limitTimes) {
        Long nowTime = System.currentTimeMillis();

        long loopInMs = timeUnit.toMillis(duration);

        Long startTime = eventName2startTime.get(eventName);
        if (startTime == null || nowTime - startTime >= loopInMs) { // 进入新的周期
            eventName2startTime.put(eventName, nowTime);
            eventName2count.put(eventName, 0);
        }
        Integer count = eventName2count.get(eventName);
        if (count == null || count >= limitTimes) {
            return false;
        } else {
            eventName2count.put(eventName, count + 1);
            return true;
        }
    }
}