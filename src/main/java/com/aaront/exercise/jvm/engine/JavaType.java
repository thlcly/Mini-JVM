package com.aaront.exercise.jvm.engine;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author tonyhui
 * @since 17/6/23
 */
@Getter
@AllArgsConstructor
public enum JavaType {
    /**
     * 只定义了测试程序用到的类型, 后序还要完善
     */
    OBJECT("object", 1),
    STRING("string", 2),
    INT("int", 3),
    FLOAT("float", 4),
    LONG("long", 5);

    private String name;
    private Integer value;
}
