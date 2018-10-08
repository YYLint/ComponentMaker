package com.duowan.mobile.compiler.multiline;

import com.duowan.mobile.plugin.homepage.multiline.MultiLineType;
import com.github.javaparser.utils.Pair;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;

/**
 * Class Name: MultiLineTypeProcessor
 * Description: 类功能说明
 * Author: 郑永欣
 * Date: 2017/11/15
 * Modified History: 修改记录，格式(Name)  (Version)  (Date) (Reason & Contents)
 */
@AutoService(Processor.class)
public class MultiLineTypeProcessor extends AbstractProcessor {

    private static final String PACKAGE_NAME = "com.yy.mobile.plugin.homeapi.ui.multiline";
    private static final String CREATE_VIEW_HOLDER = "onCreateViewHolder";
    private static final String CREATE_VIEW = "getCreateView";
    private static final String BIND_VIEW_HOLDER = "onBindViewHolder";
    private static final String VIEW_ATTACHED_TO_WINDOW = "onViewAttachedToWindow";
    private static final String VIEW_DETACHED_FROM_WINDOW = "onViewDetachedFromWindow";
    private static final String VIEW_RECYCLED = "onViewRecycled";
    private static final String VIEW_HOLDER_MAPPING_EX = "ViewBinderMappingEx";
    private static final String BASE_VIEW_HOLDER_MAPPING = "BaseViewHolderMapping";
    private Elements elementUtils;
    private Messager messager;
    private ClassName viewClass = ClassName.get("android.view", "View");
    private ClassName inflaterClass = ClassName.get("android.view", "LayoutInflater");
    private ClassName viewGroupClass = ClassName.get("android.view", "ViewGroup");
    private ClassName homeContentCallbackClass = ClassName.get(PACKAGE_NAME, "IMultiLinePresenter");
    private ClassName baseSubViewHolderFactoryClass = ClassName.get(PACKAGE_NAME, "BaseViewBinder");

    private RFileResolver rFileResolver = null;
    private ClassName R = null;

    /**
     * @return 指定哪些注解应该被注解处理器注册
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(MultiLineType.class.getCanonicalName());
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        messager = processingEnv.getMessager();

        createRFileResolver();
        addViewHolderFactory(roundEnvironment);

        return false;
    }

    private void createRFileResolver() {
        Pair<RFileResolver, ClassName> RFile;
        try {
            RFile = RFileResolver.create(processingEnv.getOptions());
            rFileResolver = RFile.a;
            R = RFile.b;
        } catch (Throwable throwable) {
            messager.printMessage(Diagnostic.Kind.ERROR, "error = " + throwable);
        }
    }

    /**
     * 生成映射表
     *
     * @param roundEnvironment
     */
    private void addViewHolderMappingEx(RoundEnvironment roundEnvironment, String packageName) {
        MethodSpec constructorMethod = getConstructorMethod();
        MethodSpec initMethod = getInitMethodSpec(roundEnvironment, packageName);

        List<MethodSpec> methodSpecs = asMethodList(constructorMethod, initMethod);

        TypeSpec viewHolderMappingEx = TypeSpec.classBuilder(VIEW_HOLDER_MAPPING_EX)
                .addModifiers(Modifier.PUBLIC)
                .addMethods(methodSpecs)
                .superclass(ClassName.get(PACKAGE_NAME, BASE_VIEW_HOLDER_MAPPING))
                .build();

        writeFile(viewHolderMappingEx, packageName);
    }

    /**
     * 生成工厂类
     *
     * @param roundEnvironment
     */
    private void addViewHolderFactory(RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(MultiLineType.class);
        Map<String, String> holderPackageMap = new HashMap<>();
        for (Element element : elements) {
            if (element.getKind() == ElementKind.CLASS) {
                TypeElement typeElement = (TypeElement) element;
                MultiLineType annotation = element.getAnnotation(MultiLineType.class);
                String packageName = getHolderPackage(element, annotation.holderPackage());
                if (!holderPackageMap.containsKey(packageName)) {
                    addViewHolderMappingEx(roundEnvironment, packageName);
                    holderPackageMap.put(packageName, packageName);
                }

                MethodSpec constructorMethod = getConstructorMethod();
                MethodSpec createViewHolderMethod = getCreateViewHolderMethod(element);
                MethodSpec createViewMethod = getCreateView(element);
                MethodSpec createViewHolder = getCreateViewHolder(element);
                MethodSpec onViewAttachedToWindow = onViewAttachedToWindow(element);
                MethodSpec onViewDetachedFromWindow = onViewDetachedFromWindow(element);
                MethodSpec onViewRecycled = onViewRecycled(element);


                List<MethodSpec> methodSpecs = asMethodList(constructorMethod, createViewHolderMethod, createViewHolder, createViewMethod, onViewAttachedToWindow, onViewDetachedFromWindow, onViewRecycled);

                TypeName listOfHoverboards = ParameterizedTypeName.get(baseSubViewHolderFactoryClass, getContentClass(annotation), TypeName.get(element.asType()));

                TypeSpec viewHolderFactoryTypeSpec = TypeSpec.classBuilder(String.format("%sBinder", typeElement.getSimpleName()))
                        .superclass(listOfHoverboards)
                        .addMethods(methodSpecs)
                        .addModifiers(Modifier.PUBLIC)
                        .build();

                writeFile(viewHolderFactoryTypeSpec, packageName);
            }
        }
    }

    /**
     * 将类文件写入PACKAGE_NAME_FACTORY包中
     *
     * @param typeSpec 类
     */
    private void writeFile(TypeSpec typeSpec) {
        writeFile(typeSpec, PACKAGE_NAME);
    }

    private void writeFile(TypeSpec typeSpec, String packageName) {
        JavaFile javaFile = JavaFile.builder(packageName + ".factory", typeSpec).build();

        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            messager.printMessage(Diagnostic.Kind.NOTE, e.getMessage());
        }
    }

    /**
     * 获取方法集合
     *
     * @param methodSpecs 方法
     * @return 方法集合
     */
    private List<MethodSpec> asMethodList(MethodSpec... methodSpecs) {
        return Arrays.asList(methodSpecs);
    }


    /**
     * 生成构造方法
     * <p>
     * public ConstructorMethod(Context context, ViewGroup parent) {
     * super(context, parent);
     * }
     *
     * @return
     */
    private MethodSpec getConstructorMethod() {
        return MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(homeContentCallbackClass, "callback")
                .addStatement("super(callback)")
                .build();
    }

    /**
     * @Override protected void init(Context context, ViewGroup parent) {
     * register(ILivingCoreConstant.Live_MODULE_BIG_BANNER_CODE, new BigBannerViewHolderFactory(context, parent));
     * register(ILivingCoreConstant.Live_MODULE_BANNER_CODE, new BannerViewHolderFactory(context, parent));
     * register(ILivingCoreConstant.Live_MODULE_COMMON_TITLE_CODE, new TitleViewHolderFactory(context, parent));
     * register(ILivingCoreConstant.Live_MODULE_CONTENT_CODE, new DoubleLiveViewHolderFactory(context, parent));
     * }
     */
    private MethodSpec getInitMethodSpec(RoundEnvironment roundEnvironment, String packageName) {
        MethodSpec.Builder initMethodBuilder = MethodSpec.methodBuilder("init")
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .returns(TypeName.VOID)
                .addParameter(homeContentCallbackClass, "callback");

        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(MultiLineType.class);
        for (Element element : elements) {
            if (element.getKind() == ElementKind.CLASS) {
                TypeElement typeElement = (TypeElement) element;
                MultiLineType annotation = element.getAnnotation(MultiLineType.class);
                String holderPackage = getHolderPackage(element, annotation.holderPackage());
                if (packageName.equals(holderPackage)) {
                    int[] types = annotation.type();
                    for (int type : types) {
                        initMethodBuilder.addStatement(String.format("register(%s, new %sBinder(callback))", type, typeElement.getSimpleName()));
                    }
                }
            }
        }
        return initMethodBuilder.build();
    }

    private String getHolderPackage(Element element, String packageName) {
        String holderPackage = packageName;
        if (packageName.length() == 0) {
            holderPackage = elementUtils.getPackageOf(element).getQualifiedName().toString();
        }
        return holderPackage;
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementUtils = processingEnv.getElementUtils();
    }

    /**
     * @return 指定使用的 Java 版本。通常返回 SourceVersion.latestSupported()。
     */
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    /**
     * 获取工厂类的createViewHolder方法
     *
     * @param element
     * @return
     */
    private MethodSpec getCreateViewHolderMethod(Element element) {
        MultiLineType annotation = element.getAnnotation(MultiLineType.class);

        TypeElement typeElement = (TypeElement) element;
        messager.printMessage(Diagnostic.Kind.NOTE, "[yoxin][getCreateViewHolderMethod]typeElement.getSimpleName() = " + typeElement.getQualifiedName());
        MethodSpec build = MethodSpec.methodBuilder(CREATE_VIEW_HOLDER)
                .returns(TypeName.get(element.asType()))
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(inflaterClass, "inflater")
                .addParameter(viewGroupClass, "parent")
                .addStatement(String.format("View itemView = getCreateView(inflater, parent)"))
                .addStatement(String.format("return new %s(itemView, getMultiLinePresenter())", typeElement.getQualifiedName()))
                .build();
        return build;
    }

    /**
     * 获取工厂类的createView方法
     * <p>
     * public View createView() {
     * return new TopBanner(mContext);
     * }
     * <p>
     * public View createView() {
     * return mLayoutInflater.inflate(R.layout.item_living_double_live_container, parent, false);
     * }
     */
    public MethodSpec getCreateView(Element element) {
        MultiLineType annotation = element.getAnnotation(MultiLineType.class);

        MethodSpec.Builder createViewMethod = MethodSpec.methodBuilder(CREATE_VIEW)
                .returns(viewClass)
                .addModifiers(Modifier.PRIVATE)
                .addParameter(inflaterClass, "inflater")
                .addParameter(viewGroupClass, "parent");
        messager.printMessage(Diagnostic.Kind.NOTE, "[yoxin] [getCreateView] annotation.xml()" + annotation.xml());
        if (annotation.xml() != 0) {
            int resId = annotation.xml();
            if (R != null && rFileResolver != null) {
                String fieldName = rFileResolver.getFieldName("layout", resId);
                createViewMethod.addStatement(String.format(
                        "return LayoutInflater.from(inflater.getContext()).inflate(" +
                                "$T.layout.%s, parent, false)", fieldName), R);
            } else {
                createViewMethod.addStatement(String.format(
                        "return LayoutInflater.from(inflater.getContext()).inflate(%s, parent, false)", resId));
            }
        } else {
            createViewMethod.addStatement(String.format("return new %s(inflater.getContext())", getViewClass(annotation)));
        }

        return createViewMethod.build();
    }

    /**
     * 获取工厂类的createView方法
     * <p>
     * public View createView() {
     * return new TopBanner(mContext);
     * }
     * <p>
     * public View createView() {
     * return mLayoutInflater.inflate(R.layout.item_living_double_live_container, parent, false);
     * }
     */
    public MethodSpec getCreateViewHolder(Element element) {
        MultiLineType annotation = element.getAnnotation(MultiLineType.class);
        TypeElement typeElement = (TypeElement) element;
        messager.printMessage(Diagnostic.Kind.NOTE, "[yoxin] [getCreateViewHolder] annotation.xml()" + annotation.xml());
        MethodSpec createViewMethod = MethodSpec.methodBuilder(BIND_VIEW_HOLDER)
                // .returns(viewClass)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(TypeName.get(element.asType()), "holder")
                .addParameter(getContentClass(annotation), "lineData")
                .addStatement(String.format("holder.onBindViewHolder(lineData)")).build();

        return createViewMethod;
    }

    private MethodSpec onViewAttachedToWindow(Element element) {
        MethodSpec onViewAttachedToWindow = MethodSpec.methodBuilder(VIEW_ATTACHED_TO_WINDOW)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(TypeName.get(element.asType()), "holder")
                .addStatement("holder.onViewAttachedToWindow()").build();

        return onViewAttachedToWindow;
    }

    private MethodSpec onViewDetachedFromWindow(Element element) {
        MethodSpec onViewDetachedFromWindow = MethodSpec.methodBuilder(VIEW_DETACHED_FROM_WINDOW)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(TypeName.get(element.asType()), "holder")
                .addStatement("holder.onViewDetachedFromWindow()").build();

        return onViewDetachedFromWindow;
    }

    private MethodSpec onViewRecycled(Element element) {
        MethodSpec onViewRecycled = MethodSpec.methodBuilder(VIEW_RECYCLED)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(TypeName.get(element.asType()), "holder")
                .addStatement("holder.onViewRecycled()").build();

        return onViewRecycled;
    }

    private Name getViewClass(MultiLineType annotation) {
        Name result = null;
        try {
            annotation.viewClass();
        } catch (MirroredTypeException exception) {
            DeclaredType classTypeMirror = (DeclaredType) exception.getTypeMirror();
            TypeElement classTypeElement = (TypeElement) classTypeMirror.asElement();
            result = classTypeElement.getQualifiedName();
        }
        return result;
    }

    private TypeName getContentClass(MultiLineType annotation) {
        TypeName result = null;
        try {
            annotation.contentClass();
        } catch (MirroredTypeException exception) {
            DeclaredType classTypeMirror = (DeclaredType) exception.getTypeMirror();
            TypeElement classTypeElement = (TypeElement) classTypeMirror.asElement();
            result = TypeName.get(classTypeElement.asType());
        }
        return result;
    }
}
