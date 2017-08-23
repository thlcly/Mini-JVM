package com.aaront.exercise.jvm;

import com.aaront.exercise.jvm.accessflag.ClassAccessFlag;
import com.aaront.exercise.jvm.constant.ConstantPool;
import com.aaront.exercise.jvm.field.Field;
import com.aaront.exercise.jvm.index.ClassIndex;
import com.aaront.exercise.jvm.index.InterfaceIndex;
import com.aaront.exercise.jvm.method.Method;
import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;

/**
 * @author tonyhui
 * @since 17/6/5
 */
@Data
public class ClassFile {
    private String magicNumber;
    private int majorVersion;
    private int minorVersion;
    private ClassIndex classIndex;
    private InterfaceIndex interfaceIndex;
    private ConstantPool constantPool;
    private List<ClassAccessFlag> accessFlags;
    private List<Field> fields;
    private Map<Pair<String, String>, Method> methods;
}
