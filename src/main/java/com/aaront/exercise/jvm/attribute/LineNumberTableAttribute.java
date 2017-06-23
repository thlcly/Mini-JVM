package com.aaront.exercise.jvm.attribute;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.aaront.exercise.jvm.utils.string.ByteUtils.byteToInteger;

/**
 * @author tonyhui
 * @since 17/6/12
 */
public class LineNumberTableAttribute extends AbstractAttribute {
    private int attributeNameIndex;
    private int attributeLength;
    private int lineNumberTableLength;
    private List<LineNumberTable> lineNumberTables = new ArrayList<>();

    public LineNumberTableAttribute() {
    }

    public LineNumberTableAttribute(int attributeNameIndex, int attributeLength, int lineNumberTableLength, List<LineNumberTable> lineNumberTables) {
        this.attributeNameIndex = attributeNameIndex;
        this.attributeLength = attributeLength;
        this.lineNumberTableLength = lineNumberTableLength;
        this.lineNumberTables = lineNumberTables;
    }


    public static LineNumberTableAttribute generateLineNumberTableAttribute(byte[] content, int index, int length) {
        int lineNumberTableLength = byteToInteger(Arrays.copyOfRange(content, 0, 2));
        List<LineNumberTable> lineNumberTables = new ArrayList<>(lineNumberTableLength);
        int start = 2;
        for (int i = 0; i < lineNumberTableLength; i++) {
            int startPC = byteToInteger(Arrays.copyOfRange(content, start, start + 2));
            int lineNumber = byteToInteger(Arrays.copyOfRange(content, start + 2, start + 4));
            lineNumberTables.add(new LineNumberTable(startPC, lineNumber));
            start += 4;
        }
        return new LineNumberTableAttribute(index, length, lineNumberTableLength, lineNumberTables);
    }


    public int getAttributeLength() {
        return attributeLength;
    }

    public void setAttributeLength(int attributeLength) {
        this.attributeLength = attributeLength;
    }

    public int getLineNumberTableLength() {
        return lineNumberTableLength;
    }

    public void setLineNumberTableLength(int lineNumberTableLength) {
        this.lineNumberTableLength = lineNumberTableLength;
    }

    public List<LineNumberTable> getLineNumberTables() {
        return lineNumberTables;
    }

    public void setLineNumberTables(List<LineNumberTable> lineNumberTables) {
        this.lineNumberTables = lineNumberTables;
    }

    public int getAttributeNameIndex() {
        return attributeNameIndex;
    }

    public void setAttributeNameIndex(int attributeNameIndex) {
        this.attributeNameIndex = attributeNameIndex;
    }
}
