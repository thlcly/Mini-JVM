package com.aaront.exercise.jvm.constant;

/**
 * @author tonyhui
 * @since 17/6/5
 */
public class MethodRefConstant extends AbstractConstant {
    private int classIndex;
    private ClassConstant classConstant;
    private int nameAndTypeIndex;
    private NameAndTypeConstant nameAndTypeConstant;

    public MethodRefConstant(ConstantPool pool, int tag, int classIndex, int nameAndTypeIndex) {
        super(tag, pool);
        this.classIndex = classIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    public int getClassIndex() {
        return classIndex;
    }

    public void setClassIndex(int classIndex) {
        this.classIndex = classIndex;
    }

    public int getNameAndTypeIndex() {
        return nameAndTypeIndex;
    }

    public void setNameAndTypeIndex(int nameAndTypeIndex) {
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    public ClassConstant getClassConstant() {
        if(classConstant == null) {
            classConstant = (ClassConstant) pool.getConstantInfo(classIndex);
        }
        return classConstant;
    }

    public void setClassConstant(ClassConstant classConstant) {
        this.classConstant = classConstant;
    }

    public NameAndTypeConstant getNameAndTypeConstant() {
        if(nameAndTypeConstant == null) {
            nameAndTypeConstant = (NameAndTypeConstant) pool.getConstantInfo(nameAndTypeIndex);
        }
        return nameAndTypeConstant;
    }

    public void setNameAndTypeConstant(NameAndTypeConstant nameAndTypeConstant) {
        this.nameAndTypeConstant = nameAndTypeConstant;
    }

    @Override
    public String toString() {
        return "MethodRefConstant{" +
                "classIndex=" + classIndex +
                ", classConstant=" + classConstant +
                ", nameAndTypeIndex=" + nameAndTypeIndex +
                ", nameAndTypeConstant=" + nameAndTypeConstant +
                '}';
    }
}
