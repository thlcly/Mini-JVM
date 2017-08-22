package com.aaront.exercise.jvm.method;

import com.aaront.exercise.jvm.ClassFile;
import com.aaront.exercise.jvm.accessflag.MethodAccessFlag;
import com.aaront.exercise.jvm.attribute.AbstractAttribute;
import com.aaront.exercise.jvm.attribute.CodeAttribute;
import com.aaront.exercise.jvm.constant.ConstantPool;
import lombok.Data;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.aaront.exercise.jvm.utils.string.ByteUtils.byte2UnsignedInt;

/**
 * @author tonyhui
 * @since 17/6/12
 */
@Data
public class Method {
    private int accessFlag;
    private List<MethodAccessFlag> accessFlags = new ArrayList<>();
    private int nameIndex;
    private String name;
    private int descriptorIndex;
    private String descriptor;
    private int attributesCount;
    private CodeAttribute codeAttribute;
    private List<AbstractAttribute> attributes = new ArrayList<>();
    private ConstantPool pool;
    private int startIndexInclude;
    private int endIndexExclude;

    public Method(int accessFlag, List<MethodAccessFlag> accessFlags, int nameIndex, String name, int descriptorIndex, String descriptor, int attributesCount, List<AbstractAttribute> attributes, ConstantPool pool, int startIndexInclude, int endIndexExclude) {
        this.accessFlag = accessFlag;
        this.accessFlags = accessFlags;
        this.nameIndex = nameIndex;
        this.name = name;
        this.descriptorIndex = descriptorIndex;
        this.descriptor = descriptor;
        this.attributesCount = attributesCount;
        this.attributes = attributes;
        this.codeAttribute = (CodeAttribute) this.attributes.get(0);
        this.pool = pool;
        this.startIndexInclude = startIndexInclude;
        this.endIndexExclude = endIndexExclude;
    }

    public static Method generateMethod(byte[] content, int start, ClassFile classFile) {
        ConstantPool pool = classFile.getConstantPool();
        int accessFlag = (int) byte2UnsignedInt(Arrays.copyOfRange(content, start, start + 2));
        List<MethodAccessFlag> methodAccessFlags = _parseMethodAccessFlag(accessFlag);
        int nameIndex = (int) byte2UnsignedInt(Arrays.copyOfRange(content, start + 2, start + 4));
        String name = pool.getUTF8String(nameIndex);
        int descriptorIndex = (int) byte2UnsignedInt(Arrays.copyOfRange(content, start + 4, start + 6));
        String descriptor = pool.getUTF8String(descriptorIndex);
        int attributesCount = (int) byte2UnsignedInt(Arrays.copyOfRange(content, start + 6, start + 8));
        Pair<List<AbstractAttribute>, Integer> pair = _parseMethodAttribute(content, start + 8, attributesCount, classFile);
        return new Method(accessFlag, methodAccessFlags, nameIndex, name, descriptorIndex, descriptor, attributesCount, pair.getLeft(), pool, start, pair.getRight());
    }

    /**
     * 解析方法修饰符
     */
    private static List<MethodAccessFlag> _parseMethodAccessFlag(int accessFlag) {
        List<MethodAccessFlag> methodAccessFlags = new ArrayList<>();
        if ((accessFlag & MethodAccessFlag.ACC_PUBLIC.getCode()) != 0) {
            methodAccessFlags.add(MethodAccessFlag.ACC_PUBLIC);
        }
        if ((accessFlag & MethodAccessFlag.ACC_PRIVATE.getCode()) != 0) {
            methodAccessFlags.add(MethodAccessFlag.ACC_PRIVATE);
        }
        if ((accessFlag & MethodAccessFlag.ACC_PROTECTED.getCode()) != 0) {
            methodAccessFlags.add(MethodAccessFlag.ACC_PROTECTED);
        }
        if ((accessFlag & MethodAccessFlag.ACC_STATIC.getCode()) != 0) {
            methodAccessFlags.add(MethodAccessFlag.ACC_STATIC);
        }
        if ((accessFlag & MethodAccessFlag.ACC_FINAL.getCode()) != 0) {
            methodAccessFlags.add(MethodAccessFlag.ACC_FINAL);
        }
        if ((accessFlag & MethodAccessFlag.ACC_SYNCHRONIZED.getCode()) != 0) {
            methodAccessFlags.add(MethodAccessFlag.ACC_SYNCHRONIZED);
        }
        if ((accessFlag & MethodAccessFlag.ACC_BRIDGE.getCode()) != 0) {
            methodAccessFlags.add(MethodAccessFlag.ACC_BRIDGE);
        }
        if ((accessFlag & MethodAccessFlag.ACC_VARARGS.getCode()) != 0) {
            methodAccessFlags.add(MethodAccessFlag.ACC_VARARGS);
        }
        if ((accessFlag & MethodAccessFlag.ACC_NATIVE.getCode()) != 0) {
            methodAccessFlags.add(MethodAccessFlag.ACC_NATIVE);
        }
        if ((accessFlag & MethodAccessFlag.ACC_ABSTRACT.getCode()) != 0) {
            methodAccessFlags.add(MethodAccessFlag.ACC_ABSTRACT);
        }
        if ((accessFlag & MethodAccessFlag.ACC_STRICT.getCode()) != 0) {
            methodAccessFlags.add(MethodAccessFlag.ACC_STRICT);
        }
        if ((accessFlag & MethodAccessFlag.ACC_SYNTHETIC.getCode()) != 0) {
            methodAccessFlags.add(MethodAccessFlag.ACC_SYNTHETIC);
        }
        return methodAccessFlags;
    }

    /**
     * 解析方法属性
     */
    private static Pair<List<AbstractAttribute>, Integer> _parseMethodAttribute(byte[] content, int start, int count, ClassFile classFile) {
        List<AbstractAttribute> attributes = new ArrayList<>();
        ConstantPool pool = classFile.getConstantPool();
        for (int i = 1; i <= count; i++) {
            int index = (int) byte2UnsignedInt(Arrays.copyOfRange(content, start, start + 2));
            // TODO: 17/6/30 这里先暂时强转, 后序数组拷贝要优化, 要支持long型的
            int length = (int) byte2UnsignedInt(Arrays.copyOfRange(content, start + 2, start + 6));
            String attributeName = pool.getUTF8String(index);
            if (attributeName.equals("Code")) {
                CodeAttribute codeAttribute = CodeAttribute.generateCodeAttribute(Arrays.copyOfRange(content, start + 6, start + 6 + length), index, length, classFile);
                attributes.add(codeAttribute);
            }
            start = start + 6 + length;
        }
        return Pair.of(attributes, start);
    }

    public List<String> getParameterList() {

        // e.g. (Ljava/util/List;Ljava/lang/String;II)V
        String paramAndType = getDescriptor();

        int first = paramAndType.indexOf("(") + 1;
        int last = paramAndType.lastIndexOf(")");
        // e.g. Ljava/util/List;Ljava/lang/String;II
        String param = paramAndType.substring(first, last);

        List<String> paramList = new ArrayList<>();

        if ("".equals(param)) {
            return paramList;
        }

        while (!param.equals("")) {
            int pos = 0;
            // 这是一个对象类型
            if (param.charAt(pos) == 'L') {
                int end = param.indexOf(";");
                if (end == -1) {
                    throw new RuntimeException("不能找到对象的类型");
                }
                paramList.add(param.substring(pos + 1, end));
                pos = end + 1;
            } else if (param.charAt(pos) == 'I') {
                // int
                paramList.add("I");
                pos++;

            } else if (param.charAt(pos) == 'F') {
                // float
                paramList.add("F");
                pos++;

            } else if (param.charAt(pos) == 'J') {
                // long
                paramList.add("J");
                pos++;
            }else {
                // TODO: 17/6/23 后序还有添加java提供的其他基本数据类型
                throw new RuntimeException("the param has unsupported type:" + param);
            }
            param = param.substring(pos);
        }
        return paramList;
    }
}
