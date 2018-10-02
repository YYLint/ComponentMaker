package com.duowan.mobile.entlive.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 每个 {@link ModuleConfigs} 的初始化配置。
 *
 * @author Abel Joo http://abeljoo.github.io/
 * @Date 2018/8/15
 * @Email zhujiajun@yy.com
 * @YY 909019111
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface ModuleConfigInitialize {

    /**
     * 需要初始化的 Module 集合
     *
     * @return
     */
    Class[] moduleMembers() default {};
}
