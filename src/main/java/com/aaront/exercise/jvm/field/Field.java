package com.aaront.exercise.jvm.field;

import com.aaront.exercise.jvm.accessflag.FieldAccessFlag;
import com.aaront.exercise.jvm.attribute.AbstractAttribute;
import com.aaront.exercise.jvm.constant.ConstantPool;
import com.aaront.exercise.jvm.constant.UTF8Constant;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

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

    private ConstantPool pool;

    public String toString() {
        String name = ((UTF8Constant) pool.getConstantInfo(this.nameIndex)).getValue();

        String desc = ((UTF8Constant) pool.getConstantInfo(this.descriptorIndex)).getValue();
        return name + ":" + desc;
    }
}
