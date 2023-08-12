package com.joyzhou.effect;

import com.google.auto.service.AutoService;
import com.joyzhou.annotation.EffectAopScope;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@AutoService(Processor.class)
public class EffectAopScopeProcessor extends AbstractProcessor {

    /**
     * 生成文件的工具类
     */
    private Filer filer;
    /**
     * 打印信息
     */
    private Messager messager;
    /**
     * 元素相关
     */
    private Elements elementUtils;
    private Types typeUtils;

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> set = new HashSet<>();
        set.add(EffectAopScope.class.getCanonicalName());
        return set;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnv.getFiler();
        messager = processingEnv.getMessager();
        elementUtils = processingEnv.getElementUtils();
        typeUtils = processingEnv.getTypeUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        messager.printMessage(Diagnostic.Kind.NOTE, "annotations size--->" + annotations.size());
        AnnotationSpec annotationSpec = AnnotationSpec.builder(MatchScope.class).build();
        MethodSpec methodSpec = MethodSpec.methodBuilder("setVibrateEnabled_redefine")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addParameter(boolean.class, "enabled").addAnnotation(annotationSpec)
                .addCode(getsetVibrateEnabled_redefineCode())
                .build();
        TypeSpec typeSpec = TypeSpec.classBuilder("Aop_Auto_Gen")
                .addModifiers(Modifier.PUBLIC)
                .addMethod(methodSpec)
                .build();
        //1、获取要处理的注解的元素的集合
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(EffectAopScope.class);
        for (Element element : elements) {
            TypeElement typeElement = (TypeElement) element;
            System.out.println(
                    "Processor, ele = " + element.getClass() + " pkgname " + elementUtils.getPackageOf(element)
                            .getQualifiedName()
                            .toString());
            JavaFile javaFile = JavaFile.builder(elementUtils.getPackageOf(element).getQualifiedName().toString(),
                                                 typeSpec).build();
            try {
                javaFile.writeTo(filer);
            } catch (IOException e) {
                System.out.println("IOException = " + e);
                e.printStackTrace();
            }
        }

        return true;
    }

    /**
     * ((OSUIEffectHelper) Knot.callThisMethod("getOSUIEffectHelper")).setVibrateEnabled(enabled);
     *
     * @return
     */
    private CodeBlock getsetVibrateEnabled_redefineCode() {
        CodeBlock.Builder codeBlockBuilder = CodeBlock.builder();
        codeBlockBuilder.addStatement("(($T) Knot.callThisMethod(\"getOSUIEffectHelper\")).setVibrateEnabled(enabled)",
                                      ClassName.get(OSUIEffectHelper2.class));
        return codeBlockBuilder.build();
    }
}
