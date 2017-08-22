package com.aaront.exercise.jvm.constant;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author tonyhui
 * @since 17/6/5
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class StringConstant extends AbstractConstant {
    private int stringIndex;
    public StringConstant(ConstantPool pool, int tag, int stringIndex) {
        super(tag, pool);
        this.stringIndex = stringIndex;
    }
}
