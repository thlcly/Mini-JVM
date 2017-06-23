package com.aaront.exercise.jvm.commands;

import com.aaront.exercise.jvm.ClassFile;
import com.aaront.exercise.jvm.constant.ConstantPool;
import com.aaront.exercise.jvm.engine.ExecutionResult;
import com.aaront.exercise.jvm.engine.JavaObject;
import com.aaront.exercise.jvm.engine.StackFrame;

/**
 * @author tonyhui
 * @since 17/6/17
 */
public class AStoreNCommand extends NoOperandCommand {
    private int pos;
    public AStoreNCommand(ClassFile clzFile, String opCode, int pos) {
        super(clzFile, opCode);
        this.pos = pos;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    @Override
    public String toString(ConstantPool pool) {
        return this.getOffset()+": "+ this.getOpCode()+" " + this.getReadableCodeText();
    }

    /**
     * 将一个 reference 类型数据保存到局部变量表中, 具体保存的局部变量的位置由pos决定
     */
    @Override
    public void execute(StackFrame frame, ExecutionResult result) {
        JavaObject javaObject = frame.getOperandStack().pop();
        frame.getLocalVariableTable()[pos] = javaObject;
    }
}
