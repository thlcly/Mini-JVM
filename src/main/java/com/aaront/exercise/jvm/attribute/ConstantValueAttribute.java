package com.aaront.exercise.jvm.attribute;

import com.aaront.exercise.jvm.ClassFile;
import com.aaront.exercise.jvm.constant.AbstractConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Arrays;

import static com.aaront.exercise.jvm.utils.string.ByteUtils.byte2UnsignedInt;

/**
 * @author tonyhui
 * @since 2017/8/22
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ConstantValueAttribute extends AbstractAttribute {
    private int constantValueIndex;
    private AbstractConstant constantInfo;

    private ConstantValueAttribute(int constantValueIndex, AbstractConstant constantInfo) {
        this.constantValueIndex = constantValueIndex;
        this.constantInfo = constantInfo;
    }

    public static ConstantValueAttribute generateConstantValueAttribute(byte[] content, int index, int length, ClassFile classFile) {
        ConstantValueAttribute constantValueAttribute = _parseConstantValueAttribute(content, classFile);
        constantValueAttribute.setAttributeNameIndex(index);
        constantValueAttribute.setAttributeLength(length);
        return constantValueAttribute;
    }

    private static ConstantValueAttribute _parseConstantValueAttribute(byte[] content, ClassFile classFile) {
        int constantValueIndex = (int)byte2UnsignedInt(Arrays.copyOfRange(content, 0, 2));
        AbstractConstant constantInfo = classFile.getConstantPool().getConstantInfo(constantValueIndex);
        return new ConstantValueAttribute(constantValueIndex, constantInfo);
    }
}
