package com.aaront.exercise.jvm.attribute;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author tonyhui
 * @since 17/6/12
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ExceptionAttribute extends AbstractAttribute {
    private int startPC;
    private int endPC;
    private int handlerPC;
    private int catchType;
}
