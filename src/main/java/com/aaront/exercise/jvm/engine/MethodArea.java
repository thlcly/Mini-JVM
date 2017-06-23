package com.aaront.exercise.jvm.engine;

import com.aaront.exercise.jvm.ClassFile;
import com.aaront.exercise.jvm.constant.MethodRefConstant;
import com.aaront.exercise.jvm.loader.ClassFileLoader;
import com.aaront.exercise.jvm.method.Method;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MethodArea {

    private static MethodArea instance;

    /**
     * 做了极大的简化, ClassLoader只有一个, 实际JVM中的ClassLoader,是一个双亲委托的模型
     */

    private ClassFileLoader loader = null;

    Map<String, ClassFile> map = new HashMap<String, ClassFile>();

    private MethodArea(ClassFileLoader loader) {
        this.loader = loader;
    }

    public static MethodArea getInstance(ClassFileLoader loader) {
        // TODO: 17/6/22 这里没有考虑并发的情况
        // TODO: 17/6/22 实际上应该要支持多个ClassFileLoader
        if (instance == null) {
            instance = new MethodArea(loader);
        }
        return instance;
    }

    public static MethodArea getInstance() {
        if(instance == null) throw new RuntimeException("instance还没有初始化");
        return instance;
    }

    public void setClassFileLoader(ClassFileLoader clzLoader) {
        this.loader = clzLoader;
    }

    /**
     * 查找main方法
     */
    public Optional<Method> queryMainMethod(ClassFile classFile) {
        if(classFile == null) throw new RuntimeException("classFile不能为null");
        return Optional.ofNullable(classFile.getMethods().get("([Ljava/lang/String;)V"));
    }

    /**
     * 根据class name查找class file
     */
    public ClassFile findClassFile(String className) throws IOException {

        if (map.get(className) != null) {
            return map.get(className);
        }
        // 看来该class 文件还没有load过
        ClassFile clzFile = this.loader.loadClass(className);

        map.put(className, clzFile);

        return clzFile;

    }


    /**
     * 通过方法描述符查找方法
     */
    public Method getMethod(String className, String methodDescriptor) throws IOException {
        ClassFile clz = this.findClassFile(className);
        return Optional.ofNullable(clz.getMethods().get(methodDescriptor)).orElseThrow(() -> new RuntimeException("没有找到指定的方法, descriptor:" + methodDescriptor));
    }

    /**
     * 通过方法常量查找方法
     */
    public Method getMethod(MethodRefConstant methodRef) throws IOException {
        ClassFile clz = this.findClassFile(methodRef.getClassConstant().getClassName().replaceAll("/", "\\."));
        return Optional.ofNullable(clz.getMethods().get(methodRef.getNameAndTypeConstant().getDescriptor())).orElseThrow(() -> new RuntimeException("没有找到指定的方法, methodRef:" + methodRef));
    }
}
