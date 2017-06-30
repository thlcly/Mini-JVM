package com.aaront.exercise.jvm.commands;

import com.aaront.exercise.jvm.ClassFile;
import com.aaront.exercise.jvm.constant.ConstantPool;
import com.aaront.exercise.jvm.constant.FieldRefConstant;
import com.aaront.exercise.jvm.engine.ExecutionResult;
import com.aaront.exercise.jvm.engine.JavaObject;
import com.aaront.exercise.jvm.engine.StackFrame;
import com.aaront.exercise.jvm.utils.string.ByteUtils;

/**
 * @author tonyhui
 * @since 17/6/17
 */
public class GetFieldCommand extends TwoOperandCommand {
    public GetFieldCommand(ClassFile clzFile, String opCode, int operand1, int operand2) {
        super(clzFile, opCode, operand1, operand2);
    }

    @Override
    public String toString(ConstantPool pool) {
        return super.getOperandAsField(pool);
    }

    /**
     * 获取对象的字段值
     */
    @Override
    public void execute(StackFrame frame, ExecutionResult result) {
        // TODO: 17/6/23 要对字段类型进行校验
        int fieldIndex = ByteUtils.byte2Int(new byte[]{(byte) operand1, (byte) operand2});
        FieldRefConstant fieldRefConstant = (FieldRefConstant) frame.getPool().getConstantInfo(fieldIndex);
        JavaObject javaObject = frame.getOperandStack().peek();
        JavaObject fieldValue = javaObject.getFieldValue(fieldRefConstant.getNameAndTypeConstant().getDescriptor());
        frame.getOperandStack().push(fieldValue);
    }
}
