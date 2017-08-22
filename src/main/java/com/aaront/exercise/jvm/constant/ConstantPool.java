package com.aaront.exercise.jvm.constant;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tonyhui
 * @since 17/6/6
 */
@Data
public class ConstantPool {
    private List<AbstractConstant> abstractConstant = new ArrayList<>();

    public ConstantPool(List<AbstractConstant> abstractConstant) {
        this.abstractConstant = abstractConstant;
    }

    public ConstantPool() {

    }

    public AbstractConstant getConstantInfo(int index) {
        return this.abstractConstant.get(index);
    }

    public String getUTF8String(int index) {
        return ((UTF8Constant) this.abstractConstant.get(index)).getValue();
    }

    public int getSize() {
        return this.abstractConstant.size() - 1;
    }
}
