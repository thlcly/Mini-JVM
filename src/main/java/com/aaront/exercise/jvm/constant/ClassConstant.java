package com.aaront.exercise.jvm.constant;

/**
 * @author tonyhui
 * @since 17/6/5
 */
public class ClassConstant extends AbstractConstant {
    private int nameIndex;
    private String className;

    public ClassConstant(ConstantPool pool, int tag, int nameIndex) {
        super(tag, pool);
        this.nameIndex = nameIndex;
    }

    public void setNameIndex(int nameIndex) {
        this.nameIndex = nameIndex;
    }

    public int getNameIndex() {
        return this.nameIndex;
    }

    public String getClassName() {
        if(className == null) className = pool.getUTF8String(nameIndex);
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
