package com.aaront.exercise.jvm.attribute;

/**
 * @author tonyhui
 * @since 17/6/12
 */
public class LocalVariableTable {
    int startPC;     //局部变量的索引都在范围[start_pc,
    int length;        // start_pc+length)中
    int nameIndex;  // 变量名索引  （在常量池中）
    int descriptorIndex;   //变量描述索引（常量池中）
    int index;   //此局部变量在当前栈帧的局部变量表中的索引

    public LocalVariableTable() {
    }

    public LocalVariableTable(int startPC, int length, int nameIndex, int descriptorIndex, int index) {
        this.startPC = startPC;
        this.length = length;
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
        this.index = index;
    }

    public int getStartPC() {
        return startPC;
    }

    public void setStartPC(int startPC) {
        this.startPC = startPC;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
