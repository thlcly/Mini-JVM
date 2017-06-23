package com.aaront.exercise.jvm.field;

import com.aaront.exercise.jvm.accessflag.FieldAccessFlag;
import com.aaront.exercise.jvm.attribute.AbstractAttribute;
import com.aaront.exercise.jvm.constant.ConstantPool;
import com.aaront.exercise.jvm.constant.UTF8Constant;

import java.util.List;

/**
 * @author tonyhui
 * @since 17/6/9
 */
public class Field {
    private int accessFlag;
    private List<FieldAccessFlag> accessFlags;
    private int nameIndex;
    private int descriptorIndex;
    private int attributeCount;
    private List<AbstractAttribute> attributes;

    private ConstantPool pool;

    public Field() {
    }

    public Field(int accessFlag, List<FieldAccessFlag> accessFlags, int nameIndex, int descriptorIndex, int attributeCount, List<AbstractAttribute> attributes, ConstantPool pool) {
        this.accessFlag = accessFlag;
        this.accessFlags = accessFlags;
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
        this.attributeCount = attributeCount;
        this.attributes = attributes;
        this.pool = pool;
    }

    public String toString() {
        String name = ((UTF8Constant) pool.getConstantInfo(this.nameIndex)).getValue();

        String desc = ((UTF8Constant) pool.getConstantInfo(this.descriptorIndex)).getValue();
        return name + ":" + desc;
    }

    public int getAccessFlag() {
        return accessFlag;
    }

    public void setAccessFlag(int accessFlag) {
        this.accessFlag = accessFlag;
    }

    public int getNameIndex() {
        return nameIndex;
    }

    public void setNameIndex(int nameIndex) {
        this.nameIndex = nameIndex;
    }

    public int getDescriptorIndex() {
        return descriptorIndex;
    }

    public void setDescriptorIndex(int descriptorIndex) {
        this.descriptorIndex = descriptorIndex;
    }

    public int getAttributeCount() {
        return attributeCount;
    }

    public void setAttributeCount(int attributeCount) {
        this.attributeCount = attributeCount;
    }

    public List<FieldAccessFlag> getAccessFlags() {
        return accessFlags;
    }

    public void setAccessFlags(List<FieldAccessFlag> accessFlags) {
        this.accessFlags = accessFlags;
    }

    public List<AbstractAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AbstractAttribute> attributes) {
        this.attributes = attributes;
    }
}
