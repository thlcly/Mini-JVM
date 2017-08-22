package com.aaront.exercise.jvm.constant;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author tonyhui
 * @since 17/6/5
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class NameAndTypeConstant extends AbstractConstant {
    private int nameIndex;
    private String name;
    private int descriptorIndex;
    private String descriptor;

    public NameAndTypeConstant(ConstantPool pool, int tag, int nameIndex, int descriptorIndex) {
        super(tag, pool);
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
    }

    public String getName() {
        if(name == null) name = pool.getUTF8String(nameIndex);
        return name;
    }

    public String getDescriptor() {
        if(descriptor == null) descriptor = pool.getUTF8String(descriptorIndex);
        return descriptor;
    }
}
