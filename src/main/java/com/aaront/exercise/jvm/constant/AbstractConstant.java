package com.aaront.exercise.jvm.constant;

import lombok.Data;

/**
 * @author tonyhui
 * @since 17/6/5
 */
@Data
public abstract class AbstractConstant {
    protected int tag;
    protected ConstantPool pool;

    public AbstractConstant(int tag, ConstantPool pool) {
        this.tag = tag;
        this.pool = pool;
    }
}
