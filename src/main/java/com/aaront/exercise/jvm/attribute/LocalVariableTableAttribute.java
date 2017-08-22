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
public class LocalVariableTableAttribute extends AbstractAttribute {
    private int attributeNameIndex;
    private int attributeLength;
    private int localVariableTableLength;
    private List<LocalVariableTable> localVariableTableAttributes = new ArrayList<>();

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
}
