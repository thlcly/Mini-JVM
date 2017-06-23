package com.aaront.exercise.jvm;

import com.aaront.exercise.jvm.accessflag.ClassAccessFlag;
import com.aaront.exercise.jvm.constant.ConstantPool;
import com.aaront.exercise.jvm.field.Field;
import com.aaront.exercise.jvm.index.ClassIndex;
import com.aaront.exercise.jvm.index.InterfaceIndex;
import com.aaront.exercise.jvm.method.Method;

import java.util.List;
import java.util.Map;

/**
 * @author tonyhui
 * @since 17/6/5
 */
public class ClassFile {
    private String magicNumber;
    private int majorVersion;
    private int minorVersion;
    private ClassIndex classIndex;
    private InterfaceIndex interfaceIndex;
    private ConstantPool constantPool;
    private List<ClassAccessFlag> classAccessFlags;
    private List<Field> fields;
    private Map<String, Method> methods;

    public int getMajorVersion() {
        return majorVersion;
    }

    public void setMajorVersion(int majorVersion) {
        this.majorVersion = majorVersion;
    }

    public int getMinorVersion() {
        return minorVersion;
    }

    public void setMinorVersion(int minorVersion) {
        this.minorVersion = minorVersion;
    }

    public ClassIndex getClassIndex() {
        return classIndex;
    }

    public void setClassIndex(ClassIndex classIndex) {
        this.classIndex = classIndex;
    }

    public InterfaceIndex getInterfaceIndex() {
        return interfaceIndex;
    }

    public void setInterfaceIndex(InterfaceIndex interfaceIndex) {
        this.interfaceIndex = interfaceIndex;
    }

    public ConstantPool getConstantPool() {
        return constantPool;
    }

    public void setConstantPool(ConstantPool constantPool) {
        this.constantPool = constantPool;
    }

    public List<ClassAccessFlag> getClassAccessFlags() {
        return classAccessFlags;
    }

    public void setClassAccessFlags(List<ClassAccessFlag> classAccessFlags) {
        this.classAccessFlags = classAccessFlags;
    }

    public List<ClassAccessFlag> getAccessFlag() {
        return classAccessFlags;
    }

    public void setAccessFlag(List<ClassAccessFlag> classAccessFlags) {
        this.classAccessFlags = classAccessFlags;
    }

    public Map<String, Method> getMethods() {
        return methods;
    }

    public void setMethods(Map<String, Method> methods) {
        this.methods = methods;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public String getMagicNumber() {
        return magicNumber;
    }

    public void setMagicNumber(String magicNumber) {
        this.magicNumber = magicNumber;
    }
}
