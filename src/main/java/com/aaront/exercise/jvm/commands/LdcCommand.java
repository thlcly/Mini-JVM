package com.aaront.exercise.jvm.commands;

import com.aaront.exercise.jvm.ClassFile;
import com.aaront.exercise.jvm.constant.*;
import com.aaront.exercise.jvm.engine.*;

/**
 * @author tonyhui
 * @since 17/6/17
 */
public class LdcCommand extends OneOperandCommand {
    public LdcCommand(ClassFile clzFile, String opCode, int operand) {
        super(clzFile, opCode, operand);
    }

    @Override
    public String toString(ConstantPool pool) {
        return this.getOffset() + ": " + this.getOpCode() + " " + this.getReadableCodeText() + " " + this.getOperand();
    }

    /**
     * 从运行时常量池中提取数据推入操作数栈, index 指向的运行时常量池项必须是int, float, 类的符号引用, 字符串字面量
     */
    @Override
    public void execute(StackFrame frame, ExecutionResult result) {
        ConstantPool pool = frame.getPool();
        AbstractConstant constant = pool.getConstantInfo(super.getOperand());
        if (constant instanceof StringConstant) {
            StringConstant stringConstant = (StringConstant) constant;
            String value = pool.getUTF8String(stringConstant.getStringIndex());
            JavaObject jo = Heap.getInstance().newString(value);
            frame.getOperandStack().push(jo);
        } else if (constant instanceof IntegerConstant) {
            IntegerConstant integerConstant = (IntegerConstant) constant;
            JavaObject javaObject = Heap.getInstance().newInt(integerConstant.getValue());
            frame.getOperandStack().push(javaObject);
        } else if (constant instanceof FloatConstant) {
            FloatConstant floatConstant = (FloatConstant) constant;
            JavaObject javaObject = Heap.getInstance().newFloat(floatConstant.getValue());
            frame.getOperandStack().push(javaObject);
        } else if (constant instanceof ClassConstant) {
            ClassConstant classConstant = (ClassConstant) constant;
            JavaObject javaObject = Heap.getInstance().newObject(classConstant.getClassName());
            frame.getOperandStack().push(javaObject);
        } else {
            throw new RuntimeException("不支持的操作数类型");
        }
    }
}
