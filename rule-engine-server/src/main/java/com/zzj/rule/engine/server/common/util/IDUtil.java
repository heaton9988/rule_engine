package com.zzj.rule.engine.server.common.util;

import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.util.Random;
import java.util.UUID;
import java.util.stream.IntStream;

/**
 * @date : 2019/11/28 11:42 上午
 * @description:
 */
@Slf4j
public final class IDUtil {


    /**
     * 获取去掉横线的长度为32的UUID串.
     *
     * @return uuid.
     */
    public static String get32UUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * 获取带横线的长度为36的UUID串.
     *
     * @return uuid.
     */
    public static String get36UUID() {
        return UUID.randomUUID().toString();
    }


    /**
     * 基于twitter snowfake 算法生成ID
     *
     * @return id
     */
    public static Long getSnowflakeId() {
        return getIdWorker().nextId();
    }


    public static long[] getSnowflakeIds(int count) {
        if (count <= 0) {
            throw new RuntimeException("count must great than 0");
        }
        long[] ids = new long[count];
        for (int i = 0; i < count; i++) {
            ids[i] = getIdWorker().nextId();
        }
        return ids;
    }


    /**
     * @param size
     * @return
     */
    public static String getRandomNumber(int size) {
        Random random = new Random();
        IntStream intStream = random.ints(0, 10);
        StringBuilder builder = new StringBuilder();
        intStream.limit(6).forEach(num -> {
            builder.append(num);
        });
        return builder.toString();
    }


    private static IdWorker buildIdWork() {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            String hostAddress = localHost.getHostAddress();
            log.info(">>> HostAddress: {}", hostAddress);
            long workId = Long.valueOf(hostAddress.substring(hostAddress.lastIndexOf(".") + 1));
            log.info(">>> workId: {}", workId);
            return new IdWorker(workId % IdWorker.maxServerId);
        } catch (Exception e) {
            log.error("buildSnowflakeIdUtil error: ", e);
            return null;
        }
    }

    private static IdWorker idWorker = buildIdWork();

    private static IdWorker getIdWorker() {
        if (idWorker != null) {
            return idWorker;
        }
        synchronized (IDUtil.class) {
            if (idWorker == null) {
                idWorker = buildIdWork();
            }
        }
        return idWorker;
    }

}
