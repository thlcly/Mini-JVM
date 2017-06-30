package com.aaront.exercise.jvm.utils.string;

/**
 * @author tonyhui
 * @since 17/6/13
 */
public class ByteUtils {

    // TODO: 17/6/30 这两个转换方法后序可以优化
    public static int byte2Int(byte[] content) {
        if(content.length >=4) throw new RuntimeException("长度不能超过4");
        if (content.length == 2)
            return ((content[0] << 8) | (content[1])) & 0x0000ffff;
        return 0;
    }

    public static long byte2UnsingedInt(byte[] content) {
        if(content.length != 4) throw new RuntimeException("长度必须为4");
        return (content[0] << 24 | content[1] << 16 | content[2] << 8 | content[3]) & 0x00000000ffffffffL;
    }

    public static String byteToHexString(byte[] codes) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < codes.length; i++) {
            byte b = codes[i];
            // 因为class文件中的字节是无符号的, 但是byte是有符号的, 所以有可能从class文件中读取的数据赋值给byte之后变成负数
            // 因此为了将其转成无符号数可以将其与0xFF做&运算, 因为0xFF是整形常量, 所以会将结果变成整形, 但是和0xFF做运算不会
            // 改变原有的值, 所以就可以将有符号数变成无符号数
            int value = b & 0xFF;
            String strHex = Integer.toHexString(value);
            if (strHex.length() < 2) {
                strHex = "0" + strHex;
            }
            buffer.append(strHex);
        }
        return buffer.toString();
    }

}
