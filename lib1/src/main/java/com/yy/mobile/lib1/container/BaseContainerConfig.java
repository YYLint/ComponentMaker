package com.yy.mobile.lib1.container;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.duowan.mobile.entlive.domain.ContainerConfig;

/**
 * 观众端模板配置类，并监听相关事件
 * 不要持有初始化和销毁时传过来的activity实例
 * Created by Administrator on 2017/11/21.
 */
public abstract class BaseContainerConfig implements ContainerConfig {
    public static final int ROTATE_3D = 4; // 1，2，3，被fragmentTransition用了，所以用4

    public abstract void onContainerInit(@NonNull Activity activity, Bundle arguments); //模板初始化

    public abstract void onContainerUninit(@NonNull Activity activity); //模板被销毁

    public abstract void onInitDone(@NonNull Activity activity);

}
