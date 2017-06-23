package com.aaront.exercise.jvm.constant;

/**
 * @author tonyhui
 * @since 17/6/5
 */
public class UTF8Constant extends AbstractConstant {
    // 2个字节长度
    private int length;
    private byte[] content;
    private String value;

    public UTF8Constant(ConstantPool pool, int tag, int length, byte[] content) {
        super(tag, pool);
        this.length = length;
        this.content = content;
        this.value = new String(content);
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
