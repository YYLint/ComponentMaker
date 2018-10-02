package com.duowan.mobile.entlive.proccessor;

import com.duowan.mobile.entlive.annotation.ModuleConfigInitialize;
import com.duowan.mobile.entlive.annotation.ModuleConfigs;
import com.duowan.mobile.entlive.utils.ELModuleConfigUtilForBuild;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
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
 * Created by shobal on 18/3/27.
 */

public class ELModulesProcessorUtil {

    public static void handleELModules(ProcessingEnvironment processingEnv, RoundEnvironment roundEnv, Messager messager) {
        Set<? extends Element> set = roundEnv.getElementsAnnotatedWith(ModuleConfigs.class);
        CodeBlock.Builder builder = CodeBlock.builder();
        ClassName hashMapCN = ClassName.get("java.util", "LinkedHashMap");
        ClassName ientModuleCN = ClassName.get("com.duowan.mobile.entlive.domain", "IEntModule");
        TypeName hashMapTN = ParameterizedTypeName.get(hashMapCN, TypeName.get(String.class), ientModuleCN);
        builder.addStatement("$T<String, $T> moduleMap = new $T<>()", hashMapCN, ientModuleCN, hashMapCN);

        for (Element element : set) {
            if (element.getKind() == ElementKind.CLASS) {
                ModuleConfigs moduleWrapper = element.getAnnotation(ModuleConfigs.class);
                ModuleConfigInitialize config = moduleWrapper.initialize();
                try {
                    config.moduleMembers();
                } catch (MirroredTypesException e) {
                    builder.beginControlFlow("if (templateName.equals($S))",
                            ELModuleConfigUtilForBuild.convertToModuleGroupKey(processingEnv,
                                    moduleWrapper));
                    for (TypeMirror tm : e.getTypeMirrors()) {
                        TypeElement classTypeElement = asTypeElement(processingEnv, tm);
                        builder.addStatement("moduleMap.put($S,new $T())", classTypeElement.getQualifiedName(), classTypeElement);
                    }
                    builder.addStatement("return moduleMap").endControlFlow();
                }

            } else {
                messager.printMessage(Diagnostic.Kind.NOTE, "only support class");
            }
        }

        MethodSpec createModules = MethodSpec.methodBuilder("createModules")
                .addModifiers(Modifier.STATIC, Modifier.PUBLIC)
                .returns(hashMapTN)
                .addParameter(String.class, "templateName")
                .addCode(builder.build())
                .addStatement("return moduleMap")
                .build();
        ArrayList<MethodSpec> methods = new ArrayList<>();
        methods.add(createModules);
        TypeSpec manager = TypeSpec.classBuilder("ELModulesFactory")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethods(methods)
                .build();

        JavaFile javaFile = JavaFile.builder("com.yy.live.basic", manager)
                .build();
        try {
            javaFile.writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            messager.printMessage(Diagnostic.Kind.WARNING, "printing ,initELModuleConfigs error" + e.getMessage());
        }
    }

    public static TypeElement asTypeElement(ProcessingEnvironment processingEnv, TypeMirror typeMirror) {
        Types typeutils = processingEnv.getTypeUtils();
        return (TypeElement) typeutils.asElement(typeMirror);
    }
}
