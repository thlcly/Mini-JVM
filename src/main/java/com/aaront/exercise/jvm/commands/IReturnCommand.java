package com.aaront.exercise.jvm.commands;

import com.aaront.exercise.jvm.ClassFile;
import com.aaront.exercise.jvm.constant.ConstantPool;
import com.aaront.exercise.jvm.engine.ExecutionResult;
import com.aaront.exercise.jvm.engine.JavaObject;
import com.aaront.exercise.jvm.engine.StackFrame;

/**
 * @author tonyhui
 * @since 2017/9/29
 */
public class IReturnCommand extends NoOperandCommand {

    public IReturnCommand(ClassFile clzFile, String opCode) {
        super(clzFile, opCode);
    }

    @Override
    public String toString(ConstantPool pool) {
        return this.getOffset() + ": " + this.getOpCode() + " " + this.getReadableCodeText();
    }

    /**
     * 结束方法，并返回一个 int 类型数. value 将从当前栈帧中出栈，然后压入 到调用者栈帧的操作数栈中
     */
    @Override
    public void execute(StackFrame frame, ExecutionResult result) {
        StackFrame callerFrame = frame.getCallerStackFrame();
        JavaObject jo = frame.getOperandStack().pop();
        callerFrame.getOperandStack().push(jo);
    }
}
