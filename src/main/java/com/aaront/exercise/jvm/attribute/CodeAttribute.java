package com.aaront.exercise.jvm.attribute;

import com.aaront.exercise.jvm.ClassFile;
import com.aaront.exercise.jvm.commands.AbstractCommand;
import com.aaront.exercise.jvm.commands.CommandParser;
import com.aaront.exercise.jvm.constant.ConstantPool;
import com.aaront.exercise.jvm.utils.string.ByteUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.aaront.exercise.jvm.utils.string.ByteUtils.byte2UnsignedInt;

/**
 * @author tonyhui
 * @since 17/6/9
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class CodeAttribute extends AbstractAttribute {
    private int maxStack;
    private int maxLocals;
    private int codeLength;
    private byte[] code = new byte[codeLength];
    private String codeText;
    private int exceptionTableLength;
    private List<ExceptionAttribute> exceptionTable = new ArrayList<>();
    private int attributesCount;
    private List<AbstractAttribute> attributes = new ArrayList<>();
    private List<AbstractCommand> commands = new ArrayList<>();
    private ClassFile classFile;

    public CodeAttribute(ClassFile classFile, int maxStack, int maxLocals, int codeLength, byte[] code, int exceptionTableLength, List<ExceptionAttribute> exceptionTable, int attributesCount, List<AbstractAttribute> attributes) {
        this.classFile  = classFile;
        this.maxStack = maxStack;
        this.maxLocals = maxLocals;
        this.codeLength = codeLength;
        this.code = code;
        this.codeText = ByteUtils.byteToHexString(code);
        this.commands = CommandParser.parse(this.classFile, codeText);
        this.exceptionTableLength = exceptionTableLength;
        this.exceptionTable = exceptionTable;
        this.attributesCount = attributesCount;
        this.attributes = attributes;
    }

    public static CodeAttribute generateCodeAttribute(byte[] content, int index, int length, ClassFile classFile) {
        CodeAttribute codeAttribute = _parseCodeAttribute(content, classFile);
        codeAttribute.setAttributeNameIndex(index);
        codeAttribute.setAttributeLength(length);
        return codeAttribute;
    }

    private static CodeAttribute _parseCodeAttribute(byte[] content, ClassFile classFile) {
        ConstantPool pool = classFile.getConstantPool();
        int maxStack = (int)byte2UnsignedInt(Arrays.copyOfRange(content, 0, 2));
        int maxLocals = (int)byte2UnsignedInt(Arrays.copyOfRange(content, 2, 4));
        // TODO: 17/6/30 这里先暂时强转, 后序数组拷贝要优化, 要支持long型的
        int codeLength = (int) byte2UnsignedInt(Arrays.copyOfRange(content, 4, 8));
        byte[] code = Arrays.copyOfRange(content, 8, 8 + codeLength);
        int exceptionTableLength = (int)byte2UnsignedInt(Arrays.copyOfRange(content, 8 + codeLength, 10 + codeLength));
        List<ExceptionAttribute> exceptions = _parseExceptionInfo(Arrays.copyOfRange(content, 10 + codeLength, 10 + codeLength + exceptionTableLength * 8));
        int subAttributeCount = (int)byte2UnsignedInt(Arrays.copyOfRange(content, 10 + codeLength + exceptionTableLength * 8, 10 + codeLength + exceptionTableLength * 8 + 2));
        List<AbstractAttribute> subAttrs = new ArrayList<>(subAttributeCount);
        int subAttributeStartIndex = 10 + codeLength + exceptionTableLength * 8 + 2;
        for (int i = 0; i < subAttributeCount; i++) {
            int index = (int)byte2UnsignedInt(Arrays.copyOfRange(content, subAttributeStartIndex, subAttributeStartIndex + 2));
            // TODO: 17/6/30 这里先暂时强转, 后序数组拷贝要优化, 要支持long型的
            int length = (int) byte2UnsignedInt(Arrays.copyOfRange(content, subAttributeStartIndex + 2, subAttributeStartIndex + 6));
            String subAttrName = pool.getUTF8String(index);
            if (subAttrName.equals("LineNumberTable")) {
                LineNumberTableAttribute lineNumberTableAttribute = LineNumberTableAttribute.generateLineNumberTableAttribute(Arrays.copyOfRange(content, subAttributeStartIndex + 6, subAttributeStartIndex + 6 + length), index, length);
                subAttrs.add(lineNumberTableAttribute);
            } else if (subAttrName.equals("LocalVariableTable")) {
                LocalVariableTableAttribute localVariableTableAttribute = LocalVariableTableAttribute.generateLocalVariableTableAttribute(Arrays.copyOfRange(content, subAttributeStartIndex + 6, subAttributeStartIndex + 6 + length), index, length);
                subAttrs.add(localVariableTableAttribute);
            }
            subAttributeStartIndex = subAttributeStartIndex + 6 + length;
        }
        return new CodeAttribute(classFile, maxStack, maxLocals, codeLength, code, exceptionTableLength, exceptions, subAttributeCount, subAttrs);
    }

    private static List<ExceptionAttribute> _parseExceptionInfo(byte[] exceptionContent) {
        // TODO: 17/6/12 后序实现
        return new ArrayList<>();
    }
}
