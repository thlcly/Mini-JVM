package com.aaront.exercise.jvm.commands;

import com.aaront.exercise.jvm.ClassFile;
import com.aaront.exercise.jvm.constant.ConstantPool;
import com.aaront.exercise.jvm.engine.ExecutionResult;
import com.aaront.exercise.jvm.engine.JavaObject;
import com.aaront.exercise.jvm.engine.StackFrame;

import java.util.Stack;

/**
 * @author tonyhui
 * @since 17/6/17
 */
public class DupCommand extends NoOperandCommand {
    public DupCommand(ClassFile clzFile, String opCode) {
        super(clzFile, opCode);
    }

    @Override
    public String toString(ConstantPool pool) {
        return this.getOffset() + ": " + this.getOpCode() + " " + this.getReadableCodeText();
    }

    /**
     * 复制操作数栈栈顶的值,并插入到栈顶
     */
    @Override
    public void execute(StackFrame frame, ExecutionResult result) {
        Stack<JavaObject> stack = frame.getOperandStack();
        stack.push(stack.peek());
    }
}
