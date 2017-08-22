package com.aaront.exercise.jvm.attribute;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author tonyhui
 * @since 17/6/12
 */
@Data
@AllArgsConstructor
public class LineNumberTable {
    private int startPC;
    private int lineNumber;
}
