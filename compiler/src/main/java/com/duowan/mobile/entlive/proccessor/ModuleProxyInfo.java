package com.duowan.mobile.entlive.proccessor;

import com.duowan.mobile.entlive.domain.IEntModule;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * 注解工作中，用于保存 Module 代理及 Module 信息的类
 * <p>
 * Created by Abel abeljoo.com
 * Created on 2018/5/28 11:08
 */
public class ModuleProxyInfo {

    private String packageName;
    private String proxyClassName;
    private TypeElement typeElement;
    private String clsName; //被注解的元模块类名

    public static final String PROXY = "ModuleProxy";

    public ModuleProxyInfo(Elements elementUtils, TypeElement classElement) {
        this.typeElement = classElement;
        PackageElement packageElement = elementUtils.getPackageOf(classElement);
        String packageName = packageElement.getQualifiedName().toString();
        //simple classname
        String className = getClassName(classElement, packageName);
        this.packageName = packageName;
        this.proxyClassName = className + "$$" + PROXY;
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

    public void generateModuleProxyClass(Filer filer) throws IOException {
        MethodSpec getModule = MethodSpec.methodBuilder("getModule")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(IEntModule.class)
                .addStatement("return new $T()", typeElement)
                .build();

        ClassName interfaceClass = ClassName.get("com.duowan.mobile.entlive.domain", "IModuleProxy");

        TypeSpec proxy = TypeSpec.classBuilder(proxyClassName)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addSuperinterface(interfaceClass)
                .addMethod(getModule)
                .build();

        JavaFile javaFile = JavaFile.builder(packageName, proxy)
                .build();
        javaFile.writeTo(filer);
    }

    public static void generateModuleProxyContainer(Filer filer, HashMap<String, ModuleProxyInfo> modulesProxyMap) throws IOException {

        CodeBlock.Builder builder = CodeBlock.builder();
        for (String key : modulesProxyMap.keySet()) {
            ModuleProxyInfo proxyInfo = modulesProxyMap.get(key);
            builder.addStatement("moduleMap.put($S,new $T().getModule())", proxyInfo.getFullClsName(),
                    ClassName.get(proxyInfo.getPackageName(), proxyInfo.getProxyClassName()));
        }

        MethodSpec initModule = MethodSpec.methodBuilder("init")
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.VOID)
                .addCode(builder.build())
                .build();

        MethodSpec getModule = MethodSpec.methodBuilder("get")
                .addModifiers(Modifier.PUBLIC)
                .returns(IEntModule.class)
                .addParameter(String.class, "cls")
                .addStatement("return moduleMap.get(cls)")
                .build();

        MethodSpec removeModule = MethodSpec.methodBuilder("remove")
                .addModifiers(Modifier.PUBLIC)
                .returns(IEntModule.class)
                .addParameter(String.class, "cls")
                .addStatement("return moduleMap.remove(cls)")
                .build();

        ArrayList<MethodSpec> methods = new ArrayList<>();
        methods.add(initModule);
        methods.add(getModule);
        methods.add(removeModule);

        FieldSpec.Builder fieldBuild = FieldSpec.builder(ParameterizedTypeName.get(HashMap.class, String.class, IEntModule.class),
                "moduleMap", Modifier.PRIVATE);
        fieldBuild.initializer("new HashMap<>()");

        TypeSpec proxy = TypeSpec.classBuilder("ModuleProxyFactory")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethods(methods)
                .addField(fieldBuild.build())
                .build();

        JavaFile javaFile = JavaFile.builder("com.yy.live.basic", proxy)
                .build();
        javaFile.writeTo(filer);
    }

    public static void generateModulesConstantName(Filer filer, HashMap<String, ModuleProxyInfo> modulesProxyMap) throws IOException {
        TypeSpec.Builder classBuilder = TypeSpec.classBuilder("ModulesNameCollection")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);

        // 元素："*"
        FieldSpec.Builder fieldBuild = FieldSpec.builder(String.class,
                "All",
                Modifier.PUBLIC,
                Modifier.FINAL,
                Modifier.STATIC);
        fieldBuild.initializer("\"" + "*" + "\"");
        classBuilder.addField(fieldBuild.build());

        // 每一个元素
        for (String key : modulesProxyMap.keySet()) {
            ModuleProxyInfo proxyInfo = modulesProxyMap.get(key);
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

    public String getProxyClassFullName() {
        return packageName + "." + proxyClassName;
    }

    public TypeElement getTypeElement() {
        return typeElement;
    }

    public String getProxyClassName() {
        return proxyClassName;
    }

    public String getPackageName() {
        return packageName;
    }
}
