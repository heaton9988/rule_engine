///**
// * *****************************************************
// * Copyright (C) 2021 bytedance.com. All Rights Reserved
// * This file is part of bytedance EA project.
// * Unauthorized copy of this file, via any medium is strictly prohibited.
// * Proprietary and Confidential.
// * ****************************************************
// **/
//
//package com.zzj.rule.server.common.util.tag.form;
//
//import com.bytedance.ea.saas.util.JSONUtil;
//import com.zzj.rule.server.common.util.JSONUtil;
//import io.vavr.Tuple3;
//
//import java.lang.annotation.Documented;
//import java.lang.annotation.Inherited;
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author liuzhifu<liuzhifu.deaco @ bytedance.com>
// * @date 06/10/2021 9:52 下午
// */
//public class Form {
//    public @interface Entity {
//    }
//
//    public @interface AggrRoot {
//    }
//
//    //entities and aggrRoot
//    @Retention(RetentionPolicy.RUNTIME)
//    @Inherited
//    @Documented
//    public @interface Scene {
//        String name() default "";
//
//        String description() default "";
//    }
//
//    @Retention(RetentionPolicy.RUNTIME)
//    @Inherited
//    @Documented
//    @Scene(name = "QueryUpdateSceneAno", description = "")
//    public @interface QueryUpdateSceneAno {
//    }
//
//    @Retention(RetentionPolicy.RUNTIME)
//    @Inherited
//    @Documented
//    @Scene(name = "CreateSceneAno", description = "")
//    public @interface CreateSceneAno {
//    }
//
//    @Retention(RetentionPolicy.RUNTIME)
//    @Inherited
//    @Documented
//    @Scene(name = "RetrySceneAno", description = "")
//    public @interface RetrySceneAno {
//    }
//
//    @Retention(RetentionPolicy.RUNTIME)
//    @Inherited
//    @Documented
//    @Scene(name = "UpdateAccoutingSceneAno", description = "")
//    public @interface UpdateAccoutingSceneAno {
//    }
//
//    public @interface InterActProxy {
//    }
//
//    public static class Rules {
//
//    }
//
//    //scene and use case---------application
//    //process
//    static List<Scene> sceneRegister = new ArrayList<>();
//    static List<Tuple3<Scene, Scene, Object>> sceneTrans;
//    //rules
//
//    public Form() {
//    }
//
//    public String tell() {
//        return "nothing";
//    }
//
//    @Override
//    public String toString() {
//        final StringBuffer sb = new StringBuffer("");
//        sb.append(JSONUtil.toJson(sceneRegister));
//        sb.append("");
//        return sb.toString();
//    }
//
//    public static void main(String[] args) {
//        Form baseDomain = new Form();
//        System.out.println(baseDomain);
//    }
//}
