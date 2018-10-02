package com.duowan.mobile.entlive.proccessor;

import com.duowan.mobile.entlive.annotation.DefaultInitAttr;
import com.duowan.mobile.entlive.annotation.InitAttrConfig;
import com.duowan.mobile.entlive.domain.InitLevel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;

/**
 * Created by ericwu on 2017/4/11.
 */

public class ComponentUnit {
    private String mClassName; //被标注了的组件
    private List<InitAttrConfig> mConfigs = new ArrayList<>();

    private DefaultInitAttr mDefaultInitAttr;

    private List<Element> mBussinessConfig = new ArrayList<>();

    public String getClassName() {
        return mClassName;
    }

    public String getWrapperClassName() {
        return mClassName + "Wrapper";
    }

    public void setClassName(String mClassName) {
        this.mClassName = mClassName;
    }


    public void addConfigs(InitAttrConfig configs, Element config) {
        mConfigs.add(configs);
        mBussinessConfig.add(config);
    }

    public DefaultInitAttr getDefaultInitAttr() {
        return mDefaultInitAttr;
    }

    public void setDefaultInitAttr(DefaultInitAttr defaultInitAttr) {
        this.mDefaultInitAttr = defaultInitAttr;
    }

    public int getDefaultInitLevel() {
        if (mDefaultInitAttr != null) {
            return mDefaultInitAttr.initLevel().getValue();
        }
        return InitLevel.VERY_LOW.getValue();
    }

    public Element getBussinessId(int index) {
        return mBussinessConfig.get(index);
    }

    public TypeMirror getParentClass(InitAttrConfig config) {
        if (config != null) {
            //这个有效，但写得长
//            List<? extends AnnotationMirror> list = mElement.getAnnotationMirrors();
//            for (AnnotationMirror annotationMirror : list) {
//                if (annotationMirror.getAnnotationType().toString().equals(container.annotationType().getCanonicalName())) {
//                    Map<? extends ExecutableElement, ? extends AnnotationValue> map = annotationMirror.getElementValues();
//                    for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : map.entrySet()) {
//                        return (TypeMirror) entry.getValue().getValue();
//                    }
//                }
//            }
            try {
                config.component();
            } catch (MirroredTypeException e) {
                return e.getTypeMirror();
            }
            return null;
        }
        return null;
    }

    public List<InitAttrConfig> getInitConfigs() {
        return mConfigs;
    }

    private static AnnotationMirror getAnnotationMirror(TypeElement typeElement, Class<?> clazz) {
        String clazzName = clazz.getName();
        for (AnnotationMirror m : typeElement.getAnnotationMirrors()) {
            if (m.getAnnotationType().toString().equals(clazzName)) {
                return m;
            }
        }
        return null;
    }

    private static AnnotationValue getAnnotationValue(AnnotationMirror annotationMirror, String key) {
        for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : annotationMirror.getElementValues().entrySet()) {
            if (entry.getKey().getSimpleName().toString().equals(key)) {
                return entry.getValue();
            }
        }
        return null;
    }

    //这个写得更长些，方法归整得好些
    private TypeMirror getContainerClass(TypeElement foo) {
        AnnotationMirror am = getAnnotationMirror(foo, InitAttrConfig.class);
        if (am == null) {
            return null;
        }
        AnnotationValue av = getAnnotationValue(am, "Value");
        if (av == null) {
            return null;
        } else {
            return (TypeMirror) av.getValue();
        }
    }

}
