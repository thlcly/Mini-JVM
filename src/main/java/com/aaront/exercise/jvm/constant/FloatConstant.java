package com.aaront.exercise.jvm.constant;

/**
 * @author tonyhui
 * @since 17/6/23
 */
public class FloatConstant extends AbstractConstant {

    private float value;

    public FloatConstant(int tag, ConstantPool pool, float value) {
        super(tag, pool);
        this.value = value;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
