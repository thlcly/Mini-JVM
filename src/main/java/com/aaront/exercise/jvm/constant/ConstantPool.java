package com.aaront.exercise.jvm.constant;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tonyhui
 * @since 17/6/6
 */
public class ConstantPool {
    private List<AbstractConstant> abstractConstant = new ArrayList<>();

    public ConstantPool(List<AbstractConstant> abstractConstant) {
        this.abstractConstant = abstractConstant;
    }

    public ConstantPool() {

    }

    public void addConstantInfo(AbstractConstant info) {
        this.abstractConstant.add(info);
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

    public List<AbstractConstant> getAbstractConstant() {
        return abstractConstant;
    }

    public void setAbstractConstant(List<AbstractConstant> abstractConstant) {
        this.abstractConstant = abstractConstant;
    }
}
