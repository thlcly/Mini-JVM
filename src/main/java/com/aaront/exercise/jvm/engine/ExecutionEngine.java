package com.aaront.exercise.jvm.engine;

import com.aaront.exercise.jvm.ClassFile;
import com.aaront.exercise.jvm.commands.AbstractCommand;
import com.aaront.exercise.jvm.method.Method;

import java.util.List;
import java.util.Stack;

/**
 * @author tonyhui
 * @since 17/6/19
 */
public class ExecutionEngine {

    private ClassFile classFile;

    private MethodArea methodArea;

    public ExecutionEngine(ClassFile classFile, MethodArea methodArea) {
        this.classFile = classFile;
        this.methodArea = methodArea;
    }

    public void execute() {
        Method mainMethod = methodArea.queryMainMethod(classFile).orElseThrow(() -> new RuntimeException("没有可执行的main方法"));
        Stack<StackFrame> frames = new Stack<>();
        frames.add(new StackFrame(mainMethod));
        List<AbstractCommand> commands = mainMethod.getCodeAttribute().getCommands();
        StackFrame frame = frames.peek();
        for (int i = frame.getIndex(); i < commands.size(); ) {
            AbstractCommand command = commands.get(i);
            ExecutionResult result = new ExecutionResult();
            command.execute(frame, result);
            if (result.isRunNextCmd()) { // 运行下一条指令
                frame.setIndex(i++);
            } else if (result.isPauseAndRunNewFrame()) { // 调用另一个函数
                // 保存当前函数下一条要执行的命令
                frame.setIndex(i + 1);
                frame = _generateStackFrame(frame, frame.getOperandStack(), result.getNextMethod());
                frames.push(frame);
                commands = frame.getCommands();
                i = frame.getIndex();
            } else if (result.isExitCurrentFrame()) {
                frames.pop();
                if (frames.isEmpty()) return;
                frame = frames.peek();
                commands = frame.getCommands();
                i = frame.getIndex();
            } else if(result.isJump()) {
                frame.setIndex(result.getNextCmdOffset());
                i = result.getNextCmdOffset();
            }
        }
    }

    private StackFrame _generateStackFrame(StackFrame callerFrame, Stack<JavaObject> operandStack, Method method) {
        StackFrame frame = new StackFrame(method);
        frame.setCallerStackFrame(callerFrame);
        int paramSize = method.getParameterList().size() + 1;// 加1是因为还是把对象引用传入(可以理解成把this传入)
        _setParams(operandStack, frame, paramSize);
        return frame;
    }

    private void _setParams(Stack<JavaObject> operandStack, StackFrame frame, Integer size) {
        JavaObject[] params = frame.getLocalVariableTable();
        for(int i = size - 1;i>=0;i--) {
            params[i] = operandStack.pop();
        }
    }
}
