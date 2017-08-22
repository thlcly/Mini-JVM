package com.aaront.exercise.jvm.constant;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author tonyhui
 * @since 17/6/5
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ClassConstant extends AbstractConstant {
    private int nameIndex;
    private String className;

    public ClassConstant(ConstantPool pool, int tag, int nameIndex) {
        super(tag, pool);
        this.nameIndex = nameIndex;
    }

    public String getClassName() {
        if(className == null) className = pool.getUTF8String(nameIndex);
        return className;
    }
}
