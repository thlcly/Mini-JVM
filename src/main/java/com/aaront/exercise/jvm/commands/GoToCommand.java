package com.aaront.exercise.jvm.commands;

import com.aaront.exercise.jvm.ClassFile;
import com.aaront.exercise.jvm.constant.ConstantPool;
import com.aaront.exercise.jvm.engine.ExecutionResult;
import com.aaront.exercise.jvm.engine.NextAction;
import com.aaront.exercise.jvm.engine.StackFrame;
import com.aaront.exercise.jvm.utils.string.ByteUtils;

import java.util.List;

/**
 * @author tonyhui
 * @since 2017/8/23
 */
public class GoToCommand extends TwoOperandCommand {
    public GoToCommand(ClassFile clzFile, String opCode, int operand1, int operand2) {
        super(clzFile, opCode, operand1, operand2);
    }

    @Override
    public String toString(ConstantPool pool) {
        return this.getOffset() + ": " + this.getOpCode() + " " + this.getReadableCodeText();
    }

    /**
     * 分支跳转, 程序将会转到这个 goto 指令之后的, 由上述偏移量(偏移量的计算方式:(operand1 << 8)| operand2)确定的目标地址上继续执行
     */
    @Override
    public void execute(StackFrame frame, ExecutionResult result) {
        int nextPC = ByteUtils.byte2Int(new byte[]{(byte) operand1, (byte) operand2});
        result.setNextAction(NextAction.JUMP);
        result.setNextCmdOffset(_calcNextCmd(nextPC + getOffset(),frame.getCommands()));
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
