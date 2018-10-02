package com.duowan.mobile.entlive.annotation;

import com.duowan.mobile.entlive.domain.ContainerConfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by ericwu on 2018/4/11.
 * 听说反射范型很慢，所以，多做一行代码咯
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface BindConfig {
    Class<? extends ContainerConfig> value();
}
