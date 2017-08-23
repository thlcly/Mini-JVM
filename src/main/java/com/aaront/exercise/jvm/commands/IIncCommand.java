package com.aaront.exercise.jvm.commands;

import com.aaront.exercise.jvm.ClassFile;
import com.aaront.exercise.jvm.constant.ConstantPool;
import com.aaront.exercise.jvm.engine.ExecutionResult;
import com.aaront.exercise.jvm.engine.JavaObject;
import com.aaront.exercise.jvm.engine.JavaType;
import com.aaront.exercise.jvm.engine.StackFrame;
import com.aaront.exercise.jvm.utils.string.ByteUtils;

/**
 * @author tonyhui
 * @since 2017/8/23
 */
public class IIncCommand extends TwoOperandCommand {
    public IIncCommand(ClassFile clzFile, String opCode, int operand1, int operand2) {
        super(clzFile, opCode, operand1, operand2);
    }

    @Override
    public String toString(ConstantPool pool) {
        return this.getOffset() + ": " + this.getOpCode() + " " + this.getReadableCodeText();
    }

    /**
     * 局部变量自增
     */
    @Override
    public void execute(StackFrame frame, ExecutionResult result) {
        JavaObject[] localVariableTable = frame.getLocalVariableTable();
        JavaObject origin = localVariableTable[operand1];
        if (origin.getType() != JavaType.INT) throw new RuntimeException("操作数必须是整形");
        int addend = ByteUtils.byte2Int(new byte[]{(byte) operand2});
        origin.setIntValue(origin.getIntValue() + addend);
        localVariableTable[operand1] = origin;
    }
}
