package com.aaront.exercise.jvm.constant;

/**
 * @author tonyhui
 * @since 17/6/5
 */
public abstract class AbstractConstant {
    protected int tag;
    protected ConstantPool pool;

    public AbstractConstant(int tag, ConstantPool pool) {
        this.tag = tag;
        this.pool = pool;
    }

    public ConstantPool getPool() {
        return pool;
    }

    public void setPool(ConstantPool pool) {
        this.pool = pool;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getTag(int tag) {
        return tag;
    }

}
