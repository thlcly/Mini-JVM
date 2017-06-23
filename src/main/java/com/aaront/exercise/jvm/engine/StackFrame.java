package com.aaront.exercise.jvm.engine;

import com.aaront.exercise.jvm.commands.AbstractCommand;
import com.aaront.exercise.jvm.constant.ConstantPool;
import com.aaront.exercise.jvm.method.Method;

import java.util.List;
import java.util.Stack;

/**
 * @author tonyhui
 * @since 17/6/19
 */
public class StackFrame {
    // 下一条指令的位置(偏移量)
    private int index = 0;
    private JavaObject returnValue = null;
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

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public JavaObject getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(JavaObject returnValue) {
        this.returnValue = returnValue;
    }

    public ConstantPool getPool() {
        return pool;
    }

    public void setPool(ConstantPool pool) {
        this.pool = pool;
    }

    public Stack<JavaObject> getOperandStack() {
        return operandStack;
    }

    public void setOperandStack(Stack<JavaObject> operandStack) {
        this.operandStack = operandStack;
    }

    public JavaObject[] getLocalVariableTable() {
        return localVariableTable;
    }

    public void setLocalVariableTable(JavaObject[] localVariableTable) {
        this.localVariableTable = localVariableTable;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public List<AbstractCommand> getCommands() {
        return commands;
    }

    public void setCommands(List<AbstractCommand> commands) {
        this.commands = commands;
    }
}
