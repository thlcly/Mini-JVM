package com.aaront.exercise.jvm.commands;

import com.aaront.exercise.jvm.ClassFile;
import com.aaront.exercise.jvm.constant.ConstantPool;
import com.aaront.exercise.jvm.engine.*;
import com.aaront.exercise.jvm.utils.string.ByteUtils;

import java.util.List;
import java.util.Stack;

/**
 * @author tonyhui
 * @since 2017/8/23
 */
public class IFICmpGtCommand extends TwoOperandCommand{
    public IFICmpGtCommand(ClassFile clzFile, String opCode, int operand1, int operand2) {
        super(clzFile, opCode, operand1, operand2);
    }

    @Override
    public String toString(ConstantPool pool) {
        return this.getOffset() + ": " + this.getOpCode() + " " + this.getReadableCodeText();
    }

    /**
     * int 数值的条件分支判断, 大于(>)操作
     */
    @Override
    public void execute(StackFrame frame, ExecutionResult result) {
        Stack<JavaObject> operandStack = frame.getOperandStack();
        JavaObject value2 = operandStack.pop();
        JavaObject value1 = operandStack.pop();
        if(value1.getType() != JavaType.INT || value2.getType() != JavaType.INT || value1.getType() != value2.getType()) {
            throw new RuntimeException("两个比较的操作数类型必须是整形");
        }
        if(value1.getIntValue() > value2.getIntValue()) {
            int nextPC = ByteUtils.byte2Int(new byte[]{(byte) operand1, (byte) operand2});
            result.setNextAction(NextAction.JUMP);
            result.setNextCmdOffset(_calcNextCmd(nextPC + getOffset(), frame.getCommands()));
        }
    }

    private int _calcNextCmd(int nextPC, List<AbstractCommand> commands) {
        for(int i = 0;i< commands.size();i++) {
            if(commands.get(i).getOffset() == nextPC) {
                return i;
            }
        }
        throw new RuntimeException("指令跳转不合法");
    }
}
