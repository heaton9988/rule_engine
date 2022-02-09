package com.zzj.rule.engine.server.common.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @date : 2019/11/28 12:54 下午
 * @description: The class Spring context holder
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     * Gets application context.
     *
     * @return the application context
     */
    public static ApplicationContext getApplicationContext() {
        assertApplicationContext();
        return applicationContext;
    }

    /**
     * Sets application context.
     *
     * @param applicationContext the application context
     * @throws BeansException the beans exception
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        SpringContextUtil.applicationContext = applicationContext;
    }

    /**
     * Gets bean.
     *
     * @param <T>      the type parameter
     * @param beanName the bean name
     * @return the bean
     */
    public static <T> T getBean(String beanName) {
        assertApplicationContext();
        return (T) applicationContext.getBean(beanName);
    }

    /**
     * Gets bean.
     *
     * @param <T>          the type parameter
     * @param requiredType the required type
     * @return the bean
     */
    public static <T> T getBean(Class<T> requiredType) {
        assertApplicationContext();
        return applicationContext.getBean(requiredType);
    }

    public static DefaultListableBeanFactory getDefaultListableBeanFactory() {
        assertApplicationContext();
        return (DefaultListableBeanFactory) ((ConfigurableApplicationContext) applicationContext).getBeanFactory();
    }

    private static void assertApplicationContext() {
        if (SpringContextUtil.applicationContext == null) {
            throw new IllegalArgumentException("applicationContext is null, pleas check SpringContextHolder!");
        }
    }

}
