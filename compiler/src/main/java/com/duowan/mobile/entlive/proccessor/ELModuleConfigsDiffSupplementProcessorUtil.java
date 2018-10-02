package com.duowan.mobile.entlive.proccessor;

import com.duowan.mobile.entlive.annotation.ModuleConfigs;
import com.duowan.mobile.entlive.annotation.ModuleConfigDiffSupplement;
import com.duowan.mobile.entlive.utils.ELModuleConfigUtilForBuild;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypesException;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * {@link ModuleConfigDiffSupplement} 所定义的内容，在此处将会被注解生成的工厂类生产出来。
 * 具体产出物详见类名 {@link #CLASS_NAME}
 *
 * @author Abel Joo http://abeljoo.github.io/
 * @Date 2018/8/8
 * @Email zhujiajun@yy.com
 * @YY 909019111
 */
public class ELModuleConfigsDiffSupplementProcessorUtil {

    private static final String CLASS_NAME = "DiffSupplementsFactory";

    private static final String METHOD_NAME = "getDiffSupplement";

    private static final String ARGUMENT_COMPONENT_NAME = "componentName";

    private static final String ARGUMENT_DIFF_NAME = "diffName";

    private static final String FIELD_APPEND = "append";

    private static final String FIELD_HIDE = "hide";

    public static void handleELModuleConfigsDiffSupplement(ProcessingEnvironment processingEnv,
                                                           RoundEnvironment roundEnv,
                                                           Messager messager) {
        Set<? extends Element> set = roundEnv.getElementsAnnotatedWith(ModuleConfigs.class);
        CodeBlock.Builder builder = CodeBlock.builder();
        ClassName linkedListClassName = ClassName.get(
                "java.util",
                "LinkedList");
        ClassName supplementClassName = ClassName.get(
                "com.yy.live.basic.module.management.differentsupplement",
                "DiffSupplement");
        builder.addStatement("$T<String> " + FIELD_APPEND + " = new $T<>()",
                linkedListClassName,
                linkedListClassName);
        builder.addStatement("$T<String> " + FIELD_HIDE + " = new $T<>()",
                linkedListClassName,
                linkedListClassName);

        boolean isFirstControlFlow = true;

        for (Element element : set) {
            if (element.getKind() == ElementKind.CLASS) {
                ModuleConfigs moduleWrapper = element.getAnnotation(ModuleConfigs.class);
                for (ModuleConfigDiffSupplement supplement : moduleWrapper.diffSupplement()) {
                    String diffName = supplement.diffName();
                    String controlFlow = "if ("
                            + ARGUMENT_COMPONENT_NAME
                            + ".equals($S)"
                            + "\n&& "
                            + ARGUMENT_DIFF_NAME
                            + ".equals($S)"
                            + ")";
                    if (isFirstControlFlow) {
                        builder.beginControlFlow(controlFlow,
                                ELModuleConfigUtilForBuild.convertToModuleGroupKey(processingEnv,
                                        moduleWrapper),
                                diffName);
                        isFirstControlFlow = false;
                    } else {
                        builder.beginControlFlow("else " + controlFlow,
                                ELModuleConfigUtilForBuild.convertToModuleGroupKey(processingEnv,
                                        moduleWrapper),
                                diffName);
                    }
                    try {
                        supplement.appendMembers();
                    } catch (MirroredTypesException e) {
                        for (TypeMirror tm : e.getTypeMirrors()) {
                            TypeElement classTypeElement = asTypeElement(processingEnv, tm);
                            builder.addStatement(FIELD_APPEND + ".add($S)",
                                    classTypeElement.getQualifiedName());
                        }
                    }
                    try {
                        supplement.removeMembers();
                    } catch (MirroredTypesException e) {
                        for (TypeMirror tm : e.getTypeMirrors()) {
                            TypeElement classTypeElement = asTypeElement(processingEnv, tm);
                            builder.addStatement(FIELD_HIDE + ".add($S)",
                                    classTypeElement.getQualifiedName());
                        }
                        builder.addStatement("return new DiffSupplement("
                                + FIELD_APPEND
                                + ", "
                                + FIELD_HIDE
                                + ")");
                    }
                    builder.endControlFlow();
                }
            } else {
                messager.printMessage(Diagnostic.Kind.NOTE, "only support class");
            }
        }

        MethodSpec createDiffSupplementMethod = MethodSpec.methodBuilder(METHOD_NAME)
                .addModifiers(Modifier.STATIC, Modifier.PUBLIC)
                .returns(supplementClassName)
                .addParameter(String.class, ARGUMENT_COMPONENT_NAME)
                .addParameter(String.class, ARGUMENT_DIFF_NAME)
                .addCode(builder.build())
                .addStatement("return null")
                .build();
        ArrayList<MethodSpec> methods = new ArrayList<>();
        methods.add(createDiffSupplementMethod);
        TypeSpec manager = TypeSpec.classBuilder(CLASS_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethods(methods)
                .build();

        JavaFile javaFile = JavaFile.builder("com.yy.live.basic", manager)
                .build();
        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            messager.printMessage(Diagnostic.Kind.WARNING,
                    "printing ,initELModuleConfigs error" + e.getMessage());
        }
    }

    private static TypeElement asTypeElement(ProcessingEnvironment processingEnv,
                                             TypeMirror typeMirror) {
        Types typeutils = processingEnv.getTypeUtils();
        return (TypeElement) typeutils.asElement(typeMirror);
    }
}
