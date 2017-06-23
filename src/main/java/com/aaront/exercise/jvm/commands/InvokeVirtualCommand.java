package com.aaront.exercise.jvm.commands;

import com.aaront.exercise.jvm.ClassFile;
import com.aaront.exercise.jvm.constant.ConstantPool;
import com.aaront.exercise.jvm.constant.MethodRefConstant;
import com.aaront.exercise.jvm.engine.ExecutionResult;
import com.aaront.exercise.jvm.engine.JavaObject;
import com.aaront.exercise.jvm.engine.MethodArea;
import com.aaront.exercise.jvm.engine.StackFrame;
import com.aaront.exercise.jvm.method.Method;
import com.aaront.exercise.jvm.utils.string.ByteUtils;

import java.io.IOException;

import static com.aaront.exercise.jvm.engine.NextAction.PAUSE_AND_RUN_NEW_FRAME;

/**
 * @author tonyhui
 * @since 17/6/17
 */
public class InvokeVirtualCommand extends TwoOperandCommand {
    public InvokeVirtualCommand(ClassFile clzFile, String opCode, int operand1, int operand2) {
        super(clzFile, opCode, operand1, operand2);
    }

    @Override
    public String toString(ConstantPool pool) {
        return super.getOperandAsMethod(pool);
    }

    /**
     * 调用实例方法,依据实例的类型进行分派
     */
    @Override
    public void execute(StackFrame frame, ExecutionResult result) {
        // TODO: 17/6/23 要对方法进行校验, 比如是父类方法, 是protected, private等
        int methodIndex = ByteUtils.byteToInteger(new byte[]{(byte) operand1, (byte) operand2});
        MethodRefConstant methodRefConstant = (MethodRefConstant) frame.getPool().getConstantInfo(methodIndex);
        String className = methodRefConstant.getClassConstant().getClassName();
        String methodName = methodRefConstant.getNameAndTypeConstant().getName();

        // TODO: 17/6/23 没有实现System.out.println方法，  所以也不用建立新的栈帧， 直接调用Java的方法， 打印出来即可. 实际的JVM是要实现这个打印等的底层功能的, 因为我的JVM是用java实现的, 所以调用即可, 如果是用c写的就要调用c的打印方法
        if (_isSystemOutPrintlnMethod(className, methodName)) {
            JavaObject jo = frame.getOperandStack().pop();
            String value = jo.toString();
            System.out.println(value);
            // 这里就是那个out对象, 因为是个假的, 直接pop出来
            frame.getOperandStack().pop();
            return;
        }

        try {
            Method method = MethodArea.getInstance().getMethod(methodRefConstant);
            result.setNextMethod(method);
            result.setNextAction(PAUSE_AND_RUN_NEW_FRAME);
        } catch (IOException e) {
            throw new RuntimeException("没有指定的方法, error:" + e);
        }
    }

    private boolean _isSystemOutPrintlnMethod(String className, String methodName) {
        return "java/io/PrintStream".equals(className)
                && "println".equals(methodName);
    }
}
