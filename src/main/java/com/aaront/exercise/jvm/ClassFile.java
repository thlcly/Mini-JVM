package com.aaront.exercise.jvm;

import com.aaront.exercise.jvm.accessflag.ClassAccessFlag;
import com.aaront.exercise.jvm.constant.ClassConstant;
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

    public String getSuperClassName(){
        ClassConstant superClass = (ClassConstant)this.getConstantPool().getConstantInfo(this.classIndex.getSuperClassIndex());
        return superClass.getClassName();
    }

    public Method getMethod(String methodName, String paramAndReturnType){

        for(Method m :methods.values()){

            int nameIndex = m.getNameIndex();
            int descriptionIndex = m.getDescriptorIndex();

            String name = this.getConstantPool().getUTF8String(nameIndex);
            String desc = this.getConstantPool().getUTF8String(descriptionIndex);
            if(name.equals(methodName) && desc.equals(paramAndReturnType)){
                return m;
            }
        }
        return null;
    }
}
