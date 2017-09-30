package com.aaront.exercise.jvm.engine;

import com.aaront.exercise.jvm.commands.AbstractCommand;
import com.aaront.exercise.jvm.constant.ConstantPool;
import com.aaront.exercise.jvm.method.Method;
import lombok.Data;

import java.util.List;
import java.util.Stack;

/**
 * @author tonyhui
 * @since 17/6/19
 */
@Data
public class StackFrame {
    // 下一条指令的位置(偏移量)
    private int index = 0;
    private JavaObject returnValue = null;
    private StackFrame callerStackFrame;
    private ConstantPool pool;
    private Stack<JavaObject> operandStack;
    //这里最好是用数据来保存, 如果使用List会很不方便, 因为一些指令会对localVariableTable指定位置设置值, 如果使用List且List的size<所要设置的位置会报错
    private JavaObject[] localVariableTable;
    private Method method;
    private List<AbstractCommand> commands;

    public StackFrame(Method method) {
        this.method = method;
        this.commands = method.getCodeAttribute().getCommands();
        this.pool = method.getPool();
        this.operandStack = new Stack<>();
        this.localVariableTable = new JavaObject[method.getCodeAttribute().getMaxLocals()];
    }
}
