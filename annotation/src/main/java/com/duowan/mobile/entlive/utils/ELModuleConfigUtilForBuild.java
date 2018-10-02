package com.duowan.mobile.entlive.utils;

import com.duowan.mobile.entlive.annotation.ModuleConfigs;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;

/**
 * 此类为编译时使用，在未来可能包含 javax 包下的内容，
 * 例如 {@link MirroredTypesException} 。Android 执行机是不包含 {@link MirroredTypesException} 的，并且
 * 在低版本 Android 会导致 {@link VerifyError} 错误。
 * <p>
 * apk运行时不会用到此类，此类仅在编译时用到，所以此类不会被打包进apk。
 * <p>
 *
 * @see <a href="https://developer.android.com/reference/">reference</a>
 * <p>
 * Created by Abel abeljoo.com
 * Created on 2018/7/11 14:53
 */
public class ELModuleConfigUtilForBuild {

    /**
     * 用于生成Module组的key
     */
    public static String convertToModuleGroupKey(ProcessingEnvironment processingEnv,
                                                 ModuleConfigs config) {
        try {
            config.componentName();
        } catch (MirroredTypesException e) {
            for (TypeMirror tm : e.getTypeMirrors()) {
                TypeElement classTypeElement = asTypeElement(processingEnv, tm);
                return classTypeElement.getQualifiedName().toString();
            }
        }
        return "";
    }

    public static TypeElement asTypeElement(ProcessingEnvironment processingEnv,
                                            TypeMirror typeMirror) {
        Types typeutils = processingEnv.getTypeUtils();
        return (TypeElement) typeutils.asElement(typeMirror);
    }
}
