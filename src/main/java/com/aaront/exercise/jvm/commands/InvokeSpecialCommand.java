package com.aaront.exercise.jvm.commands;

import com.aaront.exercise.jvm.ClassFile;
import com.aaront.exercise.jvm.constant.ConstantPool;
import com.aaront.exercise.jvm.constant.MethodRefConstant;
import com.aaront.exercise.jvm.engine.ExecutionResult;
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
public class InvokeSpecialCommand extends TwoOperandCommand {
    public InvokeSpecialCommand(ClassFile clzFile, String opCode, int operand1, int operand2) {
        super(clzFile, opCode, operand1, operand2);
    }

    @Override
    public String toString(ConstantPool pool) {
        return super.getOperandAsMethod(pool);
    }

    /**
     * 调用实例方法;方法类型必须是超类方法、私有方法和实例初始化方法
     */
    @Override
    public void execute(StackFrame frame, ExecutionResult result) {
        // TODO: 17/6/23 要对方法的类型进行校验
        int methodIndex = (int)ByteUtils.byte2UnsignedInt(new byte[]{(byte) operand1, (byte) operand2});
        MethodRefConstant methodRefConstant = (MethodRefConstant) frame.getPool().getConstantInfo(methodIndex);
        if(methodRefConstant.getClassConstant().getClassName().equals("java/lang/Object")) {
            // TODO: 17/6/23 这里不处理Object的构造方法, 因为程序现在不会去扫描系统PATH路径下class path, 所以是找不到Ojbect类的, 因此从这里也可以知道为什么要把class path配置到系统的PATH目录中了
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
}
