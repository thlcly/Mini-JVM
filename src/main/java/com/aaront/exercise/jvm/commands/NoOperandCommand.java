package com.aaront.exercise.jvm.commands;

import com.aaront.exercise.jvm.ClassFile;

/**
 * @author tonyhui
 * @since 17/6/17
 */
public abstract class NoOperandCommand extends AbstractCommand {
    public NoOperandCommand(ClassFile clzFile, String opCode) {
        super(clzFile, opCode);
    }

    @Override
    public int getCommandLength() {
        return 1;
    }
}
