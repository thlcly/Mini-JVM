package com.aaront.exercise.jvm.engine;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tonyhui
 * @since 17/6/19
 */
@Data
public class JavaObject {
    private JavaType type;
    private String className;

    private Map<String, JavaObject> fieldValues = new HashMap<>();

    private String stringValue;

    private int intValue;

    private float floatValue;

    private long longValue;

    public void setFieldValue(String fieldName, JavaObject fieldValue) {
        fieldValues.put(fieldName, fieldValue);
    }

    public JavaObject(JavaType type) {
        this.type = type;
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
            case LONG:
                return String.valueOf(this.longValue);
            default:
                return null;
        }
    }
}
