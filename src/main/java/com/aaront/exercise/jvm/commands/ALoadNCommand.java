package com.aaront.exercise.jvm.commands;

import com.aaront.exercise.jvm.ClassFile;
import com.aaront.exercise.jvm.constant.ConstantPool;
import com.aaront.exercise.jvm.engine.*;

/**
 * @author tonyhui
 * @since 17/6/17
 */
public class ALoadNCommand extends NoOperandCommand {
    private int pos;

    public ALoadNCommand(ClassFile clzFile, String opCode, int pos) {
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
        return this.getOffset() + ": " + this.getOpCode() + " " + this.getReadableCodeText();
    }

    /**
     * 从局部变量表加载一个 reference 类型值到操作数栈中, 具体加载哪一个由pos决定
     */
    @Override
    public void execute(StackFrame frame, ExecutionResult result) {
        // TODO: 17/6/23 这里要进行数据类型的校验, 必须是reference类型, 不能是基础数据类型, 或者实现自动拆箱和装箱操作
        JavaObject javaObject = frame.getLocalVariableTable()[this.pos];
        frame.getOperandStack().push(javaObject);
    }
}
