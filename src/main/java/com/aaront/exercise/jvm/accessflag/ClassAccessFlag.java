package com.aaront.exercise.jvm.accessflag;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author tonyhui
 * @since 17/6/5
 */
@AllArgsConstructor
@Getter
public enum ClassAccessFlag {
    ACC_PUBLIC(0X0001, "PUBLIC"),
    ACC_FINAL(0x0010, "FINAL"),
    ACC_SUPER(0x0020, "SUPER"),
    ACC_ABSTRACT(0x0400, "ABSTRACT"),
    ACC_SYNTHETIC(0x1000, "SYNTHETIC"),
    ACC_ANNOTATION(0x2000, "ANNOTATION"),
    ACC_ENUM(0x4000, "ENUM");

    private int code;
    private String name;
}
