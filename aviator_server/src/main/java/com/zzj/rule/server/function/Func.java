/**
 * *****************************************************
 * Copyright (C) 2021 bytedance.com. All Rights Reserved
 * This file is part of bytedance EA project.
 * Unauthorized copy of this file, via any medium is strictly prohibited.
 * Proprietary and Confidential.
 * ****************************************************
 **/
package com.zzj.rule.server.function;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @date 06/09/2021 10:24 下午
 */
@Component
public class Func implements ApplicationContextAware {
    private static ApplicationContext applicationContext;

//    public static FiLoanListQuery fiLoanListQuery;
//    public static FormLoanDetailQuery formLoanDetailQuery;
//    public static MdmVendorAPI mdmVendorAPI;
//    public static FormPayDetailQuery formPayDetailQuery;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Func.applicationContext = applicationContext;
//        fiLoanListQuery = applicationContext.getBean(FiLoanListQuery.class);
//        formLoanDetailQuery = applicationContext.getBean(FormLoanDetailQuery.class);
//        mdmVendorAPI = applicationContext.getBean(MdmVendorAPIImpl.class);
//        formPayDetailQuery = applicationContext.getBean(FormPayDetailQuery.class);
    }
}