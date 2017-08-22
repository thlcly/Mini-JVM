package com.aaront.exercise.jvm.index;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author tonyhui
 * @since 17/6/6
 */
@Data
@AllArgsConstructor
public class ClassIndex {
    private int thisClassIndex;
    private int superClassIndex;
}
