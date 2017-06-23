package com.aaront.exercise.jvm.commands;

import com.aaront.exercise.jvm.ClassFile;

/**
 * @author tonyhui
 * @since 17/6/17
 */
public abstract class OneOperandCommand extends AbstractCommand {

    private int operand;

    public OneOperandCommand(ClassFile clzFile, String opCode, int operand) {
        super(clzFile, opCode);
        this.operand = operand;
    }

    public int getOperand() {
        return operand;
    }

    public void setOperand(int operand) {
        this.operand = operand;
    }

    @Override
    public int getCommandLength() {
        return 2;
    }
}
