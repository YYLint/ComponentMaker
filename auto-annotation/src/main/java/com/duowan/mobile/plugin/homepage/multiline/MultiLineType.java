package com.duowan.mobile.plugin.homepage.multiline;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.swing.text.View;

/**
 * Class Name: HomeType
 * Description: 类功能说明
 * Author: 郑永欣
 * Date: 2017/11/15
 * Modified History: 修改记录，格式(Name)  (Version)  (Date) (Reason & Contents)
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface MultiLineType {
    int[] type();

    int xml() default 0;

    String holderPackage() default "";

    Class viewClass() default View.class;

    Class contentClass();
}
