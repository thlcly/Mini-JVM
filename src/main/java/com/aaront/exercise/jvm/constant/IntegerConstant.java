package com.aaront.exercise.jvm.constant;

/**
 * @author tonyhui
 * @since 17/6/23
 */
public class IntegerConstant extends AbstractConstant {

    private int value;

    public IntegerConstant(int tag, ConstantPool pool, int value) {
        super(tag, pool);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
