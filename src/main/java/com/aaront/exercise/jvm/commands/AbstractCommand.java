package com.aaront.exercise.jvm.commands;

import com.aaront.exercise.jvm.ClassFile;
import com.aaront.exercise.jvm.constant.AbstractConstant;
import com.aaront.exercise.jvm.constant.ConstantPool;
import com.aaront.exercise.jvm.engine.ExecutionResult;
import com.aaront.exercise.jvm.engine.StackFrame;

import java.util.Optional;

/**
 * @author tonyhui
 * @since 17/6/17
 */
public abstract class AbstractCommand {

    private String opCode;
    private ClassFile clzFile;
    private int offset;

    protected AbstractCommand(ClassFile clzFile, String opCode) {
        this.clzFile = clzFile;
        this.opCode = opCode;
    }

    protected ClassFile getClassFile() {
        return clzFile;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    protected AbstractConstant getConstantInfo(int index) {
        return this.getClassFile().getConstantPool().getConstantInfo(index);
    }

    protected ConstantPool getConstantPool() {
        return this.getClassFile().getConstantPool();
    }


    public String getOpCode() {
        return opCode;
    }

    public abstract int getCommandLength();


    public String toString() {
        return this.opCode;
    }

    public abstract String toString(ConstantPool pool);

    public String getReadableCodeText() {
        String cmd = Command.byCode(opCode).getCmd();
        return Optional.ofNullable(cmd).orElse(opCode);
    }

    //  执行指令
    public abstract void execute(StackFrame frame, ExecutionResult result);
}
