package com.aaront.exercise.jvm.attribute;

import lombok.Data;

/**
 * @author tonyhui
 * @since 17/6/9
 */
@Data
public abstract class AbstractAttribute {
    private int attributeNameIndex;
    private int attributeLength;
}
