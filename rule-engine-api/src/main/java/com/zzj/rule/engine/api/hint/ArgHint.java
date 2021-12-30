package com.zzj.rule.engine.api.hint;

/**
 * @author : zhuhongying@bytedance.com
 * @since : 2021/5/6
 */
public interface ArgHint extends Comparable<ArgHint> {

    /** Hit之间的排序值, order 越小越靠前 */
    int order();

    /** 根据Arg上挂载的Hint判断是否命中这个处理钩子 */
    boolean matchHint(String[] hints);

    /** 返回钩子的处理器 */
    ArgHintHandler getHandler();

    @Override
    default int compareTo(ArgHint argHint) {
        return order() - argHint.order();
    }

}
