package com.aaront.exercise.jvm.loader;

import com.aaront.exercise.jvm.ClassFile;
import com.aaront.exercise.jvm.commands.AbstractCommand;
import com.aaront.exercise.jvm.commands.BiPushCommand;
import com.aaront.exercise.jvm.commands.OneOperandCommand;
import com.aaront.exercise.jvm.commands.TwoOperandCommand;
import com.aaront.exercise.jvm.constant.*;
import com.aaront.exercise.jvm.field.Field;
import com.aaront.exercise.jvm.index.ClassIndex;
import com.aaront.exercise.jvm.method.Method;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author tonyhui
 * @since 17/3/31
 */
public class ClassFileLoaderTest {
    private static String path1 = "./target/classes";
    private static final String FULL_QUALIFIED_CLASS_NAME = "com/aaront/exercise/jvm/EmployeeV1";

    private ClassFileLoader loader = null;
    private ClassFile clzFile = null;

    @Before
    public void setUp() throws Exception {
        loader = new ClassFileLoader();
        loader.addClassPath(path1);
        String className = "com.aaront.exercise.jvm.EmployeeV1";
        clzFile = loader.loadClass(className);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testClassPath() {
        loader.addClassPath(path1);
        String clzPath = loader.getClassPath();
        Assert.assertEquals(path1, clzPath);
    }

    @Test
    public void testClassFileLength() throws IOException {
        loader.addClassPath(path1);
        String className = "com.aaront.exercise.jvm.EmployeeV1";
        byte[] byteCodes = loader.readBinaryCode(className);

        // 注意：这个字节数可能和你的JVM版本有关系， 你可以看看编译好的类到底有多大
        Assert.assertEquals(1056, byteCodes.length);
    }


    @Test
    public void testMagicNumber() throws IOException {
        loader.addClassPath(path1);
        String className = "com.aaront.exercise.jvm.EmployeeV1";
        byte[] byteCodes = loader.readBinaryCode(className);
        byte[] codes = new byte[]{byteCodes[0], byteCodes[1], byteCodes[2], byteCodes[3]};
        String acctualValue = this.byteToHexString(codes);

        Assert.assertEquals("cafebabe", acctualValue);
    }

    private String byteToHexString(byte[] codes) {
        StringBuilder buffer = new StringBuilder();
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


    @Test
    public void testVersion() {
        Assert.assertEquals(0, clzFile.getMinorVersion());
        Assert.assertEquals(52, clzFile.getMajorVersion());
    }

    @Test
    public void testConstantPool() {


        ConstantPool pool = clzFile.getConstantPool();

        Assert.assertEquals(53, pool.getSize());

        {
            ClassConstant clzInfo = (ClassConstant) pool.getConstantInfo(7);
            Assert.assertEquals(44, clzInfo.getNameIndex());

            UTF8Constant utf8Info = (UTF8Constant) pool.getConstantInfo(44);
            Assert.assertEquals(FULL_QUALIFIED_CLASS_NAME, utf8Info.getValue());
        }
        {
            ClassConstant clzInfo = (ClassConstant) pool.getConstantInfo(11);
            Assert.assertEquals(48, clzInfo.getNameIndex());

            UTF8Constant utf8Info = (UTF8Constant) pool.getConstantInfo(48);
            Assert.assertEquals("java/lang/Object", utf8Info.getValue());
        }
        {
            UTF8Constant utf8Info = (UTF8Constant) pool.getConstantInfo(12);
            Assert.assertEquals("name", utf8Info.getValue());

            utf8Info = (UTF8Constant) pool.getConstantInfo(13);
            Assert.assertEquals("Ljava/lang/String;", utf8Info.getValue());

            utf8Info = (UTF8Constant) pool.getConstantInfo(14);
            Assert.assertEquals("age", utf8Info.getValue());

            utf8Info = (UTF8Constant) pool.getConstantInfo(15);
            Assert.assertEquals("I", utf8Info.getValue());

            utf8Info = (UTF8Constant) pool.getConstantInfo(16);
            Assert.assertEquals("<init>", utf8Info.getValue());

            utf8Info = (UTF8Constant) pool.getConstantInfo(17);
            Assert.assertEquals("(Ljava/lang/String;I)V", utf8Info.getValue());

            utf8Info = (UTF8Constant) pool.getConstantInfo(18);
            Assert.assertEquals("Code", utf8Info.getValue());
        }

        {
            MethodRefConstant methodRef = (MethodRefConstant) pool.getConstantInfo(1);
            Assert.assertEquals(11, methodRef.getClassIndex());
            Assert.assertEquals(36, methodRef.getNameAndTypeIndex());
        }

        {
            NameAndTypeConstant nameAndType = (NameAndTypeConstant) pool.getConstantInfo(36);
            Assert.assertEquals(16, nameAndType.getNameIndex());
            Assert.assertEquals(28, nameAndType.getDescriptorIndex());
        }
        //抽查几个吧
        {
            MethodRefConstant methodRef = (MethodRefConstant) pool.getConstantInfo(6);
            Assert.assertEquals(42, methodRef.getClassIndex());
            Assert.assertEquals(43, methodRef.getNameAndTypeIndex());
        }

        {
            UTF8Constant utf8Info = (UTF8Constant) pool.getConstantInfo(35);
            Assert.assertEquals("EmployeeV1.java", utf8Info.getValue());
        }
    }

    @Test
    public void testClassIndex() {

        ClassIndex clzIndex = clzFile.getClassIndex();
        ClassConstant thisClassInfo = (ClassConstant) clzFile.getConstantPool().getConstantInfo(clzIndex.getThisClassIndex());
        ClassConstant superClassInfo = (ClassConstant) clzFile.getConstantPool().getConstantInfo(clzIndex.getSuperClassIndex());

        Assert.assertEquals(FULL_QUALIFIED_CLASS_NAME, ((UTF8Constant) clzFile.getConstantPool().getConstantInfo(thisClassInfo.getNameIndex())).getValue());
        Assert.assertEquals("java/lang/Object", ((UTF8Constant) clzFile.getConstantPool().getConstantInfo(superClassInfo.getNameIndex())).getValue());
    }

    @Test
    public void testReadFields() {

        List<Field> fields = clzFile.getFields();
        Assert.assertEquals(2, fields.size());
        {
            Field f = fields.get(0);
            Assert.assertEquals("name:Ljava/lang/String;", f.toString());
        }
        {
            Field f = fields.get(1);
            Assert.assertEquals("age:I", f.toString());
        }
    }

    @Test
    public void testMethods1() {
        Map<Pair<String, String>, Method> methods = clzFile.getMethods();
        Assert.assertEquals(5, methods.size());
    }

    @Test
    public void testMethods2() {
        Map<Pair<String, String>, Method> methods = clzFile.getMethods();
        ConstantPool pool = clzFile.getConstantPool();

        {
            Method m = methods.get(Pair.of("<init>", "(Ljava/lang/String;I)V"));
            assertMethodEquals(pool, m,
                               "<init>",
                               "(Ljava/lang/String;I)V",
                               "2ab700012a2bb500022a1cb50003b1");

        }
        {
            Method m = methods.get(Pair.of("setName", "(Ljava/lang/String;)V"));
            assertMethodEquals(pool, m,
                               "setName",
                               "(Ljava/lang/String;)V",
                               "2a2bb50002b1");

        }
        {
            Method m = methods.get(Pair.of("setAge", "(I)V"));
            assertMethodEquals(pool, m,
                               "setAge",
                               "(I)V",
                               "2a1bb50003b1");
        }
        {
            Method m = methods.get(Pair.of("sayHello", "()V"));
            assertMethodEquals(pool, m,
                               "sayHello",
                               "()V",
                               "b200041205b60006b1");

        }
        {
            Method m = methods.get(Pair.of("main", "([Ljava/lang/String;)V"));
            assertMethodEquals(pool, m,
                               "main",
                               "([Ljava/lang/String;)V",
                               "bb0007591208101db700094c2bb6000ab1");
        }
    }

    private void assertMethodEquals(ConstantPool pool, Method m, String expectedName, String expectedDesc, String expectedCode) {
        String methodName = pool.getUTF8String(m.getNameIndex());
        String methodDesc = pool.getUTF8String(m.getDescriptorIndex());
        String code = m.getCodeAttribute().getCodeText();
        Assert.assertEquals(expectedName, methodName);
        Assert.assertEquals(expectedDesc, methodDesc);
        Assert.assertEquals(expectedCode, code);
    }

    @Test
    public void testByteCodeCommand() {
        {
            //Method initMethod = this.clzFile.getMethod("<init>", "(Ljava/lang/String;I)V");
            Method initMethod = this.clzFile.getMethods().get(Pair.of("<init>", "(Ljava/lang/String;I)V"));
            List<AbstractCommand> cmds = initMethod.getCodeAttribute().getCommands();

            assertOpCodeEquals("0: aload_0", cmds.get(0));
            assertOpCodeEquals("1: invokespecial #1", cmds.get(1));
            assertOpCodeEquals("4: aload_0", cmds.get(2));
            assertOpCodeEquals("5: aload_1", cmds.get(3));
            assertOpCodeEquals("6: putfield #2", cmds.get(4));
            assertOpCodeEquals("9: aload_0", cmds.get(5));
            assertOpCodeEquals("10: iload_2", cmds.get(6));
            assertOpCodeEquals("11: putfield #3", cmds.get(7));
            assertOpCodeEquals("14: return", cmds.get(8));
        }

        {
            //Method setNameMethod = this.clzFile.getMethod("setName", "(Ljava/lang/String;)V");
            Method setNameMethod = this.clzFile.getMethods().get(Pair.of("setName", "(Ljava/lang/String;)V"));
            List<AbstractCommand> cmds = setNameMethod.getCodeAttribute().getCommands();

            assertOpCodeEquals("0: aload_0", cmds.get(0));
            assertOpCodeEquals("1: aload_1", cmds.get(1));
            assertOpCodeEquals("2: putfield #2", cmds.get(2));
            assertOpCodeEquals("5: return", cmds.get(3));

        }

        {
            //Method sayHelloMethod = this.clzFile.getMethod("sayHello", "()V");
            Method sayHelloMethod = this.clzFile.getMethods().get(Pair.of("sayHello", "()V"));
            List<AbstractCommand> cmds = sayHelloMethod.getCodeAttribute().getCommands();

            assertOpCodeEquals("0: getstatic #4", cmds.get(0));
            assertOpCodeEquals("3: ldc #5", cmds.get(1));
            assertOpCodeEquals("5: invokevirtual #6", cmds.get(2));
            assertOpCodeEquals("8: return", cmds.get(3));

        }

        {
            Method mainMethod = this.clzFile.getMethods().get(Pair.of("main", "([Ljava/lang/String;)V"));

            List<AbstractCommand> cmds = mainMethod.getCodeAttribute().getCommands();

            assertOpCodeEquals("0: new #7", cmds.get(0));
            assertOpCodeEquals("3: dup", cmds.get(1));
            assertOpCodeEquals("4: ldc #8", cmds.get(2));
            assertOpCodeEquals("6: bipush 29", cmds.get(3));
            assertOpCodeEquals("8: invokespecial #9", cmds.get(4));
            assertOpCodeEquals("11: astore_1", cmds.get(5));
            assertOpCodeEquals("12: aload_1", cmds.get(6));
            assertOpCodeEquals("13: invokevirtual #10", cmds.get(7));
            assertOpCodeEquals("16: return", cmds.get(8));
        }

    }

    private void assertOpCodeEquals(String expected, AbstractCommand cmd) {

        String acctual = cmd.getOffset() + ": " + cmd.getReadableCodeText();

        if (cmd instanceof OneOperandCommand) {
            if (cmd instanceof BiPushCommand) {
                acctual += " " + ((OneOperandCommand) cmd).getOperand();
            } else {
                acctual += " #" + ((OneOperandCommand) cmd).getOperand();
            }
        }
        if (cmd instanceof TwoOperandCommand) {
            acctual += " #" + ((TwoOperandCommand) cmd).getIndex();
        }
        Assert.assertEquals(expected, acctual);
    }
}
