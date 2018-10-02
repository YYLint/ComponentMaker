package com.yy.mobile.ui.basicchanneltemplate.generate;

import android.os.Bundle;

import com.duowan.mobile.entlive.domain.ContainerConfig;
import com.yy.mobile.lib1.component.Component;

import java.util.HashMap;

/**
 * Created by ericwu on 2017/4/9.
 */
public abstract class AbstractComponentWrapper<T extends Component> implements ComponentWrapper<T> {
    protected HashMap<Class<? extends ContainerConfig>, InitConfig> mConfigs; //开太多hashmap？能排重，List也可以替代，后续衡量呗
    private T mInstance; //这个可能要做成多个实例
    private int mDefaultInitLevel;

    public AbstractComponentWrapper(int defaultLevel) {
        mDefaultInitLevel = defaultLevel;
    }

    @Override
    public boolean enableForDebug() {
        return false;
    }

    @Override
    public int initLevel(Class<? extends ContainerConfig> container) {
        if (mConfigs != null) {
            InitConfig config = mConfigs.get(container);
            if (config != null) {
                return config.getLevel();
            }
        }
        return mDefaultInitLevel;
    }

    @Override
    public int resourceId(Class<? extends ContainerConfig> container) {
        if (mConfigs != null) {
            InitConfig config = mConfigs.get(container);
            if (config != null) {
                return config.getResourceId();
            }
        }
        return -1;
    }

    @Override
    public Bundle initBundle(Class<? extends ContainerConfig> container) {
        Bundle bundle = getConfigBundle(container);
        if (bundle == null) {
            bundle = new Bundle();
        }
        return bundle;
    }

    abstract public Bundle getConfigBundle(Class<? extends ContainerConfig> container);

    @Override
    public T instance(Bundle bundle) {
        if (mInstance == null) {
            mInstance = creatInstance(bundle);
        } else {
            // Fragment fragment = mInstance.getContent();
            // Bundle arg = fragment.getArguments();
            // if (arg != null) {
            //     arg.clear();
            //     if (bundle != null) {
            //         arg.putAll(bundle);
            //     } else {
            //
            //     }
            // }
        }
        return mInstance;
//        return creatInstance(bundle);
    }

    @Override
    public T next() {
        return null;
    }

    abstract public T creatInstance(Bundle bundle);
}
