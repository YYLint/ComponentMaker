package com.duowan.mobile.entlive.proccessor;

import com.duowan.mobile.entlive.annotation.BindConfig;
import com.duowan.mobile.entlive.annotation.ComponentWrapper;
import com.duowan.mobile.entlive.annotation.DefaultInitAttr;
import com.duowan.mobile.entlive.annotation.InitAttrConfig;
import com.duowan.mobile.entlive.annotation.InitAttrConfigs;
import com.duowan.mobile.entlive.annotation.InitWithBundle;
import com.duowan.mobile.entlive.annotation.ModuleConfigDiffSupplement;
import com.duowan.mobile.entlive.annotation.ModuleConfigs;
import com.duowan.mobile.entlive.annotation.ModuleWrapper;
import com.duowan.mobile.entlive.domain.FreeContainer;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * Created by ericwu on 2017/4/10.
 */
@AutoService(Processor.class)
public class ComponentProcessor extends AbstractProcessor {
    /**
     * 使用 Google 的 auto-service 库可以自动生成 META-INF/services/javax.annotation.processing.Processor 文件
     */
    private Filer mFiler; //文件相关的辅助类
    private Elements mElementUtils; //元素相关的辅助类
    private Messager mMessager; //日志相关的辅助类

    private ClassName mWrapperParent;
    private TypeName mIComponentTypeName;

    // key是被注解标识的类，value是要去生成的元素
    private HashMap<TypeMirror, ComponentUnit> mComponentsMap = new HashMap<>();
    // key是容器，value是包含的组件
    private HashMap<TypeElement, List<? extends TypeMirror>> mConfigToComponentMap = new HashMap<>();

    private HashMap<TypeElement, HashMap<TypeMirror, Element>> mConfigMap = new HashMap<>();
    private HashMap<String, ModuleProxyInfo> modulesProxyMap = new HashMap<>();
    private HashMap<String, ComponentInfo> componentMap = new HashMap<>();

    private final static String PACKAGE = "com.duowan.mobile.entlive";

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mFiler = processingEnv.getFiler();
        mElementUtils = processingEnv.getElementUtils();
        mMessager = processingEnv.getMessager();
    }

    /**
     * @return 指定哪些注解应该被注解处理器注册
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        types.add(DefaultInitAttr.class.getCanonicalName());
        types.add(InitWithBundle.class.getCanonicalName());
        types.add(ModuleWrapper.class.getCanonicalName());
        types.add(ModuleConfigs.class.getCanonicalName());
        types.add(ModuleConfigDiffSupplement.class.getCanonicalName());
        types.add(BindConfig.class.getCanonicalName());
        types.add(ComponentWrapper.class.getCanonicalName());
        return types;
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        getInitBundleMethod(roundEnv);
        setupComponentsMap(roundEnv);
        initComponentsMap(roundEnv);
        setupContainerToConfigMap(roundEnv);
        initModulesMap(roundEnv);
        initComponentMap(roundEnv);
        initELModuleConfigs(roundEnv);
        mWrapperParent = getAbstractWrapper();
        mIComponentTypeName = getIComponentTypeName();
        createWrapperChildren();
        createFactoryImpl();
        createModulesConstantName();
        createComponentConstantName();
        createWrapperModules();
        return true;
    }

    /**
     * 生成 Module 对象名的静态变量
     */
    private void createModulesConstantName() {
        try {
            ModuleProxyInfo.generateModulesConstantName(processingEnv.getFiler(), modulesProxyMap);
        } catch (IOException e) {
            System.out.println("createModulesConstantName " + e);
        }
    }

    /**
     * 生成 Component 对象名的静态变量
     */
    private void createComponentConstantName() {
        try {
            ComponentInfo.generateComponentConstantName(processingEnv.getFiler(), componentMap);
        } catch (IOException e) {
            System.out.println("createModulesConstantName " + e);
        }
    }

    private void initComponentMap(RoundEnvironment roundEnv) {
        Set<? extends Element> set = roundEnv.getElementsAnnotatedWith(ComponentWrapper.class);
        for (Element element : set) {
            if (element.getKind() == ElementKind.CLASS && !ProcessorUtil.isAbstract(element)) {
                String clzName = element.getSimpleName().toString();
                ComponentInfo componentInfo = null;
                if (componentMap.get(clzName) != null) {
                    componentInfo = componentMap.get(clzName);
                } else {
                    componentInfo = new ComponentInfo(mElementUtils, (TypeElement) element);
                }
                componentInfo.setTypeElement((TypeElement) element);
                componentInfo.setClsName(clzName);
                componentMap.put(clzName, componentInfo);
            } else {
                mMessager.printMessage(Diagnostic.Kind.NOTE, "only support class");
            }
        }
    }

    private void createWrapperModules() {
        for (String key : modulesProxyMap.keySet()) {
            ModuleProxyInfo proxyInfo = modulesProxyMap.get(key);
            try {
                proxyInfo.generateModuleProxyClass(processingEnv.getFiler());
            } catch (IOException e) {
                System.out.println("generateModuleProxyClass " + e);
            }
        }
        try {
            ModuleProxyInfo.generateModuleProxyContainer(processingEnv.getFiler(), modulesProxyMap);
        } catch (IOException e) {
            System.out.println("generateModuleProxyContainer " + e);
        }
    }

    private void initELModuleConfigs(RoundEnvironment roundEnv) {
        ELModulesProcessorUtil.handleELModules(processingEnv, roundEnv, mMessager);
        ELModuleConfigsDiffSupplementProcessorUtil.handleELModuleConfigsDiffSupplement(processingEnv,
                roundEnv,
                mMessager);
    }

    private void initModulesMap(RoundEnvironment roundEnv) {
        Set<? extends Element> set = roundEnv.getElementsAnnotatedWith(ModuleWrapper.class); //暂时用这个注解查询，后续对根接口做注解
        for (Element element : set) {
//            e.getEnclosingElement(); //获取方法所在的类
            if (element.getKind() == ElementKind.CLASS && !ProcessorUtil.isAbstract(element)) {
                String clzName = element.getSimpleName().toString();
                ModuleProxyInfo proxyInfo = null;
                if (modulesProxyMap.get(clzName) != null) {
                    proxyInfo = modulesProxyMap.get(clzName);
                } else {
                    proxyInfo = new ModuleProxyInfo(mElementUtils, (TypeElement) element);
                }
                proxyInfo.setTypeElement((TypeElement) element);
                proxyInfo.setClsName(clzName);
                modulesProxyMap.put(clzName, proxyInfo);
            } else {
                mMessager.printMessage(Diagnostic.Kind.NOTE, "only support class");
            }
        }
    }

    private TypeName getIComponentTypeName() {
        //要不引入baseapi？
        TypeName targetClassName = ClassName.get("com.yy.mobile.ui.basicchanneltemplate.component", "IComponent");
        return targetClassName;
    }

    private void initComponentsMap(RoundEnvironment roundEnv) {
        Set<? extends Element> set = roundEnv.getElementsAnnotatedWith(DefaultInitAttr.class); //暂时用这个注解查询，后续对根接口做注解
        for (Element element : set) {
//            ExecutableElement e = (ExecutableElement) element;
//            e.getEnclosingElement(); //获取方法所在的类
            if (element.getKind() == ElementKind.CLASS && !ProcessorUtil.isAbstract(element)) {
                String clzName = element.getSimpleName().toString();
                ComponentUnit unit = null;
                if (mComponentsMap.get(element.asType()) != null) {
                    unit = mComponentsMap.get(element.asType());
                } else {
                    unit = new ComponentUnit();
                }
                unit.setDefaultInitAttr(element.getAnnotation(DefaultInitAttr.class));
                unit.setClassName(clzName);
                mComponentsMap.put(element.asType(), unit);
            } else {
                System.out.println("only support class");
            }
        }
    }

    private void setupComponentsMap(RoundEnvironment roundEnv) {
        Set<? extends Element> set = roundEnv.getElementsAnnotatedWith(InitAttrConfigs.class);
        for (Element element : set) {
            if (element.getKind() == ElementKind.INTERFACE || element.getKind() == ElementKind.CLASS) {
                InitAttrConfigs configs = element.getAnnotation(InitAttrConfigs.class);
                if (configs != null) {
                    InitAttrConfig[] value = configs.value();
                    List<TypeMirror> list = new ArrayList<>();
                    for (int i = 0; i < value.length; i++) {
                        try {
                            value[i].component();
                        } catch (MirroredTypeException e) {
                            String clzName = element.getSimpleName().toString();
                            TypeMirror typeMirror = e.getTypeMirror();
                            list.add(typeMirror);
                            ComponentUnit unit = null;
                            if (mComponentsMap.get(typeMirror) != null) {
                                unit = mComponentsMap.get(typeMirror);
                            } else {
                                unit = new ComponentUnit();
                            }
                            unit.setClassName(clzName);
                            unit.addConfigs(value[i], element);
                            mComponentsMap.put(typeMirror, unit);
                        }
                    }
                    mConfigToComponentMap.put((TypeElement) element, list);

                }


            }
        }
    }

    private HashMap<TypeElement, TypeMirror> mContainerToConfigMap = new HashMap<>();

    private void setupContainerToConfigMap(RoundEnvironment roundEnv) {
        Set<? extends Element> set = roundEnv.getElementsAnnotatedWith(BindConfig.class);
        for (Element element : set) {
            if (element.getKind() == ElementKind.CLASS) {
                BindConfig bindConfig = element.getAnnotation(BindConfig.class);
                try {
                    bindConfig.value();
                } catch (MirroredTypeException e) {
                    TypeMirror typeMirror = e.getTypeMirror();
                    mContainerToConfigMap.put((TypeElement) element, typeMirror);
                }

            }
        }
    }

    private void getInitBundleMethod(RoundEnvironment roundEnv) {
        Set<? extends Element> set = roundEnv.getElementsAnnotatedWith(InitWithBundle.class);
        for (Element element : set) {
            if (element.getKind() == ElementKind.METHOD) {
                ExecutableElement method = (ExecutableElement) element;
                Set<Modifier> modifiers = method.getModifiers();
                if (!modifiers.containsAll(Arrays.asList(Modifier.PUBLIC, Modifier.STATIC))) {
                    throw new NullPointerException("the method must be public static");
                }
                if (!method.getReturnType().toString().equals("android.os.Bundle")) {
                    throw new NullPointerException("the method must be return type android.os.Bundle");
                }
                TypeElement configClz = (TypeElement) element.getEnclosingElement();
                HashMap<TypeMirror, Element> methodMap = mConfigMap.get(configClz);
                if (methodMap == null) {
                    methodMap = new HashMap<>();
                    mConfigMap.put(configClz, methodMap);
                }
//                BussniessId bussniessId = configClz.getAnnotation(BussniessId.class);
//                String id = bussniessId.value();
                mMessager.printMessage(Diagnostic.Kind.NOTE, "method name:" + method.getSimpleName());
                InitWithBundle components = element.getAnnotation(InitWithBundle.class);
                try {
                    components.value();
                } catch (MirroredTypeException e) {
                    TypeMirror mirror = e.getTypeMirror();
                    methodMap.put(mirror, method);
                }
            } else {
                System.out.println("not support abstract class");
            }
        }
    }

    private void createFactoryImpl() {
        CodeBlock.Builder codeBlock = CodeBlock.builder();
        for (Map.Entry<TypeMirror, ComponentUnit> entry : mComponentsMap.entrySet()) {
            TypeMirror typeElement = entry.getKey();
            String className = entry.getValue().getWrapperClassName();

            ClassName implClass = ClassName.get(PACKAGE, className);
            codeBlock.addStatement("mComponentWrappers.put($T.class, $T.class)", typeElement, implClass);
        }

        int index = 1;
        for (Map.Entry<TypeElement, List<? extends TypeMirror>> entry : mConfigToComponentMap.entrySet()) {
            TypeElement typeElement = entry.getKey();
            List<? extends TypeMirror> list = entry.getValue();
            ParameterizedTypeName fanxing = ParameterizedTypeName.get(ClassName.get(Class.class), WildcardTypeName.subtypeOf(mIComponentTypeName)); // Class<? extend Icomponent>
            ParameterizedTypeName name = ParameterizedTypeName.get(ClassName.get(ArrayList.class), fanxing); //ArrayList<Class<? extend Icomponent>>
            codeBlock.addStatement("$T list$L = new $T<>()", name, index, ArrayList.class);
            for (TypeMirror mirror : list) {
                codeBlock.addStatement("list$L.add($T.class)", index, mirror);
            }
            codeBlock.addStatement("mConfigToComponentMap.put($T.class, list$L)", typeElement, index);
            index++;
        }

        for (Map.Entry<TypeElement, TypeMirror> entry : mContainerToConfigMap.entrySet()) {
            TypeMirror typeElement = entry.getValue();
            TypeElement id = entry.getKey();
            codeBlock.addStatement("mContainerToConfigMap.put($T.class, $T.class)", id, typeElement);
        }

        MethodSpec initClzMap = MethodSpec.methodBuilder("initClzMap")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addAnnotation(Override.class)
                .returns(TypeName.VOID)
                .addCode(codeBlock.build())
                .build();

        TypeName injectInterfaceType = ClassName.get("com.yy.mobile.ui.basicchanneltemplate.generate", "ComponentsFactory");
        TypeSpec typeSpec = TypeSpec.classBuilder("ComponentsFactoryIpml")
                .superclass(injectInterfaceType)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(initClzMap)
                .build();

        JavaFile javaFile = JavaFile.builder(PACKAGE, typeSpec)
                .build();

        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (Exception e) {
            System.out.println("createFactoryImpl " + e);
        }
    }

    private void createWrapperChildren() {
        if (mWrapperParent == null) {
            return;
        }
        for (Map.Entry<String, String> entry : System.getenv().entrySet()) {
            mMessager.printMessage(Diagnostic.Kind.NOTE,
                    "zy key = " + entry.getKey() + ",value = " + entry.getValue());
        }

        TypeName initConfigClass = ClassName.get("com.yy.mobile.ui.basicchanneltemplate.generate", "InitConfig");
        TypeName containerConfigClass = ClassName.get("com.duowan.mobile.entlive.domain", "ContainerConfig");
        TypeName bundle = ClassName.get("android.os", "Bundle");
        for (Map.Entry<TypeMirror, ComponentUnit> entry : mComponentsMap.entrySet()) {
            ComponentUnit unit = entry.getValue();
            TypeMirror typeElement = entry.getKey();

            List<InitAttrConfig> configs = unit.getInitConfigs();
            CodeBlock.Builder builder = CodeBlock.builder();
            if (configs != null) {
                builder.addStatement("mConfigs = new $T()", HashMap.class);
                int index = 0;
                for (InitAttrConfig config : configs) {
                    StringBuilder sb = new StringBuilder();
                    HashMap<TypeMirror, Element> map = mConfigMap.get(unit.getBussinessId(index));
                    if (map != null) {
                        Element method = map.get(typeElement);
                        if (method != null) {
                            mMessager.printMessage(Diagnostic.Kind.NOTE, "get method:" + method);
//                            builder.addStatement("mConfigs.put($T.class, new $T($L, $L, $T.$N()))", unit.getBussinessId(index) == null ? FreeContainer.class : unit.getBussinessId(index),
//                                    initConfigClass, config.initLevel().getValue(), config.resourceId(), unit.getBussinessId(index), method.getSimpleName());
                        }
                    }
                    builder.addStatement("mConfigs.put($T.class, new $T($L, $L))",
                            unit.getBussinessId(index) == null ? FreeContainer.class : unit.getBussinessId(index),
                            initConfigClass, config.initLevel().getValue(), config.resourceId());

                    index++;

                }
            }


            CodeBlock.Builder builder2 = CodeBlock.builder();
            if (configs != null) {
                int index = 0;
                for (InitAttrConfig config : configs) {
                    HashMap<TypeMirror, Element> map = mConfigMap.get(unit.getBussinessId(index));
                    if (map != null) {
                        Element method = map.get(typeElement);
                        if (method != null) {
                            builder2.beginControlFlow("if (config == $T.class)", unit.getBussinessId(index));
                            mMessager.printMessage(Diagnostic.Kind.NOTE, "get method:" + method);
                            builder2.addStatement("return $T.$N()", unit.getBussinessId(index), method.getSimpleName());
                            builder2.endControlFlow();
                        }
                    }
                    index++;
                }
            }

            MethodSpec superMethod = MethodSpec.constructorBuilder()
                    .addModifiers(Modifier.PUBLIC)
                    .addStatement("super($L)", unit.getDefaultInitLevel())
                    .addCode(builder.build())
                    .build();

            MethodSpec createInstanceMethod = MethodSpec.methodBuilder("creatInstance")
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addAnnotation(Override.class)
                    .addParameter(bundle, "bundle")
                    .returns(TypeName.get(typeElement))// 这个可以具体类
                    .addStatement("$T ret = new $T()", typeElement, typeElement)
//                    .beginControlFlow("if (null == bundle) ")
//                    .addStatement("bundle = new $T()", bundle)
//                    .endControlFlow()
                    .addStatement("ret.setArguments(bundle)")
                    .addStatement("return ret", typeElement)
                    .build();

            MethodSpec getConfigBundleMethod = MethodSpec.methodBuilder("getConfigBundle")
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(Override.class)
                    .addParameter(ParameterizedTypeName.get(ClassName.get(Class.class), WildcardTypeName.subtypeOf(containerConfigClass)), "config")
                    .returns(bundle)
                    .addCode(builder2.build())
                    .addStatement("return null")
                    .build();

            ParameterizedTypeName name = ParameterizedTypeName.get(mWrapperParent, TypeName.get(typeElement));
            TypeSpec typeSpec = TypeSpec.classBuilder(unit.getWrapperClassName())
                    .addModifiers(Modifier.PUBLIC)
                    .superclass(name)
                    .addMethod(superMethod)
                    .addMethod(createInstanceMethod)
                    .addMethod(getConfigBundleMethod)
                    .build();


            JavaFile javaFile = JavaFile.builder(PACKAGE, typeSpec).build();

            try {
                javaFile.writeTo(processingEnv.getFiler());
            } catch (IOException e) {
                //todo
            }
        }
    }

    private TypeMirror getConfigComponentClass(InitAttrConfig config) {
        try {
            config.component();
        } catch (MirroredTypeException e) {
            return e.getTypeMirror();
        }
        return null;
    }

//    private TypeMirror getConfigParentClass(InitAttrConfig config) {
//        try {
//            config.config();
//        } catch (MirroredTypeException e) {
//            return e.getTypeMirror();
//        }
//        return null;
//    }

    private ClassName getAbstractWrapper() {
        return ClassName.get("com.yy.mobile.ui.basicchanneltemplate.generate", "AbstractComponentWrapper");
    }

    /**
     * @return 指定使用的 Java 版本。通常返回 SourceVersion.latestSupported()。
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    private TypeSpec makeClass(String name, Modifier... modifiers) {
        TypeSpec.Builder builder = TypeSpec.classBuilder(name);
        builder.addModifiers(modifiers);
        return builder.build();
    }

    private FieldSpec makeField(Class type, String name, Modifier... modifiers) {
        FieldSpec fieldSpec = FieldSpec.builder(type, name)
                .addModifiers(modifiers)
                .build();
        return fieldSpec;
    }

    private MethodSpec makeConstructors(List<ParameterSpec> parameters, List<Modifier> modifiers) {
        MethodSpec.Builder builder = MethodSpec.constructorBuilder();
        builder.addModifiers(modifiers)
                .addParameters(parameters);
        for (ParameterSpec parameter : parameters) {
            String name = parameter.name;
            TypeName type = parameter.type;
            type.toString();
            builder.addStatement("this.$N = $N", name, name);
        }
        MethodSpec flux = builder.build();

        return flux;
    }

    private MethodSpec makeMethod() {
        MethodSpec main = MethodSpec.methodBuilder("main")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(String[].class, "args")
                .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
                .build();
        return main;
    }

    private void getTargetClassName(String packageName, String className) {
        TypeName targetClassName = ClassName.get(packageName, className);
    }

    /**
     * 获取类所在包名
     *
     * @param element
     */
    private String getPackageName(Element element) {
        String packageName = processingEnv.getElementUtils().getPackageOf(element).getQualifiedName().toString();
        return packageName;
    }

    public TypeElement asTypeElement(TypeMirror typeMirror) {
        Types typeutils = this.processingEnv.getTypeUtils();
        return (TypeElement) typeutils.asElement(typeMirror);
    }
}
