package com.aaront.exercise.jvm.accessflag;

/**
 * @author tonyhui
 * @since 17/6/5
 */
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

    ClassAccessFlag(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
