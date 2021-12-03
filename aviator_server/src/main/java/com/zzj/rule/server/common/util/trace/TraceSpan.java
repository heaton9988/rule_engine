package com.zzj.rule.server.common.util.trace;

import lombok.Data;
import lombok.ToString;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : jianglei.d@bytedance.com
 * @date : 2020/10/25 9:52 下午
 * @description:
 */
@Data
@ToString
class TraceSpan {
    private int id;
    private int pid;
    private String time;
    private String traceId;
    private String errorCode;

    public TraceSpan() {
        time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").format(new Date());
    }
}
