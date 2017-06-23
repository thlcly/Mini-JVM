package com.aaront.exercise.jvm.commands;

import java.util.Arrays;

/**
 * @author tonyhui
 * @since 17/6/17
 */
public enum Command {

    ACONST_NULL("01", "aconst_null"),
    NEW("BB", "new"),
    LSTORE("37", "lstore"),
    INVOKESPECIAL("B7", "invokespecial"),
    INVOKEVIRTUAL("B6", "invokevirtual"),
    GETFIELD("B4", "getfield"),
    PUTFIELD("B5", "putfield"),
    GETSTATIC("B2", "getstatic"),
    ALOAD_0("2A", "aload_0"),
    ALOAD_1("2B", "aload_1"),
    ALOAD_2("2C", "aload_2"),
    ALOAD_3("2D", "aload_3"),
    BIPUSH("10", "bipush"),
    ILOAD("15", "iload"),
    ILOAD_0("1A", "iload_0"),
    ILOAD_1("1B", "iload_1"),
    ILOAD_2("1C", "iload_2"),
    ILOAD_3("1D", "iload_3"),
    FLOAD_3("25", "fload_3"),
    LLOAD_0("1E", "lload_0"),
    FLOAD_2("24", "fload_2"),
    ASTORE_0("4B", "astore_0"),
    ASTORE_1("4C", "astore_1"),
    ASTORE_2("4D", "astore_2"),
    ASTORE_3("4E", "astore_3"),
    IF_ICMP_GE("A2", "if_icmp_ge"),
    IF_ICMPLE("A4", "if_icmple"),
    GOTO("A7", "goto"),
    RETURN("B1", "return"),
    IRETURN("AC", "ireturn"),
    FRETURN("AE", "freturn"),
    ICONST_0("03", "iconst_0"),
    ICONST_1("04", "iconst_1"),
    ISTORE_1("3C", "istore_1"),
    ISTORE_2("3D", "istore_2"),
    DUP("59", "dup"),
    IADD("60", "iadd"),
    IINC("84", "iinc"),
    LDC("12", "ldc");

    private String code;
    private String cmd;

    public static Command byCode(String code) {
        return Arrays.stream(Command.values()).filter(r -> r.getCode().equals(code)).findAny().orElse(null);
    }

    Command(String code, String cmd) {
        this.code = code;
        this.cmd = cmd;
    }

    public String getCode() {
        return code;
    }

    public String getCmd() {
        return cmd;
    }
}
