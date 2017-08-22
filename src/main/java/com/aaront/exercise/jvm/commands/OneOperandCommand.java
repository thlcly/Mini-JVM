package com.aaront.exercise.jvm.commands;

import com.aaront.exercise.jvm.ClassFile;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author tonyhui
 * @since 17/6/17
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class OneOperandCommand extends AbstractCommand {

    private int operand;

    public OneOperandCommand(ClassFile clzFile, String opCode, int operand) {
        super(clzFile, opCode);
        this.operand = operand;
    }

    @Override
    public int getCommandLength() {
        return 2;
    }
}
