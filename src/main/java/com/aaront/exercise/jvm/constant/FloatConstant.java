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
public class FloatConstant extends AbstractConstant {

    private float value;
    private byte[] content;

    public FloatConstant(int tag, ConstantPool pool, byte[] content) {
        super(tag, pool);
        this.content = content;
        this.value = _toFloat(this.content);
    }

    private float _toFloat(byte[] content) {
        int bits = ByteUtils.byte2Int(content);
        if (bits == 0x7f800000) return Float.MAX_VALUE;
        if (bits == 0xff800000) return Float.MIN_VALUE;
        if (bits >= 0x7f800001 && bits <= 0x7fffffff || bits >= 0xff800001 && bits <= 0xffffffff) return Float.NaN;
        int s = ((bits >> 31) == 0) ? 1 : -1;
        int e = ((bits >> 23) & 0xff);
        int m = (e == 0) ?
                (bits & 0x7fffff) << 1 : (bits & 0x7fffff) | 0x800000;
        return s * m * 2 << (e - 150);
    }
}
