package com.aaront.exercise.jvm;

import com.aaront.exercise.jvm.engine.ExecutionEngine;
import com.aaront.exercise.jvm.engine.MethodArea;
import com.aaront.exercise.jvm.loader.ClassFileLoader;

import java.io.IOException;

/**
 * @author tonyhui
 * @since 17/6/22
 */
public class MiniJVM {

    public void run(String[] classPaths, String className) throws IOException {
        ClassFileLoader loader = new ClassFileLoader();
        loader.addClassPaths(classPaths);
        ClassFile classFile = loader.loadClass(className);
        MethodArea methodArea = MethodArea.getInstance(loader);
        ExecutionEngine engine = new ExecutionEngine(classFile, methodArea);
        engine.execute();
    }

    public static void main(String[] args) throws IOException {
        String className = "com.aaront.exercise.jvm.HourlyEmployee";
        String PATH1 = "./target/classes";
        String PATH2 = "./classes";
        String[] classPaths = {PATH1, PATH2};
        MiniJVM jvm = new MiniJVM();
        jvm.run(classPaths, className);
    }
}
