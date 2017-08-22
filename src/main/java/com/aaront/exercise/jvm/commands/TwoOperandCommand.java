package com.aaront.exercise.jvm.commands;

import com.aaront.exercise.jvm.ClassFile;
import com.aaront.exercise.jvm.constant.ClassConstant;
import com.aaront.exercise.jvm.constant.ConstantPool;
import com.aaront.exercise.jvm.constant.FieldRefConstant;
import com.aaront.exercise.jvm.constant.MethodRefConstant;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author tonyhui
 * @since 17/6/17
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class TwoOperandCommand extends AbstractCommand {

    protected int operand1;
    protected int operand2;

    public TwoOperandCommand(ClassFile clzFile, String opCode, int operand1, int operand2) {
        super(clzFile, opCode);
        this.operand1 = operand1;
        this.operand2 = operand2;
    }

    public int getIndex() {
        return operand1 << 8 | operand2;
    }

    protected String getOperandAsClassInfo(ConstantPool pool) {
        int index = getIndex();
        String codeTxt = getReadableCodeText();
        ClassConstant classConstant = (ClassConstant) pool.getConstantInfo(index);
        return this.getOffset() + ":" + this.getOpCode() + " " + codeTxt + "  " + pool.getUTF8String(classConstant.getNameIndex());
    }

    protected String getOperandAsMethod(ConstantPool pool) {
        int index = getIndex();
        String codeTxt = getReadableCodeText();
        MethodRefConstant info = (MethodRefConstant) this.getConstantInfo(index);
        return this.getOffset() + ":" + this.getOpCode() + " " + codeTxt + "  " + info.toString();
    }

    protected String getOperandAsField(ConstantPool pool) {
        int index = getIndex();
        String codeTxt = getReadableCodeText();
        FieldRefConstant info = (FieldRefConstant) this.getConstantInfo(index);
        return this.getOffset() + ":" + this.getOpCode() + " " + codeTxt + "  " + info.toString();
    }

    @Override
    public int getCommandLength() {
        return 3;
    }
}
