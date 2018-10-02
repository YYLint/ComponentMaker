package com.yy.mobile.ui.basicchanneltemplate.generate;

import android.os.Bundle;

import com.duowan.mobile.entlive.domain.ContainerConfig;
import com.yy.mobile.lib1.component.Component;

/**
 * Created by ericwu on 2017/4/9.
 */

public interface ComponentWrapper<T extends Component> {
    T instance(Bundle bundle);

    boolean enableForDebug();

    T next();

    int initLevel(Class<? extends ContainerConfig> container);

    int resourceId(Class<? extends ContainerConfig> container);

    Bundle initBundle(Class<? extends ContainerConfig> container);

}
