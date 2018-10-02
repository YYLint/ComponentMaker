package com.yy.mobile.ui.basicchanneltemplate.generate;

import com.duowan.mobile.entlive.domain.ContainerConfig;
import com.yy.mobile.lib1.component.Component;
import com.yy.mobile.ui.basicchanneltemplate.component.IComponent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by ericwu on 2017/4/9.
 */
public abstract class ComponentsFactory {
    // protected Map<Class<? extends AbstractComponentContainer>, Class<? extends ContainerConfig>> mContainerToConfigMap = new HashMap<>();
    // key是config？
    protected Map<Class<? extends ContainerConfig>, List<Class<? extends IComponent>>> mConfigToComponentMap = new HashMap<>();
    // 真实对象与装饰对象映射缓存
    protected Map<Class<? extends IComponent>, Class<? extends ComponentWrapper>> mComponentWrappers
            = new HashMap<>();

    protected Map<Class<? extends IComponent>, ComponentWrapper> mPluginComponentWrappers = new HashMap<>();

    public ComponentsFactory() {
        initClzMap();
    }

    /**
     * Initialize component classes' map.
     */
    protected abstract void initClzMap();

    /**
     * Initialize component wrapper.
     *
     * @param clz component class name
     * @param <T>
     * @return
     */
    public final <T extends Component> ComponentWrapper<T> create(Class<T> clz) {
        Class<? extends ComponentWrapper> wrapperClass = mComponentWrappers.get(clz);
        try {
            if (wrapperClass != null) {
//            return wrapperClass.getDeclaredConstructor(Class.class)
//                    .newInstance(clz);
                ComponentWrapper<T> instance = wrapperClass.newInstance();
                return instance;
            } else {
                // 去插件进来的数据找temp方案
                return mPluginComponentWrappers.get(clz);
            }
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Get component classes.
     *
     * @return
     */
    public Set<Class<? extends IComponent>> componentClasses() {
        return mComponentWrappers.keySet();
    }

//    public List<Class<? extends IComponent>> componentClasses(Class<? extends IComponentContainer> containerClz) {
//        return mContainerMap.get(containerClz);
//    }

    // public List<Class<? extends IComponent>> componentClzsByContainer(Class<? extends AbstractComponentContainer> containerClz) {
    //     Class configClz = mContainerToConfigMap.get(containerClz);
    //     List<Class<? extends IComponent>> ret = mConfigToComponentMap.get(configClz);
    //     return ret;
    // }
    //
    // public void addPluginComponentInto(Class<? extends AbstractComponentContainer> containerClz, Class<? extends IComponent> componentClz, int resId, Bundle bundle) {
    //     List<Class<? extends IComponent>> res = componentClzsByContainer(containerClz);
    //     if (FP.empty(res) || res.contains(componentClz)) {
    //         return;
    //     }
    //     res.add(componentClz);
    //     ComponentWrapper wrapper = mPluginComponentWrappers.get(componentClz);
    //     if (wrapper instanceof PluginComponentWrapper) {
    //         PluginComponentWrapper pluginWrapper = (PluginComponentWrapper) wrapper;
    //         pluginWrapper.addConfigs(mContainerToConfigMap.get(containerClz), resId);
    //     } else {
    //         PluginComponentWrapper pluginWrapper = new PluginComponentWrapper(componentClz, mContainerToConfigMap.get(containerClz), resId, bundle);
    //         mPluginComponentWrappers.put(componentClz, pluginWrapper);
    //     }
    // }
    //
    // public Class<? extends ContainerConfig> getConfig(Class<? extends AbstractComponentContainer> containerClz) {
    //     return mContainerToConfigMap.get(containerClz);
    // }

    public List<Class<? extends IComponent>> componentClasses(Class<? extends ContainerConfig> config) {
        return mConfigToComponentMap.get(config);
    }
}
