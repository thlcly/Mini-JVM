package com.aaront.exercise.jvm.constant;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author tonyhui
 * @since 17/6/5
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FieldRefConstant extends AbstractConstant {
    private int classIndex;
    private ClassConstant classConstant;
    private int nameAndTypeIndex;
    private NameAndTypeConstant nameAndTypeConstant;

    public FieldRefConstant(ConstantPool pool, int tag, int classIndex, int nameAndTypeIndex) {
        super(tag, pool);
        this.classIndex = classIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    public ClassConstant getClassConstant() {
        if(classConstant == null) classConstant = (ClassConstant) pool.getConstantInfo(classIndex);
        return classConstant;
    }

    public NameAndTypeConstant getNameAndTypeConstant() {
        if(nameAndTypeConstant == null) nameAndTypeConstant = (NameAndTypeConstant) pool.getConstantInfo(nameAndTypeIndex);
        return nameAndTypeConstant;
    }
}
