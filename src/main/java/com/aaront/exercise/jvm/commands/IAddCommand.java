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
public class IAddCommand extends NoOperandCommand{
    public IAddCommand(ClassFile clzFile, String opCode) {
        super(clzFile, opCode);
    }

    @Override
    public String toString(ConstantPool pool) {
        return this.getOffset() + ": " + this.getOpCode() + " " + this.getReadableCodeText();
    }

    /**
     * int 类型数据相加, value1 和 value2 都必须为 int 类型数据, 指令执行时, value1 和 value2 从操作数栈中出栈,
     * 将这两个数值相加得到 int 类型数据 result(result=value1+value2), 最后 result 被压入到操作数栈中
     */
    @Override
    public void execute(StackFrame frame, ExecutionResult result) {
        Stack<JavaObject> operandStack = frame.getOperandStack();
        JavaObject value1 = operandStack.pop();
        JavaObject value2 = operandStack.pop();
        if(value1.getType() != JavaType.INT || value2.getType() != JavaType.INT) throw new RuntimeException("两个操作数必须都是整数");
        int addResult = value1.getIntValue() + value2.getIntValue();
        JavaObject javaObject = new JavaObject(JavaType.INT);
        javaObject.setIntValue(addResult);
        operandStack.push(javaObject);
    }
}
