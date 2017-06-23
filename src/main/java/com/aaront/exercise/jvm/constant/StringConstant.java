package com.aaront.exercise.jvm.constant;

/**
 * @author tonyhui
 * @since 17/6/5
 */
public class StringConstant extends AbstractConstant {
    private int stringIndex;
    public StringConstant(ConstantPool pool, int tag, int stringIndex) {
        super(tag, pool);
        this.stringIndex = stringIndex;
    }

    public int getStringIndex() {
        return stringIndex;
    }

    public void setStringIndex(int stringIndex) {
        this.stringIndex = stringIndex;
    }
}
