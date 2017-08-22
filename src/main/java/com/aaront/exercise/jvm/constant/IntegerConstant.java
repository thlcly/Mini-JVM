package com.aaront.exercise.jvm.constant;

import com.aaront.exercise.jvm.utils.string.ByteUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author tonyhui
 * @since 17/6/23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class IntegerConstant extends AbstractConstant {

    private int value;
    private byte[] content;

    public IntegerConstant(int tag, ConstantPool pool, byte[] content) {
        super(tag, pool);
        this.content = content;
        this.value = ByteUtils.byte2Int(this.content);
    }
}
