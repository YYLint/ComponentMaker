package com.duowan.mobile.entlive.annotation;

import com.duowan.mobile.entlive.domain.InitLevel;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by ericwu on 2017/4/13.
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.SOURCE)
@Inherited
public @interface DefaultInitAttr {
    InitLevel initLevel() default InitLevel.VERY_LOW;
}
