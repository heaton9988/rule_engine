package com.zzj.rule.server.common.util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;

/**
 * @author : jianglei.d@bytedance.com
 * @date : 2021/2/23 2:05 下午
 * @description:
 */
public class IdWorker {

    private static final long serverIdBits = 6L;
    private final long reserveBits = 2L;
    private final long namespaceBits = 6L;
    private final long sequenceBits = 18L;

    private final long reserveLeftShift = serverIdBits;
    private final long namespaceLeftShift = reserveBits + serverIdBits;
    private final long sequenceLeftShift = namespaceBits + reserveBits + serverIdBits;
    private final long timestampLeftShift = sequenceBits + namespaceBits + reserveBits + serverIdBits;

    /**
     * 支持的最大机器id，结果是63
     */
    public static final long maxServerId = -1L ^ (-1L << serverIdBits);
    /**
     * 生成序列的掩码，这里为4095 (0b111111111111=0xfff=4095)
     */
    private final long sequenceMask = -1L ^ (-1L << sequenceBits);

    private long serverId;
    private long sequence = 0L;
    private long namespace = 63L;
    private long reserve = 3L;
    /**
     * 上次生成ID的时间截
     */
    private long lastTimestamp = -1L;

    //==============================Constructors=====================================

    /**
     * 构造函数
     *
     * @param serverId 工作ID (0~63)
     */
    public IdWorker(long serverId) {
        if (serverId > maxServerId || serverId < 0) {
            throw new IllegalArgumentException(String.format("worker Id can't be greater than %d or less than 0", maxServerId));
        }
        this.serverId = serverId;
    }

    // ==============================Methods==========================================

    /**
     * 测试
     */
    public static void main(String[] args) throws UnknownHostException {
        InetAddress localHost = InetAddress.getLocalHost();
        String hostAddress = localHost.getHostAddress();
        long workId = Long.valueOf(hostAddress.substring(hostAddress.lastIndexOf(".") + 1));
        IdWorker idWorker = new IdWorker(workId % 63);
        for (int i = 0; i < 1000; i++) {
            long id = idWorker.nextId();
            System.out.println(Long.toBinaryString(id));
            System.out.println(id);
        }
    }

    /**
     * 获得下一个ID (该方法是线程安全的)
     *
     * @return SnowflakeId
     */
    public synchronized long nextId() {
        long timestamp = timeGen();

        //如果当前时间小于上一次ID生成的时间戳，说明系统时钟回退过这个时候应当抛出异常
        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    String.format("Clock moved backwards.  Refusing to generate id for %d milliseconds", lastTimestamp - timestamp));
        }

        //如果是同一时间生成的，则进行毫秒内序列
        if (lastTimestamp == timestamp) {
            sequence = (sequence + 1) & sequenceMask;
            //毫秒内序列溢出
            if (sequence == 0) {
                //阻塞到下一个毫秒,获得新的时间戳
                timestamp = tilNextMillis(lastTimestamp);
            }
        }
        //时间戳改变，毫秒内序列重置
        else {
            sequence = 0L;
        }

        //上次生成ID的时间截
        lastTimestamp = timestamp;

        //移位并通过或运算拼到一起组成64位的ID
        return ((timestamp << timestampLeftShift)
                | (sequence << sequenceLeftShift)
                | (namespace << namespaceLeftShift)
                | (reserve << reserveLeftShift)
                | serverId
        );
    }

    /**
     * 阻塞到下一个毫秒，直到获得新的时间戳
     *
     * @param lastTimestamp 上次生成ID的时间截
     * @return 当前时间戳
     */
    protected long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    //==============================Test=============================================

    /**
     * 返回以毫秒为单位的当前时间
     *
     * @return 当前时间(毫秒)
     */
    protected long timeGen() {
        return Instant.now().getEpochSecond();
    }
}