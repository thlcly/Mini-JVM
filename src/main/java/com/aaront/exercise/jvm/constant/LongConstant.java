package com.aaront.exercise.jvm.constant;

import com.aaront.exercise.jvm.utils.string.ByteUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author tonyhui
 * @since 2017/8/8
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LongConstant extends AbstractConstant {

    private long high;
    private long low;
    private byte[] highBytes;
    private byte[] lowBytes;
    private long value;

    public LongConstant(int tag, ConstantPool pool, byte[] highContent, byte[] lowContent) {
        super(tag, pool);
        this.highBytes = highContent;
        this.lowBytes = lowContent;
        this.high = ByteUtils.byte2UnsignedInt(this.highBytes);
        this.low = ByteUtils.byte2UnsignedInt(this.lowBytes);
        this.value = (this.high << 32) + this.low;
    }
}
