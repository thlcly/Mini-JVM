package com.aaront.exercise.jvm.index;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tonyhui
 * @since 17/6/9
 */
@Data
@AllArgsConstructor
public class InterfaceIndex {
    private List<Integer> interfaceIndexs = new ArrayList<>();
}