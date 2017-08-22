package com.aaront.exercise.jvm.commands;

import com.aaront.exercise.jvm.ClassFile;
import com.aaront.exercise.jvm.constant.ConstantPool;
import com.aaront.exercise.jvm.engine.ExecutionResult;
import com.aaront.exercise.jvm.engine.JavaObject;
import com.aaront.exercise.jvm.engine.StackFrame;

/**
 * @author tonyhui
 * @since 2017/8/22
 */
public class LLoadNCommand extends NoOperandCommand {
    int pos;
    public LLoadNCommand(ClassFile clzFile, String opCode, int pos) {
        super(clzFile, opCode);
        this.pos = pos;
    }

    @Override
    public String toString(ConstantPool pool) {
        return this.getOffset()+": "+ this.getOpCode()+" " + this.getReadableCodeText();
    }

    /**
     * 从局部变量表加载一个 long 类型值到操作数栈中, 数据在局部变量表中的位置由pos决定
     */
    @Override
    public void execute(StackFrame frame, ExecutionResult result) {
        JavaObject javaObject = frame.getLocalVariableTable()[this.pos];
        frame.getOperandStack().push(javaObject);
    }
}
