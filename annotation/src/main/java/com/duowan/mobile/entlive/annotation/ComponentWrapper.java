package com.duowan.mobile.entlive.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记Component。
 * 注意，因为Container继承于Component，因此同样用于标记Container
 *
 * Created by Abel abeljoo.com
 * Created on 2018/5/25 19:52
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface ComponentWrapper {
}
