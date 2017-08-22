package com.aaront.exercise.jvm.engine;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author tonyhui
 * @since 17/6/20
 */
@Getter
@AllArgsConstructor
public enum NextAction {
    /**
     * 只定义了测试程序用到的执行结果, 后序需要补充
     */
    RUN_NEXT_CMD("运行下一个命令", 1),
    JUMP("跳转", 2),
    EXIT_CURRENT_FRAME("退出当前函数栈", 3),
    PAUSE_AND_RUN_NEW_FRAME("暂停并且运行下一个栈帧", 4);

    private String name;
    private int code;
}
