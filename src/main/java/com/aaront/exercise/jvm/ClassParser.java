package com.aaront.exercise.jvm;

import com.aaront.exercise.jvm.accessflag.ClassAccessFlag;
import com.aaront.exercise.jvm.accessflag.FieldAccessFlag;
import com.aaront.exercise.jvm.attribute.AbstractAttribute;
import com.aaront.exercise.jvm.constant.*;
import com.aaront.exercise.jvm.field.Field;
import com.aaront.exercise.jvm.index.ClassIndex;
import com.aaront.exercise.jvm.index.InterfaceIndex;
import com.aaront.exercise.jvm.method.Method;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

import static com.aaront.exercise.jvm.accessflag.ClassAccessFlag.*;
import static com.aaront.exercise.jvm.utils.string.ByteUtils.byteToHexString;
import static com.aaront.exercise.jvm.utils.string.ByteUtils.byte2Int;

/**
 * @author tonyhui
 * @since 17/6/5
 */
public class ClassParser {
    private ClassFile classFile = new ClassFile();
    private static final int MAGIC_NUMBER_START = 0;
    private static final int MAGIC_NUMBER_END = 4;
    private static final int VERSION_START = 4;
    private static final int VERSION_END = 8;
    private static final int CONSTANT_POOL_LENGTH_START = 8;
    private static final int CONSTANT_POOL_LENGTH_END = 10;
    private static final int CONSTANT_POOL_START = 10;

    private static final byte CONSTANT_UTF8 = 1;
    private static final byte CONSTANT_INTEGER = 3;
    private static final byte CONSTANT_FLOAT = 4;
    private static final byte CONSTANT_LONG = 5;
    private static final byte CONSTANT_DOUBLE = 6;
    private static final byte CONSTANT_CLASS = 7;
    private static final byte CONSTANT_STRING = 8;
    private static final byte CONSTANT_FIELD_REF = 9;
    private static final byte CONSTANT_METHOD_REF = 10;
    private static final byte CONSTANT_INTERFACE_METHOD_REF = 11;
    private static final byte CONSTANT_NAME_AND_TYPE = 12;
    private static final byte CONSTANT_METHOD_HANDLE = 15;
    private static final byte CONSTANT_METHOD_TYPE = 16;
    private static final byte CONSTANT_INVOKE_DYNAMIC = 18;


    public ClassFile parse(byte[] contents) {
        _parseMagicNumber(Arrays.copyOfRange(contents, MAGIC_NUMBER_START, MAGIC_NUMBER_END));
        _parseVersion(Arrays.copyOfRange(contents, VERSION_START, VERSION_END));
        int constantPoolEnd = _parseConstantPool(contents);
        int accessFlagEnd = _parseClassAccessFlag(contents, constantPoolEnd);
        int classIndexEnd = _parseClassIndex(contents, accessFlagEnd);
        int interfaceEnd = _parseInterface(contents, classIndexEnd);
        int fieldEnd = _parseField(contents, interfaceEnd);
        int methodEnd = _parseMethod(contents, fieldEnd);
        return classFile;
    }

    /**
     * 解析java文件的魔数
     */
    private void _parseMagicNumber(byte[] contents) {
        String magicNumber = byteToHexString(contents);
        classFile.setMagicNumber(magicNumber);
    }

    /**
     * 解析编译java源文件的jdk的版本
     */
    private void _parseVersion(byte[] contents) {
        int minor = byte2Int(new byte[]{contents[0], contents[1]});
        int major = byte2Int(new byte[]{contents[2], contents[3]});
        classFile.setMinorVersion(minor);
        classFile.setMajorVersion(major);
    }

    /**
     * 解析常量池
     */
    private int _parseConstantPool(byte[] contents) {
        Integer constantPoolLength = byte2Int(Arrays.copyOfRange(contents, CONSTANT_POOL_LENGTH_START, CONSTANT_POOL_LENGTH_END));
        int pos = CONSTANT_POOL_START;
        List<AbstractConstant> abstractConstant = new ArrayList<>();
        ConstantPool pool = new ConstantPool(abstractConstant);
        abstractConstant.add(new NullConstant());
        for (int i = 0; i < constantPoolLength - 1; i++) {
            byte tag = contents[pos];
            pos = pos + 1;
            switch (tag) {
                case CONSTANT_UTF8: {
                    int length = byte2Int(Arrays.copyOfRange(contents, pos, pos + 2));
                    byte[] content = Arrays.copyOfRange(contents, pos + 2, pos + 2 + length);
                    UTF8Constant utf8Constant = new UTF8Constant(pool, tag, length, content);
                    abstractConstant.add(utf8Constant);
                    pos += (2 + length);
                    break;
                }
                case CONSTANT_INTEGER: {
                    // TODO: 17/6/6 后序添加
                    break;
                }
                case CONSTANT_FLOAT: {
                    // TODO: 17/6/6 后序添加
                    break;
                }
                case CONSTANT_LONG: {
                    // TODO: 17/6/6 后序添加
                    break;
                }
                case CONSTANT_DOUBLE: {
                    // TODO: 17/6/6 后序添加
                    break;
                }
                case CONSTANT_CLASS: {
                    int nameIndex = byte2Int(Arrays.copyOfRange(contents, pos, pos + 2));
                    ClassConstant classConstant = new ClassConstant(pool, tag, nameIndex);
                    abstractConstant.add(classConstant);
                    pos += 2;
                    break;
                }
                case CONSTANT_STRING: {
                    Integer stringIndex = byte2Int(Arrays.copyOfRange(contents, pos, pos + 2));
                    StringConstant stringConstant = new StringConstant(pool, tag, stringIndex);
                    abstractConstant.add(stringConstant);
                    pos += 2;
                    break;
                }
                case CONSTANT_FIELD_REF: {
                    Integer classIndex = byte2Int(Arrays.copyOfRange(contents, pos, pos + 2));
                    Integer nameAndTypeIndex = byte2Int(Arrays.copyOfRange(contents, pos + 2, pos + 4));
                    FieldRefConstant fieldRefConstant = new FieldRefConstant(pool, tag, classIndex, nameAndTypeIndex);
                    abstractConstant.add(fieldRefConstant);
                    pos += 4;
                    break;
                }
                case CONSTANT_METHOD_REF: {
                    Integer classIndex = byte2Int(Arrays.copyOfRange(contents, pos, pos + 2));
                    Integer nameAndTypeIndex = byte2Int(Arrays.copyOfRange(contents, pos + 2, pos + 4));
                    MethodRefConstant methodRefConstant = new MethodRefConstant(pool, tag, classIndex, nameAndTypeIndex);
                    abstractConstant.add(methodRefConstant);
                    pos += 4;
                    break;
                }
                case CONSTANT_INTERFACE_METHOD_REF: {
                    // TODO: 17/6/6 后序添加
                    break;
                }
                case CONSTANT_NAME_AND_TYPE: {
                    Integer nameIndex = byte2Int(Arrays.copyOfRange(contents, pos, pos + 2));
                    Integer descriptorIndex = byte2Int(Arrays.copyOfRange(contents, pos + 2, pos + 4));
                    NameAndTypeConstant nameAndTypeConstant = new NameAndTypeConstant(pool, tag, nameIndex, descriptorIndex);
                    abstractConstant.add(nameAndTypeConstant);
                    pos += 4;
                    break;
                }
                case CONSTANT_METHOD_HANDLE: {
                    // TODO: 17/6/6 后序添加
                    break;
                }
                case CONSTANT_METHOD_TYPE: {
                    // TODO: 17/6/6 后序添加
                    break;
                }
                case CONSTANT_INVOKE_DYNAMIC: {
                    // TODO: 17/6/6 后序添加
                    break;
                }
                default:
                    throw new RuntimeException("class文件常量池结构不正确");
            }
        }
        classFile.setConstantPool(pool);
        return pos;
    }

    /**
     * 解析Class的修饰符
     */
    private int _parseClassAccessFlag(byte[] contents, int accessFlagStart) {
        int accessFlag = byte2Int(Arrays.copyOfRange(contents, accessFlagStart, accessFlagStart + 2));
        List<ClassAccessFlag> classAccessFlags = new ArrayList<>();
        if ((accessFlag & ACC_PUBLIC.getCode()) != 0) {
            classAccessFlags.add(ACC_PUBLIC);
        }
        if ((accessFlag & ACC_FINAL.getCode()) != 0) {
            classAccessFlags.add(ACC_FINAL);
        }
        if ((accessFlag & ACC_SUPER.getCode()) != 0) {
            classAccessFlags.add(ACC_SUPER);
        }
        if ((accessFlag & ACC_ABSTRACT.getCode()) != 0) {
            classAccessFlags.add(ACC_ABSTRACT);
        }
        if ((accessFlag & ACC_SYNTHETIC.getCode()) != 0) {
            classAccessFlags.add(ACC_SYNTHETIC);
        }
        if ((accessFlag & ACC_ENUM.getCode()) != 0) {
            classAccessFlags.add(ACC_ENUM);
        }
        classFile.setAccessFlag(classAccessFlags);
        return accessFlagStart + 2;
    }

    /**
     * 解析Class和其父类在常量池中的索引
     */
    private int _parseClassIndex(byte[] contents, int classIndexStart) {
        int thisClassIndex = byte2Int(Arrays.copyOfRange(contents, classIndexStart, classIndexStart + 2));
        int superClassIndex = byte2Int(Arrays.copyOfRange(contents, classIndexStart + 2, classIndexStart + 4));
        ClassIndex classIndex = new ClassIndex(thisClassIndex, superClassIndex);
        classFile.setClassIndex(classIndex);
        return classIndexStart + 4;
    }

    /**
     * 解析Class实现的接口
     */
    private int _parseInterface(byte[] contents, int interfaceIndexStart) {
        int length = byte2Int(Arrays.copyOfRange(contents, interfaceIndexStart, interfaceIndexStart + 2));
        List<Integer> interfaceIndexes = new ArrayList<>();
        int start = interfaceIndexStart + 2;
        for (int i = 1; i <= length; i++) {
            int index = byte2Int(Arrays.copyOfRange(contents, start, start + 2 * i));
            interfaceIndexes.add(index);
            start += 2 * i;
        }
        InterfaceIndex interfaceIndex = new InterfaceIndex(interfaceIndexes);
        classFile.setInterfaceIndex(interfaceIndex);
        return interfaceIndexStart + 2 + 2 * length;
    }

    /**
     * 解析字段
     */
    private int _parseField(byte[] contents, int fieldIndexStart) {
        int length = byte2Int(Arrays.copyOfRange(contents, fieldIndexStart, fieldIndexStart + 2));
        List<Field> fields = new ArrayList<>(length);
        int start = fieldIndexStart + 2;
        for (int i = 1; i <= length; i++) {
            int accessFlags = byte2Int(Arrays.copyOfRange(contents, start, start + 2));
            List<FieldAccessFlag> fieldAccessFlags = _parseFieldAccessFlag(accessFlags);
            int nameIndex = byte2Int(Arrays.copyOfRange(contents, start + 2, start + 4));
            int descriptorIndex = byte2Int(Arrays.copyOfRange(contents, start + 4, start + 6));
            int attributesCount = byte2Int(Arrays.copyOfRange(contents, start + 6, start + 8));
            Pair<List<AbstractAttribute>, Integer> pair = _parseFieldAttribute(contents, start + 8, attributesCount);
            fields.add(new Field(accessFlags, fieldAccessFlags, nameIndex, descriptorIndex, attributesCount, pair.getLeft(), classFile.getConstantPool()));
            start += (8 + pair.getRight());
        }
        classFile.setFields(fields);
        return start;
    }

    /**
     * 解析字段修饰符
     */
    private List<FieldAccessFlag> _parseFieldAccessFlag(int accessFlag) {
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
    private Pair<List<AbstractAttribute>, Integer> _parseFieldAttribute(byte[] contents, int attributeStartIndex, int attributeCount) {
        List<AbstractAttribute> attributes = new ArrayList<>();
        for (int i = 1; i <= attributeCount; i++) {
            // TODO: 17/6/12 后序添加解析字段属性的代码
        }
        return Pair.of(attributes, 0);
    }

    /**
     * 解析方法
     */
    private int _parseMethod(byte[] contents, int methodIndexStart) {
        int length = byte2Int(Arrays.copyOfRange(contents, methodIndexStart, methodIndexStart + 2));
        //List<Method> methods = new ArrayList<>(length);
        Map<String, Method> methods = new HashMap<>(length);
        int start = methodIndexStart + 2;
        for (int i = 0; i < length; i++) {
            Method method = Method.generateMethod(contents, start, classFile);
            methods.put(method.getDescriptor(), method);
            start = method.getEndIndexExclude();
        }
        classFile.setMethods(methods);
        return start;
    }
}
