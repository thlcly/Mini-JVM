package com.aaront.exercise.jvm.constant;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author tonyhui
 * @since 17/6/5
 */
@Data
@EqualsAndHashCode(callSuper = true)
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
}
