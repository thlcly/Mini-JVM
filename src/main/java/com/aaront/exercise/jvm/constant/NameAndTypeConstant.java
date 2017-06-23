package com.aaront.exercise.jvm.constant;

/**
 * @author tonyhui
 * @since 17/6/5
 */
public class NameAndTypeConstant extends AbstractConstant {
    private int nameIndex;
    private String name;
    private int descriptorIndex;
    private String descriptor;

    public NameAndTypeConstant(ConstantPool pool, int tag, int nameIndex, int descriptorIndex) {
        super(tag, pool);
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
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

    public String getName() {
        if(name == null) name = pool.getUTF8String(nameIndex);
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptor() {
        if(descriptor == null) descriptor = pool.getUTF8String(descriptorIndex);
        return descriptor;
    }

    public void setDescriptor(String descriptor) {
        this.descriptor = descriptor;
    }
}
