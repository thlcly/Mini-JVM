package com.aaront.exercise.jvm.commands;

import com.aaront.exercise.jvm.ClassFile;
import com.aaront.exercise.jvm.constant.ConstantPool;
import com.aaront.exercise.jvm.engine.ExecutionResult;
import com.aaront.exercise.jvm.engine.StackFrame;

import static com.aaront.exercise.jvm.engine.NextAction.EXIT_CURRENT_FRAME;

/**
 * @author tonyhui
 * @since 17/6/17
 */
public class ReturnCommand extends NoOperandCommand {
    public ReturnCommand(ClassFile clzFile, String opCode) {
        super(clzFile, opCode);
    }

    @Override
    public String toString(ConstantPool pool) {
        return this.getOffset()+": "+ this.getOpCode()+" " + this.getReadableCodeText();
    }

    /**
     * 无返回值的方法返回
     */
    // TODO: 17/6/20 如果方法是synchronized类型要进行特殊处理
    @Override
    public void execute(StackFrame frame, ExecutionResult result) {
        result.setNextAction(EXIT_CURRENT_FRAME);
    }
}
