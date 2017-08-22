package com.aaront.exercise.jvm.attribute;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.aaront.exercise.jvm.utils.string.ByteUtils.byte2UnsignedInt;

/**
 * @author tonyhui
 * @since 17/6/12
 */
public class LocalVariableTableAttribute extends AbstractAttribute {
    private int attributeNameIndex;
    private int attributeLength;
    private int localVariableTableLength;
    private List<LocalVariableTable> localVariableTableAttributes = new ArrayList<>();

    public LocalVariableTableAttribute(int attributeNameIndex) {
        this.attributeNameIndex = attributeNameIndex;
    }

    public LocalVariableTableAttribute(int localVariableTableLength, List<LocalVariableTable> localVariableTableAttributes) {
        this.localVariableTableLength = localVariableTableLength;
        this.localVariableTableAttributes = localVariableTableAttributes;
    }

    public LocalVariableTableAttribute(int attributeNameIndex, int attributeLength, int localVariableTableLength, List<LocalVariableTable> localVariableTableAttributes) {
        this.attributeNameIndex = attributeNameIndex;
        this.attributeLength = attributeLength;
        this.localVariableTableLength = localVariableTableLength;
        this.localVariableTableAttributes = localVariableTableAttributes;
    }

    public static LocalVariableTableAttribute generateLocalVariableTableAttribute(byte[] content, int attributeNameIndex, int attributeLength) {
        int lineVariableTableLength = (int)byte2UnsignedInt(Arrays.copyOfRange(content, 0, 2));
        List<LocalVariableTable> localVariableTables = new ArrayList<>(lineVariableTableLength);
        int start = 2;
        for (int i = 0; i < lineVariableTableLength; i++) {
            int startPC = (int)byte2UnsignedInt(Arrays.copyOfRange(content, start, start + 2));
            int length = (int)byte2UnsignedInt(Arrays.copyOfRange(content, start + 2, start + 4));
            int nameIndex = (int)byte2UnsignedInt(Arrays.copyOfRange(content, start + 4, start + 6));
            int descriptorIndex = (int)byte2UnsignedInt(Arrays.copyOfRange(content, start + 6, start + 8));
            int index = (int)byte2UnsignedInt(Arrays.copyOfRange(content, start + 8, start + 10));
            localVariableTables.add(new LocalVariableTable(startPC, length, nameIndex, descriptorIndex, index));
            start += 10;
        }
        return new LocalVariableTableAttribute(attributeNameIndex, attributeLength, lineVariableTableLength, localVariableTables);
    }

    public int getAttributeNameIndex() {
        return attributeNameIndex;
    }

    public void setAttributeNameIndex(int attributeNameIndex) {
        this.attributeNameIndex = attributeNameIndex;
    }

    public int getAttributeLength() {
        return attributeLength;
    }

    public void setAttributeLength(int attributeLength) {
        this.attributeLength = attributeLength;
    }

    public int getLocalVariableTableLength() {
        return localVariableTableLength;
    }

    public void setLocalVariableTableLength(int localVariableTableLength) {
        this.localVariableTableLength = localVariableTableLength;
    }

    public List<LocalVariableTable> getLocalVariableTableAttributes() {
        return localVariableTableAttributes;
    }

    public void setLocalVariableTableAttributes(List<LocalVariableTable> localVariableTableAttributes) {
        this.localVariableTableAttributes = localVariableTableAttributes;
    }
}
