package com.duowan.mobile.entlive.annotation;

import com.duowan.mobile.entlive.domain.InitLevel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by ericwu on 2017/4/16.
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
public @interface InitAttrConfig {
    InitLevel initLevel();
    int resourceId();
    Class component();
}
