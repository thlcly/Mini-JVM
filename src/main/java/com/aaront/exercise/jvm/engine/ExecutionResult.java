package com.aaront.exercise.jvm.engine;

import com.aaront.exercise.jvm.method.Method;
import lombok.Data;

import static com.aaront.exercise.jvm.engine.NextAction.*;

/**
 * @author tonyhui
 * @since 17/6/19
 */
@Data
public class ExecutionResult {

    private NextAction nextAction = RUN_NEXT_CMD;

    private int nextCmdOffset = 0;

    private Method nextMethod;

    public boolean isPauseAndRunNewFrame() {
        return this.nextAction == PAUSE_AND_RUN_NEW_FRAME;
    }

    public boolean isExitCurrentFrame() {
        return this.nextAction == EXIT_CURRENT_FRAME;
    }

    public boolean isRunNextCmd() {
        return this.nextAction == RUN_NEXT_CMD;
    }

    public boolean isJump() {
        return this.nextAction == JUMP;
    }
}
