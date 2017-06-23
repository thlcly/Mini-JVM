package com.aaront.exercise.jvm.attribute;

/**
 * @author tonyhui
 * @since 17/6/12
 */
public class ExceptionAttribute extends AbstractAttribute {
    private int startPC;
    private int endPC;
    private int handlerPC;
    private int catchType;

    public ExceptionAttribute(int startPC) {
        this.startPC = startPC;
    }

    public ExceptionAttribute(int startPC, int endPC, int handlerPC, int catchType) {
        this.startPC = startPC;
        this.endPC = endPC;
        this.handlerPC = handlerPC;
        this.catchType = catchType;
    }

    public int getStartPC() {
        return startPC;
    }

    public void setStartPC(int startPC) {
        this.startPC = startPC;
    }

    public int getEndPC() {
        return endPC;
    }

    public void setEndPC(int endPC) {
        this.endPC = endPC;
    }

    public int getHandlerPC() {
        return handlerPC;
    }

    public void setHandlerPC(int handlerPC) {
        this.handlerPC = handlerPC;
    }

    public int getCatchType() {
        return catchType;
    }

    public void setCatchType(int catchType) {
        this.catchType = catchType;
    }
}
