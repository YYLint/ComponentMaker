package com.duowan.mobile.entlive.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 运用在 {@link ModuleConfigs#diffSupplement()} 的差异配置。
 * 用途是，如果某个 component 需要增删 module，则可以通过声明需要增减的 module，以达到效果。
 *
 * @author Abel Joo http://abeljoo.github.io/
 * @Date 2018/8/15
 * @Email zhujiajun@yy.com
 * @YY 909019111
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface ModuleConfigDiffSupplement {

    /**
     * 唯一命名
     *
     * @return
     */
    String diffName();

    /**
     * 在 {@link ModuleConfigInitialize#moduleMembers()} 的基础上，需要增加的 module。
     * 新增的 module 会直接在布局的最上层依次叠加。
     *
     * @return
     */
    Class[] appendMembers() default {};

    /**
     * 所指定的 module 如果存在 {@link ModuleConfigInitialize#moduleMembers()} 中，则会被移除。
     *
     * [NOTE]：注意，实际上操作的并不是真正的移除，而是仅仅把 module 隐藏起来。如果被再次唤出，则会将其显示。
     *
     * @return
     */
    Class[] removeMembers() default {};
}
