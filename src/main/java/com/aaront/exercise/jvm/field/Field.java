package com.aaront.exercise.jvm.field;

import com.aaront.exercise.jvm.ClassFile;
import com.aaront.exercise.jvm.accessflag.FieldAccessFlag;
import com.aaront.exercise.jvm.attribute.AbstractAttribute;
import com.aaront.exercise.jvm.attribute.ConstantValueAttribute;
import com.aaront.exercise.jvm.constant.ConstantPool;
import com.aaront.exercise.jvm.constant.UTF8Constant;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.aaront.exercise.jvm.utils.string.ByteUtils.byte2UnsignedInt;

/**
 * @author tonyhui
 * @since 17/6/9
 */
@Data
@AllArgsConstructor
public class Field {
    private int accessFlag;
    private List<FieldAccessFlag> accessFlags;
    private int nameIndex;
    private int descriptorIndex;
    private int attributeCount;
    private List<AbstractAttribute> attributes;
    private int startIndexInclude;
    private int endIndexExclude;

    private ConstantPool pool;

    public static Field generateField(byte[] contents, int start, ClassFile classFile) {
        int accessFlag = (int) byte2UnsignedInt(Arrays.copyOfRange(contents, start, start + 2));
        List<FieldAccessFlag> accessFlags = _parseFieldAccessFlag(accessFlag);
        int nameIndex = (int) byte2UnsignedInt(Arrays.copyOfRange(contents, start + 2, start + 4));
        int descriptorIndex = (int) byte2UnsignedInt(Arrays.copyOfRange(contents, start + 4, start + 6));
        int attributesCount = (int) byte2UnsignedInt(Arrays.copyOfRange(contents, start + 6, start + 8));
        Pair<List<AbstractAttribute>, Integer> pair = _parseFieldAttribute(contents, start + 8, attributesCount, classFile);
        return new Field(accessFlag, accessFlags, nameIndex, descriptorIndex, attributesCount, pair.getLeft(), start, pair.getRight(), classFile.getConstantPool());
    }

    /**
     * 解析字段修饰符
     */
    private static List<FieldAccessFlag> _parseFieldAccessFlag(int accessFlag) {
        List<FieldAccessFlag> fieldAccessFlags = new ArrayList<>();
        if ((accessFlag & FieldAccessFlag.ACC_PUBLIC.getCode()) != 0) {
            fieldAccessFlags.add(FieldAccessFlag.ACC_PUBLIC);
        }
        if ((accessFlag & FieldAccessFlag.ACC_PRIVATE.getCode()) != 0) {
            fieldAccessFlags.add(FieldAccessFlag.ACC_PRIVATE);
        }
        if ((accessFlag & FieldAccessFlag.ACC_PROTECTED.getCode()) != 0) {
            fieldAccessFlags.add(FieldAccessFlag.ACC_PROTECTED);
        }
        if ((accessFlag & FieldAccessFlag.ACC_STATIC.getCode()) != 0) {
            fieldAccessFlags.add(FieldAccessFlag.ACC_STATIC);
        }
        if ((accessFlag & FieldAccessFlag.ACC_FINAL.getCode()) != 0) {
            fieldAccessFlags.add(FieldAccessFlag.ACC_FINAL);
        }
        if ((accessFlag & FieldAccessFlag.ACC_VOLATILE.getCode()) != 0) {
            fieldAccessFlags.add(FieldAccessFlag.ACC_VOLATILE);
        }
        if ((accessFlag & FieldAccessFlag.ACC_TRANSIENT.getCode()) != 0) {
            fieldAccessFlags.add(FieldAccessFlag.ACC_TRANSIENT);
        }
        if ((accessFlag & FieldAccessFlag.ACC_SYNTHETIC.getCode()) != 0) {
            fieldAccessFlags.add(FieldAccessFlag.ACC_SYNTHETIC);
        }
        if ((accessFlag & FieldAccessFlag.ACC_ENUM.getCode()) != 0) {
            fieldAccessFlags.add(FieldAccessFlag.ACC_ENUM);
        }
        return fieldAccessFlags;
    }

    /**
     * 解析字段属性
     */
    private static Pair<List<AbstractAttribute>, Integer> _parseFieldAttribute(byte[] content, int start, int attributeCount, ClassFile classFile) {
        List<AbstractAttribute> attributes = new ArrayList<>();
        ConstantPool pool = classFile.getConstantPool();
        for (int i = 1; i <= attributeCount; i++) {
            // 获取属性名在常量池中的索引
            int index = (int) byte2UnsignedInt(Arrays.copyOfRange(content, start, start + 2));
            // TODO: 17/6/30 这里先暂时强转, 后序数组拷贝要优化, 要支持long型的
            // 获取属性的长度
            int length = (int) byte2UnsignedInt(Arrays.copyOfRange(content, start + 2, start + 6));
            String attributeName = pool.getUTF8String(index);
            if (attributeName.equals("ConstantValue")) {
                ConstantValueAttribute constantValueAttribute = ConstantValueAttribute.generateConstantValueAttribute(Arrays.copyOfRange(content, start + 6, start + 6 + length), index + 6, length, classFile);
                attributes.add(constantValueAttribute);
            }
            start = start + 6 + length;
        }
        return Pair.of(attributes, start);
    }

    public String toString() {
        String name = ((UTF8Constant) pool.getConstantInfo(this.nameIndex)).getValue();

        String desc = ((UTF8Constant) pool.getConstantInfo(this.descriptorIndex)).getValue();
        return name + ":" + desc;
    }
}
