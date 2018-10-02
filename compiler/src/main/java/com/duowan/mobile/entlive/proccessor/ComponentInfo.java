package com.duowan.mobile.entlive.proccessor;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.HashMap;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * 注解工作中，用于保存 Component 信息的类
 * <p>
 * Created by Abel abeljoo.com
 * Created on 2018/5/28 11:08
 */
public class ComponentInfo {

    private String packageName;
    private TypeElement typeElement;
    private String clsName; //被注解的元模块类名

    public ComponentInfo(Elements elementUtils, TypeElement classElement) {
        this.typeElement = classElement;
        PackageElement packageElement = elementUtils.getPackageOf(classElement);
        String packageName = packageElement.getQualifiedName().toString();
        this.packageName = packageName;
    }

    public void setClsName(String clsName) {
        this.clsName = clsName;
    }

    public String getClsName() {
        return clsName;
    }

    public String getFullClsName() {
        return packageName + "." + clsName;
    }

    public void setTypeElement(TypeElement typeElement) {
        this.typeElement = typeElement;
    }

    private String getClassName(TypeElement type, String packageName) {
        int packageLen = packageName.length() + 1;
        return type.getQualifiedName().toString().substring(packageLen)
                .replace('.', '$');
    }

    public static void generateComponentConstantName(Filer filer, HashMap<String, ComponentInfo> componentMap)
            throws IOException {
        TypeSpec.Builder classBuilder = TypeSpec.classBuilder("ComponentNameCollection")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);

        FieldSpec.Builder fieldBuild;

        // 每一个元素
        for (String key : componentMap.keySet()) {
            ComponentInfo proxyInfo = componentMap.get(key);
            fieldBuild = FieldSpec.builder(String.class,
                    proxyInfo.getClsName(),
                    Modifier.PUBLIC,
                    Modifier.FINAL,
                    Modifier.STATIC);
            fieldBuild.initializer("\"" + proxyInfo.getFullClsName() + "\"");
            classBuilder.addField(fieldBuild.build());
        }

        TypeSpec spec = classBuilder.build();
        JavaFile javaFile = JavaFile.builder("com.yy.live.basic", spec)
                .build();
        javaFile.writeTo(filer);
    }

    public TypeElement getTypeElement() {
        return typeElement;
    }

    public String getPackageName() {
        return packageName;
    }
}
