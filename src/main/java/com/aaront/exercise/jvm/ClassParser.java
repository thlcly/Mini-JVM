package com.aaront.exercise.jvm;

import com.aaront.exercise.jvm.accessflag.ClassAccessFlag;
import com.aaront.exercise.jvm.constant.*;
import com.aaront.exercise.jvm.field.Field;
import com.aaront.exercise.jvm.index.ClassIndex;
import com.aaront.exercise.jvm.index.InterfaceIndex;
import com.aaront.exercise.jvm.method.Method;
import org.apache.commons.lang3.tuple.Pair;

import java.util.*;

import static com.aaront.exercise.jvm.accessflag.ClassAccessFlag.*;
import static com.aaront.exercise.jvm.utils.string.ByteUtils.byte2UnsignedInt;
import static com.aaront.exercise.jvm.utils.string.ByteUtils.byteToHexString;

/**
 * @author tonyhui
 * @since 17/6/5
 */
public class ClassParser {
    private ClassFile classFile;
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
        classFile = new ClassFile();
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
        int minor = (int) byte2UnsignedInt(new byte[]{contents[0], contents[1]});
        int major = (int) byte2UnsignedInt(new byte[]{contents[2], contents[3]});
        classFile.setMinorVersion(minor);
        classFile.setMajorVersion(major);
    }

    /**
     * 解析常量池
     */
    private int _parseConstantPool(byte[] contents) {
        Integer constantPoolLength = (int) byte2UnsignedInt(Arrays.copyOfRange(contents, CONSTANT_POOL_LENGTH_START, CONSTANT_POOL_LENGTH_END));
        int pos = CONSTANT_POOL_START;
        List<AbstractConstant> abstractConstant = new ArrayList<>();
        ConstantPool pool = new ConstantPool(abstractConstant);
        abstractConstant.add(new NullConstant());
        for (int i = 0; i < constantPoolLength - 1; i++) {
            byte tag = contents[pos];
            pos = pos + 1;
            switch (tag) {
                case CONSTANT_UTF8: {
                    int length = (int) byte2UnsignedInt(Arrays.copyOfRange(contents, pos, pos + 2));
                    byte[] content = Arrays.copyOfRange(contents, pos + 2, pos + 2 + length);
                    UTF8Constant utf8Constant = new UTF8Constant(pool, tag, length, content);
                    abstractConstant.add(utf8Constant);
                    pos += (2 + length);
                    break;
                }
                case CONSTANT_INTEGER: {
                    byte[] content = Arrays.copyOfRange(contents, pos, pos + 4);
                    IntegerConstant integerConstant = new IntegerConstant(tag, pool, content);
                    abstractConstant.add(integerConstant);
                    pos += 4;
                    break;
                }
                case CONSTANT_FLOAT: {
                    byte[] content = Arrays.copyOfRange(contents, pos, pos + 4);
                    FloatConstant floatConstant = new FloatConstant(tag, pool, content);
                    abstractConstant.add(floatConstant);
                    pos += 4;
                    break;
                }
                case CONSTANT_LONG: {
                    byte[] highContent = Arrays.copyOfRange(contents, pos, pos + 4);
                    byte[] lowContent = Arrays.copyOfRange(contents, pos + 4, pos + 8);
                    LongConstant longConstant = new LongConstant(tag, pool, highContent, lowContent);
                    abstractConstant.add(longConstant);
                    pos += 8;
                    abstractConstant.add(new NullConstant());
                    i++;
                    break;
                }
                case CONSTANT_DOUBLE: {
                    // TODO: 17/6/6 后序添加

                    break;
                }
                case CONSTANT_CLASS: {
                    int nameIndex = (int) byte2UnsignedInt(Arrays.copyOfRange(contents, pos, pos + 2));
                    ClassConstant classConstant = new ClassConstant(pool, tag, nameIndex);
                    abstractConstant.add(classConstant);
                    pos += 2;
                    break;
                }
                case CONSTANT_STRING: {
                    Integer stringIndex = (int) byte2UnsignedInt(Arrays.copyOfRange(contents, pos, pos + 2));
                    StringConstant stringConstant = new StringConstant(pool, tag, stringIndex);
                    abstractConstant.add(stringConstant);
                    pos += 2;
                    break;
                }
                case CONSTANT_FIELD_REF: {
                    Integer classIndex = (int) byte2UnsignedInt(Arrays.copyOfRange(contents, pos, pos + 2));
                    Integer nameAndTypeIndex = (int) byte2UnsignedInt(Arrays.copyOfRange(contents, pos + 2, pos + 4));
                    FieldRefConstant fieldRefConstant = new FieldRefConstant(pool, tag, classIndex, nameAndTypeIndex);
                    abstractConstant.add(fieldRefConstant);
                    pos += 4;
                    break;
                }
                case CONSTANT_METHOD_REF: {
                    Integer classIndex = (int) byte2UnsignedInt(Arrays.copyOfRange(contents, pos, pos + 2));
                    Integer nameAndTypeIndex = (int) byte2UnsignedInt(Arrays.copyOfRange(contents, pos + 2, pos + 4));
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
                    Integer nameIndex = (int) byte2UnsignedInt(Arrays.copyOfRange(contents, pos, pos + 2));
                    Integer descriptorIndex = (int) byte2UnsignedInt(Arrays.copyOfRange(contents, pos + 2, pos + 4));
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
        int accessFlag = (int) byte2UnsignedInt(Arrays.copyOfRange(contents, accessFlagStart, accessFlagStart + 2));
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
        classFile.setAccessFlags(classAccessFlags);
        return accessFlagStart + 2;
    }

    /**
     * 解析Class和其父类在常量池中的索引
     */
    private int _parseClassIndex(byte[] contents, int classIndexStart) {
        int thisClassIndex = (int) byte2UnsignedInt(Arrays.copyOfRange(contents, classIndexStart, classIndexStart + 2));
        int superClassIndex = (int) byte2UnsignedInt(Arrays.copyOfRange(contents, classIndexStart + 2, classIndexStart + 4));
        ClassIndex classIndex = new ClassIndex(thisClassIndex, superClassIndex);
        classFile.setClassIndex(classIndex);
        return classIndexStart + 4;
    }

    /**
     * 解析Class实现的接口
     */
    private int _parseInterface(byte[] contents, int interfaceIndexStart) {
        int length = (int) byte2UnsignedInt(Arrays.copyOfRange(contents, interfaceIndexStart, interfaceIndexStart + 2));
        List<Integer> interfaceIndexes = new ArrayList<>();
        int start = interfaceIndexStart + 2;
        for (int i = 1; i <= length; i++) {
            int index = (int) byte2UnsignedInt(Arrays.copyOfRange(contents, start, start + 2 * i));
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
        int length = (int) byte2UnsignedInt(Arrays.copyOfRange(contents, fieldIndexStart, fieldIndexStart + 2));
        List<Field> fields = new ArrayList<>(length);
        int start = fieldIndexStart + 2;
        for (int i = 1; i <= length; i++) {
            Field field = Field.generateField(contents, start, classFile);
            fields.add(field);
            start = field.getEndIndexExclude();
        }
        classFile.setFields(fields);
        return start;
    }

    /**
     * 解析方法
     */
    private int _parseMethod(byte[] contents, int methodIndexStart) {
        int length = (int) byte2UnsignedInt(Arrays.copyOfRange(contents, methodIndexStart, methodIndexStart + 2));
        //List<Method> methods = new ArrayList<>(length);
        Map<Pair<String, String>, Method> methods = new HashMap<>(length);
        int start = methodIndexStart + 2;
        for (int i = 0; i < length; i++) {
            Method method = Method.generateMethod(contents, start, classFile);
            methods.put(Pair.of(method.getName(), method.getDescriptor()), method);
            start = method.getEndIndexExclude();
        }
        classFile.setMethods(methods);
        return start;
    }
}
