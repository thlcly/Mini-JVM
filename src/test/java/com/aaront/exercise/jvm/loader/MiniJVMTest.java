package com.aaront.exercise.jvm.loader;

import com.aaront.exercise.jvm.MiniJVM;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class MiniJVMTest {
    private static String PATH1 = "./target/classes";

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testMiniJVM() throws Exception {
        String[] classPaths = {PATH1};
        MiniJVM jvm = new MiniJVM();
        jvm.run(classPaths, "com.aaront.exercise.jvm.EmployeeV1");
    }

}