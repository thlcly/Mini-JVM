package com.aaront.exercise.jvm.commands;

import com.aaront.exercise.jvm.ClassFile;
import com.aaront.exercise.jvm.constant.AbstractConstant;
import com.aaront.exercise.jvm.constant.ConstantPool;
import com.aaront.exercise.jvm.constant.LongConstant;
import com.aaront.exercise.jvm.engine.ExecutionResult;
import com.aaront.exercise.jvm.engine.JavaObject;
import com.aaront.exercise.jvm.engine.StackFrame;

import static com.aaront.exercise.jvm.engine.JavaType.LONG;

/**
 * @author tonyhui
 * @since 2017/8/22
 */
public class LDC2WCommand extends TwoOperandCommand {

    public LDC2WCommand(ClassFile clzFile, String opCode, int operand1, int operand2) {
        super(clzFile, opCode, operand1, operand2);
    }


    @Override
    public String toString(ConstantPool pool) {
        return super.getOperandAsMethod(pool);
    }

    /**
     * 从运行时常量池中提取 long 或 double 数据推入操作数栈(宽索引)
     */
    @Override
    public void execute(StackFrame frame, ExecutionResult result) {
        AbstractConstant constantInfo = frame.getPool().getConstantInfo(getIndex());
        if(constantInfo instanceof LongConstant) {
            LongConstant longConstant = (LongConstant) constantInfo;
            long value = longConstant.getValue();
            JavaObject javaObject = new JavaObject(LONG);
            javaObject.setLongValue(value);
            frame.getOperandStack().push(javaObject);
        }

        // TODO: 2017/8/22 处理double类型
    }
}
