package com.aaront.exercise.jvm.attribute;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.aaront.exercise.jvm.utils.string.ByteUtils.byte2UnsignedInt;

/**
 * @author tonyhui
 * @since 17/6/12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class LineNumberTableAttribute extends AbstractAttribute {
    private int attributeNameIndex;
    private int attributeLength;
    private int lineNumberTableLength;
    private List<LineNumberTable> lineNumberTables = new ArrayList<>();


    public static LineNumberTableAttribute generateLineNumberTableAttribute(byte[] content, int index, int length) {
        int lineNumberTableLength = (int)byte2UnsignedInt(Arrays.copyOfRange(content, 0, 2));
        List<LineNumberTable> lineNumberTables = new ArrayList<>(lineNumberTableLength);
        int start = 2;
        for (int i = 0; i < lineNumberTableLength; i++) {
            int startPC = (int)byte2UnsignedInt(Arrays.copyOfRange(content, start, start + 2));
            int lineNumber = (int)byte2UnsignedInt(Arrays.copyOfRange(content, start + 2, start + 4));
            lineNumberTables.add(new LineNumberTable(startPC, lineNumber));
            start += 4;
        }
        return new LineNumberTableAttribute(index, length, lineNumberTableLength, lineNumberTables);
    }
}
