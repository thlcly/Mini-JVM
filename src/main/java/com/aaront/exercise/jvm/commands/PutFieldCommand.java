package com.aaront.exercise.jvm.commands;

import com.aaront.exercise.jvm.ClassFile;
import com.aaront.exercise.jvm.constant.ConstantPool;
import com.aaront.exercise.jvm.constant.FieldRefConstant;
import com.aaront.exercise.jvm.constant.NameAndTypeConstant;
import com.aaront.exercise.jvm.engine.ExecutionResult;
import com.aaront.exercise.jvm.engine.JavaObject;
import com.aaront.exercise.jvm.engine.StackFrame;
import com.aaront.exercise.jvm.utils.string.ByteUtils;

import java.util.Stack;

/**
 * @author tonyhui
 * @since 17/6/17
 */
public class PutFieldCommand extends TwoOperandCommand {
    public PutFieldCommand(ClassFile clzFile, String opCode, int operand1, int operand2) {
        super(clzFile, opCode, operand1, operand2);
    }

    @Override
    public String toString(ConstantPool pool) {
        return super.getOperandAsField(pool);
    }

    /**
     * 设置对象字段
     */
    @Override
    public void execute(StackFrame frame, ExecutionResult result) {
        // TODO: 17/6/23 需要对字段以及其所属对象进行校验, 比如是否是protected, 是否是final, 是否是父类的成员, 值和字段的类型是否匹配等
        int constantIndex = ByteUtils.byteToInteger(new byte[]{(byte) operand1, (byte) operand2});
        ConstantPool pool = frame.getPool();
        FieldRefConstant fieldRefConstant = (FieldRefConstant)pool.getConstantInfo(constantIndex);
        NameAndTypeConstant nameAndTypeConstant = (NameAndTypeConstant) pool.getConstantInfo(fieldRefConstant.getNameAndTypeIndex());
        Stack<JavaObject> stack = frame.getOperandStack();
        JavaObject value = stack.pop();
        JavaObject objectRef = stack.pop();
        objectRef.setFieldValue(nameAndTypeConstant.getDescriptor(), value);
    }
}
