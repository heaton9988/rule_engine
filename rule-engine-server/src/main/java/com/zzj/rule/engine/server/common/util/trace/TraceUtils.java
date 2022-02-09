//package com.zzj.rule.engine.server.common.util.trace;
//
//import com.bytedance.ea.saas.util.IDUtil;
//import lombok.extern.slf4j.Slf4j;
//import org.slf4j.MDC;
//import org.springframework.util.StringUtils;
//
//import java.util.concurrent.ThreadLocalRandom;
//
///**
// * @Date: 2020/10/26 8:18 下午
// * @Description:
// */
//@Slf4j
//public class TraceUtils {
//
//    public static String trace() {
//        return traceSpan().toString();
//    }
//
//    private static TraceSpan traceSpan() {
//        TraceSpan span = TraceContext.getSpan();
////        log.info("span: {}",span);
//        if (span == null) {
//            span = new TraceSpan();
//            span.setTraceId(getDefaultTraceId());
//            span.setId(ThreadLocalRandom.current().nextInt(1, Integer.MAX_VALUE));
//        } else {
//            TraceSpan oldSpan = span;
//            span = new TraceSpan();
//            span.setTraceId(oldSpan.getTraceId());
//            span.setPid(oldSpan.getId());
//            span.setId(oldSpan.getId() + 1);
//        }
//        TraceContext.push(span);
//        return span;
//    }
//
//    public static String traceId() {
//        return traceSpan().getTraceId();
//    }
//
//    public static String trace(String traceId, int spanId) {
//        if (StringUtils.isEmpty(traceId)) {
//            traceId = getDefaultTraceId();
//        }
//        TraceSpan span = new TraceSpan();
//        span.setTraceId(traceId);
//        span.setPid(spanId);
//        span.setId(spanId + 1);
//        TraceContext.push(span);
//        return span.toString();
//    }
//
//    public static void traceWithPrint() {
//        log.info(trace());
//    }
//
//    private static String getDefaultTraceId() {
//        String traceId = org.apache.skywalking.apm.toolkit.trace.TraceContext.traceId();
//        log.info("111 - traceId:{}", traceId);
//        if (!StringUtils.isEmpty(traceId)) {
//            setTraceId(traceId);
//            return traceId;
//        }
//        traceId = MDC.get(TraceConstants.TRACE_ID_LOGBACK_KEY);
//        log.info("222 - traceId:{}", traceId);
//        if (!StringUtils.isEmpty(traceId)) {
//            setTraceId(traceId);
//            return traceId;
//        }
//        traceId = MDC.get(TraceConstants.TRACE_ID_LOG4J_KEY);
//        log.info("333 - traceId:{}", traceId);
//        if (!StringUtils.isEmpty(traceId)) {
//            setTraceId(traceId);
//            return traceId;
//        }
//        if (StringUtils.isEmpty(traceId)) {
//            traceId = String.valueOf(IDUtil.getSnowflakeId());
//            log.info("444 - traceId:{}", traceId);
//            setTraceId(traceId);
//            return traceId;
//        }
//        log.info("555 - traceId:{}", traceId);
//        return traceId;
//    }
//
//
//    public static String getErrorCode() {
//        return MDC.get(TraceConstants.ERROR_CODE);
//    }
//
//    public static void putErrorCode(String code) {
//        MDC.put(TraceConstants.ERROR_CODE, code);
//    }
//
//
//    public static String get(String key) {
//        return MDC.get(key);
//    }
//
//    public static void put(String key, String value) {
//        MDC.put(key, value);
//    }
//
//
//    public static void setTraceId(String traceId) {
//        MDC.put(TraceConstants.TRACE_ID_LOG4J_KEY, traceId);
//        MDC.put(TraceConstants.TRACE_ID_LOGBACK_KEY, traceId);
//    }
//
//
//}
