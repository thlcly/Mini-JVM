package com.aaront.exercise.jvm.commands;

import com.aaront.exercise.jvm.ClassFile;
import com.aaront.exercise.jvm.constant.ClassConstant;
import com.aaront.exercise.jvm.constant.ConstantPool;
import com.aaront.exercise.jvm.engine.ExecutionResult;
import com.aaront.exercise.jvm.engine.Heap;
import com.aaront.exercise.jvm.engine.JavaObject;
import com.aaront.exercise.jvm.engine.StackFrame;
import com.aaront.exercise.jvm.utils.string.ByteUtils;

/**
 * @author tonyhui
 * @since 17/6/17
 */
public class NewObjectCommand extends TwoOperandCommand {
    public NewObjectCommand(ClassFile clzFile, String opCode, int operand1, int operand2) {
        super(clzFile, opCode, operand1, operand2);
    }

    @Override
    public String toString(ConstantPool pool) {
        return super.getOperandAsClassInfo(pool);
    }

    /**
     * 创建一个对象, (indexbyte1 << 8) | indexbyte2: 类的运行时常量池索引值
     */
    @Override
    public void execute(StackFrame frame, ExecutionResult result) {
        int classIndex = ByteUtils.byte2Int(new byte[]{(byte) operand1, (byte) operand2});
        ConstantPool pool = frame.getPool();
        ClassConstant constant = (ClassConstant)pool.getConstantInfo(classIndex);
        String className = constant.getClassName();
        JavaObject javaObject = Heap.getInstance().newObject(className);
        frame.getOperandStack().push(javaObject);
    }
}
