package com.aaront.exercise.jvm.commands;

import com.aaront.exercise.jvm.ClassFile;
import com.aaront.exercise.jvm.constant.ConstantPool;
import com.aaront.exercise.jvm.engine.ExecutionResult;
import com.aaront.exercise.jvm.engine.JavaObject;
import com.aaront.exercise.jvm.engine.JavaType;
import com.aaront.exercise.jvm.engine.StackFrame;

import java.util.Stack;

/**
 * @author tonyhui
 * @since 2017/8/23
 */
public class IStoreNCommand extends NoOperandCommand {
    private int pos;

    public IStoreNCommand(ClassFile clzFile, String opCode, int pos) {
        super(clzFile, opCode);
        this.pos = pos;
    }

    @Override
    public String toString(ConstantPool pool) {
        return this.getOffset() + ": " + this.getOpCode() + " " + this.getReadableCodeText();
    }


    /**
     * 将一个 int 类型数据保存到局部变量表中, 具体保存到局部变量表的位置由pos决定
     */
    @Override
    public void execute(StackFrame frame, ExecutionResult result) {
        Stack<JavaObject> operandStack = frame.getOperandStack();
        JavaObject javaObject = operandStack.pop();
        if(javaObject.getType() != JavaType.INT) throw new RuntimeException("操作类型不正确");
        frame.getLocalVariableTable()[pos] = javaObject;
    }
}
