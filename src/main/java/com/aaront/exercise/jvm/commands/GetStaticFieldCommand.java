package com.aaront.exercise.jvm.commands;

import com.aaront.exercise.jvm.ClassFile;
import com.aaront.exercise.jvm.constant.ClassConstant;
import com.aaront.exercise.jvm.constant.ConstantPool;
import com.aaront.exercise.jvm.constant.FieldRefConstant;
import com.aaront.exercise.jvm.engine.ExecutionResult;
import com.aaront.exercise.jvm.engine.Heap;
import com.aaront.exercise.jvm.engine.JavaObject;
import com.aaront.exercise.jvm.engine.StackFrame;

/**
 * @author tonyhui
 * @since 17/6/17
 */
public class GetStaticFieldCommand extends TwoOperandCommand {
    public GetStaticFieldCommand(ClassFile clzFile, String opCode, int operand1, int operand2) {
        super(clzFile, opCode, operand1, operand2);
    }

    @Override
    public String toString(ConstantPool pool) {
        return super.getOperandAsField(pool);
    }

    /**
     * 获取对象的静态字段值
     */
    @Override
    public void execute(StackFrame frame, ExecutionResult result) {
        FieldRefConstant fieldRefConstant = (FieldRefConstant) this.getConstantInfo(this.getIndex());
        ClassConstant classConstant = fieldRefConstant.getClassConstant();
        String className = classConstant.getClassName();
        JavaObject jo = Heap.getInstance().newObject(className);
        frame.getOperandStack().push(jo);
    }
}
