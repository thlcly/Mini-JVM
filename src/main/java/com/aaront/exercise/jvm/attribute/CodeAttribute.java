package com.aaront.exercise.jvm.attribute;

import com.aaront.exercise.jvm.ClassFile;
import com.aaront.exercise.jvm.commands.AbstractCommand;
import com.aaront.exercise.jvm.commands.CommandParser;
import com.aaront.exercise.jvm.constant.ConstantPool;
import com.aaront.exercise.jvm.utils.string.ByteUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.aaront.exercise.jvm.utils.string.ByteUtils.byteToInteger;

/**
 * @author tonyhui
 * @since 17/6/9
 */
public class CodeAttribute extends AbstractAttribute {
    private int attributeNameIndex;
    private int attributeLength;
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
        int maxStack = byteToInteger(Arrays.copyOfRange(content, 0, 2));
        int maxLocals = byteToInteger(Arrays.copyOfRange(content, 2, 4));
        int codeLength = byteToInteger(Arrays.copyOfRange(content, 4, 8));
        byte[] code = Arrays.copyOfRange(content, 8, 8 + codeLength);
        int exceptionTableLength = byteToInteger(Arrays.copyOfRange(content, 8 + codeLength, 10 + codeLength));
        List<ExceptionAttribute> exceptions = _parseExceptionInfo(Arrays.copyOfRange(content, 10 + codeLength, 10 + codeLength + exceptionTableLength * 8));
        int subAttributeCount = byteToInteger(Arrays.copyOfRange(content, 10 + codeLength + exceptionTableLength * 8, 10 + codeLength + exceptionTableLength * 8 + 2));
        List<AbstractAttribute> subAttrs = new ArrayList<>(subAttributeCount);
        int subAttributeStartIndex = 10 + codeLength + exceptionTableLength * 8 + 2;
        for (int i = 0; i < subAttributeCount; i++) {
            int index = byteToInteger(Arrays.copyOfRange(content, subAttributeStartIndex, subAttributeStartIndex + 2));
            int length = byteToInteger(Arrays.copyOfRange(content, subAttributeStartIndex + 2, subAttributeStartIndex + 6));
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

    public CodeAttribute(int attributeNameIndex, int attributeLength, int maxStack, int maxLocals, int codeLength, byte[] code, int exceptionTableLength, List<ExceptionAttribute> exceptionTable, int attributesCount, List<AbstractAttribute> attributes) {
        this.attributeNameIndex = attributeNameIndex;
        this.attributeLength = attributeLength;
        this.maxStack = maxStack;
        this.maxLocals = maxLocals;
        this.codeLength = codeLength;
        this.code = code;
        this.exceptionTableLength = exceptionTableLength;
        this.exceptionTable = exceptionTable;
        this.attributesCount = attributesCount;
        this.attributes = attributes;
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

    public int getMaxStack() {
        return maxStack;
    }

    public void setMaxStack(int maxStack) {
        this.maxStack = maxStack;
    }

    public int getMaxLocals() {
        return maxLocals;
    }

    public void setMaxLocals(int maxLocals) {
        this.maxLocals = maxLocals;
    }

    public int getCodeLength() {
        return codeLength;
    }

    public void setCodeLength(int codeLength) {
        this.codeLength = codeLength;
    }

    public byte[] getCode() {
        return code;
    }

    public void setCode(byte[] code) {
        this.code = code;
    }

    public String getCodeText() {
        return codeText;
    }

    public void setCodeText(String codeText) {
        this.codeText = codeText;
    }

    public int getExceptionTableLength() {
        return exceptionTableLength;
    }

    public void setExceptionTableLength(int exceptionTableLength) {
        this.exceptionTableLength = exceptionTableLength;
    }

    public List<ExceptionAttribute> getExceptionTable() {
        return exceptionTable;
    }

    public void setExceptionTable(List<ExceptionAttribute> exceptionTable) {
        this.exceptionTable = exceptionTable;
    }

    public int getAttributesCount() {
        return attributesCount;
    }

    public void setAttributesCount(int attributesCount) {
        this.attributesCount = attributesCount;
    }

    public List<AbstractAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<AbstractAttribute> attributes) {
        this.attributes = attributes;
    }

    public List<AbstractCommand> getCommands() {
        return commands;
    }

    public void setCommands(List<AbstractCommand> commands) {
        this.commands = commands;
    }

    public ClassFile getClassFile() {
        return classFile;
    }

    public void setClassFile(ClassFile classFile) {
        this.classFile = classFile;
    }
}
