package com.aaront.exercise.jvm.engine;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tonyhui
 * @since 17/6/19
 */
public class JavaObject {
    private JavaType type;
    private String className;

    private Map<String, JavaObject> fieldValues = new HashMap<>();

    private String stringValue;

    private int intValue;

    private float floatValue;

    public void setFieldValue(String fieldName, JavaObject fieldValue) {
        fieldValues.put(fieldName, fieldValue);
    }

    public JavaObject(JavaType type) {
        this.type = type;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setStringValue(String value) {
        stringValue = value;
    }

    public String getStringValue() {
        return this.stringValue;
    }

    public void setIntValue(int value) {
        this.intValue = value;
    }

    public int getIntValue() {
        return this.intValue;
    }

    public JavaType getType() {
        return type;
    }

    public JavaObject getFieldValue(String fieldName) {
        return this.fieldValues.get(fieldName);
    }

    public String toString() {
        switch (this.getType()) {
            case INT:
                return String.valueOf(this.intValue);
            case STRING:
                return this.stringValue;
            case OBJECT:
                return this.className + ":" + this.fieldValues;
            case FLOAT:
                return String.valueOf(this.floatValue);
            default:
                return null;
        }
    }

    public String getClassName() {
        return this.className;
    }

    public void setFloatValue(float value) {
        this.floatValue = value;
    }

}
