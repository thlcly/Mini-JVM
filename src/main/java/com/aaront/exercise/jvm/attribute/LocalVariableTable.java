package com.aaront.exercise.jvm.attribute;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author tonyhui
 * @since 17/6/12
 */
@Data
@AllArgsConstructor
public class LocalVariableTable {
    int startPC;     //局部变量的索引都在范围[start_pc,
    int length;        // start_pc+length)中
    int nameIndex;  // 变量名索引  （在常量池中）
    int descriptorIndex;   //变量描述索引（常量池中）
    int index;   //此局部变量在当前栈帧的局部变量表中的索引
}
