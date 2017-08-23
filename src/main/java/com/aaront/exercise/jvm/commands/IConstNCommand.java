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
public class IConstNCommand extends NoOperandCommand {
    private int constValue;
    public IConstNCommand(ClassFile clzFile, String opCode, int constValue) {
        super(clzFile, opCode);
        this.constValue = constValue;
    }

    @Override
    public String toString(ConstantPool pool) {
        return this.getOffset() + ": " + this.getOpCode() + " " + this.getReadableCodeText();
    }

    /**
     * 将 int 类型数据压入到操作数栈中, 具体压入的值由constValue决定
     */
    @Override
    public void execute(StackFrame frame, ExecutionResult result) {
        JavaObject javaObject = new JavaObject(JavaType.INT);
        javaObject.setIntValue(constValue);
        Stack<JavaObject> operandStack = frame.getOperandStack();
        operandStack.push(javaObject);
    }
}
