package com.zzj.rule.engine.server.common.util.trace;


import org.slf4j.MDC;

import java.util.Stack;

/**
 * @date : 2020/10/26 4:01 下午
 * @description:
 */
class TraceContext {
    private static final InheritableThreadLocal<Stack<TraceSpan>> TRACE_CONTEXT = new InheritableThreadLocal<Stack<TraceSpan>>();

    public static void push(TraceSpan span) {
        Stack<TraceSpan> stack = TRACE_CONTEXT.get();
        if (stack == null) {
            stack = new Stack<>();
        }
        stack.push(span);
        TRACE_CONTEXT.set(stack);
        syncToMDC(span);
    }

    public static TraceSpan getSpan() {
        Stack<TraceSpan> stack = TRACE_CONTEXT.get();
        if (stack == null) {
            return null;
        }
        return stack.pop();
    }

    private static void syncToMDC(TraceSpan span) {
        MDC.put(TraceConstants.TRACE_ID_LOGBACK_KEY, span.getTraceId());
        MDC.put(TraceConstants.ERROR_CODE, span.getErrorCode());
    }
}
