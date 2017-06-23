package com.aaront.exercise.jvm.index;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tonyhui
 * @since 17/6/9
 */
public class InterfaceIndex {
    private List<Integer> interfaceIndexs = new ArrayList<>();

    public InterfaceIndex() {
    }

    public InterfaceIndex(List<Integer> interfaceIndexs) {
        this.interfaceIndexs = interfaceIndexs;
    }

    public List<Integer> getInterfaceIndexs() {
        return interfaceIndexs;
    }

    public void setInterfaceIndexs(List<Integer> interfaceIndexs) {
        this.interfaceIndexs = interfaceIndexs;
    }
}