package com.aaront.exercise.jvm.attribute;

/**
 * @author tonyhui
 * @since 17/6/12
 */
public class LineNumberTable {
    private int startPC;
    private int lineNumber;

    public LineNumberTable() {
    }

    public LineNumberTable(int startPC, int lineNumber) {
        this.startPC = startPC;
        this.lineNumber = lineNumber;
    }

    public int getStartPC() {
        return startPC;
    }

    public void setStartPC(int startPC) {
        this.startPC = startPC;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }
}
