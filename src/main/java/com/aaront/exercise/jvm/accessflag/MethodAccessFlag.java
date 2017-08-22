package com.aaront.exercise.jvm.accessflag;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author tonyhui
 * @since 17/6/12
 */
@AllArgsConstructor
@Getter
public enum MethodAccessFlag {
    ACC_PUBLIC(0X0001, "PUBLIC"),
    ACC_PRIVATE(0x0002, "PRIVATE"),
    ACC_PROTECTED(0x0004, "PROTECTED"),
    ACC_STATIC(0x0008, "STATIC"),
    ACC_FINAL(0x0010, "FINAL"),
    ACC_SYNCHRONIZED(0x0020, "SYNCHRONIZED"),
    ACC_BRIDGE(0x0040, "BRIDGE"),
    ACC_VARARGS(0x0080, "VARARGS"),
    ACC_NATIVE(0x0100, "NATIVE"),
    ACC_ABSTRACT(0x0400, "ABSTRACT"),
    ACC_STRICT(0x0800, "STRICT"),
    ACC_SYNTHETIC(0x1000, "SYNTHETIC");

    private int code;
    private String name;
}
