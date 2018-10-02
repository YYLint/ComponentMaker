package com.duowan.mobile.entlive.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于装载 module 配置的最外层注解类。一个 {@link ModuleConfigs} 对应一个 component
 *
 * @author Abel Joo http://abeljoo.github.io/
 * @Date 2018/8/15
 * @Email zhujiajun@yy.com
 * @YY 909019111
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface ModuleConfigs {

    /**
     * 当前配置所作用的 component
     *
     * @return
     */
    Class componentName();

    /**
     * 初始化配置
     *
     * @return
     */
    ModuleConfigInitialize initialize();

    /**
     * 与 {@link #initialize()} 所对比的差异配置。可选。
     *
     * @return
     */
    ModuleConfigDiffSupplement[] diffSupplement() default {};
}
