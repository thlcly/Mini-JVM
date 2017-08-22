package com.aaront.exercise.jvm.accessflag;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author tonyhui
 * @since 17/6/12
 */
@AllArgsConstructor
@Getter
public enum FieldAccessFlag {
    ACC_PUBLIC(0X0001, "PUBLIC"),
    ACC_PRIVATE(0x0002, "PRIVATE"),
    ACC_PROTECTED(0x0004, "PROTECTED"),
    ACC_STATIC(0x0008, "STATIC"),
    ACC_FINAL(0x0010, "FINAL"),
    ACC_VOLATILE(0x0040, "VOLATILE"),
    ACC_TRANSIENT(0x0080, "TRANSIENT"),
    ACC_SYNTHETIC(0x1000, "SYNTHETIC"),
    ACC_ENUM(0X4000, "ENUM");

    private int code;
    private String name;
}
