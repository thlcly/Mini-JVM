package com.aaront.exercise.jvm.commands;

import com.aaront.exercise.jvm.ClassFile;
import com.aaront.exercise.jvm.constant.ConstantPool;
import com.aaront.exercise.jvm.engine.ExecutionResult;
import com.aaront.exercise.jvm.engine.Heap;
import com.aaront.exercise.jvm.engine.JavaObject;
import com.aaront.exercise.jvm.engine.StackFrame;


/**
 * @author tonyhui
 * @since 17/6/17
 */
public class BiPushCommand extends OneOperandCommand {
    public BiPushCommand(ClassFile clzFile, String opCode, int operand) {
        super(clzFile, opCode, operand);
    }

    @Override
    public String toString(ConstantPool pool) {
        return this.getOffset() + ": " + this.getOpCode() + " " + this.getReadableCodeText() + " " + this.getOperand();
    }

    /**
     * 将一个 byte 类型数据入栈, 但是这个byte类型要转成int类型
     */
    @Override
    public void execute(StackFrame frame, ExecutionResult result) {
        JavaObject javaObject = Heap.getInstance().newInt(super.getOperand());
        frame.getOperandStack().push(javaObject);
    }
}
